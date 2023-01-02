package database.models.models

data class AlbumWithPhotosModel(
    val album: AlbumModel,
    val photos: List<PhotoModel>
)
