package com.example.mvisample

import android.app.Application
import br.com.alaksion.features.albums.di.albumScreenDI
import cafe.adriel.voyager.core.registry.ScreenRegistry
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MviSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ScreenRegistry {
            albumScreenDI()
        }
    }

}
