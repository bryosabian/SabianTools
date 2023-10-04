package com.sabiantools.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.Card;
import com.sabiantools.R;
import com.sabiantools.controls.texts.SabianCondensedText;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

/**
 * Created By Brian Sabana on 7/18/2016.
 */
public class SabianNotificationBox extends FrameLayout {

    protected Context _context;

    protected View root;

    protected SabianCondensedText txt_title, txt_description;

    protected ButtonFlat btn_Action;

    protected Card cdv_Container;

    protected boolean displayButton = true;

    public SabianNotificationBox(Context context) {
        super(context);

        init_elements(context);
    }

    public SabianNotificationBox(Context context, AttributeSet set) {
        super(context, set);

        init_elements(context);
    }

    public SabianNotificationBox(Context context, AttributeSet set, int defStyle) {
        super(context, set, defStyle);

        init_elements(context);
    }

    public void init_elements(Context context) {
        _context = context;

        LayoutInflater inflater = LayoutInflater.from(context);

        root = inflater.inflate(R.layout.control_sabian_notification_box, this, true);

        cdv_Container = (Card) root.findViewById(R.id.cdv_SabianNotificationContainer);

        txt_title = (SabianCondensedText) root.findViewById(R.id.txt_SabianNotificationTitle);

        txt_description = (SabianCondensedText) root.findViewById(R.id.txt_SabianNotificationDescription);

        btn_Action = (ButtonFlat) root.findViewById(R.id.btn_SabianNotificationButton);

        if (!displayButton)
            btn_Action.setVisibility(GONE);

    }

    public void setNotificationColorRes(@ColorRes int colorRes) {
        setNotificationColor(ContextCompat.getColor(_context, colorRes));
    }


    public void setButtonColorRes(@ColorRes int colorRes) {
        setButtonColor(ContextCompat.getColor(_context, colorRes));
    }

    public void setButtonColor(@ColorInt int color) {
        btn_Action.setBackgroundColor(color);
    }

    public void setNotificationColor(@ColorInt int color) {
        cdv_Container.setBackgroundColor(color);
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

    public void setOnButtonClickListener(OnClickListener listener) {
        btn_Action.setOnClickListener(listener);
    }

    public ButtonFlat getBtnAction() {
        return btn_Action;
    }

    public boolean isDisplayButton() {
        return displayButton;
    }

    public void setDisplayButton(boolean displayButton) {
        this.displayButton = displayButton;
        this.btn_Action.setVisibility((displayButton) ? VISIBLE : GONE);
    }
}
