package com.example.mvisample

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import dagger.hilt.android.HiltAndroidApp
import features.albums.create.di.createAlbumStepDi
import features.albums.home.di.albumHomeStepDi

@HiltAndroidApp
class MviSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ScreenRegistry {
            createAlbumStepDi()
            albumHomeStepDi()
        }
    }

}
