package com.sabiantools.extensions


fun String.removeAllSpaces(): String {
    return replace("\\s+".toRegex(), "")
}
