package com.example.androiddemo.module

import android.content.Context
import com.example.androiddemo.AppSchedulerProvider
import com.example.androiddemo.SchedulerProvider
import com.example.common.network.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    @Singleton
    fun provideNetworkManager(@ApplicationContext context: Context):NetworkManager = NetworkManager(context)

    @Provides
    @Singleton
    fun providesScheduler():SchedulerProvider = AppSchedulerProvider()

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }



}
