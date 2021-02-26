package com.sabiantools.controls.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

public class SabianFlexibleScrollLayoutManager extends LinearLayoutManager {

    private boolean canScrollVertically = true;
    private boolean canScrollHorizontally = true;

    public SabianFlexibleScrollLayoutManager(Context context) {
        super(context);
    }

    public SabianFlexibleScrollLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public SabianFlexibleScrollLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SabianFlexibleScrollLayoutManager setCanScrollVertically(boolean canScrollVertically) {
        this.canScrollVertically = canScrollVertically;
        return this;
    }

    public SabianFlexibleScrollLayoutManager setCanScrollHorizontally(boolean canScrollHorizontally) {
        this.canScrollHorizontally = canScrollHorizontally;
        return this;
    }

    @Override
    public boolean canScrollHorizontally() {
        return canScrollHorizontally;
    }

    @Override
    public boolean canScrollVertically() {
        return canScrollVertically;
    }
}
