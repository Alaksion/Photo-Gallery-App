package platform.database.models.data.datasources

import platform.database.models.data.entities.PhotoEntityDao
import platform.database.models.models.PhotoModel
import platform.database.models.models.mapToEntity
import platform.database.models.utils.runQuery
import javax.inject.Inject

interface PhotoDataSource {

    suspend fun addPhotos(photos: List<PhotoModel>)

}

internal class PhotoDataSourceImpl @Inject constructor(
    private val photoDao: PhotoEntityDao
) : PhotoDataSource {

    override suspend fun addPhotos(photos: List<PhotoModel>) {
        runQuery {
            photoDao.addPhotos(
                photos.map { it.mapToEntity() }
            )
        }
    }

}