package features.albums.shared.domain.repository

import platform.database.models.models.album.AlbumModel
import platform.database.models.models.AlbumWithPhotosModel
import features.albums.shared.domain.model.CreateAlbumDTO

interface AlbumRepository {

    suspend fun getAlbums(): List<AlbumModel>

    suspend fun getAlbumById(id: Int): AlbumWithPhotosModel

    suspend fun createAlbum(data: CreateAlbumDTO)

}
