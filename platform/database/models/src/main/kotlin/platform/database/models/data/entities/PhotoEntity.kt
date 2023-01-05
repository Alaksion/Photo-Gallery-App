package platform.database.models.data.entities

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import platform.database.models.models.PhotoModel
import platform.database.models.models.PhotoModelData

@Entity("photos")
internal data class PhotoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "hostAlbumId")
    val albumId: Int,
    val path: String,
    val source: PhotoEntitySource
) {
    fun mapToModel() = PhotoModel(
        albumId = this.albumId,
        photoId = this.id,
        data = when (this.source) {
            PhotoEntitySource.Remote -> PhotoModelData.Remote(this.path)
            PhotoEntitySource.Local -> PhotoModelData.Local(Uri.parse(this.path))
        }
    )
}

internal enum class PhotoEntitySource {
    Remote,
    Local;
}

@Dao
internal interface PhotoEntityDao {

    @Insert
    suspend fun addPhotos(list: List<PhotoEntity>)

}
