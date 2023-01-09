package platform.database.models.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import platform.database.models.AppDatabase
import platform.database.models.UriPermissionHandler
import platform.database.models.data.datasources.AlbumDataSource
import platform.database.models.data.datasources.AlbumDataSourceImplementation
import platform.database.models.data.datasources.PhotoDataSource
import platform.database.models.data.datasources.PhotoDataSourceImpl
import platform.database.models.data.validator.AlbumDataSourceValidator
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
            validator = validator,
        )
    }

    @Provides
    @Singleton
    fun providePhotoDataSource(
        database: AppDatabase,
        uriHandler: UriPermissionHandler
    ): PhotoDataSource {
        return PhotoDataSourceImpl(
            photoDao = database.photoDao(),
            uriPermission = uriHandler
        )
    }

    @Provides
    @Singleton
    fun providePersistentUriManager(@ApplicationContext context: Context): UriPermissionHandler {
        return UriPermissionHandler(context)
    }

}
