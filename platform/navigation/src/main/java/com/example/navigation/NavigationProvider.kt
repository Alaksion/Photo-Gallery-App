package com.example.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class NavigationProvider : ScreenProvider {

    sealed class Albums : NavigationProvider() {
        object Home : Albums()
    }
}
