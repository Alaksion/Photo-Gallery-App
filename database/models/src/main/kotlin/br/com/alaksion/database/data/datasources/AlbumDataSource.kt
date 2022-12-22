package br.com.alaksion.database.data.datasources

import br.com.alaksion.database.data.entities.AlbumEntity
import br.com.alaksion.database.data.entities.AlbumEntityDao
import br.com.alaksion.database.domain.models.AlbumModel
import java.util.UUID
import javax.inject.Inject

interface AlbumDataSource {

    suspend fun getAll(): List<AlbumModel>

    suspend fun getById(albumId: UUID): AlbumModel

    suspend fun create(album: AlbumModel)

    suspend fun delete(album: AlbumModel)

}

internal class AlbumDataSourceImplementation @Inject constructor(
    private val albumDao: AlbumEntityDao
) : AlbumDataSource {

    override suspend fun getAll(): List<AlbumModel> {
        return albumDao.getAll().map { it.mapToModel() }
    }

    override suspend fun getById(albumId: UUID): AlbumModel {
        return albumDao.getById(albumId).mapToModel()
    }

    override suspend fun create(album: AlbumModel) {
        albumDao.create(AlbumEntity.createFromModel(album))
    }

    override suspend fun delete(album: AlbumModel) {
        albumDao.delete(AlbumEntity.createFromModel(album))
    }

}