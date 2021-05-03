package com.sabiantools.modals;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.sabiantools.R;

import androidx.annotation.DrawableRes;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class SabianMaterialAlert extends android.app.Dialog {

    private ViewGroup vg_Header, vg_Content;

    private ImageView sfi_HeaderIcon, sfi_HeaderIconClose, sfi_HeaderIconBack;

    private ButtonFlat btn_Ok, btn_Cancel, btn_NotSure;

    private TextView sct_Title;

    private Context context;

    private LayoutInflater inflater;

    private int headerIcon;

    private String buttonAcceptText, buttonCancelText;

    private int buttonAcceptColor, buttonCancelColor;

    private int headerColor;

    private int headerSize;

    private String title;

    private View content;

    private View.OnClickListener onAcceptButtonClickListener;

    private View.OnClickListener onCancelButtonClickListener;

    private boolean iconVectorDrawable = true;

    private boolean displayCloseIcon = true;

    private int headerTextColor;

    private boolean displayHeaderText = true;

    private boolean displayHeaderIcon = true;

    private Drawable headerIconDrawable;

    private int headerHeight = Integer.MIN_VALUE;

    private boolean noContentPadding = false;

    private View.OnClickListener onButtonBackClickListener;

    private View view;

    public SabianMaterialAlert(Context context) {
        super(context, R.style.SabianMaterialDialog);
        this.context = context;
    }

    public SabianMaterialAlert(Context context, int themeResId) {
        super(context, R.style.SabianMaterialDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sabian_material_modal_layout);
        init_elements();
    }

    public SabianMaterialAlert init(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        init_elements();
        return this;
    }

    private void init_elements() {

        //view=inflater.inflate(R.layout.sabian_material_modal_layout, null);

        vg_Header = (ViewGroup) findViewById(R.id.rll_SabianMaterialModalHeader);

        vg_Content = (ViewGroup) findViewById(R.id.ll_SabianMaterialModalContent);

        sfi_HeaderIcon = (ImageView) findViewById(R.id.sfi_SabianMaterialModalIcon);

        sfi_HeaderIconClose = (ImageView) findViewById(R.id.sfi_SabianMaterialModalIconClose);

        sfi_HeaderIconBack = (ImageView) findViewById(R.id.sfi_SabianMaterialModalIconBack);

        sct_Title = (TextView) findViewById(R.id.sct_SabianMaterialModalTitle);

        btn_Ok = (ButtonFlat) findViewById(R.id.btn_SabianMaterialModalOk);

        btn_Cancel = (ButtonFlat) findViewById(R.id.btn_SabianMaterialModalCancel);

        btn_NotSure = (ButtonFlat) findViewById(R.id.btn_SabianMaterialModalMiddle);

        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAcceptButtonClickListener != null) {
                    onAcceptButtonClickListener.onClick(view);
                    return;
                }
                dismiss();
            }
        });

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCancelButtonClickListener != null)
                    onCancelButtonClickListener.onClick(view);
                dismiss();
            }
        });

        btn_Ok.setText(buttonAcceptText);

        sfi_HeaderIconClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        if (getButtonCancelText() == null) {
            btn_Cancel.setVisibility(View.GONE);
        } else {
            btn_Cancel.setText(buttonCancelText);
        }

        if (!isDisplayCloseIcon())
            sfi_HeaderIconClose.setVisibility(View.GONE);

        if (!displayHeaderText)
            sct_Title.setVisibility(View.GONE);

        if (!displayHeaderIcon)
            sfi_HeaderIcon.setVisibility(View.GONE);

        if (noContentPadding) {
            vg_Content.setPadding(0, 0, 0, 0);
        }

        sct_Title.setText(title);
        vg_Content.addView(view);
    }

    public int getHeaderIcon() {
        return headerIcon;
    }

    public SabianMaterialAlert setHeaderIcon(@DrawableRes int headerIcon) {

        this.headerIcon = headerIcon;

        if (isIconVectorDrawable()) {

            VectorDrawableCompat compat = VectorDrawableCompat.create(context.getResources(), headerIcon, null);

            sfi_HeaderIcon.setImageDrawable(compat);

            return this;
        }

        sfi_HeaderIcon.setImageResource(headerIcon);

        return this;
    }

    public String getButtonAcceptText() {
        return buttonAcceptText;
    }

    public SabianMaterialAlert setButtonAcceptText(String buttonAcceptText) {
        this.buttonAcceptText = buttonAcceptText;
        //this.btn_Ok.setText(buttonAcceptText);
        return this;
    }

    public String getButtonCancelText() {
        return buttonCancelText;
    }

    public SabianMaterialAlert setButtonCancelText(String buttonCancelText) {
        this.buttonCancelText = buttonCancelText;
        return this;
    }

    public int getButtonAcceptColor() {
        return buttonAcceptColor;
    }

    public SabianMaterialAlert setButtonAcceptColor(int buttonAcceptColor) {
        this.buttonAcceptColor = buttonAcceptColor;
        this.btn_Ok.setBackgroundColor(buttonAcceptColor);
        return this;
    }

    public int getButtonCancelColor() {
        return buttonCancelColor;
    }

    public SabianMaterialAlert setButtonCancelColor(int buttonCancelColor) {
        this.buttonCancelColor = buttonCancelColor;
        this.btn_Cancel.setBackgroundColor(buttonCancelColor);
        return this;
    }

    public int getHeaderColor() {
        return headerColor;
    }

    public SabianMaterialAlert setHeaderColor(int headerColor) {
        this.headerColor = headerColor;
        this.vg_Header.setBackgroundColor(headerColor);
        return this;
    }

    public int getHeaderSize() {
        return headerSize;
    }

    public SabianMaterialAlert setHeaderSize(int headerSize) {
        this.headerSize = headerSize;
        this.vg_Header.getLayoutParams().height = headerSize;
        vg_Header.requestLayout();
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SabianMaterialAlert setTitle(String title) {
        this.title = title;
        //this.sct_Title.setText(title);
        return this;
    }

    public View.OnClickListener getOnAcceptButtonClickListener() {
        return onAcceptButtonClickListener;
    }

    public SabianMaterialAlert setOnAcceptButtonClickListener(View.OnClickListener onAcceptButtonClickListener) {
        this.onAcceptButtonClickListener = onAcceptButtonClickListener;
        return this;
    }

    public View.OnClickListener getOnCancelButtonClickListener() {
        return onCancelButtonClickListener;
    }

    public SabianMaterialAlert setOnCancelButtonClickListener(View.OnClickListener onCancelButtonClickListener) {
        this.onCancelButtonClickListener = onCancelButtonClickListener;
        return this;
    }

    public boolean isIconVectorDrawable() {
        return iconVectorDrawable;
    }

    public SabianMaterialAlert setIconVectorDrawable(boolean iconVectorDrawable) {
        this.iconVectorDrawable = iconVectorDrawable;
        return this;
    }

    public boolean isDisplayCloseIcon() {
        return displayCloseIcon;
    }

    public SabianMaterialAlert setDisplayCloseIcon(boolean displayCloseIcon) {
        this.displayCloseIcon = displayCloseIcon;
        return this;
    }

    public SabianMaterialAlert setButtonOkVisibility(int visiblity) {

        this.btn_Ok.setVisibility(visiblity);

        return this;
    }

    public int getHeaderTextColor() {
        return headerTextColor;
    }

    public SabianMaterialAlert setHeaderTextColor(int headerTextColor) {
        this.headerTextColor = headerTextColor;
        this.sct_Title.setTextColor(headerTextColor);
        return this;
    }

    public boolean isDisplayHeaderIcon() {
        return displayHeaderIcon;
    }

    public SabianMaterialAlert setDisplayHeaderIcon(boolean displayHeaderIcon) {
        this.displayHeaderIcon = displayHeaderIcon;
        return this;
    }

    public boolean isDisplayHeaderText() {
        return displayHeaderText;
    }

    public SabianMaterialAlert setDisplayHeaderText(boolean displayHeaderText) {
        this.displayHeaderText = displayHeaderText;
        return this;
    }

    public Drawable getHeaderIconDrawable() {
        return headerIconDrawable;
    }

    public SabianMaterialAlert setHeaderIconDrawable(Drawable headerIconDrawable) {
        this.headerIconDrawable = headerIconDrawable;
        this.sfi_HeaderIcon.setImageDrawable(headerIconDrawable);
        return this;
    }

    public int getHeaderHeight() {
        return headerHeight;
    }

    public SabianMaterialAlert setHeaderHeight(int headerHeight) {

        this.headerHeight = headerHeight;

        vg_Header.getLayoutParams().height = headerHeight;

        vg_Header.requestLayout();

        return this;
    }

    public SabianMaterialAlert setButtonOkEnabled(boolean enabled) {

        //btn_Ok.setEnabled(enabled);

        return this;
    }

    public SabianMaterialAlert setButtonCancelEnabled(boolean enabled) {

        //btn_Cancel.setEnabled(enabled);

        return this;
    }

    public boolean isNoContentPadding() {
        return noContentPadding;
    }

    public SabianMaterialAlert setNoContentPadding(boolean noContentPadding) {
        this.noContentPadding = noContentPadding;
        return this;
    }

    public View.OnClickListener getOnButtonBackClickListener() {
        return onButtonBackClickListener;
    }

    public SabianMaterialAlert setOnButtonBackClickListener(View.OnClickListener onButtonBackClickListener) {
        this.onButtonBackClickListener = onButtonBackClickListener;
        if (onButtonBackClickListener != null) {
            sfi_HeaderIconBack.setOnClickListener(onButtonBackClickListener);
        }
        return this;
    }

    public SabianMaterialAlert setBackButtonVisibility(int visibility) {
        sfi_HeaderIconBack.setVisibility(visibility);
        return this;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void show() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        super.show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getWindow().setAttributes(lp);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
