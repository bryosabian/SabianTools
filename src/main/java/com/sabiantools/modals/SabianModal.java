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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.sabiantools.R;
import com.sabiantools.utilities.SabianUtilities;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public class SabianModal extends SabianCustomModal {

    private View view;
    private ButtonFlat btnOk, btnCancel;
    private View.OnClickListener onOkayClickListener, onCancelClickListener;
    private String okayButtonText, cancelButtonText;
    private @ColorRes
    int okayButtonColorRes = NO_RES_ID;
    private @ColorRes
    int cancelButtonColorRes = NO_RES_ID;


    private @ColorInt
    int okayButtonColor = NO_RES_ID;
    private @ColorInt
    int cancelButtonColor = NO_RES_ID;

    public static final int NO_RES_ID = -1;
    private TextView txtTitle, txtMessage;
    private String title, message;
    private @ColorRes
    int titleColor = NO_RES_ID;
    private @ColorRes
    int messageColor = NO_RES_ID;

    private boolean isFooterFixed = false;

    private ViewGroup actionsContainer;
    private ScrollView scrollView;

    public static final int ACTIONS_ALIGN_VERTICAL = LinearLayout.VERTICAL;
    public static final int ACTIONS_ALIGN_HORIZONTAL = LinearLayout.HORIZONTAL;

    private int actionsAlignment = ACTIONS_ALIGN_HORIZONTAL;


    public SabianModal(@NonNull Context context) {
        super(context, R.style.SabianMaterialDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initModalElements();
    }

    @LayoutRes
    private int getLayout() {
        if (isFooterFixed) {
            return R.layout.sabian_modal_smooth_fixed_bottom;
        }
        return R.layout.sabian_modal_smooth;
    }

    protected void initModalElements() {
        initElements();
        super.initModalElements();
    }

    protected void initElements() {
        ViewGroup vgBodyContainer = (ViewGroup) findViewById(R.id.rll_SabianModalContainer);
        setBodyContainer(vgBodyContainer);

        ViewGroup vgBody = (ViewGroup) findViewById(R.id.ll_SabianModalBody);


        btnOk = (ButtonFlat) findViewById(R.id.btn_SabianModalOk);
        btnCancel = (ButtonFlat) findViewById(R.id.btn_SabianModalCancel);
        txtTitle = (TextView) findViewById(R.id.sct_SabianModalTitle);
        txtMessage = (TextView) findViewById(R.id.sct_SabianModalMessage);


        scrollView = (ScrollView) findViewById(R.id.scrl_SabianModalScroller);
        actionsContainer = (ViewGroup) findViewById(R.id.ll_SabianModalActionsContainer);

        if (actionsContainer instanceof LinearLayout) {
            LinearLayout actionLinear = (LinearLayout) actionsContainer;
            actionLinear.setOrientation(actionsAlignment);
        }

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
            btnOk.setOnClickListener(view -> dismiss());
        }

        btnCancel.setOnClickListener(view -> {
            if (onCancelClickListener != null)
                onCancelClickListener.onClick(view);
            dismiss();
        });

        if (okayButtonColorRes != NO_RES_ID)
            btnOk.setBackgroundColor(getContext().getResources().getColor(okayButtonColorRes));

        if (cancelButtonColorRes != NO_RES_ID)
            btnCancel.setBackgroundColor(getContext().getResources().getColor(cancelButtonColorRes));

        if (okayButtonColor != NO_RES_ID)
            btnOk.setBackgroundColor(okayButtonColor);

        if (cancelButtonColor != NO_RES_ID)
            btnCancel.setBackgroundColor(cancelButtonColor);

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

    public SabianModal setActionsAlignment(int actionsAlignment) {
        this.actionsAlignment = actionsAlignment;
        return this;
    }

    @Override
    public void show() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        super.show();
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

    public SabianModal setOkayButtonColorRes(@ColorRes int okayButtonColorRes) {
        this.okayButtonColorRes = okayButtonColorRes;
        return this;
    }

    public SabianModal setCancelButtonColorRes(@ColorRes int cancelButtonColorRes) {
        this.cancelButtonColorRes = cancelButtonColorRes;
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


    public SabianModal setFooterFixed(boolean footerFixed) {
        isFooterFixed = footerFixed;
        return this;
    }

    public ScrollView getScrollView() {
        return scrollView;
    }

    public SabianModal setOkayButtonColor(@ColorInt int okayButtonColor) {
        this.okayButtonColor = okayButtonColor;
        return this;
    }

    public SabianModal setCancelButtonColor(@ColorInt int cancelButtonColor) {
        this.cancelButtonColor = cancelButtonColor;
        return this;
    }
}
