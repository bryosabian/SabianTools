package com.sabiantools.controls.recyclerview;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by Brian Sabana on 11/03/2018.
 */
public class OffsetHorizontalItemDecoration extends RecyclerView.ItemDecoration {

    public static final int NO_MARGIN=-1;

    private int firstMargin = NO_MARGIN;
    private int lastMargin = NO_MARGIN;

    public OffsetHorizontalItemDecoration(int firstMargin, int lastMargin) {
        this.firstMargin = firstMargin;
        this.lastMargin = lastMargin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int itemPosition = parent.getChildAdapterPosition(view);

        if(itemPosition==RecyclerView.NO_POSITION){
            return;
        }

        int totalItems = state.getItemCount();

            /*Apply first margin to first position*/
        if(itemPosition==0 && firstMargin!=NO_MARGIN){
            outRect.left=firstMargin;
        }

            /*Apply last margin to final position*/
        if(totalItems>0 && itemPosition==totalItems-1 && lastMargin!=NO_MARGIN){
            outRect.right=lastMargin;
        }
    }
}
