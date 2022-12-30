package features.albums.home.di

import cafe.adriel.voyager.core.registry.screenModule
import features.albums.home.presentation.HomeScreen
import platform.navigation.NavigationProvider

val albumHomeStepDi = screenModule {
    register<NavigationProvider.Albums.Home> {
        HomeScreen
    }
}
