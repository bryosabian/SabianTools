package com.sabiantools.controls;

import static com.sabiantools.controls.SabianButtonText.NO_RES;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.iangclifton.android.floatlabel.FloatLabel;
import com.sabiantools.R;
import com.sabiantools.controls.texts.TypeFaceFactory;

import org.jetbrains.annotations.NotNull;

/**
 * Created By Brian Sabana on 7/6/2016.
 */
public class SabianFloatLabel extends FloatLabel {
    
    private Typeface typeface;


    private TextWatcher onTextListener;
    private OnTextChangeListener onTextChangeListener;

    public SabianFloatLabel(Context context) {
        super(context);
    }

    public SabianFloatLabel(Context context, AttributeSet set) {
        super(context, set);
        this.init_type_face();
        init_attributes(set);
    }

    public SabianFloatLabel(Context context, AttributeSet set, int defStyle) {
        super(context, set, defStyle);
        this.init_type_face();
        init_attributes(set);
    }

    private void init_type_face() {
        typeface = TypeFaceFactory.getTypeFace(getContext(), "fonts/RobotoCondensed-Regular.ttf");
        this.getEditText().setTypeface(typeface);

    }

    public void setCondensed(String type) {
        typeface = TypeFaceFactory.getTypeFace(getContext(), "fonts/RobotoCondensed-" + type + ".ttf");
        this.getEditText().setTypeface(typeface);
    }

    public void setRoboto(String type) {
        typeface = TypeFaceFactory.getTypeFace(getContext(), "fonts/Roboto-" + type + ".ttf");
        this.getEditText().setTypeface(typeface);
    }

    public void setTextGravity(int gravity) {
        if (gravity == -1)
            return;
        getEditText().setGravity(gravity);
    }

    public String getText() {
        return this.getEditText().getText().toString();
    }

    public void setText(String text) {
        this.getEditText().setText(text);
    }

    public void setHint(String hint) {
        getEditText().setHint(hint);
    }

    public void setTextColor(int color) {
        getEditText().setTextColor(color);
    }

    public void setTextHintColor(int color) {
        getEditText().setHintTextColor(color);
    }

    public void setFilters(@NotNull InputFilter[] filters) {
        this.getEditText().setFilters(filters);
    }

    public void setInputType(int inputType) {
        if (inputType != NO_RES) {
            getEditText().setInputType(inputType);
        }
    }

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
        clearTextListener();
        activateTextListener();
    }


    private void activateTextListener() {
        clearTextListener();
        if (onTextChangeListener != null) {
            onTextListener = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    onTextChangeListener.onTextChange(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            };
            getEditText().addTextChangedListener(onTextListener);
        }
    }

    private void clearTextListener() {
        if (onTextListener != null)
            getEditText().removeTextChangedListener(onTextListener);
    }

    private void init_attributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SabianFloatLabel);
        int aCount = a.getIndexCount();
        for (int i = 0; i < aCount; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SabianFloatLabel_Sfl_CondensedType) {
                this.setCondensed(a.getString(attr));
            } else if (attr == R.styleable.SabianFloatLabel_sfl_RobotoType) {
                this.setRoboto(a.getString(attr));
            } else if (attr == R.styleable.SabianFloatLabel_android_gravity) {
                this.setTextGravity(a.getInteger(attr, -1));
            } else if (attr == R.styleable.SabianFloatLabel_android_textColor) {
                this.setTextColor(a.getColor(attr, -1));
            } else if (attr == R.styleable.SabianFloatLabel_android_textColorHint) {
                this.setTextHintColor(a.getColor(attr, -1));
            }
        }
        a.recycle();
    }
}
