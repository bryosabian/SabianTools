package com.sabiantools.extensions

import java.math.BigDecimal
import java.math.RoundingMode


/**
 * Returns a friendly price output
 */
fun BigDecimal.toPricePlainString(): String {
    val rbc = setScale(2, RoundingMode.HALF_EVEN)
    return rbc.toPlainString()
}

/**
 * Returns a friendly price output with format
 */
fun BigDecimal.toPricePlainString(currency: String?): String {
    return "${this.toPricePlainString()} ${currency ?: ""}"
}

/**
 * Returns a friendly price output
 */
fun Double.toPricePlainString(): String {
    val bc = BigDecimal(this)
    val rbc = bc.setScale(2, RoundingMode.HALF_EVEN)
    return rbc.toPlainString()
}

/**
 * Returns a friendly price output
 */
fun Double.toPricePlainString(currency: String?): String {
    return "${this.toPricePlainString()} ${currency ?: ""}"
}

/**
 * Returns price friendly output with formatted string
 */
fun Double.toPriceFormattedString(): String {
    return String.format("%1$,.2f", this)
}

/**
 * Returns price friendly output with formatted string
 */
fun Double.toPriceFormattedString(currency: String): String {
    return "${this.toPriceFormattedString()} $currency"
}