package features.albums.details.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.PreviewContainer
import platform.uicomponents.components.errorview.DefaultErrorView
import platform.uicomponents.components.errorview.DefaultErrorViewButton
import platform.uicomponents.components.errorview.DefaultErrorViewOptions
import platform.uicomponents.components.spacers.VerticalSpacer
import platform.uistate.uistate.UiStateContent

internal data class AlbumDetailsScreen(
    private val albumId: Int
) : AndroidScreen() {

    @Composable
    override fun Content() {
        val model = getViewModel<AlbumDetailsViewModel>()
        val state by model.collectUiState()
        val navigator = LocalNavigator.current

        state.UiStateContent(
            stateContent = {
                DetailScreen(
                    state = it,
                    albumId = albumId,
                    handleIntent = model::handleIntent,
                    goBack = { navigator?.pop() }
                )
            },
            errorState = {
                DefaultErrorView(
                    error = it,
                    options = DefaultErrorViewOptions(
                        primaryButton = DefaultErrorViewButton(
                            title = "Try again",
                            onClick = { model.handleIntent(AlbumDetailsIntent.RetryLoad(albumId)) }
                        ),
                        secondaryButton = DefaultErrorViewButton(
                            title = "Return to home screen",
                            onClick = { navigator?.pop() }
                        )
                    )
                )
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    internal fun DetailScreen(
        state: AlbumDetailsState,
        handleIntent: (AlbumDetailsIntent) -> Unit,
        albumId: Int,
        goBack: () -> Unit,
    ) {
        LaunchedEffect(Unit) {
            handleIntent(AlbumDetailsIntent.LoadAlbumData(albumId))
        }

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Go back") }, navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack, contentDescription = null
                        )
                    }
                })
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(180.dp)
                                .clip(CircleShape)
                                .background(Color.Red)
                        )
                        VerticalSpacer(height = MviSampleSizes.medium)
                        Text(
                            text = state.album.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = state.album.description,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PreviewContainer(contentPaddingValues = PaddingValues(vertical = MviSampleSizes.medium)) {
        AlbumDetailsScreen(1).DetailScreen(
            albumId = 1,
            goBack = {},
            handleIntent = {},
            state = AlbumDetailsState()
        )
    }
}
