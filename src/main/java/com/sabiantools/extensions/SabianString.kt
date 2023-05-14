package com.sabiantools.extensions

import android.util.Base64
import java.nio.charset.Charset
import java.util.regex.Pattern

private const val SPECIAL_CHARACTERS_REGEX = "[\\<\\>\\/{}%()\\[\\].+*?^$\\\\|]"

fun String.removeAllSpaces(): String {
    return replace("\\s+".toRegex(), "")
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
    val pattern = Pattern.compile(SPECIAL_CHARACTERS_REGEX)
    return pattern.matcher(this).replaceAll("\\\\$0")
}


fun String.replaceSpecialRegexChars(replacement: String = ""): String {
    val pattern = Pattern.compile(SPECIAL_CHARACTERS_REGEX)
    return pattern.matcher(this).replaceAll(replacement)
}

fun String.toBase64(charset: Charset = Charsets.UTF_8, flags: Int = Base64.DEFAULT): String {
    val bytes = this.toByteArray(charset)
    return Base64.encodeToString(bytes, flags)
}

fun String?.ifNullOrBlank(defaultValue: () -> String?): String? {
    if (this == null)
        return defaultValue()
    return this.ifBlank(defaultValue)
}