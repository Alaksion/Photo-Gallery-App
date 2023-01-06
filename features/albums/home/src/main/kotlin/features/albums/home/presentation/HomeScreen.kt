package features.albums.home.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import platform.navigation.NavigationProvider
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.EmptyState
import platform.uicomponents.components.spacers.VerticalSpacer
import platform.uistate.uistate.UiStateContent

internal object HomeScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val model = getViewModel<HomeViewModel>()
        val state by model.uiState.collectAsState()
        val navigator = LocalNavigator.current

        LaunchedEffect(key1 = model) {
            model.handleIntent(HomeIntent.LoadData)
        }

        state.UiStateContent(
            stateContent = {
                HomeScreenContent(
                    state = it,
                    goToCreateAlbum = {
                        navigator?.push(
                            ScreenRegistry.get(NavigationProvider.Albums.Create)
                        )
                    },
                    goToAlbumDetail = { albumId ->
                        navigator?.push(
                            ScreenRegistry.get(NavigationProvider.Albums.Details(albumId))
                        )
                    }
                )
            },
            errorState = {
                Log.d("error 1", it.toString())
            }
        )
    }

}

@Suppress("UnusedPrivateMember")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    state: HomeState,
    goToAlbumDetail: (Int) -> Unit,
    goToCreateAlbum: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = goToCreateAlbum) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
            }
        },
        topBar = {
            TopAppBar(title = { Text(text = "My albums") })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {

            if (state.albums.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = MviSampleSizes.medium),
                    verticalArrangement = Arrangement.spacedBy(MviSampleSizes.small)
                ) {
                    items(state.albums) { album ->
                        AlbumCard(
                            data = album,
                            onClick = goToAlbumDetail,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        VerticalSpacer(height = MviSampleSizes.medium)
                    }
                }
            } else {
                EmptyState(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    title = "Nothing to see here",
                    description = "Create at least one album to see your albums list"
                )
            }
        }
    }

}
