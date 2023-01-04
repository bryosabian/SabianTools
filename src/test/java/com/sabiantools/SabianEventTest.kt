package com.sabiantools

import com.sabiantools.utilities.events.*
import org.junit.Test

data class TestEventPayload(val message: String) : SabianEventPayload

data class TestEventPayloadAction(var message: String) : SabianEventPayloadAction()

class TestEvent() : SabianEvent() {
    init {
        println("Initialized event test")
    }
}

class TestChangeEvent : SabianEvent()

class TestEventObserver : SabianEventObserver() {
    override fun handle(arg: SabianEventPayload, event: SabianEvent) {
        assert(arg is TestEventPayload)
        val testArg = arg as TestEventPayload
        println("Message ${testArg.message}")
        assert(testArg.message == "Hello Test Event")
    }

    override fun getID(): String {
        return "TestEventObserver"
    }
}

class TestEventObserver2 : SabianEventObserver() {
    override fun handle(arg: SabianEventPayload, event: SabianEvent) {
        val testArg = arg as TestEventPayload
        println("Message 2 ${testArg.message}")
    }

    override fun getID(): String {
        return "TestEventObserver2"
    }
}

class TestEventChanger : SabianEventObserver() {
    override fun handle(arg: SabianEventPayload, event: SabianEvent) {
        assert(arg is TestEventPayloadAction)
        val payload = arg as TestEventPayloadAction
        payload.message = "Hello Test Event Changed"
        payload.canProceed = false
        println("Event Observer " + payload.message)
    }

    override fun getID(): String {
        return "TestEventChanger"
    }
}

class TestEventChangerDuplicate : SabianEventObserver() {
    override fun handle(arg: SabianEventPayload, event: SabianEvent) {
        assert(arg is TestEventPayloadAction)
        val payload = arg as TestEventPayloadAction
        payload.message = "Hello Test Event Changed Duplicate"
        payload.canProceed = false
        println("Event Observer Duplicate " + payload.message)
    }

    override fun getID(): String {
        return "TestEventChanger"
    }
}

class TestEventChanger2 : SabianEventObserver() {
    override fun handle(arg: SabianEventPayload, event: SabianEvent) {
        val payload = arg as TestEventPayloadAction
        println("Event Observer 2 " + payload.message)
    }

    override fun getID(): String {
        return "TestEventChanger2"
    }

}


class SabianEventTest {

    val center = SabianEventCenter.instance

    @Test
    fun testEventSubscription() {
        center.subscribeTo<TestEvent>("on.test.event", TestEventObserver())
        center.subscribeTo<TestEvent>("on.test.event", TestEventObserver2())
        val payload = TestEventPayload("Hello Test Event")
        center.trigger<TestEvent>("on.test.event", payload)
        assert(center.getEvent("on.test.event")?.totalObservers == 2)
    }

    @Test
    fun testEventAction() {
        center.subscribeTo<TestChangeEvent>("on.test.change.event", TestEventChanger())
        center.subscribeTo<TestChangeEvent>("on.test.change.event", TestEventChangerDuplicate())
        center.subscribeTo<TestChangeEvent>("on.test.change.event", TestEventChanger2())
        assert(center.getEvent("on.test.change.event")?.totalObservers == 2)
        val payload = center.triggerWithAction<TestChangeEvent>("on.test.change.event", TestEventPayloadAction("Hello Test Event")) as TestEventPayloadAction
        assert(payload.message == "Hello Test Event Changed")
        assert(!payload.canProceed)
        println(payload.message)
    }
}