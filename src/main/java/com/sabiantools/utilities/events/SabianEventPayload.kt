package com.sabiantools.utilities.events

/**
 * Payload
 */
interface SabianEventPayload

/**
 * A payload that has an action chained event
 */
abstract class SabianEventPayloadAction : SabianEventPayload {

    /**
     * Whether the action chain is unbroken. Always set to true initially
     *
     */
    var canProceed: Boolean = true

    /**
     * Action to be resumed when event chain is resumed/unbroken
     */
    var proceedAction: (() -> Unit)? = null

    /**
     * Proceeds with the event chain if it was broken
     */
    fun proceed(force: Boolean = true) {
        if (!canProceed && !force)
            return
        proceedAction?.invoke()
    }

    /**
     * Sets the action to be resumed when event chain is resumed/unbroken
     */
    open fun setProceedAction(action: () -> Unit): SabianEventPayloadAction {
        this.proceedAction = action
        return this
    }

    /**
     * Whether the event chain can proceed. Setting it to false breaks and terminates the event chain.
     *
     * Usually called by observers i.e [SabianEventObserver]s
     */
    fun setCanProceed(can: Boolean): SabianEventPayloadAction {
        this.canProceed = can
        return this
    }
}