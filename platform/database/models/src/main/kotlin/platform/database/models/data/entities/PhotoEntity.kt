package platform.database.models.data.entities

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import platform.database.models.models.photo.PhotoModel

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
        location = Uri.parse(this.path)
    )
}

@Dao
internal interface PhotoEntityDao {

    @Insert
    suspend fun addPhotos(list: List<PhotoEntity>)

    @Query("SELECT * FROM PHOTOS WHERE id=:photoId")
    suspend fun getPhoto(photoId: Int): PhotoEntity

    @Delete
    suspend fun deletePhoto(photo: PhotoEntity)

}
