package com.sabiantools.controls;

import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created By Brian Sabana on 9/20/2016.
 */
public class SabianRecyclerMarginTopDecorator extends RecyclerView.ItemDecoration {

    private final int marginTopSize;

    private boolean applyTopAndBottom = false;

    private ArrayList<Integer> excludePositions = new ArrayList<>();

    private boolean onlyApplyToFirstElement = false;

    private boolean ignoreFirstElement = true;

    public SabianRecyclerMarginTopDecorator(int marginTopSize) {
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
        if (pos == 0 && ignoreFirstElement) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }

        if (pos != 0 && onlyApplyToFirstElement) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }

        if (excludePositions.contains(pos)) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }

        outRect.top = marginTopSize;
    }

    public SabianRecyclerMarginTopDecorator setApplyTopAndBottom(boolean applyTopAndBottom) {
        this.applyTopAndBottom = applyTopAndBottom;
        return this;
    }

    public SabianRecyclerMarginTopDecorator setExcludePositionsFromMargin(ArrayList<Integer> excludePositions) {
        this.excludePositions = excludePositions;
        return this;
    }

    public SabianRecyclerMarginTopDecorator addExcludedPositionFromMargin(int pos) {
        excludePositions.add(pos);
        return this;
    }

    public SabianRecyclerMarginTopDecorator addExcludedPositionsFromMargin(int[] pos) {
        for (int i = 0; i < pos.length; i++)
            addExcludedPositionFromMargin(pos[i]);
        return this;
    }


    public SabianRecyclerMarginTopDecorator setOnlyApplyToFirstElement(boolean onlyApplyToFirstElement) {
        this.onlyApplyToFirstElement = onlyApplyToFirstElement;
        return this;
    }

    public SabianRecyclerMarginTopDecorator setIgnoreFirstElement(boolean ignoreFirstElement) {
        this.ignoreFirstElement = ignoreFirstElement;
        return this;
    }
}
