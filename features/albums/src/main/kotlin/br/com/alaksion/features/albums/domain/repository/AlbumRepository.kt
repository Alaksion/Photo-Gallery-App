package br.com.alaksion.features.albums.domain.repository

import br.com.alaksion.features.albums.domain.model.CreateAlbumDTO
import database.models.models.AlbumModel

internal interface AlbumRepository {

    suspend fun getAlbums(): List<AlbumModel>

    suspend fun createAlbum(data: CreateAlbumDTO)

}
