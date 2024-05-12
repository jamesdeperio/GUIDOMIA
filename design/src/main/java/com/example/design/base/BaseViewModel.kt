package com.example.design.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel: ViewModel() {
    val disposable = CompositeDisposable()
    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}