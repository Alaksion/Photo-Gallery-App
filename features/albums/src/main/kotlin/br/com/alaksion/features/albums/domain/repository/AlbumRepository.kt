package br.com.alaksion.features.albums.domain.repository

import database.models.models.AlbumModel

internal interface AlbumRepository {

    suspend fun getAlbums(): List<AlbumModel>

}
