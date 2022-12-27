package com.example.mvisample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.Navigator
import platform.navigation.NavigationProvider
import platform.uicomponents.theme.MviSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MviSampleTheme {
                val albumHome = rememberScreen(provider = NavigationProvider.Albums.Home)

                Navigator(screen = albumHome)
            }
        }
    }
}
