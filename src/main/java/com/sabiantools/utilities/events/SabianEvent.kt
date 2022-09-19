package com.sabiantools.utilities.events

import java.util.*

abstract class SabianEvent : Observable() {
    /**
     * Adds a new observer
     */
    @Synchronized
    fun addObserver(o: SabianEventObserver) {
        super.addObserver(o)
    }

    /**
     * Triggers a new event
     */
    fun trigger(payload: SabianEventPayload) {
        setChanged()
        notifyObservers(payload)
    }

    /**
     * Observers
     */
    val totalObservers: Int
        get() = countObservers()
}