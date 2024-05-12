package com.example.androiddemo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androiddemo.SchedulerProvider
import com.example.androiddemo.data.domain.car.CarEntity
import com.example.androiddemo.data.interactor.CarUseCase
import com.example.common.helper.UiState
import com.example.design.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel  @Inject constructor(
    private val carUseCase: CarUseCase,
    private val schedulers: SchedulerProvider
): BaseViewModel() {
    var cars: List<CarEntity> = ArrayList()
    var filteredCards: List<CarEntity> = ArrayList()
    //region RXJAVA EXAMPLE
    private val _carData = MutableLiveData<UiState<List<CarEntity>>>()
    val carData: LiveData<UiState<List<CarEntity>>>
        get() = _carData

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

    //endregion

    //region KOTLIN FLOW EXAMPLE
    private val _carDataFlow = MutableStateFlow<UiState<List<CarEntity>>>(UiState.Idle)
    val carDataFlow: StateFlow<UiState<List<CarEntity>>> = _carDataFlow.asStateFlow()

    fun loadCarsFlow() {
        viewModelScope.launch {
            carUseCase.getCarListFlow()
                .onStart { _carDataFlow.value = UiState.Loading }
                .onCompletion {
                    if (it!=null) {
                        _carDataFlow.value = UiState.Error(it)
                    }
                }
                .collect {
                    cars = it
                    filteredCards = it
                    _carDataFlow.value = UiState.Success(it)
            }
        }
    }

    fun filterCarsFlow(maker: String?, model: String?) {
        viewModelScope.launch {
             _carDataFlow.value = UiState.Loading
            filteredCards = cars.filter {
                if(maker.equals("ALL",true) || maker.isNullOrEmpty()){
                    return@filter true
                }else {
                    maker.isNotEmpty() && it.make == maker
                }
            }
                .filter {
                    if(model.isNullOrEmpty() || model.equals("ALL",true)){
                        return@filter true
                    }else {
                        return@filter it.model == model
                    }
                }
            if (filteredCards.isEmpty()) {
                _carDataFlow.value = UiState.Error(RuntimeException("No cars found"))
            } else {
                _carDataFlow.value = UiState.Success(filteredCards)
            }
        }

    }
    //endregion

}