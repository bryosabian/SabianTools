package com.sabiantools.controls.recyclerview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sabiantools.utilities.SabianArrays;

import java.util.ArrayList;

/**
 * Created by Brian Sabana on 14/04/2017.
 */
public class SabianDividerDecorator extends RecyclerView.ItemDecoration {


    public static final int LAST_POSITION = -1;

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private static final String TAG = "DividerItem";
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;

    /**
     * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    private int mOrientation = RecyclerView.VERTICAL;

    private final Rect mBounds = new Rect();


    private int[] excludedPositions;

    /**
     * Only applies to view types
     */
    private int[] forViewTypes = null;


    public SabianDividerDecorator(Context context, @DrawableRes int resID) {
        this.mDivider = ContextCompat.getDrawable(context, resID);
    }

    public SabianDividerDecorator(Context context, @DrawableRes int resID, int[] forViewTypes) {
        this(context, resID);
        this.forViewTypes = forViewTypes;
    }

    public SabianDividerDecorator(Context context, @DrawableRes int resID, int[] forViewTypes, int[] excludedPositions) {
        this(context, resID, forViewTypes);
        this.excludedPositions = excludedPositions;
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager} changes orientation.
     *
     * @param orientation {@link RecyclerView#HORIZONTAL} or {@link RecyclerView#VERTICAL}
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    /**
     * Sets the {@link Drawable} for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || mDivider == null) {
            return;
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (isExcluded(i, childCount))
                continue;
            final View child = parent.getChildAt(i);
            if (!canDraw(child, parent))
                return;
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }


    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top;
        final int bottom;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (isExcluded(i, childCount))
                continue;
            final View child = parent.getChildAt(i);
            if (!canDraw(child, parent))
                return;
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(child.getTranslationX());
            final int left = right - mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

    private boolean isExcluded(int position, int totalChildren) {

        if (excludedPositions == null || excludedPositions.length == 0)
            return false;

        if (SabianArrays.exists(excludedPositions, position))
            return true;

        if (SabianArrays.exists(excludedPositions, LAST_POSITION)) {
            return position == totalChildren - 1;
        }
        return false;
    }


    private boolean canDraw(View child, RecyclerView parent) {
        try {
            if (forViewTypes != null && parent.getAdapter() != null) {
                int position = parent.getChildAdapterPosition(child);
                int viewType = parent.getAdapter().getItemViewType(position);
                return SabianArrays.exists(forViewTypes, viewType);
            }
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return true;
        }
    }


    public SabianDividerDecorator setExcludedPositions(int[] excludedPositions) {
        this.excludedPositions = excludedPositions;
        return this;
    }

    public SabianDividerDecorator setForViewTypes(int[] forViewTypes) {
        this.forViewTypes = forViewTypes;
        return this;
    }

}
