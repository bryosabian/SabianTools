package com.sabiantools.extensions

class SabianString {
    fun String.removeAllSpaces(): String {
        return replace("\\s".toRegex(), "")
    }
}