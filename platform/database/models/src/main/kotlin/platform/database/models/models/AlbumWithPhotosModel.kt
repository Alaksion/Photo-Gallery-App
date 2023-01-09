package platform.database.models.models

import platform.database.models.models.album.AlbumModel
import platform.database.models.models.photo.PhotoModel

data class AlbumWithPhotosModel(
    val album: AlbumModel,
    val photos: List<PhotoModel>
)
