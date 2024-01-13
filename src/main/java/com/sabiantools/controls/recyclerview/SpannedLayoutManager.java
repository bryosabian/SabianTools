package com.sabiantools.controls.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Creates a fixed width/height layout manager where width/height of items is assigned according to the item count
 * thus fitting items in a container horizontally or vertically
 */
public class SpannedLayoutManager extends LinearLayoutManager {

    public static final int NO_MAXIMUM_SIZE = -1;

    private int maximumItemSize = NO_MAXIMUM_SIZE;

    public SpannedLayoutManager(Context context) {
        super(context);
    }

    public SpannedLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public SpannedLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        int average = (maximumItemSize > NO_MAXIMUM_SIZE) ? Math.min(maximumItemSize, getItemCount()) : getItemCount();
        if (getOrientation() == RecyclerView.HORIZONTAL) {
            params.width = (int) Math.round(getHorizontalSpace() / (double) average);
        } else if (getOrientation() == RecyclerView.VERTICAL) {
            params.height = (int) Math.round(getVerticalSpace() / (double) average);
        }
        return params;
    }


    @Override
    public boolean canScrollHorizontally() {
        return maximumItemSize > NO_MAXIMUM_SIZE;
    }

    @Override
    public boolean canScrollVertically() {
        return maximumItemSize > NO_MAXIMUM_SIZE;
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    public SpannedLayoutManager setMaximumItemSize(int maximumItemSize) {
        this.maximumItemSize = maximumItemSize;
        return this;
    }


}
