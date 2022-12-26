package com.example.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher() = Dispatchers.Default

    @IODispatcher
    @Provides
    fun provideIODispatcher() = Dispatchers.IO


    @MainDispatcher
    @Provides
    fun provideMainDispatcher() = Dispatchers.Main

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IODispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher
