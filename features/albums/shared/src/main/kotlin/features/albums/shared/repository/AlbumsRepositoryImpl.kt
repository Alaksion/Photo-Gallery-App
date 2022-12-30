package features.albums.shared.repository

import features.albums.shared.domain.model.CreateAlbumDTO
import features.albums.shared.domain.repository.AlbumRepository
import database.models.data.datasources.AlbumDataSource
import database.models.models.AlbumModel
import database.models.models.CreateAlbumModel
import javax.inject.Inject

internal class AlbumsRepositoryImpl @Inject constructor(
    private val albumDataSource: AlbumDataSource
) : AlbumRepository {

    override suspend fun getAlbums(): List<AlbumModel> {
        return albumDataSource.getAll()
    }

    override suspend fun createAlbum(data: CreateAlbumDTO) {
        albumDataSource.create(
            CreateAlbumModel(
                name = data.name,
                description = data.description
            )
        )
    }

}
