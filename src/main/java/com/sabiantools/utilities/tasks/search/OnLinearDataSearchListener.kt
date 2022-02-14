package com.sabiantools.utilities.tasks.search

interface OnLinearDataSearchListener {
    /**
     * Called when search operation is loading
     */
    fun onSearching() {

    }

    /**
     * Called when items have been searched
     */
    fun onSearched(newList: ArrayList<Any>)

    /**
     * Called when search operation has been cancelled
     */
    fun onCancel() {

    }

    /**
     * Filter the list before searching
     */
    fun filterBeforeSearch(list: List<Any>): List<Any> {
        return list
    }
}