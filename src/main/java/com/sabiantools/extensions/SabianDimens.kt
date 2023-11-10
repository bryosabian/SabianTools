package com.sabiantools.extensions

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

fun Int.toDp(context: Context): Float = this.toFloat().toDp(context)

fun Float.toDp(context: Context): Float = this / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

fun Int.toPx(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics).toInt()

fun Float.toPx(context: Context): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        context.resources.displayMetrics)