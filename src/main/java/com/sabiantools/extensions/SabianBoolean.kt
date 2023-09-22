package com.sabiantools.extensions

fun String.toDeepBoolean(): Boolean {
    val value = this.lowercase()
    val defaultParsed = value.toBooleanStrictOrNull()
    if (defaultParsed == true)
        return true
    val acceptable = arrayOf("yes", "1", "on")
    return acceptable.any {
        it == value
    }
}