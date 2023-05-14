package com.sabiantools.controls.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginDecorator : RecyclerView.ItemDecoration {
    private var left: Int = 0
    private var top: Int = 0
    private var right: Int = 0
    private var bottom: Int = 0

    constructor(margins: Int) : this(margins, margins, margins, margins)

    constructor(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) : super() {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = left
        outRect.right = right
        outRect.top = top
        outRect.bottom = bottom
    }
}