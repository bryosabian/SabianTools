package com.sabiantools.controls.recyclerview;

import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

/**
 * Created by edith on 08/05/2018.
 */
public class MarginBottomDecorator extends MarginTopDecorator {

    public MarginBottomDecorator(int margin) {
        super(margin);
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
        outRect.bottom = margin;
    }
}
