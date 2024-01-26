package com.sabiantools.extensions

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.IdRes

fun Drawable.setLayerBackgroundColor(@IdRes layerItemID: Int, @ColorInt color: Int) {
    try {
        val bgDrawableItem = getGradientDrawable(layerItemID) ?: return
        bgDrawableItem.setColor(color)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}


fun Drawable.getGradientDrawable(@IdRes layerItemID: Int): GradientDrawable? {
    val layerDrawable = this as? LayerDrawable ?: return null
    return layerDrawable.findDrawableByLayerId(layerItemID) as? GradientDrawable
}


fun Drawable.setLayerStrokeColor(@IdRes layerItemID: Int, @ColorInt color: Int, width: Int) {
    try {
        val bgDrawableItem = getGradientDrawable(layerItemID) ?: return
        bgDrawableItem.setStroke(width, color)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}


fun Drawable.setLayerBackgroundAndStrokeColor(@IdRes layerItemID: Int, @ColorInt bgColor: Int, @ColorInt strokeColor: Int, width: Int) {
    try {
        val bgDrawableItem = getGradientDrawable(layerItemID) ?: return
        bgDrawableItem.setStroke(width, strokeColor)
        bgDrawableItem.setColor(bgColor)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}


fun Drawable.setLayerGradientColor(@IdRes layerItemID: Int, @ColorInt color: IntArray) {
    try {
        val bgDrawableItem = getGradientDrawable(layerItemID) ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            bgDrawableItem.colors = color
        } else {
            bgDrawableItem.setColor(color.first())
        }
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}


fun Drawable.setLayerBorderRadius(@IdRes layerItemID: Int, radius: Float) {
    try {
        val bgDrawableItem = getGradientDrawable(layerItemID) ?: return
        bgDrawableItem.cornerRadius = radius
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}