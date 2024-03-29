package com.sabiantools.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.sabiantools.R;
import com.sabiantools.utilities.SabianUtilities;

/**
 * Created by Brian Sabana on 14/03/2018.
 */
public class SabianRoundButton extends FrameLayout {

    private ViewGroup ll_Container;

    private TextView txt_Text;

    private FontAwesomeText fat_Icon;

    private Context context;

    private static final int NO_COLOR = -1;

    private static final int NO_DRAWABLE = -1;

    public SabianRoundButton(Context context) {
        super(context);
        init(context);
    }

    public SabianRoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        init_attrs(attrs);
    }

    public SabianRoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        init_attrs(attrs);
    }

    private void init(Context context) {
        this.context = context;
        View root = LayoutInflater.from(context).inflate(R.layout.control_sabian_round_button, this, true);
        ll_Container = (ViewGroup) root.findViewById(R.id.ll_ControlSabianRoundButtonContainer);
        txt_Text = (TextView) root.findViewById(R.id.txt_ControlSabianRoundButtonText);
        fat_Icon = (FontAwesomeText) root.findViewById(R.id.fat_ControlSabianRoundButtonIcon);
    }

    private void init_attrs(AttributeSet attrs) {

        TypedArray theAttrs = context.obtainStyledAttributes(attrs, R.styleable.SabianRoundButton);

        int count = theAttrs.getIndexCount();

        for (int i = 0; i < count; i++) {
            int index = theAttrs.getIndex(i);
            if (index == R.styleable.SabianRoundButton_srb_Text) {
                setText(theAttrs.getString(index));
            } else if (index == R.styleable.SabianRoundButton_srb_TextColor) {
                setTextColor(theAttrs.getColor(index, NO_COLOR));
            } else if (index == R.styleable.SabianRoundButton_srb_Icon) {
                setIcon(theAttrs.getString(index));
            } else if (index == R.styleable.SabianRoundButton_srb_TypeDrawable) {
                setTypeDrawable(theAttrs.getResourceId(index, NO_DRAWABLE));
            }
        }
        theAttrs.recycle();

    }

    public void setText(String text) {
        txt_Text.setText(text);
    }

    public void setTextColor(int color) {
        txt_Text.setTextColor(color);
        fat_Icon.setTextColor(color);
    }

    public void setIcon(@Nullable String faIcon) {
        int visibility = View.VISIBLE;
        if (SabianUtilities.IsStringBlankOrEmpty(faIcon))
            visibility = View.GONE;
        fat_Icon.setVisibility(visibility);
        if (!SabianUtilities.IsStringBlankOrEmpty(faIcon))
            fat_Icon.setIcon(faIcon);
    }

    public void setTypeDrawable(int resourceID) {
        if (resourceID == NO_DRAWABLE)
            return;
        ll_Container.setBackgroundResource(resourceID);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setBackground(Drawable drawable) {
        ll_Container.setBackground(drawable);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        ll_Container.setBackgroundDrawable(drawable);
    }

    public Drawable getBackground() {
        return ll_Container.getBackground();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        ll_Container.setOnClickListener(onClickListener);
    }

}
