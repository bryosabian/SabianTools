package com.sabiantools.extensions

import android.view.View
import android.view.ViewGroup

val ViewGroup.allChildren: List<View>
    get() {
        val all = arrayListOf<View>()
        val childCount: Int = childCount
        for (i in 0 until childCount) {
            all.add(getChildAt(i));
        }
        return all
    }