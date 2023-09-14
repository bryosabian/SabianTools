package com.sabiantools.extensions

import android.content.Context

fun Int.toDp(context: Context): Float = (this / context.resources.displayMetrics.density)

fun Int.toPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()