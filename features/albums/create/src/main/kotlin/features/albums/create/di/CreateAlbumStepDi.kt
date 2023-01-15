package features.albums.create.di

import cafe.adriel.voyager.core.registry.screenModule
import features.albums.create.presentation.ManageAlbumFlow
import platform.navigation.NavigationProvider

val manageAlbumStepDi = screenModule {
    register<NavigationProvider.Albums.Manage> {
        ManageAlbumFlow(it.type)
    }

}
