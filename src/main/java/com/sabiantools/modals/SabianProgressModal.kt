package com.sabiantools.modals

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.gc.materialdesign.views.ProgressBarDeterminate
import com.sabiantools.R
import kotlinx.android.synthetic.main.layout_modal_progress.*

class SabianProgressModal(context: Context) : Dialog(context, R.style.SabianMaterialDialog) {
    private var pbProgress: ProgressBarDeterminate? = null
    private var txtTitle: TextView? = null
    private var txtProgressTitle: TextView? = null
    private var vgBodyContainer : ViewGroup? = null
    private var animate = true

    private var title: String = ""
    private var progressTitle: String = ""
    private var progress: Int = 0
    private var min: Int = 0
    private var max: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_modal_progress)
        initElements()
    }

    private fun initElements() {
        vgBodyContainer = rll_ModalProgressContainer
        pbProgress = pb_ModalProgressBar
        txtTitle = sct_ModalProgressTitle
        txtProgressTitle = sct_ModalProgressBarTitle

        pbProgress?.setMin(min)
        pbProgress?.setMax(max)
        pbProgress?.progress = progress
        txtTitle?.text = title
        txtProgressTitle?.text = progressTitle
    }

    fun setTitle(title: String): SabianProgressModal {
        txtTitle?.text = title
        this.title = title
        return this
    }

    fun setProgressTitle(title: String): SabianProgressModal {
        txtProgressTitle?.text = title
        this.progressTitle = title
        return this
    }

    fun setProgress(progress: Int): SabianProgressModal {
        pbProgress?.progress = progress
        this.progress = progress
        return this
    }

    fun setMin(min: Int): SabianProgressModal {
        pbProgress?.setMin(min)
        this.min = min
        return this
    }

    fun setMax(max: Int): SabianProgressModal {
        pbProgress?.setMax(max)
        this.max = max
        return this
    }

    override fun show() {
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        super.show()
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        window?.attributes = lp
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (animate) {
            val anim = AnimationUtils.loadAnimation(context, R.anim.modal_popup_show)
            vgBodyContainer?.startAnimation(anim)
        }
    }


}