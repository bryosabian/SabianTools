package com.sabiantools.utilities.events

import kotlin.jvm.Throws

/**
 * The event center
 */
open class SabianEventCenter protected constructor() {

    /**
     * The events
     */
    protected var events: LinkedHashMap<String, SabianEvent> = linkedMapOf()

    /**
     * Registers or updates a new event
     * @param channel The event subscription
     */
    fun register(channel: String, event: SabianEvent) {
        events[channel] = event
    }

    /**
     * Gets an event with the subscription channel
     */
    fun getEvent(channel: String): SabianEvent? {
        return events[channel]
    }


    /**
     * Gets an event or creates a new one under the channel subscription
     * Note for it to be automatically created, the event class must have an empty constructor or it'll crash
     * @param channel The event
     */
    @Throws(Exception::class)
    inline fun <reified T : SabianEvent> getEventOrCreate(channel: String): SabianEvent {
        var event = getEvent(channel)
        if (event == null) {
            val newEvent = T::class.java.newInstance()
            register(channel, newEvent)
            event = newEvent
        }
        return event
    }


    /**
     * Subscribes to an event or crashes if event subscription if not found.
     * See [subscribeTo] to avoid crash when channel is not found to automatically create
     * @param channel The event channel subscription
     * @param observer The observer
     */
    @Throws(IllegalArgumentException::class)
    fun subscribe(channel: String, observer: SabianEventObserver) {
        val event = getEvent(channel)
            ?: throw IllegalArgumentException("No event found with the subscription")
        event.addObserver(observer)
        register(channel, event)
    }

    /**
     * Subscribes to an event or crashes if event subscription if not found.
     * See [subscribeTo] to avoid crash when channel is not found to automatically create
     * @param channel The event channel subscription
     * @param observers The observers
     */
    @Throws(IllegalArgumentException::class)
    fun subscribe(channel: String, observers: Array<SabianEventObserver>) {
        val sortedObservers = observers.sorted()
        sortedObservers.forEach {
            subscribe(channel, it)
        }
    }

    /**
     * Subscribes to an event. If no event is found with the channel subscription, a new event is registered automatically
     * under that channel. Note for it to be automatically created, the event class must have an empty constructor or it'll crash
     * See [subscribe] to avoid automatic registration
     * @param channel The subscription channel
     * @param observer The observer
     */
    inline fun <reified T : SabianEvent> subscribeTo(
        channel: String,
        observer: SabianEventObserver
    ) {
        val event = getEventOrCreate<T>(channel)
        event.addObserver(observer)
        register(channel, event)
    }

    /**
     * Subscribes to an event. If no event is found with the channel subscription, a new event is registered automatically
     * under that channel. Note for it to be automatically created, the event class must have an empty constructor or it'll crash
     * See [subscribe] to avoid automatic registration
     * @param channel The subscription channel
     * @param observers The observers
     */
    inline fun <reified T : SabianEvent> subscribeTo(
        channel: String,
        observers: Array<SabianEventObserver>
    ) {
        val sortedObservers = observers.sorted()
        sortedObservers.forEach {
            subscribeTo<T>(channel, it)
        }
    }


    /**
     * Triggers a new event or throws an exception if subscription is not found
     */
    @Throws(IllegalArgumentException::class)
    inline fun <reified T : SabianEvent> trigger(channel: String, payload: SabianEventPayload) {
        val event = getEventOrCreate<T>(channel)
        event.trigger(payload)
    }

    /**
     * Triggers a new event and returns the payload or throws an exception if subscription is not found
     */
    @Throws(IllegalArgumentException::class)
    inline fun <reified T : SabianEvent> triggerAndReturn(
        channel: String,
        payload: SabianEventPayload
    ): SabianEventPayload {
        trigger<T>(channel, payload)
        return payload
    }

    /**
     * Triggers and returns an action or throws an exception if subscription is not found
     */
    @Throws(IllegalArgumentException::class)
    inline fun <reified T : SabianEvent> triggerWithAction(
        channel: String,
        payload: SabianEventPayloadAction
    ): SabianEventPayloadAction {
        trigger<T>(channel, payload)
        return payload
    }

    /**
     * Removes this observer from this channel
     */
    fun unSubscribe(channel: String, observer: SabianEventObserver) {
        val event = getEvent(channel) ?: return
        event.deleteObserver(observer)
        events[channel] = event
    }

    /**
     * Removes these observers from this channel
     */
    fun unSubscribe(channel: String, observers: List<SabianEventObserver>) {
        val event = getEvent(channel) ?: return
        observers.forEach {
            event.deleteObserver(it)
        }
        events[channel] = event
    }

    /**
     * Removes all observers to this channel
     */
    fun unSubscribeAll(channel: String) {
        val event = getEvent(channel) ?: return
        event.deleteObservers()
        events[channel] = event
    }

    /**
     * Removes all events and observers
     */
    fun clear() {
        events.clear()
    }

    /**
     * Same as clear. Will find more use cases
     */
    fun refresh() {
        if (events.isEmpty())
            return
        clear()
    }

    /**
     * Gets total subscribers if any
     */
    fun getTotalSubscribers(channel: String): Int {
        val event = getEvent(channel) ?: return 0
        return event.totalObservers
    }


    /**
     * Singletons
     */
    companion object {
        @JvmStatic
        val instance: SabianEventCenter by lazy {
            SabianEventCenter()
        }
    }
}