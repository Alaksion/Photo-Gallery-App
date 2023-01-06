package features.photos.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import features.photos.data.PhotoRepository
import features.photos.data.PhotoRepositoryImpl
import platform.database.models.data.datasources.PhotoDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object PhotoDiModule {

    @Singleton
    @Provides
    fun providePhotoRepository(dataSource: PhotoDataSource): PhotoRepository {
        return PhotoRepositoryImpl(dataSource)
    }

}
