package platform.database.models.data.entities

import androidx.room.Embedded
import androidx.room.Relation

internal data class AlbumWithPhotosEntity(
    @Embedded val album: AlbumEntity,
    @Relation(
        parentColumn = "albumId",
        entityColumn = "hostAlbumId"
    )
    val photos: List<PhotoEntity>
) {
    companion object {
        val fixture = AlbumWithPhotosEntity(
            album = AlbumEntity.fixture,
            photos = listOf()
        )
    }
}
