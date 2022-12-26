package br.com.alaksion.features.albums.domain.repository

import br.com.alaksion.database.domain.models.AlbumModel

internal interface AlbumRepository {

    suspend fun getAlbums(): List<AlbumModel>

}