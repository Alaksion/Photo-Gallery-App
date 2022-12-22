package br.com.alaksion.database.data.datasources

import br.com.alaksion.database.data.entities.AlbumEntity
import br.com.alaksion.database.data.entities.AlbumEntityDao
import java.util.UUID


internal class AlbumEntityDaoMock : AlbumEntityDao {

    var albumListResponse = listOf<AlbumEntity>()

    var albumResponse = AlbumEntity.fixture

    var getAllCalls = 0
    var getByIdCalls = 0
    var createCalls = 0
    var deleteCalls = 0

    override suspend fun getAll(): List<AlbumEntity> {
        getAllCalls++
        return albumListResponse
    }

    override suspend fun getById(albumId: UUID): AlbumEntity {
        getByIdCalls++
        return albumResponse
    }

    override suspend fun create(album: AlbumEntity) {
        createCalls++
    }

    override suspend fun delete(album: AlbumEntity) {
        deleteCalls++
    }
}