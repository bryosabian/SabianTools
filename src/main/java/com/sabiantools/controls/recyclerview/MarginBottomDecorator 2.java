package com.sabiantools.controls.recyclerview;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by edith on 08/05/2018.
 */
public class MarginBottomDecorator extends RecyclerView.ItemDecoration {

    private int margin;

    public MarginBottomDecorator(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == RecyclerView.NO_POSITION) {
            return;
        }
        int totalItems = state.getItemCount();
        int lastItemPos = totalItems - 1;

        if (totalItems > 0 && itemPosition == lastItemPos) {
            outRect.bottom = margin;
        }
    }
}
