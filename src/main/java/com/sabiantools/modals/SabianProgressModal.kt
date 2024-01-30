package com.sabiantools.modals

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.annotation.ColorInt
import com.gc.materialdesign.views.ProgressBarDeterminate
import com.sabiantools.R
import kotlinx.android.synthetic.main.layout_modal_progress.pb_ModalProgressBar
import kotlinx.android.synthetic.main.layout_modal_progress.rll_ModalProgressContainer
import kotlinx.android.synthetic.main.layout_modal_progress.sct_ModalProgressBarTitle
import kotlinx.android.synthetic.main.layout_modal_progress.sct_ModalProgressTitle

class SabianProgressModal(context: Context) : SabianCustomModal(context, R.style.SabianMaterialDialog) {
    private var pbProgress: ProgressBarDeterminate? = null
    private var txtTitle: TextView? = null
    private var txtProgressTitle: TextView? = null

    private var animate = true

    private var title: String = ""
    private var progressTitle: String = ""
    private var progress: Int = 0
    private var min: Int = 0
    private var max: Int = 100

    @ColorInt
    private var progressColor: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_modal_progress)
        initModalElements()
    }

    override fun initModalElements() {
        bodyContainer = rll_ModalProgressContainer
        super.initModalElements()
        initElements()
    }

    private fun initElements() {
        pbProgress = pb_ModalProgressBar
        txtTitle = sct_ModalProgressTitle
        txtProgressTitle = sct_ModalProgressBarTitle

        pbProgress?.setMin(min)
        pbProgress?.setMax(max)
        pbProgress?.progress = progress
        progressColor?.let {
            pbProgress?.setBackgroundColor(it)
        }
        txtTitle?.text = title
        txtProgressTitle?.text = progressTitle
    }

    fun setTitle(title: String): SabianProgressModal {
        txtTitle?.text = title
        this.title = title
        return this
    }

    fun setProgressColor(@ColorInt color: Int): SabianProgressModal {
        pbProgress?.progress = color
        progressColor = color
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
}