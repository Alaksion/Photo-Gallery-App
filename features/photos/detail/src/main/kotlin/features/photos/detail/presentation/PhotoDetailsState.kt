package features.photos.detail.presentation

import platform.database.models.models.photo.PhotoModel

internal data class PhotoDetailsState(
    val photo: PhotoModel = PhotoModel.fixture,
    val isInitialized: Boolean = false
)
