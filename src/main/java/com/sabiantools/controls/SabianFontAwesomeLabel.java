package com.sabiantools.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.sabiantools.R;
import com.sabiantools.controls.texts.SabianCondensedText;

public class SabianFontAwesomeLabel extends FrameLayout {

    private FontAwesomeText ftText;
    private SabianCondensedText sctText;

    public SabianFontAwesomeLabel(@NonNull Context context) {
        super(context);
        initElements();
    }

    public SabianFontAwesomeLabel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initElements();
        initAttributes(attrs);
    }

    public SabianFontAwesomeLabel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initElements();
        initAttributes(attrs);
    }

    public void setTextColor(@ColorInt int color) {
        if (color == -1)
            return;
        ftText.setTextColor(color);
        sctText.setTextColor(color);
    }

    public void setRoboto(String type) {
        sctText.setRoboto(type);
    }

    public void setRobotoCondensed(String type) {
        sctText.setCondensed(type);
    }

    public void setFontIcon(String icon) {
        ftText.setIcon(icon);
    }

    public void setTextSize(float size) {
        if (size <= -1)
            return;
        ftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        sctText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setText(String text) {
        sctText.setText(text);
    }

    private void initElements() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.control_sabian_font_awesome_label, this, true);
        ftText = root.findViewById(R.id.fat_SabianFontAwesomeText);
        sctText = root.findViewById(R.id.sct_SabianFontAwesomeText);
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SabianFontAwesomeLabel);
        int aCount = a.getIndexCount();
        for (int i = 0; i < aCount; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SabianFontAwesomeLabel_android_textColor) {
                this.setTextColor(a.getColor(attr, -1));
            } else if (attr == R.styleable.SabianFontAwesomeLabel_sfat_robotoType) {
                this.setRoboto(a.getString(attr));
            } else if (attr == R.styleable.SabianFontAwesomeLabel_sfat_condensedType) {
                this.setRobotoCondensed(a.getString(attr));
            } else if (attr == R.styleable.SabianFontAwesomeLabel_sfat_fontIcon) {
                this.setFontIcon(a.getString(attr));
            } else if (attr == R.styleable.SabianFontAwesomeLabel_android_textSize) {
                this.setTextSize(a.getDimension(attr, -1));
            } else if (attr == R.styleable.SabianFontAwesomeLabel_android_text) {
                this.setText(a.getString(attr));
            }
        }
        a.recycle();
    }
}
