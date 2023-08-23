package com.sabiantools.extensions

fun <K, V> Map<K, V>.toHashMap(): HashMap<K, V> {
    return HashMap(this)
}