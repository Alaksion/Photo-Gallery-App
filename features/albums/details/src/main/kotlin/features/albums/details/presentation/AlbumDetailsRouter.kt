package features.albums.details.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import platform.navigation.NavigationProvider
import platform.navigation.params.CreateAlbumOperation

internal sealed class AlbumDetailDestination {
    object GoBack : AlbumDetailDestination()

    data class Edit(val albumId: Int) : AlbumDetailDestination()
    data class AddPhotos(val albumId: Int) : AlbumDetailDestination()
    data class PhotoDetail(val photoId: Int) : AlbumDetailDestination()
}

internal class AlbumDetailsRouter(private val navigator: Navigator) {

    fun handleNavigation(
        destination: AlbumDetailDestination
    ) {
        when (destination) {
            AlbumDetailDestination.GoBack -> navigator.pop()
            is AlbumDetailDestination.AddPhotos -> {
                val screen = ScreenRegistry.get(
                    NavigationProvider.Photos.PickPhotoSource(destination.albumId)
                )
                navigator.push(screen)
            }

            is AlbumDetailDestination.PhotoDetail -> {
                val screen = ScreenRegistry.get(
                    NavigationProvider.Photos.PhotoDetails(destination.photoId)
                )
                navigator.push(screen)
            }

            is AlbumDetailDestination.Edit -> {
                val screen = ScreenRegistry.get(
                    NavigationProvider.Albums.Manage(
                        CreateAlbumOperation.Edit(destination.albumId)
                    )
                )
                navigator.push(screen)
            }
        }
    }

    companion object {
        @Composable
        fun rememberAlbumDetailRouter(): AlbumDetailsRouter {
            val navigator = LocalNavigator.current

            return remember {
                navigator?.let { AlbumDetailsRouter(it) } ?: error("navigator not found")
            }
        }
    }

}
