package com.sabiantools.extensions

fun <T> Array<T>.toArrayList(): ArrayList<T> {
    return ArrayList(toList())
}


inline fun <reified T> T.collect(): Array<T> {
    return arrayOf(this)
}

inline fun <reified T> T.collectAsList(): ArrayList<T> {
    return arrayListOf(this)
}

