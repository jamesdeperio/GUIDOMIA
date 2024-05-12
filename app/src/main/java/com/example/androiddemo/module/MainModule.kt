package com.example.androiddemo.module

import android.content.Context
import com.example.androiddemo.data.interactor.CarUseCase
import com.example.androiddemo.data.repository.CarRepository
import com.example.androiddemo.data.repository.CarService
import com.example.common.network.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainDataSourceModule {
    @Provides
    @Singleton
    fun provideService(networkManager: NetworkManager) : CarService =
        networkManager.create(CarService::class.java) as CarService



}


@Module
@InstallIn(FragmentComponent::class)
object MainModule {

    @Provides
    @ActivityScoped
    fun provideRepository(service: CarService, @ApplicationContext context:Context) : CarRepository {
        return CarRepository(service,context)
    }

    @Provides
    @ActivityScoped
    fun provideUseCase(repository: CarRepository) : CarUseCase =
        CarUseCase(repository)

}