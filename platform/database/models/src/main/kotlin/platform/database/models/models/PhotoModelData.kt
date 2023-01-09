package platform.database.models.models

import android.net.Uri
import platform.database.models.data.entities.PhotoEntity

data class PhotoModel(
    val photoId: Int,
    val albumId: Int,
    val location: Uri,
)

sealed class PhotoModelData {
    data class Remote(val url: String) : PhotoModelData()
    data class Local(val uri: String) : PhotoModelData()
}

internal fun PhotoModel.mapToEntity(): PhotoEntity {

    return PhotoEntity(
        id = this.photoId,
        albumId = this.albumId,
        path = this.location.toString()
    )

}
