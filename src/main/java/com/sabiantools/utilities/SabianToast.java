package com.sabiantools.utilities;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.sabiantools.R;
import com.sabiantools.controls.texts.SabianCondensedText;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

/**
 * Created by Brian Sabana on 10/02/2017.
 */
public class SabianToast {

    private View view;

    private Context context;

    private FontAwesomeText toastIcon;

    private ViewGroup toastContainer;

    private SabianCondensedText toastText;

    private boolean displayIcon = true;

    public SabianToast(Context context) {
        this.context = context;
        getView();
    }

    public View getView() {

        view = LayoutInflater.from(context).inflate(R.layout.sabian_toast_layout, null);

        this.toastContainer = (RelativeLayout) view.findViewById(R.id.ll_ToastContainer);

        this.toastIcon = (FontAwesomeText) view.findViewById(R.id.ft_ToastIcon);

        this.toastText = (SabianCondensedText) view.findViewById(R.id.sct_ToastText);

        return view;

    }

    public SabianToast setText(String text) {
        this.toastText.setText(text);
        return this;
    }

    public SabianToast setIcon(String icon) {
        this.toastIcon.setIcon(icon);
        return this;
    }

    public SabianToast setDisplayIcon(boolean display) {

        toastIcon.setVisibility((display) ? View.VISIBLE : View.GONE);

        return this;
    }

    public SabianToast setSuccess() {

        toastContainer.setBackgroundResource(R.drawable.sabian_toast_layout_success);

        return this;
    }

    public SabianToast setDanger() {

        toastContainer.setBackgroundResource(R.drawable.sabian_toast_layout_danger);

        return this;
    }

    public SabianToast setPrimary() {

        toastContainer.setBackgroundResource(R.drawable.sabian_toast_layout_primary);

        return this;
    }

    public SabianToast setCustomType(@DrawableRes int backgroundResource) {

        toastContainer.setBackgroundResource(backgroundResource);

        return this;
    }

    public void display(int duration) {

        Toast toast = new Toast(context);

        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);

        toast.setDuration(duration);

        toast.setView(view);

        toast.show();
    }

    public enum MessageType {
        ERROR("fa-exclamation-circle", R.drawable.sabian_toast_layout_danger, R.color.sabian_toast_danger, R.color.sabian_white, R.drawable.vc_sabian_warning_24),
        SUCCESS("fa-check-circle", R.drawable.sabian_toast_layout_success, R.color.sabian_toast_success, R.color.sabian_white, R.drawable.vc_sabian_check_circle_24),
        INFORMATION("fa-info-circle", R.drawable.sabian_toast_layout_primary, R.color.sabian_toast_primary, R.color.sabian_white, R.drawable.vc_sabian_info_24);

        private final String icon;

        private final @DrawableRes
        int drawableResource;

        private final @ColorRes int color;

        private final @ColorRes int textColor;

        private final @DrawableRes int vectorIcon;

        MessageType(String icon, @DrawableRes int drawableResource, int color, int textColor, int vectorIcon) {
            this.icon = icon;
            this.drawableResource = drawableResource;
            this.color = color;
            this.textColor = textColor;
            this.vectorIcon = vectorIcon;
        }

        public String getIcon() {
            return icon;
        }

        @DrawableRes
        public int getDrawableResource() {
            return drawableResource;
        }

        @ColorRes
        public int getColor() {
            return color;
        }

        @ColorRes
        public int getTextColor() {
            return textColor;
        }

        @DrawableRes
        public int getVectorIcon() {
            return vectorIcon;
        }
    }
}
