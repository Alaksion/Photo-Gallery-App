package platform.database.models.data.entities

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import platform.database.models.models.PhotoModel

@Entity("photos")
internal data class PhotoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "hostAlbumId")
    val albumId: Int,
    val path: String,
) {
    fun mapToModel() = PhotoModel(
        albumId = this.albumId,
        photoId = this.id,
        path = this.path
    )
}

@Dao
internal interface PhotoEntityDao {

    @Insert
    suspend fun addPhotos(list: List<PhotoEntity>)

}
