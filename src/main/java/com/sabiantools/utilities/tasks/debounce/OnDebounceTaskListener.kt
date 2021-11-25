package com.sabiantools.utilities.tasks.debounce

interface OnDebounceTaskListener<T> {
    fun onComplete(item: T)

    fun onLoading() {

    }

    fun onError(e: Throwable) {

    }

    fun onCancel() {

    }
}