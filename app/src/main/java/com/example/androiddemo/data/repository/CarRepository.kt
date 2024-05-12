package com.example.androiddemo.data.repository

import AppDatabase
import android.content.Context
import com.example.androiddemo.data.domain.car.CarEntity
import com.example.androiddemo.data.domain.car.CarResponse
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.InputStream
import java.nio.charset.StandardCharsets
import javax.inject.Inject


class CarRepository  @Inject constructor( private val service: CarService, private val context: Context) {

    private val userBox = AppDatabase.store.boxFor(CarEntity::class.java)

    fun getCarList(): Observable<List<CarEntity>> {
        return Observable.just( userBox.all)
            .flatMap { localCars ->
                if (localCars.isNotEmpty()) {
                    return@flatMap Observable.just(localCars)
                } else {
                    return@flatMap Observable.fromCallable {
                        val inputStream: InputStream = context.assets.open("api.json")
                        val size = inputStream.available()
                        val buffer = ByteArray(size)
                        inputStream.read(buffer)
                        inputStream.close()
                        return@fromCallable Gson().fromJson(String(buffer, StandardCharsets.UTF_8), CarResponse::class.java)
                    }.doOnNext {
                        userBox.put(it)
                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .cache()

    }

}