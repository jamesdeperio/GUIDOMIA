package com.example.androiddemo.data.interactor

import com.example.androiddemo.data.domain.car.CarEntity
import com.example.androiddemo.data.repository.CarRepository
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class CarUseCase  @Inject constructor(private val repository: CarRepository) {
    open fun getCarList(): Observable<List<CarEntity>> {
        return repository.getCarList()
    }

    open suspend fun getCarListFlow(): Flow<List<CarEntity>> {
        return repository.getCarListFlow()
    }
}