package com.sabiantools.utilities.tasks.queue

interface OnQueueTaskListener<Q> {

    fun execute(item: Q)

    fun onQueued(item: Q) {

    }

    fun onProcessing(item: Q) {

    }

    fun onCompleted(item: Q) {

    }

    fun onFail(throwable: Throwable, item: Q?) {

    }

    fun onAllCompleted(throwable: Throwable?) {

    }
}