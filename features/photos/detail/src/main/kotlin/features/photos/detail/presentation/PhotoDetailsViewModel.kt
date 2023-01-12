package features.photos.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import features.photos.shared.data.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import platform.error.InternalException
import platform.injection.IODispatcher
import platform.uistate.uievent.UiEventHandler
import platform.uistate.uievent.UiEventOwner
import platform.uistate.uistate.UiStateHandler
import platform.uistate.uistate.UiStateOwner
import javax.inject.Inject

@HiltViewModel
internal class PhotoDetailsViewModel @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val photoRepository: PhotoRepository
) : ViewModel(),
    UiStateOwner<PhotoDetailsState> by UiStateHandler(PhotoDetailsState()),
    UiEventOwner<PhotoDetailsEvents> by UiEventHandler() {
    fun handleIntent(intent: PhotoDetailsIntent) {
        when (intent) {
            is PhotoDetailsIntent.LoadData -> loadData(intent.photoId)
            is PhotoDetailsIntent.DeletePhoto -> deletePhoto()
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

    private fun deletePhoto() {
        viewModelScope.launch(dispatcher) {
            asyncRunCatching(showLoading = false) {
                val event = kotlin.runCatching {
                    photoRepository.deletePhoto(stateData.photo)
                }.fold(
                    onSuccess = { PhotoDetailsEvents.DeleteSuccess },
                    onFailure = { error ->
                        val message = when (error) {
                            is InternalException.Generic -> error.message.orEmpty()
                            else -> "An error occurred and this photo could not be deleted"
                        }
                        PhotoDetailsEvents.DeleteError(message)
                    }
                )
                enqueueEvent(event)
            }
        }
    }

}
