package com.sabiantools.utilities.events

/**
 * Payload
 */
interface SabianEventPayload

/**
 * A payload that has a proceed action
 */
abstract class SabianEventPayloadAction : SabianEventPayload {

    var canProceed: Boolean = true

    fun proceed(action: () -> Unit) {
        if (!canProceed)
            return
        action.invoke()
    }
}