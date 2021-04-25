package com.sabiantools.controls;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.sabiantools.R;
import com.sabiantools.controls.texts.TypeFaceFactory;

/**
 * Created By Brian Sabana on 6/21/2016.
 */
public class SabianTransparentButtonText extends FrameLayout {

    private static final float NO_RES = -1;

    private LayoutInflater inflater;

    private FontAwesomeText icon;

    private EditText editText;

    private Context _context;
    private int inputType;

    public SabianTransparentButtonText(Context context) {
        super(context);

        this._context = context;

        init_elements();
    }

    public SabianTransparentButtonText(Context context, AttributeSet set) {
        super(context, set);

        this._context = context;

        init_elements();

        init_attributes(set);
    }

    public SabianTransparentButtonText(Context context, AttributeSet set, int defStyle) {
        super(context, set, defStyle);

        this._context = context;

        init_elements();

        init_attributes(set);
    }

    private void init_elements() {
        this.inflater = LayoutInflater.from(_context);

        View view = inflater.inflate(R.layout.sabian_transparent_button_text, this, true);

        icon = (FontAwesomeText) view.findViewById(R.id.img_SabianEditIcon);

        editText = (EditText) view.findViewById(R.id.txt_SabianEditText);

        editText.setTypeface(TypeFaceFactory.getTypeFace(_context, "fonts/RobotoCondensed-Regular.ttf"));
    }

    public void setText(String text) {
        this.editText.setText(text);
    }

    public void setHint(String hint) {
        this.editText.setHint(hint);
    }

    public void setIcon(String favicon) {
        this.icon.setIcon(favicon);
    }

    public void setTextColor(int colorRes) {
        this.editText.setTextColor(colorRes);

        this.icon.setTextColor(colorRes);
    }

    public void setTextColor(String color) {
        int _col = Color.parseColor(color);

        this.setTextColor(_col);
    }

    public void setPassword(boolean yes) {
        if (yes) {
            this.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            return;
        }

    }

    public String getText() {

        String txt = editText.getText().toString();

        return txt;
    }

    public void setTextSize(float size) {
        if (size != NO_RES) {
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
        if (inputType != NO_RES) {
            editText.setInputType(inputType);
        }
    }

    private void init_attributes(AttributeSet set) {
        Resources res = _context.getResources();

        TypedArray a = _context.obtainStyledAttributes(set, R.styleable.SabianTransparentButtonText);

        int aCount = a.getIndexCount();

        for (int i = 0; i < aCount; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SabianTransparentButtonText_sabian_edit_icon) {
                setIcon(a.getString(attr));
            } else if (attr == R.styleable.SabianTransparentButtonText_sabian_edit_text) {
                this.setText(a.getString(attr));
            } else if (attr == R.styleable.SabianTransparentButtonText_sabian_edit_textColor) {
                this.setTextColor(a.getColor(attr, res.getColor(R.color.sabian_actionbar_text_color)));
            } else if (attr == R.styleable.SabianTransparentButtonText_sabian_edit_hint) {
                this.setHint(a.getString(attr));
            } else if (attr == R.styleable.SabianTransparentButtonText_sabian_edit_password) {
                this.setPassword(a.getBoolean(attr, false));
            } else if (attr == R.styleable.SabianTransparentButtonText_android_inputType) {
                setInputType(a.getInt(attr, InputType.TYPE_CLASS_TEXT));
            }
        }
        // set.getAttributeName()
    }


}
