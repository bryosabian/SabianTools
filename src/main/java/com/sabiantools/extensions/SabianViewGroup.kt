package com.sabiantools.extensions

import android.view.View
import android.view.ViewGroup

val ViewGroup.allChildViews: List<View>
    get() {
        val all = arrayListOf<View>()
        val childCount: Int = childCount
        for (i in 0 until childCount) {
            val v = getChildAt(i)
            all.add(v)
            if (v is ViewGroup) {
                all.addAll(v.allChildViews)
            }
        }
        return all
    }