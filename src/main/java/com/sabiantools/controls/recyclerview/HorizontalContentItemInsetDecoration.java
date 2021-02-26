package com.sabiantools.controls.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class HorizontalContentItemInsetDecoration extends RecyclerView.ItemDecoration {
    private int startOffset;
    private int itemOffset;

    public HorizontalContentItemInsetDecoration(int startOffset, int itemOffset) {
        this.startOffset = startOffset;
        this.itemOffset = itemOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        int totalItems = state.getItemCount();

        if (position == 0) {
            outRect.set(startOffset, 0, 0, 0);
        } else if (position == totalItems - 1) {
            outRect.set(0, 0, itemOffset, 0);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }
}