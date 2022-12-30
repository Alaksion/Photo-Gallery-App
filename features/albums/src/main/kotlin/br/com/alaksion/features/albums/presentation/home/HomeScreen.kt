package br.com.alaksion.features.albums.presentation.home

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import br.com.alaksion.features.albums.presentation.create.CreateAlbumScreen
import br.com.alaksion.features.albums.presentation.create.NameScreen
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.EmptyState
import platform.uistate.uistate.UiStateContent

internal object HomeScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val model = getViewModel<HomeViewModel>()
        val state by model.collectUiState()
        val navigator = LocalNavigator.current

        LaunchedEffect(key1 = model) {
            model.handleIntent(HomeIntent.LoadData)
        }

        state.UiStateContent(
            stateContent = {
                HomeScreenContent(
                    state = it,
                    intentHandler = model::handleIntent,
                    goToCreateAlbum = { navigator?.push(NameScreen) }
                )
            },
            errorState = {}
        )
    }

}

@Suppress("UnusedPrivateMember")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    state: HomeState,
    intentHandler: (HomeIntent) -> Unit,
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
                            onClick = {},
                            modifier = Modifier.fillMaxWidth()
                        )
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
