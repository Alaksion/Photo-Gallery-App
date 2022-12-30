package features.albums.shared.domain.repository

import features.albums.shared.domain.model.CreateAlbumDTO
import database.models.models.AlbumModel

interface AlbumRepository {

    suspend fun getAlbums(): List<AlbumModel>

    suspend fun createAlbum(data: CreateAlbumDTO)

}
