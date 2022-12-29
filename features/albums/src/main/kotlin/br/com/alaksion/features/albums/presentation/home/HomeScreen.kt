package br.com.alaksion.features.albums.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.EmptyState
import platform.uistate.uistate.UiStateContent

internal object HomeScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val model = getViewModel<HomeViewModel>()
        val state by model.collectUiState()

        LaunchedEffect(key1 = model) {
            model.handleIntent(HomeIntent.LoadData)
        }

        state.UiStateContent(
            stateContent = {
                HomeScreenContent(
                    state = it,
                    intentHandler = model::handleIntent
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
    intentHandler: (HomeIntent) -> Unit
) {
    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            Text(
                text = "My albums",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MviSampleSizes.medium)
            )
            if (state.albums.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = MviSampleSizes.medium)
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
