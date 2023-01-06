package features.photos.presentation.gallerypick

import android.net.Uri
import dagger.hilt.android.lifecycle.HiltViewModel
import features.photos.data.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import platform.injection.IODispatcher
import platform.uistate.uievent.UiEventHandler
import platform.uistate.uievent.UiEventHandlerImpl
import platform.uistate.uistate.UiStateHandler
import javax.inject.Inject

@HiltViewModel
internal class GalleryPhotoPickerViewModel @Inject constructor(
    @IODispatcher dispatcher: CoroutineDispatcher,
    private val photoRepo: PhotoRepository
) : UiStateHandler<GalleryPhotoPickerState>(
    GalleryPhotoPickerState(), dispatcher
), UiEventHandler<GalleryPhotoPickerEvents> by UiEventHandlerImpl() {

    fun handleIntent(intent: GalleryPhotoPickerIntent) {
        when (intent) {
            is GalleryPhotoPickerIntent.AddPhoto -> addPhoto(intent.uris)
            is GalleryPhotoPickerIntent.ConfirmPhotos -> createPhotos(intent.albumId)
        }
    }

    private fun addPhoto(uri: List<Uri>) {
        setState(showLoading = false) { currentState ->
            currentState.copy(photos = currentState.photos + uri)
        }
    }

    private fun createPhotos(albumId: Int) {
        runSuspendCatching(showLoading = true) {

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
