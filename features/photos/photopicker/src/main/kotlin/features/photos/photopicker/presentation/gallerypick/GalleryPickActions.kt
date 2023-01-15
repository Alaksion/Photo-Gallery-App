package features.photos.photopicker.presentation.gallerypick

import android.net.Uri
import platform.uistate.uievent.UiEvent

internal sealed class GalleryPhotoPickerIntent {
    data class AddPhoto(val uris: List<Uri>) : GalleryPhotoPickerIntent()
    data class ConfirmPhotos(val albumId: Int) : GalleryPhotoPickerIntent()
}

internal sealed class GalleryPhotoPickerEvents : UiEvent() {

    object Success : GalleryPhotoPickerEvents()

    object Error : GalleryPhotoPickerEvents()
}
