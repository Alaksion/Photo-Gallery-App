package database.models.data.datasources

import database.models.data.entities.AlbumEntity
import database.models.data.entities.AlbumEntityDao
import database.models.data.validator.AlbumDataSourceValidator
import database.models.models.AlbumModel
import database.models.models.CreateAlbumModel
import database.models.utils.runQuery
import javax.inject.Inject

interface AlbumDataSource {

    suspend fun getAll(): List<AlbumModel>

    suspend fun getById(albumId: Int): AlbumModel

    suspend fun create(album: CreateAlbumModel)

    suspend fun delete(album: AlbumModel)

}

internal class AlbumDataSourceImplementation @Inject constructor(
    private val albumDao: AlbumEntityDao,
    private val validator: AlbumDataSourceValidator
) : AlbumDataSource {

    override suspend fun getAll(): List<AlbumModel> {
        return runQuery { albumDao.getAll().map { it.mapToModel() } }
    }

    override suspend fun getById(albumId: Int): AlbumModel {
        return runQuery { albumDao.getById(albumId).mapToModel() }
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
