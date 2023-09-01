package com.sabiantools.controls;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.gc.materialdesign.views.ButtonFlat;
import com.sabiantools.R;
import com.sabiantools.extensions.SabianViewKt;
import com.sabiantools.utilities.SabianToast;

import kotlin.Function;
import kotlin.jvm.functions.Function0;

public class SabianStickyNotificationView {

    private final ContextWrapper wrapper;

    private NotView mView;

    @DrawableRes
    private Integer vectorIcon = null;

    @ColorRes
    private Integer vectorIconColor = null;

    @ColorRes
    private Integer textColor = null;

    @ColorRes
    private Integer backgroundColor = null;


    private SabianToast.MessageType messageType;

    @DrawableRes
    private Integer icon = null;

    private View bodyView;

    private String message;


    private String actionText;

    @ColorRes
    private Integer actionTextColor;

    private View.OnClickListener onActionClickContainer;

    private OnNotificationViewListener listener;

    public interface OnNotificationViewListener {
        default void onShown() {

        }

        default void onHidden() {

        }
    }

    public SabianStickyNotificationView(Context context) {
        wrapper = new ContextWrapper(context);
    }

    public void create(ViewGroup parent) {
        if (mView != null) {
            return;
        }
        mView = new NotView(wrapper.getBaseContext());
        mView.create(parent);
        setProperties();
    }

    public void show(ViewGroup parent) {
        if (mView != null) {
            show();
            return;
        }
        create(parent);
        show();
    }

    public void show() {
        if (mView != null) {
            mView.show();
        }
    }

    public void hide() {
        if (mView != null)
            mView.hide();
    }

    private void setProperties() {
        setVectorIcon(vectorIcon, vectorIconColor)
                .setIcon(icon)
                .setMessage(message)
                .setTextColor(textColor)
                .setBackgroundColor(backgroundColor)
                .setActionTextColor(actionTextColor)
                .setMessageType(messageType)
                .setBodyView(bodyView)
                .setAction(actionText, onActionClickContainer)
                .setListener(listener)
        ;
    }


    public SabianStickyNotificationView setVectorIcon(@DrawableRes Integer vectorIcon, @ColorRes Integer vectorIconColor) {
        this.vectorIcon = vectorIcon;
        this.vectorIconColor = vectorIconColor;
        if (mView != null && vectorIcon != null) {
            mView.activateMessageView();
            if (vectorIconColor != null) {
                mView.mIconView.setColorFilter(ContextCompat.getColor(getContext(), vectorIconColor));
            }
            VectorDrawableCompat vc = VectorDrawableCompat.create(wrapper.getResources(), vectorIcon, null);
            mView.mIconView.setImageDrawable(vc);
        }
        return this;
    }

    public SabianStickyNotificationView setAction(String action, View.OnClickListener actionListener) {
        this.actionText = action;
        this.onActionClickContainer = actionListener;
        if (mView != null) {
            if (action != null) {
                mView.mActionContainerView.setVisibility(View.VISIBLE);
                mView.mActionButtonView.setText(action);
            } else {
                mView.mActionContainerView.setVisibility(View.GONE);
            }
            mView.mActionButtonView.setOnClickListener(actionListener);
        }
        return this;
    }


    public SabianStickyNotificationView setActionTextColor(@ColorRes Integer actionTextColor) {
        this.actionTextColor = actionTextColor;
        if (mView != null && actionTextColor != null) {
            mView.mActionButtonView.setTextColor(ContextCompat.getColor(getContext(), actionTextColor));
        }
        return this;
    }

    public void removeIcon() {
        icon = null;
        vectorIcon = null;
        if (mView != null)
            mView.mIconView.setImageDrawable(null);
    }


    public SabianStickyNotificationView setIcon(@DrawableRes Integer icon) {
        this.icon = icon;
        if (mView != null && icon != null) {
            mView.mIconView.setImageDrawable(ContextCompat.getDrawable(getContext(), icon));
        }
        return this;
    }

    public SabianStickyNotificationView setBodyView(View bodyView) {
        this.bodyView = bodyView;
        if (mView != null && bodyView != null) {
            mView.activateBodyView();
            mView.mBodyView.removeAllViews();
            mView.mBodyView.addView(bodyView);
        }
        return this;
    }

    public SabianStickyNotificationView setMessage(String message) {
        this.message = message;
        if (mView != null && message != null) {
            mView.activateMessageView();
            mView.mTxtMessage.setText(message);
        }
        return this;
    }

    public SabianStickyNotificationView setMessageType(SabianToast.MessageType messageType) {
        this.messageType = messageType;
        if (mView != null && messageType != null) {
            setBackgroundColor(messageType.getColor());
            setTextColor(messageType.getTextColor());
            setVectorIcon(messageType.getVectorIcon(), messageType.getTextColor());
        }
        return this;
    }

    public SabianStickyNotificationView setBackgroundColor(@ColorRes Integer color) {
        this.backgroundColor = color;
        if (mView != null && color != null) {
            mView.mContainerView.setBackgroundColor(ContextCompat.getColor(getContext(), color));
        }
        return this;
    }

    public SabianStickyNotificationView setTextColor(@ColorRes Integer textColor) {
        this.textColor = textColor;
        if (mView != null && textColor != null) {
            mView.mTxtMessage.setTextColor(ContextCompat.getColor(getContext(), textColor));
            mView.mIconView.setColorFilter(ContextCompat.getColor(getContext(), textColor));
            mView.mActionButtonView.setTextColor(ContextCompat.getColor(getContext(), textColor));
        }
        return this;
    }

    public SabianStickyNotificationView setListener(OnNotificationViewListener listener) {
        this.listener = listener;
        if (mView != null)
            mView.listener = listener;
        return this;
    }

    private Context getContext() {
        return wrapper.getBaseContext();
    }

    private static class NotView {

        private View mLayoutView;

        private ViewGroup mBodyView;
        private ImageView mIconView;
        private TextView mTxtMessage;
        private ViewGroup mContainerView;

        private ViewGroup mMessageContainerView;

        private ViewGroup mActionContainerView;

        private TextView mActionButtonView;

        private final Context context;

        private boolean isShowing;

        private OnNotificationViewListener listener;

        private final long duration = 300L;

        public NotView(Context context) {
            this.context = context;
        }

        public void create(ViewGroup parent) {
            if (mLayoutView != null)
                return;
            mLayoutView = LayoutInflater.from(context).inflate(R.layout.sabian_sticky_view_layout, parent, false);

            if (parent instanceof LinearLayout)
                parent.addView(mLayoutView, 0);
            else
                parent.addView(mLayoutView);

            mContainerView = mLayoutView.findViewById(R.id.ll_StickyViewContainer);
            mBodyView = mLayoutView.findViewById(R.id.ll_StickyViewBody);
            mIconView = mLayoutView.findViewById(R.id.img_StickyViewMessageIcon);
            mTxtMessage = mLayoutView.findViewById(R.id.sct_StickyViewMessageText);
            mMessageContainerView = mLayoutView.findViewById(R.id.ll_StickyViewMessageContainer);
            mActionContainerView = mLayoutView.findViewById(R.id.ll_StickyViewMessageActionContainer);
            mActionButtonView = mLayoutView.findViewById(R.id.btn_StickyViewMessageAction);
        }

        public void activateMessageView() {
            mMessageContainerView.setVisibility(View.VISIBLE);
            mBodyView.setVisibility(View.GONE);
        }

        public void activateBodyView() {
            mMessageContainerView.setVisibility(View.GONE);
            mBodyView.setVisibility(View.VISIBLE);
        }

        public void show() {
            if (isShowing)
                return;
            SabianViewKt.fadeVisibility(mLayoutView, View.VISIBLE, duration);
            mLayoutView.postDelayed(() -> {
                mLayoutView.setVisibility(View.VISIBLE);
                isShowing = true;
                if (listener != null)
                    listener.onShown();
            }, duration);
        }

        public void hide() {
            if (!isShowing)
                return;
            SabianViewKt.fadeVisibility(mLayoutView, View.GONE, duration);
            mLayoutView.postDelayed(() -> {
                mLayoutView.setVisibility(View.GONE); //Remove the extra space
                isShowing = false;
                if (listener != null)
                    listener.onHidden();
            }, duration);
        }
    }
}
