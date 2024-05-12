package com.example.design.listener

import android.os.SystemClock
import android.view.View
import kotlin.math.abs

abstract class OnThrottleClickListener(private val delayInMillis: Long=750) : View.OnClickListener {
    private var prevTimestamp: Long = 0
    abstract fun onThrottleClick(v: View)

    override fun onClick(clickedView: View) {
        val currentTimestamp = SystemClock.uptimeMillis()
        if (prevTimestamp == 0L || abs(currentTimestamp - prevTimestamp) > delayInMillis) {
            prevTimestamp = currentTimestamp
            onThrottleClick(clickedView)
        }
    }
}

fun View.setOnThrottleClickListener(delayInMillis:Long=750,callback:(view:View)->Unit){
    this.setOnClickListener(object :OnThrottleClickListener(delayInMillis){
        override fun onThrottleClick(v: View) {
            callback(v)
        }
    })
}