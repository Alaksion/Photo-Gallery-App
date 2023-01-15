package com.example.mvisample

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import dagger.hilt.android.HiltAndroidApp
import features.albums.create.di.manageAlbumStepDi
import features.albums.details.di.albumDetailsScreenDi
import features.albums.home.di.albumHomeStepDi
import features.photos.detail.di.photoDetailsScreenModule
import features.photos.photopicker.di.photoPickerScreenModule

@HiltAndroidApp
class MviSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ScreenRegistry {
            manageAlbumStepDi()
            albumHomeStepDi()
            albumDetailsScreenDi()
            photoDetailsScreenModule()
            photoPickerScreenModule()
        }
    }

}
