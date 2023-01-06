package features.albums.details.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import features.albums.shared.domain.repository.AlbumRepository
import kotlinx.coroutines.CoroutineDispatcher
import platform.injection.IODispatcher
import platform.uistate.uistate.UiStateHandler
import javax.inject.Inject

internal sealed class AlbumDetailsIntent {
    data class LoadAlbumData(val albumId: Int) : AlbumDetailsIntent()
    data class RetryLoad(val albumId: Int) : AlbumDetailsIntent()
}

@HiltViewModel
internal class AlbumDetailsViewModel @Inject constructor(
    @IODispatcher dispatcher: CoroutineDispatcher,
    private val repository: AlbumRepository,
) : UiStateHandler<AlbumDetailsState>(AlbumDetailsState(), dispatcher) {

    fun handleIntent(intent: AlbumDetailsIntent) {
        when (intent) {
            is AlbumDetailsIntent.LoadAlbumData -> loadData(intent.albumId)
            is AlbumDetailsIntent.RetryLoad -> loadData(intent.albumId, true)
        }
    }

    private fun loadData(
        albumId: Int,
        forceLoad: Boolean = false
    ) {
        if (stateData.isInitialized.not() || forceLoad) {
            setState { currentState ->
                val response = repository.getAlbumById(albumId)
                currentState.copy(
                    album = response.album,
                    photos = response.photos,
                    isInitialized = true
                )
            }
        }
    }

}
