package com.sabiantools.modals

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.annotation.CallSuper
import com.sabiantools.R

abstract class SabianCustomModal : Dialog {

    protected open var bodyContainer: ViewGroup? = null

    protected open var closeButton: View? = null

    protected open var isFull: Boolean = true

    constructor(context: Context) : super(context)

    constructor(context: Context, themeResId: Int) : super(context, themeResId)


    @CallSuper
    protected open fun initModalElements() {
        closeButton?.setOnClickListener {
            close()
        }
    }

    protected open fun canClose(): Boolean {
        return true
    }

    protected open fun close() {
        if (!canClose())
            return
        dismiss()
    }

    override fun show() {
        if (!isFull)
            return super.show()
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        super.show()
        window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        window!!.attributes = lp
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val anim = AnimationUtils.loadAnimation(context, R.anim.modal_popup_show)
        bodyContainer?.startAnimation(anim)
    }
}