package features.albums.create.presentation

import platform.database.models.models.album.AlbumModel
import java.time.LocalDate

internal val EmptyAlbum = AlbumModel(
    name = "",
    description = "",
    createdAt = LocalDate.now(),
    updatedAt = LocalDate.now(),
    id =0
)

internal data class ManageAlbumState(
    val album: AlbumModel = EmptyAlbum
) {
    val isButtonEnabled = album.name.isNotEmpty() && album.description.isNotEmpty()
}
