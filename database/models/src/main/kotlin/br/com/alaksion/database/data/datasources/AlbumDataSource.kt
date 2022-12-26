package br.com.alaksion.database.data.datasources

import br.com.alaksion.database.data.entities.AlbumEntity
import br.com.alaksion.database.data.entities.AlbumEntityDao
import br.com.alaksion.database.data.validator.AlbumDataSourceValidator
import br.com.alaksion.database.domain.models.AlbumModel
import br.com.alaksion.database.domain.models.CreateAlbumModel
import java.util.UUID
import javax.inject.Inject

interface AlbumDataSource {

    suspend fun getAll(): List<AlbumModel>

    suspend fun getById(albumId: UUID): AlbumModel

    suspend fun create(album: CreateAlbumModel)

    suspend fun delete(album: AlbumModel)

}

internal class AlbumDataSourceImplementation @Inject constructor(
    private val albumDao: AlbumEntityDao,
    private val validator: AlbumDataSourceValidator
) : AlbumDataSource {

    override suspend fun getAll(): List<AlbumModel> {
        return albumDao.getAll().map { it.mapToModel() }
    }

    override suspend fun getById(albumId: UUID): AlbumModel {
        return albumDao.getById(albumId).mapToModel()
    }

    override suspend fun create(album: CreateAlbumModel) {
        validator.validateCreateAlbumPayload(album)

        albumDao.create(AlbumEntity.createFromModel(album))
    }

    override suspend fun delete(album: AlbumModel) {
        albumDao.delete(album.toAlbumEntity())
    }

}
