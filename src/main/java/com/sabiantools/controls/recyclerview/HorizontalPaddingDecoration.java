package com.sabiantools.controls.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalPaddingDecoration extends SabianAbstractDecoration {

    private final int padding;

    public HorizontalPaddingDecoration(int padding) {
        this.padding = padding;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
        if (!canBeApplied(view, parent, state)) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }
        outRect.left = padding;
        outRect.right = padding;
    }
}
