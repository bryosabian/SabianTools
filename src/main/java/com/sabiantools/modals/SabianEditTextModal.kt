package com.sabiantools.modals

import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import androidx.annotation.ColorInt
import com.gc.materialdesign.views.ButtonFlat
import com.sabiantools.R
import com.sabiantools.controls.texts.SabianCondensedEditText
import kotlinx.android.synthetic.main.layout_modal_edit_text.btn_ModalEditTextCancel
import kotlinx.android.synthetic.main.layout_modal_edit_text.btn_ModalEditTextOk
import kotlinx.android.synthetic.main.layout_modal_edit_text.rll_ModalEditTextContainer
import kotlinx.android.synthetic.main.layout_modal_edit_text.sct_ModalEditText
import kotlinx.android.synthetic.main.layout_modal_edit_text.sct_ModalEditTextTitle

class SabianEditTextModal(
        context: Context,
        private var onSave: (String?, SabianEditTextModal) -> Unit
) :
        SabianCustomModal(context, R.style.SabianMaterialDialog) {
    private var txtTitle: TextView? = null
    private var sctEditText: SabianCondensedEditText? = null
    private var btnSubmit: ButtonFlat? = null
    private var btnCancel: ButtonFlat? = null


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

    @ColorInt
    var okayButtonColor: Int? = null
        set(value) {
            if (value != null)
                btnSubmit?.let { it.setBackgroundColor(value) }
            field = value
        }

    @ColorInt
    var cancelButtonColor: Int? = null
        set(value) {
            if (value != null)
                btnCancel?.let { it.setBackgroundColor(value) }
            field = value
        }

    var buttonColors: Int? = null
        set(value) {
            okayButtonColor = value
            cancelButtonColor = value
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_modal_edit_text)
        initModalElements()
    }

    override fun initModalElements() {
        bodyContainer = rll_ModalEditTextContainer
        closeButton = btn_ModalEditTextCancel
        super.initModalElements()
        initElements()
    }

    private fun initElements() {
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



        okayButtonColor?.let {
            okayButtonColor = it
        }

        cancelButtonColor?.let {
            cancelButtonColor = it
        }

        buttonColors?.let {
           buttonColors = it
        }
    }

}