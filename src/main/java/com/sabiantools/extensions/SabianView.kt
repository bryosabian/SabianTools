package com.sabiantools.extensions

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
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
        val layerDrawable = background.mutate() as? LayerDrawable ?: return
        val bgDrawableItem = layerDrawable.findDrawableByLayerId(layerItemID) as? GradientDrawable
                ?: return
        bgDrawableItem.setColor(color)
        setSupportDrawable(layerDrawable)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

fun View.setLayerStrokeColor(@IdRes layerItemID: Int, @ColorInt color: Int, width: Int) {
    try {

        val layerDrawable = background.mutate() as? LayerDrawable ?: return
        val bgDrawableItem = layerDrawable.findDrawableByLayerId(layerItemID) as? GradientDrawable
                ?: return
        bgDrawableItem.setStroke(width, color)
        setSupportDrawable(layerDrawable)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}


fun View.setLayerBackgroundAndStrokeColor(@IdRes layerItemID: Int, @ColorInt bgColor: Int, @ColorInt strokeColor: Int, width: Int) {
    try {
        val layerDrawable = background.mutate() as? LayerDrawable ?: return
        val bgDrawableItem = layerDrawable.findDrawableByLayerId(layerItemID) as? GradientDrawable
                ?: return
        bgDrawableItem.setStroke(width, strokeColor)
        bgDrawableItem.setColor(bgColor)
        setSupportDrawable(layerDrawable)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

fun View.setSupportDrawable(drawable: Drawable) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        background = drawable
    } else {
        setBackgroundDrawable(drawable)
    }
}