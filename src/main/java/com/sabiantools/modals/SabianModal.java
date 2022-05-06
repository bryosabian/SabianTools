package com.sabiantools.modals;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.sabiantools.R;
import com.sabiantools.utilities.SabianUtilities;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public class SabianModal extends Dialog {

    private View view;
    private ViewGroup vgBody;
    private ButtonFlat btnOk, btnCancel;
    private View.OnClickListener onOkayClickListener, onCancelClickListener;
    private String okayButtonText, cancelButtonText;
    private @ColorRes
    int okayButtonColor = NO_RES_ID;
    private @ColorRes
    int cancelButtonColor = NO_RES_ID;
    public static final int NO_RES_ID = -1;
    private TextView txtTitle, txtMessage;
    private String title, message;
    private @ColorRes
    int titleColor = NO_RES_ID;
    private @ColorRes
    int messageColor = NO_RES_ID;
    private boolean animate = true;
    private ViewGroup vgBodyContainer;
    private boolean isFooterFixed = false;


    public SabianModal(@NonNull Context context) {
        super(context, R.style.SabianMaterialDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        init_elements();
    }

    @LayoutRes
    private int getLayout() {
        if (isFooterFixed) {
            return R.layout.sabian_modal_smooth_fixed_bottom;
        }
        return R.layout.sabian_modal_smooth;
    }

    protected void init_elements() {
        vgBody = (ViewGroup) findViewById(R.id.ll_SabianModalBody);
        btnOk = (ButtonFlat) findViewById(R.id.btn_SabianModalOk);
        btnCancel = (ButtonFlat) findViewById(R.id.btn_SabianModalCancel);
        txtTitle = (TextView) findViewById(R.id.sct_SabianModalTitle);
        txtMessage = (TextView) findViewById(R.id.sct_SabianModalMessage);
        vgBodyContainer = (ViewGroup) findViewById(R.id.rll_SabianModalContainer);

        if (!SabianUtilities.IsStringEmpty(okayButtonText)) {
            btnOk.setText(okayButtonText);
        }

        if (!SabianUtilities.IsStringEmpty(cancelButtonText)) {
            btnCancel.setText(cancelButtonText);
        } else {
            btnCancel.setVisibility(View.GONE);
        }

        if (onOkayClickListener != null) {
            btnOk.setOnClickListener(onOkayClickListener);
        } else {
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCancelClickListener != null)
                    onCancelClickListener.onClick(view);
                dismiss();
            }
        });

        if (okayButtonColor != NO_RES_ID)
            btnOk.setBackgroundColor(getContext().getResources().getColor(okayButtonColor));

        if (cancelButtonColor != NO_RES_ID)
            btnCancel.setBackgroundColor(getContext().getResources().getColor(cancelButtonColor));

        if (!SabianUtilities.IsStringEmpty(title))
            txtTitle.setText(title);

        if (titleColor != NO_RES_ID)
            txtTitle.setTextColor(getContext().getResources().getColor(titleColor));

        if (messageColor != NO_RES_ID)
            txtMessage.setTextColor(getContext().getResources().getColor(messageColor));

        if (view != null) {
            vgBody.setVisibility(View.VISIBLE);
            vgBody.addView(view);
            txtMessage.setVisibility(View.GONE);
        } else {
            vgBody.setVisibility(View.GONE);
            txtMessage.setVisibility(View.VISIBLE);
            if (!SabianUtilities.IsStringEmpty(message)) {
                txtMessage.setText(message);
            }
        }
    }

    @Override
    public void show() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        super.show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getWindow().setAttributes(lp);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (animate) {
            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.modal_popup_show);
            vgBodyContainer.startAnimation(anim);
        }
    }

    public SabianModal setOnOkayClickListener(View.OnClickListener onOkayClickListener) {
        this.onOkayClickListener = onOkayClickListener;
        return this;
    }

    public SabianModal setOnCancelClickListener(View.OnClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
        return this;
    }

    public SabianModal setOkayButtonText(String okayButtonText) {
        this.okayButtonText = okayButtonText;
        return this;
    }

    public SabianModal setCancelButtonText(String cancelButtonText) {
        this.cancelButtonText = cancelButtonText;
        return this;
    }

    public SabianModal setOkayButtonColor(@ColorRes int okayButtonColor) {
        this.okayButtonColor = okayButtonColor;
        return this;
    }

    public SabianModal setCancelButtonColor(@ColorRes int cancelButtonColor) {
        this.cancelButtonColor = cancelButtonColor;
        return this;
    }

    public void setView(View view) {
        this.view = view;
    }

    public SabianModal setTitle(String title) {
        this.title = title;
        return this;
    }

    public SabianModal setTitleColor(@ColorRes int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public SabianModal setMessage(String message) {
        this.message = message;
        return this;
    }

    public SabianModal setMessageColor(@ColorRes int messageColor) {
        this.messageColor = messageColor;
        return this;
    }

    public SabianModal setAnimate(boolean animate) {
        this.animate = animate;
        return this;
    }

    public SabianModal setFooterFixed(boolean footerFixed) {
        isFooterFixed = footerFixed;
        return this;
    }
}
