package com.sabiantools.utilities.events

import java.util.Observable

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

//    private var hasSorted: Boolean = false
//    override fun onBeforeNotify() {
//
//        super.onBeforeNotify()
//
//        if (hasSorted)
//            return
//
//        hasSorted = true
////Higher priority gets called first
//        obs.sortByDescending { (it as SabianEventObserver).getPriority() }
//    }
}