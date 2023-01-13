package features.photos.detail.di

import cafe.adriel.voyager.core.registry.screenModule
import features.photos.detail.presentation.PhotoDetailsScreen
import platform.navigation.NavigationProvider

val photoDetailsScreenModule = screenModule {

    register<NavigationProvider.Photos.PhotoDetails> {
        PhotoDetailsScreen(it.photoId)
    }

}
