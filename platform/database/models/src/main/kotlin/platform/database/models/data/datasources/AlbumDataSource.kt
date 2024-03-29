package platform.database.models.data.datasources

import platform.database.models.data.entities.AlbumEntity
import platform.database.models.data.entities.AlbumEntityDao
import platform.database.models.data.validator.AlbumDataSourceValidator
import platform.database.models.models.AlbumWithPhotosModel
import platform.database.models.models.album.AlbumModel
import platform.database.models.models.album.CreateAlbumModel
import platform.database.models.utils.runQuery
import platform.logs.loggers.AppLogger
import javax.inject.Inject

interface AlbumDataSource {

    suspend fun getAll(): List<AlbumModel>

    suspend fun getById(albumId: Int): AlbumWithPhotosModel

    suspend fun create(album: CreateAlbumModel)

    suspend fun delete(album: AlbumModel)

    suspend fun update(album: AlbumModel)

}

internal class AlbumDataSourceImplementation @Inject constructor(
    private val albumDao: AlbumEntityDao,
    private val validator: AlbumDataSourceValidator,
    private val logger: AppLogger
) : AlbumDataSource {

    override suspend fun getAll(): List<AlbumModel> {
        return runQuery(logger) { albumDao.getAll().map { it.mapToModel() } }
    }

    override suspend fun getById(albumId: Int): AlbumWithPhotosModel {
        return runQuery(logger) {
            val response = albumDao.getById(albumId)
            AlbumWithPhotosModel(
                album = response.album.mapToModel(),
                photos = response.photos.map { it.mapToModel() }
            )

        }
    }

    override suspend fun create(album: CreateAlbumModel) {
        runQuery(logger) {
            validator.validateCreateAlbumPayload(album)

            albumDao.create(AlbumEntity.createFromModel(album))
        }
    }

    override suspend fun delete(album: AlbumModel) {
        runQuery(logger) {
            albumDao.delete(album.toAlbumEntity())
        }
    }

    override suspend fun update(album: AlbumModel) {
        runQuery(logger) {
            albumDao.update(album.toAlbumEntity())
        }
    }

}
