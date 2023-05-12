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

import com.sabiantools.R;
import com.sabiantools.utilities.SabianUtilities;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class SabianRibbon extends FrameLayout {

    public enum Type {
        DANGER(0, R.drawable.bg_ribbon_danger),
        SUCCESS(1, R.drawable.bg_ribbon_success),
        WARNING(2, R.drawable.bg_ribbon_warning);

        private int ID;

        @DrawableRes
        private int drawable;

        Type(int ID, @DrawableRes int drawable) {
            this.ID = ID;
            this.drawable = drawable;
        }

        public int getID() {
            return ID;
        }

        @DrawableRes
        public int getDrawable() {
            return drawable;
        }
    }

    private String title;
    private TextView txtTitle;
    private ViewGroup vgContainer;
    private Type type;

    public SabianRibbon(@NonNull Context context) {
        super(context);
        initElements(context);
    }

    public SabianRibbon(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initElements(context);
        initAttributes(attrs);
    }

    public SabianRibbon(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initElements(context);
        initAttributes(attrs);
    }

    private void initElements(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.control_sabian_ribbon, this, true);
        txtTitle = view.findViewById(R.id.sct_SabianRibbonText);
        vgContainer = view.findViewById(R.id.rll_SabianRibbonContainer);
    }

    public void setType(Type type) {
        this.type = type;
        Drawable drawable = ContextCompat.getDrawable(getContext(), type.getDrawable());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            vgContainer.setBackground(drawable);
        } else {
            vgContainer.setBackgroundDrawable(drawable);
        }
    }

    public void setTitle(String title) {
        this.title = title;
        this.txtTitle.setText(title);
    }

    private void setType(int value) {
        Type[] types = Type.values();
        for (Type type : types) {
            if (type.getID() == value) {
                setType(type);
                return;
            }
        }
        SabianUtilities.WriteLog("No type id found for " + value);
    }

    private void initAttributes(AttributeSet set) {
        TypedArray a = getContext().obtainStyledAttributes(set, R.styleable.SabianRibbon);
        int aCount = a.getIndexCount();
        for (int i = 0; i < aCount; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SabianRibbon_srb_title) {
                setTitle(a.getString(attr));
            } else if (attr == R.styleable.SabianRibbon_srb_type) {
                setType(a.getInt(attr, Type.SUCCESS.getID()));
            }
        }
    }
}
