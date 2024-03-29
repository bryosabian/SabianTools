package com.sabiantools.modals

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.sabiantools.R
import org.joda.time.DateTime

abstract class SabianCustomDialog : DialogFragment() {

    protected open var bodyContainer: ViewGroup? = null

    protected open var closeButton: View? = null

    protected open var isFull: Boolean = true

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

    override fun onStart() {
        super.onStart()
        configureWindow()
    }

    private fun configureWindow() {
        if (!isFull)
            return
        val window = dialog!!.window!!
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        window.attributes = lp
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val anim = AnimationUtils.loadAnimation(context, R.anim.modal_popup_show)
        bodyContainer?.startAnimation(anim)
    }

    fun show(manager: FragmentManager) {
        super.show(manager, DateTime.now().toString())
    }
}