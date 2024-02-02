package com.sabiantools.controls.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by bryosabian on 9/20/2016.
 */
public class MarginTopDecorator extends SabianAbstractDecoration {


    protected final int margin;


    protected boolean applyTopAndBottom = false;

    public MarginTopDecorator(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        if (!canBeApplied(view, parent, state)) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }

        if (applyTopAndBottom) {
            outRect.top = margin;
            outRect.bottom = margin;
            return;
        }

        outRect.top = margin;
    }


    public MarginTopDecorator setApplyTopAndBottom(boolean applyTopAndBottom) {
        this.applyTopAndBottom = applyTopAndBottom;
        return this;
    }


}
