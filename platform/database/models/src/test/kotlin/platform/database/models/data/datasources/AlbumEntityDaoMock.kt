package platform.database.models.data.datasources

import platform.database.models.data.entities.AlbumEntity
import platform.database.models.data.entities.AlbumEntityDao
import platform.database.models.data.entities.AlbumWithPhotosEntity


internal class AlbumEntityDaoMock : platform.database.models.data.entities.AlbumEntityDao {

    var albumListResponse = listOf<platform.database.models.data.entities.AlbumEntity>()

    var albumResponse = platform.database.models.data.entities.AlbumWithPhotosEntity.fixture

    var getAllCalls = 0
    var getByIdCalls = 0
    var createCalls = 0
    var deleteCalls = 0

    override suspend fun getAll(): List<platform.database.models.data.entities.AlbumEntity> {
        getAllCalls++
        return albumListResponse
    }

    override suspend fun getById(albumId: Int): platform.database.models.data.entities.AlbumWithPhotosEntity {
        getByIdCalls++
        return albumResponse
    }

    override suspend fun create(album: platform.database.models.data.entities.AlbumEntity) {
        createCalls++
    }

    override suspend fun delete(album: platform.database.models.data.entities.AlbumEntity) {
        deleteCalls++
    }
}
