package com.sabiantools.extensions

fun <T> ArrayList<T>.removeAllBy(predicate: (T) -> Boolean) {
    val newList: ArrayList<T> = ArrayList()
    this.filter(predicate).forEach { newList.add(it) }
    this.removeAll(newList)
}

fun <T> MutableList<T>.removeAllBy(predicate: (T) -> Boolean) {
    val newList: ArrayList<T> = ArrayList()
    this.filter(predicate).forEach { newList.add(it) }
    this.removeAll(newList)
}

fun <T> List<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}

fun <T> Set<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}