package com.sabiantools.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;

import com.iangclifton.android.floatlabel.FloatLabel;
import com.sabiantools.R;
import com.sabiantools.controls.texts.TypeFaceFactory;

/**
 * Created By Brian Sabana on 7/6/2016.
 */
public class SabianFloatLabel extends FloatLabel {

    private Context _context;
    private Typeface typeface;

    public SabianFloatLabel(Context context) {
        super(context);
        this._context = context;
    }

    public SabianFloatLabel(Context context, AttributeSet set) {
        super(context, set);
        this._context = context;
        this.init_type_face();
        init_attributes(set);
    }

    public SabianFloatLabel(Context context, AttributeSet set, int defStyle) {
        super(context, set, defStyle);
        this._context = context;
        this.init_type_face();
        init_attributes(set);
    }

    private void init_type_face() {
        typeface = TypeFaceFactory.getTypeFace(_context, "fonts/RobotoCondensed-Regular.ttf");
        this.getEditText().setTypeface(typeface);

    }

    public void setCondensed(String type) {
        typeface = TypeFaceFactory.getTypeFace(_context, "fonts/RobotoCondensed-" + type + ".ttf");
        this.getEditText().setTypeface(typeface);
    }

    public void setRoboto(String type) {
        typeface = TypeFaceFactory.getTypeFace(_context, "fonts/Roboto-" + type + ".ttf");
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

    private void init_attributes(AttributeSet attrs) {
        TypedArray a = _context.obtainStyledAttributes(attrs, R.styleable.SabianFloatLabel);
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
    }
}
