package com.example.androiddemo

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

interface SchedulerProvider {
    fun mainThread(): Scheduler
    fun io(): Scheduler
}

class AppSchedulerProvider : SchedulerProvider {
    override fun mainThread(): Scheduler = AndroidSchedulers.mainThread()
    override fun io(): Scheduler = Schedulers.io()
}

class TestSchedulerProvider : SchedulerProvider {
    override fun mainThread(): Scheduler = Schedulers.trampoline()
    override fun io(): Scheduler = Schedulers.trampoline()
}