package com.sabiantools.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.sabiantools.R;

import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created By Brian Sabana on 6/21/2016.
 */
public class SabianCondensedEditText extends AppCompatEditText {

    private Context _context;

    private Typeface typeface;

    private String _condensed="RobotoCondensed-Regular.ttf";
    private String _roboto = "Roboto-Regular.ttf";

    private ArrayList<TextWatcher> mListeners = null;

    public SabianCondensedEditText(Context context)
    {
        super(context);

        _context=context;

        initElements();
    }
    public SabianCondensedEditText(Context context,AttributeSet set)
    {
        super(context,set);

        _context=context;

        initElements();

        initAttributes(set);
    }
    public SabianCondensedEditText(Context context,AttributeSet set,int defStyle)
    {
        super(context,set,defStyle);

        _context=context;

        initElements();

        initAttributes(set);
    }
    private void initElements()
    {
        typeface=Typeface.createFromAsset(_context.getAssets(),"fonts/"+_condensed);

        this.setTypeface(typeface);
    }


    /**
     * Depracated since 32.7.0 use {@link SabianCondensedEditText.setCondensedType}
     */
    @Deprecated
    public void setCondensed(String condensed)
    {
        this._condensed=condensed;

        this.setTypeface(Typeface.createFromAsset(_context.getAssets(),"fonts/"+condensed));
    }

    public void setRobotoType(String roboto)
    {
        this._roboto=roboto;

        this.setTypeface(Typeface.createFromAsset(_context.getAssets(),"fonts/Roboto-"+roboto+".ttf"));
    }

    public void setCondensedType(String condensed)
    {
        this._condensed=condensed;

        this.setTypeface(Typeface.createFromAsset(_context.getAssets(),"fonts/RobotoCondensed-"+condensed+".ttf"));
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
    public void addTextChangedListener(TextWatcher watcher)
    {
        if (mListeners == null)
        {
            mListeners = new ArrayList<>();
        }
        mListeners.add(watcher);

        super.addTextChangedListener(watcher);
    }

    @Override
    public void removeTextChangedListener(TextWatcher watcher)
    {
        if (mListeners != null)
        {
            int i = mListeners.indexOf(watcher);
            if (i >= 0)
            {
                mListeners.remove(i);
            }
        }

        super.removeTextChangedListener(watcher);
    }

    public void clearTextChangedListeners()
    {
        if(mListeners != null)
        {
            for(TextWatcher watcher : mListeners)
            {
                super.removeTextChangedListener(watcher);
            }

            mListeners.clear();
            mListeners = null;
        }
    }
}
