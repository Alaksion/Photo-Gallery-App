package platform.database.models.data.datasources

import platform.database.models.data.entities.AlbumEntity
import platform.database.models.data.entities.AlbumEntityDao
import platform.database.models.data.validator.AlbumDataSourceValidator
import platform.database.models.models.AlbumModel
import platform.database.models.models.AlbumWithPhotosModel
import platform.database.models.models.CreateAlbumModel
import platform.database.models.utils.runQuery
import javax.inject.Inject

interface AlbumDataSource {

    suspend fun getAll(): List<AlbumModel>

    suspend fun getById(albumId: Int): AlbumWithPhotosModel

    suspend fun create(album: CreateAlbumModel)

    suspend fun delete(album: AlbumModel)

}

internal class AlbumDataSourceImplementation @Inject constructor(
    private val albumDao: AlbumEntityDao,
    private val validator: AlbumDataSourceValidator,
) : AlbumDataSource {

    override suspend fun getAll(): List<AlbumModel> {
        return runQuery { albumDao.getAll().map { it.mapToModel() } }
    }

    override suspend fun getById(albumId: Int): AlbumWithPhotosModel {
        return runQuery {
            val response = albumDao.getById(albumId)
            AlbumWithPhotosModel(
                album = response.album.mapToModel(),
                photos = response.photos.map { it.mapToModel() }
            )

        }
    }

    override suspend fun create(album: CreateAlbumModel) {
        runQuery {
            validator.validateCreateAlbumPayload(album)

            albumDao.create(AlbumEntity.createFromModel(album))
        }
    }

    override suspend fun delete(album: AlbumModel) {
        runQuery {
            albumDao.delete(album.toAlbumEntity())
        }
    }

}
