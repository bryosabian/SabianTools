package com.sabiantools.utilities.tasks.search

import com.sabiantools.utilities.SabianUtilities
import com.sabiantools.utilities.tasks.search.OnLinearDataSearchListener
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import java.util.regex.Pattern
import kotlin.coroutines.CoroutineContext

/**
 * Utility class for searching data in lists with non-blocking aspects
 * @author bryosabian
 */
abstract class SabianLinearDataSearcher<T>(
    private var clazz: Class<T>,
    listener: OnLinearDataSearchListener?
) : CoroutineScope {

    private var searchJob: Job? = null
    private val debounceRate: Long = 500
    private var contents: List<Any> = listOf()
    private var newContent: ArrayList<Any> = arrayListOf()
    private var query: String = ""

    protected open var defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    protected open var mainDispatcher: CoroutineDispatcher = Dispatchers.Main

    private var listenerReference: WeakReference<OnLinearDataSearchListener> =
        WeakReference(listener)

    private var onSearchListener: OnLinearDataSearchListener?

    init {
        onSearchListener = listenerReference.get()
    }


    /**
     * What to search for
     */
    abstract fun getSearchCriteria(content: T): Array<String?>

    /**
     * Searches through the list using the query provided non-blockingly
     */
    fun search(contents: List<Any>, query: String) {
        this.contents = contents
        this.query = query
        onSearchListener?.onSearching()
        searchJob?.cancel()
        searchJob = launch(defaultDispatcher) {
            delay(debounceRate)
            searchItems(query)
        }
    }

    /**
     * Searches through the items
     */
    protected open fun searchItems(query: String) {

        newContent = arrayListOf()

        val pattern = Pattern.compile(".*$query.*", Pattern.CASE_INSENSITIVE)

        LoopContentQ@ for (content in contents) {

            if (!clazz.isInstance(content))
                continue@LoopContentQ

            var matches = false
            val searchCriteria = getSearchCriteria(content as T)

            val searchList = searchCriteria.filterNotNull()
            searchList.forEach { search ->
                if (pattern.matcher(search).matches() && !matches) {
                    matches = true
                }
            }
            if (matches) {
                newContent.add(content)
            }
        }

        afterSearch()
    }

    /**
     * Called after search operation is completed
     */
    private fun afterSearch() {
        launch(mainDispatcher) {
            searchJob?.join()
            onSearchListener?.onSearched(newContent)
        }
    }

    /**
     * Cancels the search operation
     */
    fun cancel() {
        try {
            searchJob?.let {
                if (it.isActive) {
                    it.cancel()
                    onSearchListener?.onCancel()
                    SabianUtilities.WriteLog("Search job has been cancelled")
                }
            }
            onSearchListener = null
        } catch (e: Exception) {
            SabianUtilities.WriteLog("Could not cancel search job ${e.message}")
            e.printStackTrace()
        }
    }

    /**
     * Gets the query that was used for the search
     */
    fun getQuery(): String {
        return query
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}