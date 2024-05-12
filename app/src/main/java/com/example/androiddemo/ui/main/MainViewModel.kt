package com.example.androiddemo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androiddemo.SchedulerProvider
import com.example.androiddemo.data.domain.car.CarEntity
import com.example.androiddemo.data.interactor.CarUseCase
import com.example.common.helper.UiState
import com.example.design.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject


@HiltViewModel
class MainViewModel  @Inject constructor(
    private val carUseCase: CarUseCase,
    private val schedulers: SchedulerProvider
): BaseViewModel() {
    private val _carData = MutableLiveData<UiState<List<CarEntity>>>()
    val carData: LiveData<UiState<List<CarEntity>>>
        get() = _carData

    var cars: List<CarEntity> = ArrayList()
    var filteredCards: List<CarEntity> = ArrayList()

    fun loadCars() {
        carUseCase.getCarList()
            .observeOn(schedulers.mainThread())
            .doOnSubscribe {
                _carData.value = UiState.Loading
            }.subscribe({
                cars = it
                filteredCards = it
                _carData.value = UiState.Success(it)
            }, {
                _carData.value = UiState.Error(it)
            })
            .addTo(disposable)
    }

    fun filterCars(maker: String?, model: String?) {
        Observable.just(cars)
            .observeOn(schedulers.mainThread())
            .doOnSubscribe {
                _carData.value = UiState.Loading
            }.subscribe {
                filteredCards = it.filter {
                    if(maker.equals("ALL",true) || maker.isNullOrEmpty()){
                        return@filter true
                    }else {
                        maker.isNotEmpty() && it.make == maker
                    }
                }.filter {
                    if(model.isNullOrEmpty() || model.equals("ALL",true)){
                       return@filter true
                    }else {
                     return@filter it.model == model
                    }
                }
                if (filteredCards.isEmpty()) {
                    _carData.value = UiState.Error(RuntimeException("No cars found"))
                } else {
                    _carData.value = UiState.Success(filteredCards)
                }
            }
            .addTo(disposable)
    }
}