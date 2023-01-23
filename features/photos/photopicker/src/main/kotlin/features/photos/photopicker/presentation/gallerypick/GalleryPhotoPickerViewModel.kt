package features.photos.photopicker.presentation.gallerypick

import android.net.Uri
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
import platform.injection.IODispatcher
import javax.inject.Inject

@HiltViewModel
internal class GalleryPhotoPickerViewModel @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val photoRepo: PhotoRepository
) : ViewModel(),
    MutableUiStateOwner<GalleryPhotoPickerState> by UiStateHandler(GalleryPhotoPickerState()),
    UiEventOwnerSender<GalleryPhotoPickerEvents> by UiEventHandler() {

    fun handleIntent(intent: GalleryPhotoPickerIntent) {
        when (intent) {
            is GalleryPhotoPickerIntent.AddPhoto -> addPhoto(intent.uris)
            is GalleryPhotoPickerIntent.ConfirmPhotos -> createPhotos(intent.albumId)
        }
    }

    private fun addPhoto(uri: List<Uri>) {
        updateState { updater ->
            updater.update { currentState ->
                currentState.copy(photos = currentState.photos + uri)
            }
        }
    }

    private fun createPhotos(albumId: Int) {
        viewModelScope.launch(dispatcher) {
            asyncCatching {
                kotlin.runCatching {
                    photoRepo.addPhotos(
                        photos = stateData.photos,
                        albumId = albumId
                    )
                    sendEvent(GalleryPhotoPickerEvents.Success)
                }.onFailure {
                    sendEvent(GalleryPhotoPickerEvents.Error)
                }
            }
        }
    }
}
