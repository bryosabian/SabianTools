package com.sabiantools.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.sabiantools.R;
import com.sabiantools.controls.texts.TypeFaceFactory;

/**
 * Created By Brian Sabana on 6/21/2016.
 */
public class SabianCondensedText extends androidx.appcompat.widget.AppCompatTextView /*TextView*/ {

    private Typeface typeface;

    private Context _context;

    private String _condensed = "RobotoCondensed-Regular.ttf";

    private String _roboto = "";

    public SabianCondensedText(Context context) {
        super(context);
        _context = context;
        initElements();
    }

    public SabianCondensedText(Context context, AttributeSet set) {
        super(context, set);
        _context = context;
        initElements();
        initAttributes(set);
    }

    public SabianCondensedText(Context context, AttributeSet set, int defStyle) {
        super(context, set, defStyle);
        _context = context;
        initElements();
        initAttributes(set);
    }

    private void initElements() {
        typeface = TypeFaceFactory.getTypeFace(_context, "fonts/" + this._condensed);
        this.setTypeface(typeface);
    }

    public void setCondensed(String condensed) {
        this._condensed = condensed;
        Typeface typeface = TypeFaceFactory.getTypeFace(_context, "fonts/RobotoCondensed-" + condensed + ".ttf");
        this.setTypeface(typeface);
    }

    public void setRoboto(String roboto) {
        this._roboto = roboto;
        Typeface typeface = TypeFaceFactory.getTypeFace(_context, "fonts/Roboto-" + roboto + ".ttf");
        this.setTypeface(typeface);
    }

    private void initAttributes(AttributeSet attrs) {

        TypedArray a = _context.obtainStyledAttributes(attrs, R.styleable.SabianCondensedText);
        int aCount = a.getIndexCount();
        for (int i = 0; i < aCount; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SabianCondensedText_SabianCondensedType) {
                this.setCondensed(a.getString(attr));
            } else if (attr == R.styleable.SabianCondensedText_SabianRobotoType) {
                this.setRoboto(a.getString(attr));
            }
        }
    }
}
