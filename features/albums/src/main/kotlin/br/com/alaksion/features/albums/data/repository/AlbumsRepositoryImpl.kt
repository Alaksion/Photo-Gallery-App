package br.com.alaksion.features.albums.data.repository

import database.models.data.datasources.AlbumDataSource
import database.models.models.AlbumModel
import br.com.alaksion.features.albums.domain.repository.AlbumRepository
import javax.inject.Inject

internal class AlbumsRepositoryImpl @Inject constructor(
    private val albumDataSource: AlbumDataSource
) : AlbumRepository {

    override suspend fun getAlbums(): List<AlbumModel> {
        return albumDataSource.getAll()
    }

}
