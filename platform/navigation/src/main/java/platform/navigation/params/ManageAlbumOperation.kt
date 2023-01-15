package platform.navigation.params

sealed class ManageAlbumOperation {
    data class Edit(val albumId: Int) : ManageAlbumOperation()
    object Create : ManageAlbumOperation()
}
