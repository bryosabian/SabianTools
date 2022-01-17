package com.sabiantools.utilities.tasks.debounce

import com.sabiantools.utilities.SabianUtilities
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

/**
 * A debounce task executable
 */
open class SabianDebounceTask<T>(
    private var debounceRate: Long, listener: OnDebounceTaskListener<T>?
) : CoroutineScope {

    private var taskJob: Job? = null

    protected open var defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    protected open var mainDispatcher: CoroutineDispatcher = Dispatchers.Main

    private var listenerReference: WeakReference<OnDebounceTaskListener<T>> =
        WeakReference(listener)

    private var onTaskListener: OnDebounceTaskListener<T>? = listenerReference.get()

    private var isComplete = false
    private var error: Throwable? = null
    private var result: T? = null

    private fun reset() {
        isComplete = false
        error = null
        result = null
    }

    /**
     * Executes a cancellable task.
     * @param task The task to execute. It's allowed to throw
     */
    fun execute(task: () -> T) {

        reset()

        onTaskListener?.onLoading()

        taskJob?.cancel()

        taskJob = launch(defaultDispatcher) {
            delay(debounceRate)
            try {
                result = task.invoke()
            } catch (e: Exception) {
                error = e
            } finally {
                isComplete = true
            }
        }

        launch(mainDispatcher) {

            taskJob?.join()

            val isCancelled = !isComplete

            if (isCancelled) {
                onTaskListener?.onCancel()
            } else {
                error?.let {
                    onTaskListener?.onError(it)
                } ?: run {
                    onTaskListener?.onComplete(result!!)
                }
            }

            reset()
        }
    }

    /**
     * Closes the task
     */
    fun close() {
        try {
            taskJob?.let {
                if (it.isActive) {
                    it.cancel()
                    onTaskListener?.onCancel()
                    SabianUtilities.WriteLog("Task job has been cancelled")
                }
                taskJob = null
            }
            onTaskListener = null
            reset()
        } catch (e: Exception) {
            SabianUtilities.WriteLog("Could not cancel task job ${e.message}")
            e.printStackTrace()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

}