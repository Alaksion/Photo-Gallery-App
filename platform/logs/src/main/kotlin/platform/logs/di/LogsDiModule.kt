package platform.logs.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import platform.logs.LogProvider
import platform.logs.loggers.AppLogger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object LogsDiModule {

    @Provides
    @Singleton
    fun provideLogger(): AppLogger {
        return LogProvider.provide()
    }

}