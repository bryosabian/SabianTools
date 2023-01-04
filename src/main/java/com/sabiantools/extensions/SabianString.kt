package com.sabiantools.extensions

import java.util.regex.Pattern


fun String.removeAllSpaces(): String {
    return replace("\\s+".toRegex(), "")
}

fun String.removeExtraSpaces(): String {
    return replace("\\s+".toRegex(), " ")
}

fun String.perfectCase(considerSpaces: Boolean = true): String {
    if (!considerSpaces)
        return lowercase().replaceFirstChar(Char::titlecase)
    val all = split("\\s+".toRegex())
    return all.joinToString(" ", transform = {
        it.perfectCase(false)
    })
}

fun String.escapeSpecialRegexChars(): String {
    val pattern = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]")
    return pattern.matcher(this).replaceAll("\\\\$0")
}