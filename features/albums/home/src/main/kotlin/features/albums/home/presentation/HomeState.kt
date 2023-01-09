package features.albums.home.presentation

import platform.database.models.models.album.AlbumModel

internal data class HomeState(
    val isInitialized: Boolean = false,
    val albums: List<AlbumModel> = listOf(),
)
