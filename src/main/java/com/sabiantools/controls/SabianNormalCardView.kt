package com.sabiantools.controls

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import com.sabiantools.R
import com.sabiantools.extensions.getCompatColor
import com.sabiantools.extensions.getDimension
import com.sabiantools.extensions.setLayerBackgroundColor
import com.sabiantools.extensions.setLayerBorderRadius
import com.sabiantools.extensions.setSupportDrawable

class SabianNormalCardView : FrameLayout {

    private val shadowLayerID: Int
        @IdRes
        get() = R.id.item_SabianCardShadowLayer

    private val cardLayerID: Int
        @IdRes
        get() = R.id.item_SabianCardLayer


    constructor(context: Context) : super(context) {
        initElements()
        initAttributes(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initElements()
        initAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initElements()
        initAttributes(attrs)
    }

    private fun initElements() {
        setSupportDrawable(ContextCompat.getDrawable(context, R.drawable.bg_sabian_card)!!)
    }

    private fun initAttributes(attrs: AttributeSet?) {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SabianNormalCardView)
        val aCount = a.indexCount
        for (i in 0 until aCount) {
            val attr = a.getIndex(i)
            if (attr == R.styleable.SabianNormalCardView_sncv_backgroundColor) {
                setBackgroundColor(a.getColor(attr, context.getCompatColor(R.color.sabian_card_color)))
            }
            if (attr == R.styleable.SabianNormalCardView_sncv_shadowColor) {
                setShadowColor(a.getColor(attr, context.getCompatColor(R.color.sabian_shadow_color)))
            }
            if (attr == R.styleable.SabianNormalCardView_sncv_borderRadius) {
                setBorderRadius(a.getDimension(attr, context.getDimension(R.dimen.sabian_shadow_radius)))
            }
        }
        a.recycle()
    }


    fun setBorderColor(color: Int) {
        setShadowColor(color)
    }

    fun setBorderRadius(radius: Float) {
        setLayerBorderRadius(shadowLayerID, radius)
        setLayerBorderRadius(cardLayerID, radius)
    }

    fun setShadowColor(color: Int) {
        setLayerBackgroundColor(shadowLayerID, color)
    }

    override fun setBackgroundColor(color: Int) {
        setLayerBackgroundColor(R.id.item_SabianCardLayer, color)
    }
}