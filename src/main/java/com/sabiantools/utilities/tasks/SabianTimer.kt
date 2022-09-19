package com.sabiantools.utilities.tasks


import java.util.*
import kotlin.concurrent.fixedRateTimer

class SabianTimer(private var seconds: Long = 1, private val task: () -> Unit) {

    private val milliseconds: Long
        get() = seconds * 1000

    private var timer: Timer? = null

    fun start() {
        timer?.let {
            it.cancel()
            timer = null
        }
        timer =
            fixedRateTimer("SabianTimer : ${this.javaClass.name}", false, 0L, milliseconds) {
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