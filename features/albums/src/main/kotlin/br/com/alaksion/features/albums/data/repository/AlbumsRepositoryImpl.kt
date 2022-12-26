package br.com.alaksion.features.albums.data.repository

import br.com.alaksion.database.data.datasources.AlbumDataSource
import br.com.alaksion.database.domain.models.AlbumModel
import br.com.alaksion.features.albums.domain.repository.AlbumRepository
import javax.inject.Inject

internal class AlbumsRepositoryImpl @Inject constructor(
    private val albumDataSource: AlbumDataSource
) : AlbumRepository {

    override suspend fun getAlbums(): List<AlbumModel> {
        return albumDataSource.getAll()
    }

}
