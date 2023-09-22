package com.sabiantools.extensions

import android.util.Base64
import com.sabiantools.utilities.SabianUtilities
import java.nio.charset.Charset
import java.util.regex.Pattern

private const val SPECIAL_CHARACTERS_REGEX = "[\\<\\>\\/{}%()\\[\\].+*?^$\\\\|]"

fun String.removeAllSpaces(replaceWith: String = ""): String {
    return replace("\\s+".toRegex(), replaceWith)
}

fun String.removeDoubleSpaces(): String {
    return this.removeAllSpaces(" ")
}

fun String.perfectCase(considerSpaces: Boolean = true): String {
    if (!considerSpaces)
        return lowercase().replaceFirstChar(Char::titlecase)
    val all = split("\\s+".toRegex())
    return all.joinToString(" ", transform = {
        it.perfectCase(false)
    })
}

/**
 * No double spaces, imperfect cases e.t.c
 */
fun String.perfectForm(): String {
    return this.trim()
            .removeDoubleSpaces()
            .perfectCase()
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


fun String.isAMatchByKeyWord(keyWord: String): Boolean {
    return isAMatchByKeyWord(keyWord, true)
}

fun String.isAMatchByKeyWord(keyWord: String, escapeSpecialCharacters: Boolean = true): Boolean {
    if (SabianUtilities.IsStringBlankOrEmpty(this) || SabianUtilities.IsStringBlankOrEmpty(keyWord)) return false
    val searchFor = if (escapeSpecialCharacters) keyWord.escapeSpecialRegexChars() else keyWord
    val pattern = Pattern.compile(
            ".*$searchFor.*",
            Pattern.CASE_INSENSITIVE
    )
    return pattern.matcher(this).matches()
}


/**
 * e.g Gets literal value from literal key.value e.t.c.
 *
 * Must be joined by dot operator (.)
 */
fun String.getValueFromDotKey(prepend: String = "key"): String? {
    val regex = "(${prepend}\\.)(.*)"
    val pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    if (matcher.matches()) {
        return try {
            matcher.group(2)
        } catch (e: Throwable) {
            null
        }
    }
    return null
}


fun String?.ifNullOrBlank(defaultValue: () -> String?): String? {
    if (this == null)
        return defaultValue()
    return this.ifBlank(defaultValue)
}


fun Collection<String>.containsKeyWord(keyWord: String, reverseLook: Boolean = false): Boolean {
    return this.any { it.isAMatchByKeyWord(keyWord) || (reverseLook && keyWord.isAMatchByKeyWord(it)) }
}

fun Array<String>.containsKeyWord(keyWord: String, reverseLook: Boolean = false): Boolean {
    return this.any { it.isAMatchByKeyWord(keyWord) || (reverseLook && keyWord.isAMatchByKeyWord(it)) }
}

fun String.matchesWithAnyKeyWord(keyWords: List<String>, reverseLook: Boolean = false): Boolean {
    return keyWords.any { this.isAMatchByKeyWord(it) || (reverseLook && it.isAMatchByKeyWord(this)) }
}

fun String.matchesWithAnyKeyWord(keyWords: Array<String>, reverseLook: Boolean = false): Boolean {
    return keyWords.any { this.isAMatchByKeyWord(it) || (reverseLook && it.isAMatchByKeyWord(this)) }
}