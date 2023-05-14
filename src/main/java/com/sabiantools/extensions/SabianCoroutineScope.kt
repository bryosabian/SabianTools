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

fun <T> CoroutineScope.asyncWithSlightDelay(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    return asyncWithDelay(context, start, 300L, block)
}

fun <T> CoroutineScope.asyncWithDelay(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    delay: Long = 500L,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    return async(context, start) {
        delay(delay)
        block.invoke(this)
    }
}


fun CoroutineScope.launchAfterDelay(
    backgroundContext: CoroutineContext = EmptyCoroutineContext,
    mainContext: CoroutineContext,
    delay: Long,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: () -> Unit
) {
    val job = launchWithDelay(backgroundContext, start, delay) {
        //Do nothing
    }
    launch(mainContext) {
        job.join()
        block.invoke()
    }
}

fun CoroutineScope.launchAfterSlightDelay(
    backgroundContext: CoroutineContext = EmptyCoroutineContext,
    mainContext: CoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: () -> Unit
) {
    launchAfterDelay(backgroundContext, mainContext, 300L, start, block)
}

fun <T> CoroutineScope.runAsync(
    block: () -> T?,
    onComplete: (T?) -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    backgroundContext: CoroutineContext = Dispatchers.IO,
    mainContext: CoroutineContext = Dispatchers.Default
) {
    var error: Throwable? = null
    val promise = async(backgroundContext) {
        try {
            block()
        } catch (e: Throwable) {
            error = e
            null
        }
    }
    launch(mainContext) {
        val value = promise.await()
        error?.let { onError?.invoke(it) } ?: run {
            onComplete(value)
        }
    }
}