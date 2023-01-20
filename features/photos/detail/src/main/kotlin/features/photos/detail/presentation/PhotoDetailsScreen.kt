package features.photos.detail.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.AsyncImage
import features.photos.detail.presentation.components.DeleteConfirmationDialog
import kotlinx.coroutines.launch
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.errorview.DefaultErrorView
import platform.uicomponents.components.errorview.DefaultErrorViewButton
import platform.uicomponents.components.errorview.DefaultErrorViewOptions
import platform.uicomponents.components.spacers.WeightSpacer
import platform.uicomponents.extensions.UiStateContent
import platform.uicomponents.sideeffects.UiEventEffect

internal data class PhotoDetailsScreen(
    private val photoId: Int
) : AndroidScreen() {

    @Composable
    override fun Content() {
        val model = getViewModel<PhotoDetailsViewModel>()
        val state by model.state.collectAsState()
        val navigator = LocalNavigator.current
        val context = LocalContext.current

        LaunchedEffect(key1 = model) {
            model.handleIntent(PhotoDetailsIntent.LoadData(photoId))
        }

        UiEventEffect(eventHandler = model) { event ->
            when (event) {
                PhotoDetailsEvents.DeleteSuccess -> {
                    Toast.makeText(context, "Photo deleted successfuly", Toast.LENGTH_SHORT).show()
                    navigator?.pop()
                }

                is PhotoDetailsEvents.DeleteError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        state.UiStateContent(
            stateContent = {
                StateContent(
                    state = it,
                    goBack = { navigator?.pop() },
                    handleIntent = model::handleIntent
                )
            },
            errorState = {
                DefaultErrorView(
                    error = it,
                    options = DefaultErrorViewOptions(
                        primaryButton = DefaultErrorViewButton(
                            title = "Try Again",
                            onClick = {
                                model.handleIntent(PhotoDetailsIntent.LoadData(photoId))
                            }
                        ),
                        secondaryButton = DefaultErrorViewButton(
                            title = "Return to Home",
                            onClick = {
                                navigator?.popUntilRoot()
                            }
                        )
                    )
                )
            }
        )

    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    @Composable
    fun StateContent(
        state: PhotoDetailsState,
        handleIntent: (PhotoDetailsIntent) -> Unit,
        goBack: () -> Unit,
    ) {
        val scope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

        ModalBottomSheetLayout(
            sheetContent = {
                DeleteConfirmationDialog(
                    onClickCancel = { scope.launch { sheetState.hide() } },
                    onClickConfirm = { handleIntent(PhotoDetailsIntent.DeletePhoto) }
                )
            },
            content = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {},
                            navigationIcon = {
                                IconButton(onClick = goBack) {
                                    Icon(
                                        imageVector = Icons.Outlined.ArrowBack,
                                        null
                                    )
                                }
                            },
                        )
                    },
                ) {
                    Box(Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize(),
                        ) {
                            WeightSpacer(weight = 1F)
                            AsyncImage(
                                model = state.photo.location,
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.FillWidth,
                                alignment = Alignment.Center
                            )
                            WeightSpacer(weight = 1F)
                        }
                        Column(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize(),
                        ) {
                            WeightSpacer(weight = 1f)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                                    )
                                    .padding(MviSampleSizes.large),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { scope.launch { sheetState.show() } }) {
                                    Icon(
                                        Icons.Outlined.Delete,
                                        null,
                                        tint = MaterialTheme.colorScheme.background
                                    )
                                }
                            }
                        }
                    }
                }
            },
            sheetShape = MaterialTheme.shapes.medium,
            sheetState = sheetState
        )
    }

}
