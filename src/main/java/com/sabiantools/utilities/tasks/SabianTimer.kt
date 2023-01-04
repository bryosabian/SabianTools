package com.sabiantools.utilities.tasks


import java.util.*
import kotlin.concurrent.fixedRateTimer

class SabianTimer(var seconds: Long = 1, private val task: () -> Unit) {

    private val milliseconds: Long
        get() = seconds * 1000

    private var timer: Timer? = null

    private var isPaused: Boolean = false

    fun start() {

        isPaused = false

        timer?.let {
            it.cancel()
            timer = null
        }
        timer =
            fixedRateTimer("SabianTimer : ${this.javaClass.name}", false, 0L, milliseconds) {
                if (isPaused)
                    return@fixedRateTimer
                task.invoke()
            }
    }

    fun stop() {
        isPaused = true
        timer?.let {
            it.cancel()
            timer = null
        }
        isPaused = false
    }


    fun pause() {
        isPaused = true
    }

    fun resume() {
        isPaused = false
    }
}