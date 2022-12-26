package br.com.alaksion.database.di

import android.content.Context
import androidx.room.Room
import br.com.alaksion.database.AppDatabase
import br.com.alaksion.database.data.datasources.AlbumDataSource
import br.com.alaksion.database.data.datasources.AlbumDataSourceImplementation
import br.com.alaksion.database.data.validator.AlbumDataSourceValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mvi-sample-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAlbumValidator() = AlbumDataSourceValidator

    @Provides
    @Singleton
    fun provideAlbumDataSource(
        database: AppDatabase,
        validator: AlbumDataSourceValidator
    ): AlbumDataSource {
        return AlbumDataSourceImplementation(
            albumDao = database.albumDao(),
            validator = validator
        )
    }

}
