package platform.database.models.data.entities

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import platform.database.models.models.PhotoModel

@Entity("photos")
internal data class PhotoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "hostAlbumId")
    val albumId: Int,
    val path: String,
    val source: PhotoEntitySource
) {
    fun mapToModel() = when (this.source) {
        PhotoEntitySource.Remote -> PhotoModel.Remote(this.path)
        PhotoEntitySource.Local -> PhotoModel.Local(Uri.parse(this.path))
    }
}

internal enum class PhotoEntitySource {
    Remote,
    Local;
}
