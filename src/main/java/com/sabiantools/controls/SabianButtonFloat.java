package com.sabiantools.controls;

import android.content.Context;
import android.util.AttributeSet;

import com.gc.materialdesign.views.ButtonFloat;
import com.sabiantools.R;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class SabianButtonFloat extends ButtonFloat {

    private final Context context;

    public SabianButtonFloat(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setImageVector(@DrawableRes int vector) {
        setImageVector(vector, R.color.sabian_white);
    }

    public void setImageVector(@DrawableRes int vector, @ColorRes int color) {
        setImageVectorWithColor(vector, ContextCompat.getColor(context, color));
    }

    public void setImageVectorWithColor(@DrawableRes int vector, @ColorInt int color) {
        VectorDrawableCompat vc = VectorDrawableCompat.create(context.getResources(), vector, null);
        icon.setImageDrawable(vc);
        setIconColor(color);
    }

    public void setIconColor(@ColorInt int color) {
        icon.setColorFilter(color);
    }
}
