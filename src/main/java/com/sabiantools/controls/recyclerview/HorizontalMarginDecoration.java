package com.sabiantools.controls.recyclerview;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by Brian Sabana on 11/03/2018.
 */
public class HorizontalMarginDecoration extends RecyclerView.ItemDecoration {

    public static final int NO_MARGIN=-1;

    private int rightMargin = NO_MARGIN;

    private int leftMargin = NO_MARGIN;

    public HorizontalMarginDecoration(int rightMargin, int leftMargin) {
        this.rightMargin = rightMargin;
        this.leftMargin = leftMargin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int itemPosition = parent.getChildAdapterPosition(view);

        if(itemPosition==RecyclerView.NO_POSITION){
            return;
        }

        int totalItems = state.getItemCount();

            /*Apply left margin to all items apart from the first*/
        if(itemPosition>0 && leftMargin !=NO_MARGIN){
            outRect.left= leftMargin;
        }

            /*Apply right margin to all items apart from the last*/
        if(totalItems>0 && itemPosition!=(totalItems-1) && rightMargin !=NO_MARGIN){
            outRect.right= rightMargin;
        }
    }
}
