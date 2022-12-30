package com.example.mvisample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dagger.hilt.android.AndroidEntryPoint
import platform.navigation.NavigationProvider
import platform.uicomponents.theme.MviSampleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MviSampleTheme {
                val albumHome = rememberScreen(provider = NavigationProvider.Albums.Home)

                Navigator(screen = albumHome) { navigator ->
                    SlideTransition(navigator = navigator)
                }
            }
        }
    }
}
