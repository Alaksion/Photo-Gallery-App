package features.photos.detail.presentation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import platform.uicomponents.components.errorview.DefaultErrorView
import platform.uicomponents.components.errorview.DefaultErrorViewButton
import platform.uicomponents.components.errorview.DefaultErrorViewOptions
import platform.uistate.uistate.UiStateContent
import platform.uistate.uistate.collectState

internal data class PhotoDetailsScreen(
    private val photoId: Int
) : AndroidScreen() {

    @Composable
    override fun Content() {
        val model = getViewModel<PhotoDetailsViewModel>()
        val state by model.collectState()
        val navigator = LocalNavigator.current

        LaunchedEffect(key1 = model) {
            model.handleIntent(PhotoDetailsIntent.LoadData(photoId))
        }

        state.UiStateContent(
            stateContent = {
                StateContent(
                    state = it,
                    goBack = { navigator?.pop() }
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StateContent(
        state: PhotoDetailsState,
        goBack: () -> Unit
    ) {
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
                    }
                )
            }
        ) {
            Column(Modifier.padding(it)) {
                AsyncImage(
                    model = state.photo.location,
                    contentDescription = null,
                    onState = { state ->
                        when (state) {
                            is AsyncImagePainter.State.Error -> {
                                Log.d("ImageError", state.result.throwable.toString())
                            }

                            else -> Unit
                        }
                    }
                )
            }
        }
    }

}
