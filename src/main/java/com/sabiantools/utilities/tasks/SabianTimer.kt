package com.sabiantools.utilities.tasks


import java.util.*
import kotlin.concurrent.fixedRateTimer

class SabianTimer(private var seconds: Long = 1, private val task: () -> Unit) {

    private val mSeconds: Long
        get() = seconds * 1000

    private var timer: Timer? = null

//    private val mainHandler: Handler by lazy {
//        Handler(Looper.getMainLooper())
//    }
//
//    private val updateTask = object : Runnable {
//        override fun run() {
//            task.invoke()
//            mainHandler.postDelayed(this, mSeconds)
//        }
//    }

    fun start() {
        timer?.let {
            it.cancel()
            timer = null
        }
        timer =
            fixedRateTimer("SabianTimer : ${this.javaClass.name}", false, 0L, mSeconds) {
                task.invoke()
            }
    }

    fun stop() {
        timer?.let {
            it.cancel()
            timer = null
        }
    }
}