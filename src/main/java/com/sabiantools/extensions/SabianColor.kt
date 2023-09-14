package com.sabiantools.extensions

import android.graphics.Color
import androidx.annotation.ColorInt

/**
Return a corresponding Int color of this String.
Supported formats are:
#RRGGBB
#AARRGGBB

The following names are also accepted: "red", "blue", "green", "black", "white", "gray", "cyan", "magenta", "yellow", "lightgray", "darkgray", "grey", "lightgrey", "darkgrey", "aqua", "fuchsia", "lime", "maroon", "navy", "olive", "purple", "silver", "teal".
Throws:
IllegalArgumentException - if this String cannot be parsed.
 */
@ColorInt
fun String.toColor(): Int {
    return Color.parseColor(this)
}

/**
Return a corresponding Int color of this String.
Supported formats are:
#RRGGBB
#AARRGGBB

The following names are also accepted: "red", "blue", "green", "black", "white", "gray", "cyan", "magenta", "yellow", "lightgray", "darkgray", "grey", "lightgrey", "darkgrey", "aqua", "fuchsia", "lime", "maroon", "navy", "olive", "purple", "silver", "teal".
Returns null - if this String cannot be parsed.
 */
@ColorInt
fun String.toColorOrNull(): Int? {
    return try {
        this.toColor()
    } catch (e: Throwable) {
        e.printStackTrace();
        null
    }
}