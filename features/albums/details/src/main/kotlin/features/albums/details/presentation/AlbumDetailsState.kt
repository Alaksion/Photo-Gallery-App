package features.albums.details.presentation

import platform.database.models.models.album.AlbumModel
import platform.database.models.models.photo.PhotoModel

internal data class AlbumDetailsState(
    val album: AlbumModel = AlbumModel.fixture,
    val photos: List<PhotoModel> = listOf(),
    val isInitialized: Boolean = false
)
