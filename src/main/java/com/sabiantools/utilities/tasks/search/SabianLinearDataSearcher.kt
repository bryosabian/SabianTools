package com.sabiantools.utilities.tasks.search

import com.sabiantools.utilities.SabianUtilities
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
    var debounceRate: Long = 500
    protected var contents: List<Any> = listOf()
    protected var newContent: ArrayList<Any> = arrayListOf()
    private var query: String = ""

    protected open var defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    protected open var mainDispatcher: CoroutineDispatcher = Dispatchers.Main

    private var listenerReference: WeakReference<OnLinearDataSearchListener> =
        WeakReference(listener)

    private var onSearchListener: OnLinearDataSearchListener?

    private var isComplete = false

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

        var error: Throwable? = null

        beforeSearch()

        searchJob?.cancel()
        searchJob = launch(defaultDispatcher) {
            try {
                delay(debounceRate)
                filterSearch()
                searchItems(query)
                isComplete = true
            } catch (e: Exception) {
                error = e
            }
        }

        launch(mainDispatcher) {
            searchJob?.join()
            if (error != null) {
                SabianUtilities.WriteLog("Something went wrong with the search ${error!!.message}")
            }
            afterSearch()
        }
    }

    private fun beforeSearch() {
        isComplete = false
        onSearchListener?.onSearching()
    }

    private fun filterSearch() {
        this.contents = onSearchListener?.filterBeforeSearch(this.contents) ?: this.contents
    }

    protected fun afterSearch() {
        if (isComplete)
            onSearchListener?.onSearched(newContent)
        else
            onSearchListener?.onCancel()
        isComplete = false
    }

    /**
     * Searches through the items
     */
    protected open fun searchItems(query: String) {

        newContent = arrayListOf()

        val pattern = getRegex(query)

        LoopContentQ@ for (content in contents) {

            if (!clazz.isInstance(content))
                continue@LoopContentQ

            //var matches = false
            val searchCriteria = getSearchCriteria(content as T)

            val searchList = searchCriteria.filterNotNull()
            val matches = searchList.any { search ->
                pattern.matcher(search).matches()
            }
            if (matches) {
                newContent.add(content)
            }
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

    /**
     * The search regex
     */
    protected open fun getRegex(query: String): Pattern {
        return Pattern.compile(".*$query.*", Pattern.CASE_INSENSITIVE)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}