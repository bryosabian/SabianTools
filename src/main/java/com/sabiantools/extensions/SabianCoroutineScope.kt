package com.sabiantools.extensions

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.launchWithSlightDelay(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launchWithDelay(context, start, 300L, block)
}

fun CoroutineScope.launchWithDelay(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    delay: Long = 500L,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(context, start) {
        delay(delay)
        block.invoke(this)
    }
}