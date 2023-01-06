package features.photos.presentation.gallerypick

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import features.photos.data.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import platform.injection.IODispatcher
import platform.uistate.uievent.UiEventHandler
import platform.uistate.uievent.UiEventOwner
import platform.uistate.uistate.UiStateHandler
import platform.uistate.uistate.UiStateOwner
import javax.inject.Inject

@HiltViewModel
internal class GalleryPhotoPickerViewModel @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val photoRepo: PhotoRepository
) : ViewModel(),
    UiStateOwner<GalleryPhotoPickerState> by UiStateHandler(GalleryPhotoPickerState()),
    UiEventOwner<GalleryPhotoPickerEvents> by UiEventHandler() {

    fun handleIntent(intent: GalleryPhotoPickerIntent) {
        when (intent) {
            is GalleryPhotoPickerIntent.AddPhoto -> addPhoto(intent.uris)
            is GalleryPhotoPickerIntent.ConfirmPhotos -> createPhotos(intent.albumId)
        }
    }

    private fun addPhoto(uri: List<Uri>) {
        updateState {
            updateData { currentState ->
                currentState.copy(photos = currentState.photos + uri)
            }
        }
    }

    private fun createPhotos(albumId: Int) {

        viewModelScope.launch(dispatcher) {
            kotlin.runCatching {
                photoRepo.addPhotos(
                    photos = stateData.photos,
                    albumId = albumId
                )
                enqueueEvent(GalleryPhotoPickerEvents.Success)
            }.onFailure {
                enqueueEvent(GalleryPhotoPickerEvents.Error)
            }
        }
    }
}
