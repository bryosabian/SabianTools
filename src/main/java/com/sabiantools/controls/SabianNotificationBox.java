package com.sabiantools.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.gc.materialdesign.views.ButtonFlat;
import com.sabiantools.R;
import com.sabiantools.controls.texts.SabianCondensedText;

import androidx.annotation.ColorInt;

/**
 * Created By Brian Sabana on 7/18/2016.
 */
public class SabianNotificationBox extends FrameLayout {


    protected View root;

    protected ViewGroup vg_ButtonContainer;

    protected SabianCondensedText txt_title, txt_description;

    protected SabianCondensedText btn_Action;

    protected SabianCardView cdv_Container;

    protected View v_Divider;

    protected boolean displayButton = true;

    public SabianNotificationBox(Context context) {
        super(context);
        initElements(context);
    }

    public SabianNotificationBox(Context context, AttributeSet set) {
        super(context, set);
        initElements(context);
        initAttributes(set);
    }

    public SabianNotificationBox(Context context, AttributeSet set, int defStyle) {
        super(context, set, defStyle);
        initElements(context);
        initAttributes(set);
    }

    public void initElements(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);

        root = inflater.inflate(R.layout.control_sabian_notification_box, this, true);

        vg_ButtonContainer = root.findViewById(R.id.rll_SabianNotificationButton);

        v_Divider = root.findViewById(R.id.v_SabianNotificationDivider);

        cdv_Container = root.findViewById(R.id.cdv_SabianNotificationContainer);

        txt_title = root.findViewById(R.id.txt_SabianNotificationTitle);

        txt_description = root.findViewById(R.id.txt_SabianNotificationDescription);

        btn_Action = root.findViewById(R.id.btn_SabianNotificationButton);

        if (!displayButton)
            btn_Action.setVisibility(GONE);

    }


    public void setButtonColor(@ColorInt int color) {
        btn_Action.setTextColor(color);
    }

    public void setNotificationColor(@ColorInt int color) {
        cdv_Container.setCardBackgroundColor(color);
    }

    public void setTitleColor(@ColorInt int color) {
        txt_title.setTextColor(color);
    }

    public void setTextColor(@ColorInt int color) {
        txt_description.setTextColor(color);
        txt_title.setTextColor(color);
    }

    public void setDividerColor(@ColorInt int color) {
        v_Divider.setBackgroundColor(color);
    }

    public void setTitle(String title) {
        txt_title.setText(title);
    }

    public void setDescription(String description) {
        txt_description.setText(description);
    }

    public void setButtonText(String text) {
        if (text != null)
            btn_Action.setText(text);
    }

    public void setElevation(float elevation) {
        if (elevation <= -1)
            return;
        cdv_Container.setMaxCardElevation(elevation);
        cdv_Container.setCardElevation(elevation);
    }

    public void setCornerRadius(float radius) {
        if (radius <= -1)
            return;
        cdv_Container.setRadius(radius);
    }

    public void setOnButtonClickListener(OnClickListener listener) {
        btn_Action.setOnClickListener(listener);
    }

    public void setDisplayButton(boolean displayButton) {
        this.displayButton = displayButton;
        this.vg_ButtonContainer.setVisibility((displayButton) ? VISIBLE : GONE);
        this.v_Divider.setVisibility(vg_ButtonContainer.getVisibility());
    }


    private void initAttributes(AttributeSet set) {
        TypedArray a = getContext().obtainStyledAttributes(set, R.styleable.SabianNotificationBox);
        int aCount = a.getIndexCount();
        for (int i = 0; i < aCount; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SabianNotificationBox_snb_Title) {
                setTitle(a.getString(attr));
            }
            if (attr == R.styleable.SabianNotificationBox_snb_Text) {
                setDescription(a.getString(attr));
            }
            if (attr == R.styleable.SabianNotificationBox_snb_ButtonText) {
                setButtonText(a.getString(attr));
            }
            if (attr == R.styleable.SabianNotificationBox_snb_TextColor) {
                setTextColor(a.getColor(attr, -1));
            }
            if (attr == R.styleable.SabianNotificationBox_snb_TitleColor) {
                setTitleColor(a.getColor(attr, -1));
            }
            if (attr == R.styleable.SabianNotificationBox_snb_DividerColor) {
                setDividerColor(a.getColor(attr, -1));
            }
            if (attr == R.styleable.SabianNotificationBox_snb_NotificationColor) {
                setNotificationColor(a.getColor(attr, -1));
            }
            if (attr == R.styleable.SabianNotificationBox_snb_ButtonColor) {
                setButtonColor(a.getColor(attr, -1));
            }
            if (attr == R.styleable.SabianNotificationBox_snb_elevation) {
                setElevation(a.getDimension(attr, -1));
            }
            if (attr == R.styleable.SabianNotificationBox_snb_cornerRadius) {
                setCornerRadius(a.getDimension(attr, -1));
            }
            if (attr == R.styleable.SabianNotificationBox_snb_DisplayButton) {
                setDisplayButton(a.getBoolean(attr, true));
            }
        }
        a.recycle();
    }
}
