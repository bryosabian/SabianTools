package com.sabiantools.controls.recyclerview;

import android.graphics.Rect;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by bryosabian on 9/20/2016.
 */
public class MarginTopDecorator extends RecyclerView.ItemDecoration {

    private int marginTopSize;

    private boolean applyTopAndBottom = false;

    private ArrayList<Integer> excludePositions = new ArrayList<>();

    public MarginTopDecorator(int marginTopSize) {
        this.marginTopSize = marginTopSize;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (applyTopAndBottom) {
            outRect.top = marginTopSize;
            outRect.bottom = marginTopSize;
            return;
        }

        int pos = parent.getChildLayoutPosition(view);

        // Add top margin only for all items (Except the first)
        if (pos == 0) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }

        if (excludePositions.contains(pos))
            return;

        outRect.top = marginTopSize;
    }

    public MarginTopDecorator setApplyTopAndBottom(boolean applyTopAndBottom) {
        this.applyTopAndBottom = applyTopAndBottom;
        return this;
    }

    public MarginTopDecorator setExcludePositionsFromMargin(ArrayList<Integer> excludePositions) {
        this.excludePositions = excludePositions;
        return this;
    }

    public MarginTopDecorator addExcludedPositionFromMargin(int pos) {
        excludePositions.add(pos);
        return this;
    }

    public MarginTopDecorator addExcludedPositionsFromMargin(int[] pos) {
        for (int i = 0; i < pos.length; i++)
            addExcludedPositionFromMargin(pos[i]);
        return this;
    }
}
