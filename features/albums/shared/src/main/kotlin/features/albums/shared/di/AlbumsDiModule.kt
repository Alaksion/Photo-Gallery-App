package features.albums.shared.di

import platform.database.models.data.datasources.AlbumDataSource
import features.albums.shared.repository.AlbumsRepositoryImpl
import features.albums.shared.domain.repository.AlbumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AlbumsDiModule {

    @Provides
    @Singleton
    fun provideAlbumRepository(dataSource: platform.database.models.data.datasources.AlbumDataSource): AlbumRepository {
        return AlbumsRepositoryImpl(dataSource)
    }


}
