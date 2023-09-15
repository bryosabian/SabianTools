package com.sabiantools.controls;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.sabiantools.R;

/**
 * Created By Brian Sabana on 3/25/2016.
 */
public class SabianMenuBar extends FrameLayout {

    private Context mContext;
    private RelativeLayout mainContainer;
    private TextView title;
    private FontAwesomeText cart, wishlist;
    private int cartItems, wishlistItems;
    private boolean displayWishlist = false;
    private RelativeLayout wishlistContainer, cartContainer;


    public SabianMenuBar(Context context) {
        super(context);
        this.mContext = context;
        initChildren();
    }

    public SabianMenuBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initChildren();
        initAttributes(attributeSet);
    }

    public SabianMenuBar(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        this.mContext = context;
        initChildren();
        initAttributes(attributeSet);
    }

    private void initChildren() {
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        View root = inflater.inflate(R.layout.sabian_actionbar, this, true);
        this.mainContainer = root.findViewById(R.id.rll_SabianActionBarContainer);
        this.title = root.findViewById(R.id.txt_SabianActionBarTitle);
    }

    public TextView getTitle() {
        return this.title;
    }

    public void setTitle(String text) {
        this.title.setText(text);
    }

    public void setTitleColor(@ColorInt int color) {
        title.setTextColor(color);
    }


    private void initAttributes(AttributeSet set) {
        TypedArray a = getContext().obtainStyledAttributes(set, R.styleable.SabianMenuBar);
        int aCount = a.getIndexCount();
        for (int i = 0; i < aCount; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SabianMenuBar_android_textColor) {
                setTitleColor(a.getColor(attr, -1));
            }
            if (attr == R.styleable.SabianMenuBar_android_text) {
                setTitle(a.getString(attr));
            }
        }
        a.recycle();
    }
}
