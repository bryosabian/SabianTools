package com.sabiantools.controls.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Brian Sabana on 20/03/2018.
 */
public class SpannedOffsetItemDecoration extends RecyclerView.ItemDecoration{

    private int marginOffset;

    private int marginLastOffset;

    public static final int NO_OFFSET=-1;

    public SpannedOffsetItemDecoration(int marginOffset) {
        this.marginOffset = marginOffset;
    }

    public SpannedOffsetItemDecoration(int marginOffset, int marginLastOffset) {
        this.marginOffset = marginOffset;
        this.marginLastOffset = marginLastOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int itemPosition = parent.getChildAdapterPosition(view);

        if(itemPosition==RecyclerView.NO_POSITION){
            return;
        }

        int totalItems = state.getItemCount();

        int lastItemPos=totalItems-1;

        if(marginOffset!=NO_OFFSET)
            outRect.right=marginOffset;

        if(totalItems>0 && itemPosition==lastItemPos){
            //outRect.right=marginLastOffset;
        }
    }
}
