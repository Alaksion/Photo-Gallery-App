package platform.database.models.models.photo

import android.net.Uri
import platform.database.models.data.entities.PhotoEntity

data class PhotoModel(
    val photoId: Int,
    val albumId: Int,
    val location: Uri,
)
internal fun PhotoModel.mapToEntity(): PhotoEntity {

    return PhotoEntity(
        id = this.photoId,
        albumId = this.albumId,
        path = this.location.toString()
    )

}
