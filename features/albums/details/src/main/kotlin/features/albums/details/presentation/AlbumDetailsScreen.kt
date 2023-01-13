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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import platform.database.models.models.photo.PhotoModel
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.EmptyState
import platform.uicomponents.components.PreviewContainer
import platform.uicomponents.components.errorview.DefaultErrorView
import platform.uicomponents.components.errorview.DefaultErrorViewButton
import platform.uicomponents.components.errorview.DefaultErrorViewOptions
import platform.uicomponents.components.spacers.VerticalSpacer
import platform.uicomponents.extensions.header
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

        LaunchedEffect(Unit) {
            model.handleIntent(AlbumDetailsIntent.LoadAlbumData(albumId))
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
                        onClick = { router.handleNavigation(AlbumDetailDestination.GoBack) })
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

        val refreshState = rememberPullRefreshState(
            refreshing = state.isRefreshing,
            onRefresh = {
                handleIntent(AlbumDetailsIntent.RetryLoad(albumId))
            }
        )

        Scaffold(
            topBar = {
                TopBar(goBack = { handleNavigation(AlbumDetailDestination.GoBack) })
            },
        ) {
            PullRefreshIndicator(
                refreshing = state.isRefreshing, state = refreshState
            )

            LazyVerticalGrid(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .pullRefresh(refreshState),
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
        goBack: () -> Unit
    ) {
        TopAppBar(
            title = { Text("Go back") },
            navigationIcon = {
                IconButton(onClick = goBack) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack, contentDescription = null
                    )
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
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = albumDescription
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
