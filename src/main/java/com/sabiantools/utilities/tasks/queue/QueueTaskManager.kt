package com.sabiantools.utilities.tasks.queue

import com.sabiantools.utilities.SabianUtilities
import java.lang.ref.WeakReference
import java.util.LinkedList
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Multi thread safe queue task manager that executes multiple tasks in form of queues. Arrangement is FIFO
 *
 * Tasks can be enqueued during execution
 */
class QueueTaskManager<Q>(listener: OnQueueTaskListener<Q>) {

    val listenerReference = WeakReference(listener)

    val listener: OnQueueTaskListener<Q>?
        get() = listenerReference.get()

    private var lock = Any()

    private var queue: LinkedList<Q> = LinkedList()

    private var _isProcessing = AtomicBoolean(false)

    private var lastQueueItem: Q? = null

    private var isProcessing: Boolean
        set(value) {
            _isProcessing.set(value)
        }
        get() = _isProcessing.get()


    fun enqueue(item: Q, startImmediate: Boolean = false) {

        synchronized(lock) {
            if (!queue.contains(item)) {
                queue.add(item)
            }
        }

        if (isProcessing)
            listener?.onQueued(item)

        if (startImmediate)
            this.runNext()
    }


    fun runNext() {

        if (isProcessing) {
            SabianUtilities.WriteLog("Queue task process is ongoing so pause for the next queue")
            return
        }

        onLastQueueCompleted()

        isProcessing = true

        synchronized(lock) {
            val next = queue.poll()
            if (next != null) {
                execute(next)
            } else
                onComplete()
        }
    }

    fun fail(item: Q, throwable: Throwable, runNext: Boolean = false) {
        onLastQueueCompleted(item, throwable)
        isProcessing = false
        if (runNext)
            this.runNext()
    }

    fun failAndRunNext(item: Q, throwable: Throwable) {
        fail(item, throwable, true)
    }

    fun completeAndRunNext(item: Q) {
        complete(item, true)
    }

    fun complete(item: Q, runNext: Boolean = false) {
        onLastQueueCompleted(item)
        isProcessing = false
        if (runNext)
            this.runNext()
    }

    private fun execute(item: Q) {
        listener?.onProcessing(item)
        try {
            lastQueueItem = item
            listener?.execute(item)
        } catch (e: Throwable) {
            listener?.onFail(e, item)
        }
    }

    private fun onLastQueueCompleted(last: Q? = null, throwable: Throwable? = null) {
        synchronized(lock) {
            val mLast = last ?: lastQueueItem
            mLast?.let {
                if (throwable != null)
                    listener?.onFail(throwable, it)
                else
                    listener?.onCompleted(it)
            }
            lastQueueItem = null
        }
    }


    private fun onComplete() {
        isProcessing = false
        listener?.onAllCompleted(null)
        onLastQueueCompleted()
    }
}