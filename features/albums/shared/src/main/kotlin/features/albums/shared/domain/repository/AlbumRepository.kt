package features.albums.shared.domain.repository

import database.models.models.AlbumModel
import features.albums.shared.domain.model.CreateAlbumDTO

interface AlbumRepository {

    suspend fun getAlbums(): List<AlbumModel>

    suspend fun getAlbumById(id: Int): AlbumModel

    suspend fun createAlbum(data: CreateAlbumDTO)

}
