package features.albums.details.presentation

import database.models.models.AlbumModel

internal data class AlbumDetailsState(
    val album: AlbumModel = AlbumModel.fixture,
    val isInitialized: Boolean = false
)
