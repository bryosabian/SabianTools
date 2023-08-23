package com.sabiantools.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by Brian Sabana on 06/02/2017.
 */
public class SabianViewPager extends ViewPager {

    private boolean allowSwipe = true;

    public SabianViewPager(Context context) {
        super(context);
    }

    public SabianViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAllowSwipe(boolean activateSwipe) {
        this.allowSwipe = activateSwipe;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!allowSwipe) {
            return false;
        } else {
            return super.onInterceptTouchEvent(event);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!allowSwipe) {
            return false;
        } else {
            return super.onTouchEvent(event);
        }

    }
}
