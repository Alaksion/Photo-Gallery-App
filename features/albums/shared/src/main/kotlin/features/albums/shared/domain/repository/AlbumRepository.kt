package features.albums.shared.domain.repository

import features.albums.shared.domain.model.CreateAlbumDTO
import platform.database.models.models.AlbumWithPhotosModel
import platform.database.models.models.album.AlbumModel

interface AlbumRepository {

    suspend fun getAlbums(): List<AlbumModel>

    suspend fun getAlbumById(id: Int): AlbumWithPhotosModel

    suspend fun createAlbum(data: CreateAlbumDTO)

    suspend fun deleteAlbum(data: AlbumModel)

}
