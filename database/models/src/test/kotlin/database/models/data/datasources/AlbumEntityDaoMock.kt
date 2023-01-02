package database.models.data.datasources

import database.models.data.entities.AlbumEntity
import database.models.data.entities.AlbumEntityDao
import database.models.data.entities.AlbumWithPhotosEntity


internal class AlbumEntityDaoMock : AlbumEntityDao {

    var albumListResponse = listOf<AlbumEntity>()

    var albumResponse = AlbumWithPhotosEntity.fixture

    var getAllCalls = 0
    var getByIdCalls = 0
    var createCalls = 0
    var deleteCalls = 0

    override suspend fun getAll(): List<AlbumEntity> {
        getAllCalls++
        return albumListResponse
    }

    override suspend fun getById(albumId: Int): AlbumWithPhotosEntity {
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
