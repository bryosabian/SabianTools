package com.sabiantools.controls.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SabianFixedWidthLayoutManager extends LinearLayoutManager {
    private int width;

    public SabianFixedWidthLayoutManager setWidth(int width) {
        this.width = width;
        return this;
    }

    public SabianFixedWidthLayoutManager(Context context, int width) {
        super(context);
        this.width = width;
    }

    public SabianFixedWidthLayoutManager(Context context, int orientation, int width, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        this.width = width;
    }

    public SabianFixedWidthLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {

        RecyclerView.LayoutParams params = spanLayoutSize(super.generateDefaultLayoutParams());

        if (params != null)
            return params;

        return super.generateDefaultLayoutParams();
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        RecyclerView.LayoutParams params = spanLayoutSize(super.generateLayoutParams(c, attrs));
        if (params != null)
            return params;
        return super.generateLayoutParams(c, attrs);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        RecyclerView.LayoutParams params = spanLayoutSize(super.generateLayoutParams(lp));
        if (params != null)
            return params;
        return super.generateLayoutParams(lp);
    }

    private RecyclerView.LayoutParams spanLayoutSize(RecyclerView.LayoutParams params) {
        if (getOrientation() == HORIZONTAL) {
            params.width = this.width;
        }
        return params;
    }

}
