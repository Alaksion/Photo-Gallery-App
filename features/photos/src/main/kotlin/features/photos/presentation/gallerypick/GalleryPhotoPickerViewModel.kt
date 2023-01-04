package features.photos.presentation.gallerypick

import android.net.Uri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import platform.injection.IODispatcher
import platform.uistate.uistate.UiStateViewModel
import javax.inject.Inject

internal sealed class GalleryPhotoPickerIntent {
    data class AddPhoto(val uris: List<Uri>) : GalleryPhotoPickerIntent()
}

@HiltViewModel
internal class GalleryPhotoPickerViewModel @Inject constructor(
    @IODispatcher dispatcher: CoroutineDispatcher
) : UiStateViewModel<GalleryPhotoPickerState>(
    GalleryPhotoPickerState(), dispatcher
) {

    fun handleIntent(intent: GalleryPhotoPickerIntent) {
        when (intent) {
            is GalleryPhotoPickerIntent.AddPhoto -> addPhoto(intent.uris)
        }
    }

    private fun addPhoto(uri: List<Uri>) {
        setState(showLoading = false) { currentState ->
            currentState.copy(photos = currentState.photos + uri)
        }
    }

}
