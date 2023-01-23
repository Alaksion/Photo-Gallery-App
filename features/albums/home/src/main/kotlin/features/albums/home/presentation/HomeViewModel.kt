package features.albums.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import features.albums.shared.domain.repository.AlbumRepository
import io.github.alaksion.MutableUiStateOwner
import io.github.alaksion.UiStateHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import platform.injection.IODispatcher
import javax.inject.Inject

internal sealed class HomeIntent {
    object LoadData : HomeIntent()
    object Refresh : HomeIntent()
}

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: AlbumRepository,
) : ViewModel(), MutableUiStateOwner<HomeState> by UiStateHandler(HomeState()) {

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadData -> loadAlbums()
            is HomeIntent.Refresh -> refreshAlbums()
        }
    }

    private fun loadAlbums() {
        if (stateData.isInitialized.not()) {
            viewModelScope.launch(dispatcher) {
                asyncUpdateState { updater ->
                    val albums = repository.getAlbums()
                    updater.update { currentState ->
                        currentState.copy(
                            isInitialized = true,
                            albums = albums
                        )
                    }
                }
            }
        }
    }

    private fun refreshAlbums() {
        viewModelScope.launch(dispatcher) {
            asyncUpdateState { updater ->
                updater.update { state ->
                    state.copy(isRefreshing = true)
                }
                val albums = repository.getAlbums()
                updater.update { currentState ->
                    currentState.copy(
                        isRefreshing = false,
                        albums = albums
                    )
                }
            }
        }
    }

}
