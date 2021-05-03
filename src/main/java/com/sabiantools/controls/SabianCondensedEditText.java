package com.sabiantools.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.sabiantools.R;
import com.sabiantools.controls.texts.TypeFaceFactory;

import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created By Brian Sabana on 6/21/2016.
 */
public class SabianCondensedEditText extends AppCompatEditText {
    private Context _context;
    private Typeface typeface;
    private String _condensed = "RobotoCondensed-Regular.ttf";
    private String _roboto = "Roboto-Regular.ttf";

    private ArrayList<TextWatcher> mListeners = null;

    public SabianCondensedEditText(Context context) {
        super(context);
        _context = context;
        initElements();
    }

    public SabianCondensedEditText(Context context, AttributeSet set) {
        super(context, set);
        _context = context;
        initElements();
        initAttributes(set);
    }

    public SabianCondensedEditText(Context context, AttributeSet set, int defStyle) {
        super(context, set, defStyle);
        _context = context;
        initElements();
        initAttributes(set);
    }

    private void initElements() {
        typeface = TypeFaceFactory.getTypeFace(_context, "fonts/" + _condensed);
        this.setTypeface(typeface);
    }

    public void setRobotoType(String roboto) {
        this._roboto = roboto;
        Typeface typeface = TypeFaceFactory.getTypeFace(_context, "fonts/Roboto-" + roboto + ".ttf");
        this.setTypeface(typeface);
    }

    public void setCondensedType(String condensed) {
        this._condensed = condensed;
        Typeface typeface = TypeFaceFactory.getTypeFace(_context, "fonts/RobotoCondensed-" + condensed + ".ttf");
        this.setTypeface(typeface);
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray a = _context.obtainStyledAttributes(attrs, R.styleable.SabianCondensedEditText);
        int aCount = a.getIndexCount();
        for (int i = 0; i < aCount; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SabianCondensedEditText_SabianEditCondensedType) {
                this.setCondensedType(a.getString(attr));

            } else if (attr == R.styleable.SabianCondensedEditText_SabianEditRobotoType) {
                this.setRobotoType(a.getString(attr));
            }
        }
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        if (mListeners == null) {
            mListeners = new ArrayList<>();
        }
        mListeners.add(watcher);
        super.addTextChangedListener(watcher);
    }

    @Override
    public void removeTextChangedListener(TextWatcher watcher) {
        if (mListeners != null) {
            int i = mListeners.indexOf(watcher);
            if (i >= 0) {
                mListeners.remove(i);
            }
        }
        super.removeTextChangedListener(watcher);
    }

    public void setTextChangeListener(TextWatcher watcher) {
        clearTextChangedListeners();
        addTextChangedListener(watcher);
    }

    public void clearTextChangedListeners() {
        if (mListeners != null) {
            for (TextWatcher watcher : mListeners) {
                super.removeTextChangedListener(watcher);
            }
            mListeners.clear();
            mListeners = null;
        }
    }
}
