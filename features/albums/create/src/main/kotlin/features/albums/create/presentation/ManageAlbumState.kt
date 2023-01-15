package features.albums.create.presentation

import platform.database.models.models.album.AlbumModel

internal data class ManageAlbumState(
    val album: AlbumModel = AlbumModel.fixture
) {
    val isButtonEnabled = album.name.isNotEmpty() && album.description.isNotEmpty()
}
