package features.photos.presentation.gallerypick

import android.net.Uri
import dagger.hilt.android.lifecycle.HiltViewModel
import features.photos.data.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import platform.injection.IODispatcher
import platform.uistate.uistate.UiStateViewModel
import javax.inject.Inject

internal sealed class GalleryPhotoPickerIntent {
    data class AddPhoto(val uris: List<Uri>) : GalleryPhotoPickerIntent()
    data class ConfirmPhotos(val albumId: Int) : GalleryPhotoPickerIntent()
}

@HiltViewModel
internal class GalleryPhotoPickerViewModel @Inject constructor(
    @IODispatcher dispatcher: CoroutineDispatcher,
    private val photoRepo: PhotoRepository
) : UiStateViewModel<GalleryPhotoPickerState>(
    GalleryPhotoPickerState(), dispatcher
) {

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
            photoRepo.addPhotos(
                photos = stateData.photos,
                albumId = albumId
            )
        }
    }

}
