package com.sabiantools.extensions

fun <T> Array<T>.toArrayList(): ArrayList<T> {
    return ArrayList(toList())
}