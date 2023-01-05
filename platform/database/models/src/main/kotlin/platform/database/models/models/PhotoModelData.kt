package platform.database.models.models

import android.net.Uri
import platform.database.models.data.entities.PhotoEntity
import platform.database.models.data.entities.PhotoEntitySource

data class PhotoModel(
    val photoId: Int,
    val albumId: Int,
    val data: PhotoModelData
)

sealed class PhotoModelData {
    data class Remote(val url: String) : PhotoModelData()
    data class Local(val uri: Uri) : PhotoModelData()
}

internal fun PhotoModel.mapToEntity(): PhotoEntity {

    return PhotoEntity(
        id = this.photoId,
        albumId = this.albumId,
        path = when (this.data) {
            is PhotoModelData.Remote -> this.data.url
            is PhotoModelData.Local -> this.data.uri.toString()
        },
        source = when (this.data) {
            is PhotoModelData.Remote -> PhotoEntitySource.Remote
            is PhotoModelData.Local -> PhotoEntitySource.Local
        }
    )

}
