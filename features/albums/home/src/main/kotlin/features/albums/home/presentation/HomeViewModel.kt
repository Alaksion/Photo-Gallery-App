package features.albums.home.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import features.albums.shared.domain.repository.AlbumRepository
import kotlinx.coroutines.CoroutineDispatcher
import platform.injection.IODispatcher
import platform.uistate.uistate.UiStateHandler
import javax.inject.Inject

internal sealed class HomeIntent {
    object LoadData : HomeIntent()
}

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    @IODispatcher dispatcher: CoroutineDispatcher,
    private val repository: AlbumRepository,
) : UiStateHandler<HomeState>(HomeState(), dispatcher) {

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
