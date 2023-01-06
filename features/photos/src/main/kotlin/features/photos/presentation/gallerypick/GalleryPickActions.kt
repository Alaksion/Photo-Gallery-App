package features.photos.presentation.gallerypick

import android.net.Uri
import platform.uistate.uievent.UiEvent
import java.util.UUID

internal sealed class GalleryPhotoPickerIntent {
    data class AddPhoto(val uris: List<Uri>) : GalleryPhotoPickerIntent()
    data class ConfirmPhotos(val albumId: Int) : GalleryPhotoPickerIntent()
}

internal sealed class GalleryPhotoPickerEvents : UiEvent {

    object Success : GalleryPhotoPickerEvents() {
        override val eventId: UUID
            get() = UUID.randomUUID()
    }

    object Error : GalleryPhotoPickerEvents() {
        override val eventId: UUID
            get() = UUID.randomUUID()
    }
}
