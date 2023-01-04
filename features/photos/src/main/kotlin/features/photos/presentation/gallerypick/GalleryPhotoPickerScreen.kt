package features.photos.presentation.gallerypick

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.AsyncImage
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.EmptyState
import platform.uicomponents.components.PreviewContainer
import platform.uicomponents.components.errorview.DefaultErrorView
import platform.uicomponents.components.errorview.DefaultErrorViewButton
import platform.uicomponents.components.errorview.DefaultErrorViewOptions
import platform.uicomponents.components.spacers.HorizontalSpacer
import platform.uicomponents.components.spacers.VerticalSpacer
import platform.uistate.uistate.UiStateContent

private const val MaxSelectionSize = 20

@Suppress("UnusedPrivateMember")
internal data class GalleryPhotoPickerScreen(
    private val albumInt: Int
) : AndroidScreen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getViewModel<GalleryPhotoPickerViewModel>()
        val state by viewModel.collectUiState()

        state.UiStateContent(
            stateContent = {
                StateContent(state = it, handleIntent = viewModel::handleIntent)
            },
            errorState = {
                DefaultErrorView(
                    error = it,
                    options = DefaultErrorViewOptions(
                        primaryButton = DefaultErrorViewButton(
                            title = "Go back to home",
                            onClick = { navigator?.pop() }
                        ),
                        secondaryButton = null
                    )
                )
            }
        )

    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    fun StateContent(
        state: GalleryPhotoPickerState,
        handleIntent: (GalleryPhotoPickerIntent) -> Unit
    ) {
        val navigator = LocalNavigator.current
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(MaxSelectionSize),
            onResult = { imageUri ->
                imageUri.let {
                    handleIntent(GalleryPhotoPickerIntent.AddPhoto(it))
                }
            }
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Pick Photos from gallery") },
                    navigationIcon = {
                        IconButton(onClick = { navigator?.pop() }) {
                            Icon(imageVector = Icons.Outlined.ArrowBack, null)
                        }
                    }
                )
            },
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = MviSampleSizes.medium)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MviSampleSizes.medium)
                ) {
                    Button(
                        onClick = {
                            launcher.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Open Gallery")
                            HorizontalSpacer(width = MviSampleSizes.xSmall3)
                            Icon(imageVector = Icons.Outlined.Add, null)
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        enabled = state.photos.isNotEmpty()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Finish")
                            HorizontalSpacer(width = MviSampleSizes.xSmall3)
                            Icon(imageVector = Icons.Outlined.Check, null)
                        }
                    }
                }

                VerticalSpacer(height = MviSampleSizes.medium)

                if (state.photos.isNotEmpty()) {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(MviSampleSizes.xSmall),
                        verticalArrangement = Arrangement.spacedBy(MviSampleSizes.xSmall),
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .weight(1f)
                    ) {
                        items(state.photos) { Uri ->
                            AsyncImage(
                                model = Uri,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center,
                            )
                        }
                        item {
                            VerticalSpacer(height = MviSampleSizes.medium)
                        }
                    }
                } else {
                    EmptyState(
                        modifier = Modifier.weight(1f),
                        title = "Nothing to see here",
                        description = "Add photos from your gallery to have a preview of which" +
                                " photos will be add to this album"
                    )
                }
                VerticalSpacer(height = MviSampleSizes.medium)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PreviewContainer {
        GalleryPhotoPickerScreen(1).Content()
    }
}

