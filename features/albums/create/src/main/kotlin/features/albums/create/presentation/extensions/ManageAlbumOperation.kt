package features.albums.create.presentation.extensions

import platform.navigation.params.ManageAlbumOperation

internal fun ManageAlbumOperation.toolbarTitle(): String {
    return when (this) {
        ManageAlbumOperation.Create -> "Create Album"
        is ManageAlbumOperation.Edit -> "Editing Album"
    }
}
