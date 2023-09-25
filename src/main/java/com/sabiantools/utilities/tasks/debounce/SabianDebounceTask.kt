package com.sabiantools.utilities.tasks.debounce

import com.sabiantools.utilities.SabianUtilities
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

/**
 * A debounce task executable
 */
open class SabianDebounceTask<T>(
        var debounceRate: Long, listener: OnDebounceTaskListener<T>?,
        protected open var defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
        protected open var mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : CoroutineScope {

    private var taskJob: Job? = null


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

        onTaskListener?.onTaskLoading()

        taskJob?.cancel()

        taskJob = launch(defaultDispatcher) {
            if (debounceRate > 0)
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
                onTaskListener?.onTaskCancel()
            } else {
                error?.let {
                    onTaskListener?.onTaskError(it)
                } ?: run {
                    onTaskListener?.onTaskComplete(result)
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
                    onTaskListener?.onTaskCancel()
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
        get() = SupervisorJob() + defaultDispatcher

}