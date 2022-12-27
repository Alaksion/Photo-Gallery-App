package br.com.alaksion.features.albums.presentation.home

import br.com.alaksion.features.albums.domain.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import platform.injection.IODispatcher
import platform.uistate.uistate.UiStateViewModel
import javax.inject.Inject

internal sealed class HomeIntent {
    object LoadData : HomeIntent()
}

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    @IODispatcher dispatcher: CoroutineDispatcher,
    private val repository: AlbumRepository,
) : UiStateViewModel<HomeState>(HomeState(), dispatcher) {

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadData -> loadAlbums()
        }
    }

    private fun loadAlbums() {
        if (stateData.isInitialized.not()) {
            setState { currentState ->
                val albums = repository.getAlbums()
                currentState.copy(
                    isInitialized = true,
                    albums = albums
                )
            }
        }
    }

}
