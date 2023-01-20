package features.albums.details.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import features.albums.details.presentation.components.DeleteBottomSheet
import kotlinx.coroutines.launch
import platform.database.models.models.photo.PhotoModel
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.EmptyState
import platform.uicomponents.components.PreviewContainer
import platform.uicomponents.components.errorview.DefaultErrorView
import platform.uicomponents.components.errorview.DefaultErrorViewButton
import platform.uicomponents.components.errorview.DefaultErrorViewOptions
import platform.uicomponents.components.spacers.VerticalSpacer
import platform.uicomponents.extensions.header
import platform.uicomponents.extensions.showToast
import platform.uicomponents.sideeffects.UiEventEffect
import platform.uistate.uistate.UiStateContent

private const val GridCellsCount = 2

internal data class AlbumDetailsScreen(
    private val albumId: Int
) : AndroidScreen() {

    @Composable
    override fun Content() {
        val model = getViewModel<AlbumDetailsViewModel>()
        val state by model.uiState.collectAsState()
        val router = AlbumDetailsRouter.rememberAlbumDetailRouter()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            model.handleIntent(AlbumDetailsIntent.LoadAlbumData(albumId))
        }

        UiEventEffect(eventHandler = model) { event ->
            when (event) {
                AlbumDetailsEvents.DeleteSuccess -> {
                    router.handleNavigation(AlbumDetailDestination.GoBack)
                    context.showToast("Album deleted successfully")
                }

                AlbumDetailsEvents.DeleteError -> {
                    context.showToast("An error occurred and your album could not be deleted")
                }
            }

        }

        state.UiStateContent(stateContent = {
            DetailScreen(
                state = it,
                handleIntent = model::handleIntent,
                handleNavigation = router::handleNavigation
            )
        }, errorState = {
            DefaultErrorView(
                error = it,
                options = DefaultErrorViewOptions(
                    primaryButton = DefaultErrorViewButton(title = "Try again",
                        onClick = { model.handleIntent(AlbumDetailsIntent.RetryLoad(albumId)) }),
                    secondaryButton = DefaultErrorViewButton(title = "Return to home screen",
                        onClick = { router.handleNavigation(AlbumDetailDestination.GoBack) }),
                    onBackClick = { router.handleNavigation(AlbumDetailDestination.GoBack) }
                )
            )
        })
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    @Composable
    internal fun DetailScreen(
        state: AlbumDetailsState,
        handleIntent: (AlbumDetailsIntent) -> Unit,
        handleNavigation: (AlbumDetailDestination) -> Unit
    ) {

        val scope = rememberCoroutineScope()
        val refreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

        SwipeRefresh(
            state = refreshState,
            onRefresh = { handleIntent(AlbumDetailsIntent.RefreshData) }
        ) {
            ModalBottomSheetLayout(
                sheetContent = {
                    DeleteBottomSheet(
                        onCancel = {
                            scope.launch {
                                sheetState.hide()
                            }
                        },
                        onContinue = {
                            scope.launch {
                                sheetState.hide()
                                handleIntent(AlbumDetailsIntent.Delete)
                            }
                        }
                    )
                },
                sheetState = sheetState,
                sheetShape = MaterialTheme.shapes.medium
            ) {
                Scaffold(
                    topBar = {
                        TopBar(
                            goBack = { handleNavigation(AlbumDetailDestination.GoBack) },
                            delete = {
                                scope.launch {
                                    sheetState.show()
                                }
                            },
                            edit = { handleNavigation(AlbumDetailDestination.Edit(albumId)) }
                        )
                    },
                ) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {

                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxSize()
                                .zIndex(0f),
                            contentPadding = PaddingValues(MviSampleSizes.medium),
                            columns = GridCells.Fixed(GridCellsCount),
                            verticalArrangement = Arrangement.spacedBy(MviSampleSizes.xSmall3),
                            horizontalArrangement = Arrangement.spacedBy(MviSampleSizes.xSmall3)
                        ) {
                            header {
                                AlbumHeader(
                                    albumName = state.album.name,
                                    albumDescription = state.album.description,
                                    goToAddPhotos = {
                                        handleNavigation(AlbumDetailDestination.AddPhotos(state.album.id))
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            header {
                                VerticalSpacer(height = MviSampleSizes.medium)
                            }

                            content(
                                photos = state.photos,
                                onPhotoClick = {
                                    handleNavigation(AlbumDetailDestination.PhotoDetail(it))
                                }
                            )

                        }
                    }

                }
            }

        }

    }

    private fun LazyGridScope.content(
        photos: List<PhotoModel>,
        onPhotoClick: (Int) -> Unit
    ) {
        if (photos.isEmpty()) {
            header {
                EmptyState(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    title = "Nothing to see here",
                    description = "Add photos to this album to see them here"
                )
            }
        } else {
            items(photos) { photo ->
                AsyncImage(
                    model = photo.location,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .clickable(
                            onClick = { onPhotoClick(photo.photoId) }
                        ),
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopBar(
        goBack: () -> Unit,
        delete: () -> Unit,
        edit: () -> Unit,
    ) {
        TopAppBar(
            title = { Text("Go back") },
            navigationIcon = {
                IconButton(onClick = goBack) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack, contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(onClick = edit) {
                    Icon(imageVector = Icons.Outlined.Edit, null)
                }
                IconButton(onClick = delete) {
                    Icon(imageVector = Icons.Outlined.Delete, null)
                }
            }
        )
    }

    @Composable
    private fun MapLocation(
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(Color.Red)
        )
    }

    @Composable
    private fun AlbumInfo(
        albumName: String,
        albumDescription: String,
        modifier: Modifier = Modifier
    ) {
        Column(modifier) {
            Text(
                text = albumName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = albumDescription,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    private fun AlbumHeader(
        modifier: Modifier = Modifier,
        albumName: String,
        albumDescription: String,
        goToAddPhotos: () -> Unit
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(MviSampleSizes.medium)
        ) {
            MapLocation(Modifier.align(Alignment.CenterHorizontally))
            AlbumInfo(
                albumName = albumName,
                albumDescription = albumDescription,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Button(
                onClick = goToAddPhotos, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add photos to album")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PreviewContainer(contentPaddingValues = PaddingValues(vertical = MviSampleSizes.medium)) {
        AlbumDetailsScreen(1).DetailScreen(
            handleIntent = {},
            state = AlbumDetailsState(),
            handleNavigation = {}
        )
    }
}
