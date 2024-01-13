package com.sabiantools.extensions

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import com.sabiantools.R

@ColorInt
fun Context.getColorFromAttrOrDefault(
        @AttrRes attrColor: Int,
        @ColorInt default: Int = Color.BLACK,
        typedValue: TypedValue = TypedValue(),
        resolveRefs: Boolean = true,
): Int {
    val has = theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    if (!has)
        return default
    return typedValue.data
}


@ColorInt
fun Context.getColorFromAttr(
        @AttrRes attrColor: Int,
        @ColorRes default: Int = R.color.sabianTransparent,
): Int {
    return getColorFromAttrOrDefault(attrColor, ContextCompat.getColor(this, default), typedValue = TypedValue(), resolveRefs = true)
}


fun Context.getCompatColor(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun Context.getDimension(@DimenRes dimen: Int): Float {
    return resources.getDimension(dimen)
}

fun Context.getDimensionSize(@DimenRes dimen: Int): Int {
    return resources.getDimensionPixelSize(dimen)
}