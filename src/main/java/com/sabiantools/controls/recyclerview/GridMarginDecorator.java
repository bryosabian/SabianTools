package com.sabiantools.controls.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class GridMarginDecorator extends RecyclerView.ItemDecoration {
    private final int rightMargin;
    private final int leftMargin;
    private final int topMargin;
    private final int bottomMargin;

    public static final int NO_MARGIN=-1;

    public GridMarginDecorator(int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        this.rightMargin = rightMargin;
        this.leftMargin = leftMargin;
        this.topMargin = topMargin;
        this.bottomMargin = bottomMargin;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = parent.getChildAdapterPosition(view);
        if(itemPosition==RecyclerView.NO_POSITION){
            return;
        }
        if(leftMargin != NO_MARGIN){
            outRect.left= leftMargin;
        }
        /*Apply right margin to all items apart from the last*/
        if(rightMargin != NO_MARGIN){
            outRect.right= rightMargin;
        }

        if(topMargin != NO_MARGIN){
            outRect.top=topMargin;
        }

        if(bottomMargin != NO_MARGIN){
            outRect.bottom=bottomMargin;
        }
    }
}
