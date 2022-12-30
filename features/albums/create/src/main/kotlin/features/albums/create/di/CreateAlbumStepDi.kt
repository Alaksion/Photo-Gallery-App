package features.albums.create.di

import cafe.adriel.voyager.core.registry.screenModule
import features.albums.create.presentation.NameScreen
import platform.navigation.NavigationProvider

val createAlbumStepDi = screenModule {
    register<NavigationProvider.Albums.Create> {
        NameScreen
    }

}
