package com.sabiantools.utilities.events

import android.content.Context
import java.util.*

abstract class SabianEventObserver : Observer, Comparable<SabianEventObserver> {

    /**
     * Reacts to event changes
     */
    abstract fun handle(arg: SabianEventPayload, event: SabianEvent);

    /**
     * The unique ID of the observer
     *
     * @return
     */
    abstract fun getID(): String

    /**
     * Gets the order of execution. Higher priority means first execution
     */
    open fun getPriority(): Int {
        return 10
    }

    /**
     * The HashCode
     * @return
     */
    override fun hashCode(): Int {
        return getID().hashCode()
    }

    /**
     * Handles the event
     */
    override fun update(o: Observable?, arg: Any?) {
        //We're only dealing with events
        if (o !is SabianEvent)
            return
        //We're only dealing with event payload here
        if (arg !is SabianEventPayload)
            return
        //Don't proceed if an event action chain is broken
        if (arg is SabianEventPayloadAction && !arg.canProceed)
            return
        //React to event
        handle(arg, o)
    }

    /**
     * The equals
     * @param other
     * @return
     */
    override fun equals(other: Any?): Boolean {
        if (other !is SabianEventObserver) {
            return false
        }
        val o: SabianEventObserver = other
        return o.getID() == getID()
    }

    /**
     * Called when an observer is about to be terminated
     */
    open fun deInit(context: Context?) {
        //do nothing
    }

    /**
     * Called when an observer is about to be initialized
     */
    open fun init(context: Context?) {
        //Do nothing
    }

    /**
     * Comparer
     */
    override fun compareTo(other: SabianEventObserver): Int {
        return other.getPriority().compareTo(getPriority())
    }
}