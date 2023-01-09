package platform.database.models.models.photo

import android.net.Uri
import platform.database.models.data.entities.PhotoEntity

data class PhotoModel(
    val photoId: Int,
    val albumId: Int,
    val location: Uri,
) {
    companion object {
        val fixture = PhotoModel(photoId = 0, albumId = 0, location = Uri.EMPTY)
    }
}

internal fun PhotoModel.mapToEntity(): PhotoEntity {

    return PhotoEntity(
        id = this.photoId,
        albumId = this.albumId,
        path = this.location.toString()
    )

}
