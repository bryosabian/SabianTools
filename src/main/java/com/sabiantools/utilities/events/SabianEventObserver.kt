package com.sabiantools.utilities.events

import java.util.*

abstract class SabianEventObserver : Observer {

    /**
     * Handles
     */
    abstract fun handle(arg: SabianEventPayload, event: SabianEvent);

    /**
     * The unique ID of the observer
     *
     * @return
     */
    abstract fun getID(): String

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
        if (o !is SabianEvent)
            return
        if (arg !is SabianEventPayload)
            return
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
}