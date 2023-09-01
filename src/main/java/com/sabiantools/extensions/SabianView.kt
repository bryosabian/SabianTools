package com.sabiantools.extensions

import android.os.Build
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup

fun View.fadeVisibility(visibility: Int, duration: Long = 300) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        val transition: Transition = Fade()
        transition.duration = duration
        transition.addTarget(this)
        TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
    }
    this.visibility = visibility
}