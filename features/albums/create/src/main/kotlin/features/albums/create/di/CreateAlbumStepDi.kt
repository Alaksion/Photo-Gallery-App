package features.albums.create.di

import cafe.adriel.voyager.core.registry.screenModule
import features.albums.create.presentation.CreateAlbumFlow
import platform.navigation.NavigationProvider

val createAlbumStepDi = screenModule {
    register<NavigationProvider.Albums.Create> {
        CreateAlbumFlow
    }

}
