package com.sabiantools.modals;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sabiantools.R;

import androidx.annotation.NonNull;

public class SabianImageModal extends Dialog {
    private Context context;
    private ImageView imgImage;
    private Bitmap image;
    private boolean animate = true;
    private View vgBodyContainer;

    public SabianImageModal(@NonNull Context context) {
        super(context, R.style.SabianMaterialDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sabian_modal_image);
        initElements();
    }

    private void initElements() {
        vgBodyContainer = findViewById(R.id.rll_SabianModalContainer);
        imgImage = findViewById(R.id.sfi_SabianModalImage);
        if (image != null)
            setImage(image);
    }

    public SabianImageModal setImage(Bitmap image) {
        this.image = image;
        if (imgImage != null)
            this.imgImage.setImageBitmap(image);
        return this;
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

        if (animate) {
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.modal_popup_show);
            vgBodyContainer.startAnimation(anim);
        }
    }
}
