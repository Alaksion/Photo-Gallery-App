package features.photos.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import features.photos.shared.data.PhotoRepository
import io.github.alaksion.MutableUiStateOwner
import io.github.alaksion.UiStateHandler
import io.github.alaksion.uievent.UiEventHandler
import io.github.alaksion.uievent.UiEventOwnerSender
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import platform.error.InternalException
import platform.injection.IODispatcher
import javax.inject.Inject

@HiltViewModel
internal class PhotoDetailsViewModel @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val photoRepository: PhotoRepository
) : ViewModel(),
    MutableUiStateOwner<PhotoDetailsState> by UiStateHandler(PhotoDetailsState()),
    UiEventOwnerSender<PhotoDetailsEvents> by UiEventHandler() {
    fun handleIntent(intent: PhotoDetailsIntent) {
        when (intent) {
            is PhotoDetailsIntent.LoadData -> loadData(intent.photoId)
            is PhotoDetailsIntent.DeletePhoto -> deletePhoto()
        }
    }

    private fun loadData(photoId: Int) {
        viewModelScope.launch(dispatcher) {
            asyncUpdateState { updater ->
                val photo = photoRepository.getPhotoById(photoId)
                updater.update { currentState ->
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
            asyncCatching(showLoading = false) {
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
                sendEvent(event)
            }
        }
    }

}
