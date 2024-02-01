package com.sabiantools.extensions

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RotateDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Build
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.IdRes

fun View.fadeVisibility(visibility: Int, duration: Long = 300) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        val transition: Transition = Fade()
        transition.duration = duration
        transition.addTarget(this)
        TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
    }
    this.visibility = visibility
}

fun View.setLayerBackgroundColor(@IdRes layerItemID: Int, @ColorInt color: Int) {
    try {
        val (layerDrawable, bgDrawableItem) = getGradientDrawable(layerItemID) ?: return
        if (bgDrawableItem == null)
            return
        bgDrawableItem.setColor(color)
        setSupportDrawable(layerDrawable)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}


fun View.setLayerStrokeColor(@IdRes layerItemID: Int, @ColorInt color: Int, width: Int) {
    try {
        val (layerDrawable, bgDrawableItem) = getGradientDrawable(layerItemID) ?: return
        if (bgDrawableItem == null)
            return
        bgDrawableItem.setStroke(width, color)
        setSupportDrawable(layerDrawable)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}


fun View.setLayerBackgroundAndStrokeColor(@IdRes layerItemID: Int, @ColorInt bgColor: Int, @ColorInt strokeColor: Int, width: Int) {
    try {
        val (layerDrawable, bgDrawableItem) = getGradientDrawable(layerItemID) ?: return
        if (bgDrawableItem == null)
            return
        bgDrawableItem.setStroke(width, strokeColor)
        bgDrawableItem.setColor(bgColor)
        setSupportDrawable(layerDrawable)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}


fun View.setLayerGradientColor(@IdRes layerItemID: Int, @ColorInt color: IntArray) {
    try {
        val (layerDrawable, bgDrawableItem) = getGradientDrawable(layerItemID) ?: return
        if (bgDrawableItem == null)
            return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            bgDrawableItem.colors = color
        } else {
            bgDrawableItem.setColor(color.first())
        }
        setSupportDrawable(layerDrawable)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}





fun View.setLayerBorderRadius(@IdRes layerItemID: Int, radius: Float) {
    try {
        val (layerDrawable, bgDrawableItem) = getGradientDrawable(layerItemID) ?: return
        if (bgDrawableItem == null)
            return
        bgDrawableItem.cornerRadius = radius
        setSupportDrawable(layerDrawable)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}


fun View.getGradientDrawable(@IdRes layerItemID: Int): LayerData? {
    val layerDrawable = background.mutate() as? LayerDrawable ?: return null
    val data = LayerData(layerDrawable, null)
    data.gradientDrawable = layerDrawable.findDrawableByLayerId(layerItemID) as? GradientDrawable
    return data
}


fun View.setSupportDrawable(drawable: Drawable) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        background = drawable
    } else {
        setBackgroundDrawable(drawable)
    }
}

data class LayerData(var layerDrawable: LayerDrawable, var gradientDrawable: GradientDrawable? = null, var rotateDrawable: RotateDrawable? = null)