package features.photos.data

import android.net.Uri
import platform.database.models.data.datasources.PhotoDataSource
import platform.database.models.models.PhotoModel
import javax.inject.Inject

internal interface PhotoRepository {
    suspend fun addPhotos(albumId: Int, photos: List<Uri>)
}

internal class PhotoRepositoryImpl @Inject constructor(
    private val dataSource: PhotoDataSource
) : PhotoRepository {

    override suspend fun addPhotos(albumId: Int, photos: List<Uri>) {
        val photoModels = photos.map {
            PhotoModel(
                photoId = 0,
                albumId = albumId,
                path = it.path ?: throw IllegalArgumentException("image path could not be resolved")
            )
        }
        dataSource.addPhotos(photoModels)
    }

}
