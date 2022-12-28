package br.com.alaksion.features.albums.di

import br.com.alaksion.features.albums.presentation.home.HomeScreen
import cafe.adriel.voyager.core.registry.screenModule
import platform.navigation.NavigationProvider

val albumScreenDI = screenModule {
    register<NavigationProvider.Albums.Home> {
        HomeScreen
    }
}
