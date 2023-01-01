package features.albums.details.di

import cafe.adriel.voyager.core.registry.screenModule
import features.albums.details.presentation.AlbumDetailsScreen
import platform.navigation.NavigationProvider

val albumDetailsScreenDi = screenModule {
    register<NavigationProvider.Albums.Details> {
        AlbumDetailsScreen(
            albumId = it.albumId
        )
    }
}