package features.photos.photopicker.di

import cafe.adriel.voyager.core.registry.screenModule
import features.photos.photopicker.presentation.pickertype.PhotoAddTypeScreen
import platform.navigation.NavigationProvider

val photoPickerScreenModule = screenModule {

    register<NavigationProvider.Photos.PickPhotoSource> {
        PhotoAddTypeScreen(
            albumId = it.albumId
        )
    }

}
