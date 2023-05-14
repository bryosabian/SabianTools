package com.sabiantools.modals

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.gc.materialdesign.views.ButtonFlat
import com.sabiantools.R
import com.sabiantools.controls.texts.SabianCondensedEditText
import kotlinx.android.synthetic.main.layout_modal_edit_text.*

class SabianEditTextModal(
    context: Context,
    private var onSave: (String?, SabianEditTextModal) -> Unit
) :
    Dialog(context, R.style.SabianMaterialDialog) {
    private var txtTitle: TextView? = null
    private var sctEditText: SabianCondensedEditText? = null
    private var btnSubmit: ButtonFlat? = null
    private var btnCancel: ButtonFlat? = null
    private var vgBodyContainer: ViewGroup? = null
    private var animate = true


    var onCancel: ((String?, SabianEditTextModal) -> Unit)? = null

    var dismissOnSubmit = true

    var title: String = ""
        set(value) {
            txtTitle?.text = value
            field = value
        }

    var hint: String = ""
        set(value) {
            sctEditText?.hint = value
            field = value
        }

    var text: String = ""
        set(value) {
            sctEditText?.setText(value)
            field = value
        }
        get() = sctEditText?.text?.toString() ?: ""

    var submitText: String? = null
        set(value) {
            btnSubmit?.let { it.text = value ?: it.text }
            field = value
        }

    var cancelText: String? = null
        set(value) {
            btnCancel?.let { it.text = value ?: it.text }
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_modal_edit_text)
        initElements()
    }

    private fun initElements() {
        vgBodyContainer = rll_ModalEditTextContainer
        txtTitle = sct_ModalEditTextTitle
        sctEditText = sct_ModalEditText
        btnSubmit = btn_ModalEditTextOk
        btnCancel = btn_ModalEditTextCancel

        txtTitle!!.text = title
        sctEditText!!.apply {
            if (this@SabianEditTextModal.hint.isNotBlank())
                hint = this@SabianEditTextModal.hint
            if (this@SabianEditTextModal.text.isNotBlank())
                setText(this@SabianEditTextModal.text)
        }

        btnSubmit!!.apply {
            submitText?.let { value ->
                if (value.isNotBlank())
                    text = value
            }
            setOnClickListener {
                val value = this@SabianEditTextModal.text
                onSave.invoke(value, this@SabianEditTextModal)
                if (dismissOnSubmit)
                    dismiss()
            }
        }

        btnCancel!!.apply {
            cancelText?.let { value ->
                if (value.isNotBlank())
                    text = value
            }
            setOnClickListener {
                onCancel?.invoke(text, this@SabianEditTextModal)
                dismiss()
            }
        }

//        sctEditText?.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                capturedText = s?.toString() ?: ""
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//
//        })
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