package features.albums.details.presentation

import database.models.models.AlbumModel
import database.models.models.PhotoModel

internal data class AlbumDetailsState(
    val album: AlbumModel = AlbumModel.fixture,
    val photos: List<PhotoModel> = listOf(),
    val isInitialized: Boolean = false
)
