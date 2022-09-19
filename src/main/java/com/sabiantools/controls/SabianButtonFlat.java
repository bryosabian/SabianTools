package com.sabiantools.controls;

import android.content.Context;
import android.util.AttributeSet;

import com.gc.materialdesign.utils.Utils;
import com.gc.materialdesign.views.ButtonFlat;

public class SabianButtonFlat extends ButtonFlat {

    protected void setDefaultProperties(){
        minHeight = 36;
        minWidth = 50;
        rippleSize = 3;
        // Min size
        setMinimumHeight(Utils.dpToPx(minHeight, getResources()));
        setMinimumWidth(Utils.dpToPx(minWidth, getResources()));
        setBackgroundResource(com.gc.materialdesign.R.drawable.background_transparent);
    }

    public SabianButtonFlat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(String text) {
        textButton.setText(text);
    }
}
