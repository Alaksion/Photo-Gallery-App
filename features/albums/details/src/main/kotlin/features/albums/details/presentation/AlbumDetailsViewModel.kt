package features.albums.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import features.albums.shared.domain.repository.AlbumRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import platform.injection.IODispatcher
import platform.uistate.uistate.UiStateHandler
import platform.uistate.uistate.UiStateOwner
import javax.inject.Inject

internal sealed class AlbumDetailsIntent {
    data class LoadAlbumData(val albumId: Int) : AlbumDetailsIntent()
    data class RetryLoad(val albumId: Int) : AlbumDetailsIntent()
    object RefreshData : AlbumDetailsIntent()
    object Delete : AlbumDetailsIntent()
}

@HiltViewModel
internal class AlbumDetailsViewModel @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: AlbumRepository,
) : ViewModel(), UiStateOwner<AlbumDetailsState> by UiStateHandler(AlbumDetailsState()) {

    fun handleIntent(intent: AlbumDetailsIntent) {
        when (intent) {
            is AlbumDetailsIntent.LoadAlbumData -> loadData(intent.albumId)
            is AlbumDetailsIntent.RetryLoad -> loadData(intent.albumId, true)
            is AlbumDetailsIntent.RefreshData -> refreshData()
            is AlbumDetailsIntent.Delete -> deleteAlbum()
        }
    }

    private fun loadData(
        albumId: Int,
        forceLoad: Boolean = false
    ) {
        if (stateData.isInitialized.not() || forceLoad) {
            viewModelScope.launch(dispatcher) {
                asyncUpdateState {
                    val response = repository.getAlbumById(albumId)

                    updateData { currentState ->
                        currentState.copy(
                            album = response.album,
                            photos = response.photos,
                            isInitialized = true
                        )
                    }
                }
            }
        }
    }

    private fun refreshData(
    ) {
        viewModelScope.launch(dispatcher) {
            asyncUpdateState(showLoading = false) {
                updateData { currentState ->
                    currentState.copy(isRefreshing = true)
                }
                val response = repository.getAlbumById(stateData.album.id)

                updateData { currentState ->
                    currentState.copy(
                        album = response.album,
                        photos = response.photos,
                        isRefreshing = false
                    )
                }
            }
        }
    }

    private fun deleteAlbum() {
        viewModelScope.launch(dispatcher) {
            asyncRunCatching(showLoading = true) {
                kotlin.runCatching {
                    repository.deleteAlbum(stateData.album)
                }
            }
        }
    }

}
