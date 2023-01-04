package features.photos.di

import cafe.adriel.voyager.core.registry.screenModule
import features.photos.presentation.picktype.PhotoAddTypeScreen
import platform.navigation.NavigationProvider

val photoScreenModule = screenModule {
    register<NavigationProvider.Photos.PickPhotoSource> {
        PhotoAddTypeScreen(
            albumId = it.albumId
        )
    }
}
