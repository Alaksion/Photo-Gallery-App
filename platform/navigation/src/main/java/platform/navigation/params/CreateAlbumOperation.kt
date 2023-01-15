package platform.navigation.params

sealed class CreateAlbumOperation {
    data class Edit(val albumId: Int) : CreateAlbumOperation()
    object Create : CreateAlbumOperation()
}
