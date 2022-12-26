package br.com.alaksion.features.albums.di

import br.com.alaksion.database.data.datasources.AlbumDataSource
import br.com.alaksion.features.albums.data.repository.AlbumsRepositoryImpl
import br.com.alaksion.features.albums.domain.repository.AlbumRepository
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
    fun provideAlbumRepository(dataSource: AlbumDataSource): AlbumRepository {
        return AlbumsRepositoryImpl(dataSource)
    }


}