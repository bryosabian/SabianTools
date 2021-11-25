package com.sabiantools.utilities.tasks.search

interface OnLinearDataSearchListener {
    fun onSearching() {

    }

    fun onSearched(newList: ArrayList<Any>)

    fun onCancel() {

    }
}