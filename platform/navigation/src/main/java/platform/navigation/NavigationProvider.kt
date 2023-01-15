package platform.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider
import platform.navigation.params.CreateAlbumOperation

sealed class NavigationProvider : ScreenProvider {

    sealed class Albums : NavigationProvider() {
        object Home : Albums()

        data class Manage(val type: CreateAlbumOperation) : Albums()

        data class Details(val albumId: Int) : Albums()
    }

    sealed class Photos : NavigationProvider() {
        data class PickPhotoSource(val albumId: Int) : Photos()

        data class PhotoDetails(val photoId: Int) : Photos()
    }
}
