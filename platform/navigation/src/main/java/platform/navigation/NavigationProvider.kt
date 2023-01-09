package platform.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class NavigationProvider : ScreenProvider {

    sealed class Albums : NavigationProvider() {
        object Home : Albums()

        object Create : Albums()

        data class Details(val albumId: Int) : Albums()
    }

    sealed class Photos : NavigationProvider() {
        data class PickPhotoSource(val albumId: Int) : Photos()

        data class PhotoDetails(val photoId: Int) : Photos()
    }
}
