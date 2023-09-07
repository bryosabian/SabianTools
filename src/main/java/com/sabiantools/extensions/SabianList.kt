package com.sabiantools.extensions

fun <T> ArrayList<T>.removeAllBy(predicate: (T) -> Boolean) {
    val newList: ArrayList<T> = ArrayList()
    this.filter(predicate).forEach { newList.add(it) }
    this.removeAll(newList.toSet())
}

fun <T> MutableList<T>.removeAllBy(predicate: (T) -> Boolean) {
    val newList: ArrayList<T> = ArrayList()
    this.filter(predicate).forEach { newList.add(it) }
    this.removeAll(newList)
}

fun <T> Collection<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}

fun <T> Collection<T>.forEachNotNull(action: (T) -> Unit) {
    filterNotNull().forEach(action)
}


fun <T> ArrayList<T>.addIfNotExist(value: T): Boolean {
    if (this.contains(value))
        return true
    return this.add(value)
}

fun <T> MutableList<T>.addIfNotExist(value: T): Boolean {
    if (this.contains(value))
        return true
    return this.add(value)
}

fun <T> MutableList<T>.update(value: T): Int {
    val index = indexOf(value)
    if (index <= -1)
        return index
    this[index] = value
    return index
}

fun <T> MutableList<T>.updateOrAdd(value: T, addAt: Int? = null): Int {
    var index = indexOf(value)
    if (index >= 0)
        removeAt(index)
    if (addAt != null)
        index = addAt
    if (index >= 0)
        add(index, value)
    else {
        add(value)
        index = size - 1
    }
    return index
}

fun <T> MutableList<T>.removeAndReturnPosition(value: T): Int {
    val index = indexOf(value)
    if (index < 0)
        return index
    removeAt(index)
    return index
}


fun <T> Collection<T>.toIndexes(): List<Int> {
    return mapIndexed { index, _ ->
        index
    }
}

fun <T, K> Collection<T>.filterNotNull(producer: ((T) -> K?)): List<T> {
    return this.filter {
        producer(it) != null
    }
}