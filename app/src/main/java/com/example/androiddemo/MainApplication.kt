package com.example.androiddemo

import AppDatabase
import android.app.Application
import com.example.androiddemo.data.domain.car.MyObjectBox
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
        val inMemoryBoxStore = MyObjectBox.builder()
            .androidContext(this)
            .inMemory("test-db")
            .build()
    }
}