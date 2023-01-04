package features.photos.presentation.gallerypick

import android.net.Uri

internal data class GalleryPhotoPickerState(
    val photos: List<Uri> = listOf()
)
