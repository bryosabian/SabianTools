package com.sabiantools.utilities.tasks.debounce

interface OnDebounceTaskListener<T> {
    fun onTaskComplete(item: T)

    fun onTaskLoading() {

    }

    fun onTaskError(e: Throwable) {

    }

    fun onTaskCancel() {

    }
}