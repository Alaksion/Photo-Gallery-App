package features.photos.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import features.photos.shared.data.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import platform.injection.IODispatcher
import platform.uistate.uistate.UiStateHandler
import platform.uistate.uistate.UiStateOwner
import javax.inject.Inject

internal sealed class PhotoDetailsIntent {
    data class LoadData(val photoId: Int) : PhotoDetailsIntent()
}

@HiltViewModel
internal class PhotoDetailsViewModel @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val photoRepository: PhotoRepository
) : ViewModel(),
    UiStateOwner<PhotoDetailsState> by UiStateHandler(PhotoDetailsState()) {

    fun handleIntent(intent: PhotoDetailsIntent) {
        when (intent) {
            is PhotoDetailsIntent.LoadData -> loadData(intent.photoId)
        }
    }

    private fun loadData(photoId: Int) {
        viewModelScope.launch(dispatcher) {
            asyncUpdateState {
                val photo = photoRepository.getPhotoById(photoId)
                updateData { currentState ->
                    currentState.copy(
                        photo = photo,
                        isInitialized = true
                    )
                }
            }
        }

    }

}
