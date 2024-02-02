package com.sabiantools.controls.recyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public abstract class SabianAbstractDecoration extends RecyclerView.ItemDecoration {

    public static final int LAST_POSITION = -1;


    protected ArrayList<Integer> excludePositions = new ArrayList<>();

    protected ArrayList<Integer> onlyFor = new ArrayList<>();

    protected ArrayList<Integer> forViewTypes = new ArrayList<>();

    private ArrayList<Validator> validators = new ArrayList<>();

    public SabianAbstractDecoration() {
        validators.add(new ExcludedPositionValidator());
        validators.add(new OnlyForValidator());
        validators.add(new ViewTypeValidator());
    }

    protected boolean canBeApplied(@NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int pos = parent.getChildLayoutPosition(view);
        for (Validator validator : validators) {
            if (!validator.canBeApplied(pos, view, parent, state)) {
                return false;
            }
        }
        return true;
    }


    public SabianAbstractDecoration setExcludePositionsFromMargin(ArrayList<Integer> excludePositions) {
        this.excludePositions = excludePositions;
        return this;
    }

    public SabianAbstractDecoration addExcludedPositionFromMargin(int pos) {
        excludePositions.add(pos);
        return this;
    }

    public SabianAbstractDecoration addExcludedPositionsFromMargin(int[] pos) {
        for (int po : pos) addExcludedPositionFromMargin(po);
        return this;
    }

    public SabianAbstractDecoration setOnlyFor(ArrayList<Integer> onlyFor) {
        this.onlyFor = onlyFor;
        return this;
    }

    public SabianAbstractDecoration addOnlyFor(int pos) {
        onlyFor.add(pos);
        return this;
    }

    public SabianAbstractDecoration addOnlyFor(int[] pos) {
        for (int po : pos) addOnlyFor(po);
        return this;
    }

    public SabianAbstractDecoration setForViewTypes(ArrayList<Integer> forViewTypes) {
        this.forViewTypes = forViewTypes;
        return this;
    }

    public SabianAbstractDecoration addForViewType(int type) {
        forViewTypes.add(type);
        return this;
    }

    public SabianAbstractDecoration addForViewType(int[] types) {
        for (int po : types) addForViewType(po);
        return this;
    }

    private interface Validator {
        boolean canBeApplied(int pos, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state);
    }

    private class ExcludedPositionValidator implements Validator {

        @Override
        public boolean canBeApplied(int pos, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            if (excludePositions.size() == 0)
                return true;
            if (excludePositions.contains(pos))
                return false;
            int totalItems = state.getItemCount();
            boolean isLast = totalItems > 0 && pos == (totalItems - 1);
            boolean isExcluded = isLast && excludePositions.contains(LAST_POSITION);
            return !isExcluded;
        }
    }

    private class OnlyForValidator implements Validator {

        @Override
        public boolean canBeApplied(int pos, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            if (onlyFor.size() == 0)
                return true;
            if (onlyFor.contains(pos))
                return true;
            int totalItems = state.getItemCount();
            boolean isLast = totalItems > 0 && pos == (totalItems - 1);
            if (isLast && onlyFor.contains(LAST_POSITION))
                return true;
            return false;
        }
    }

    private class ViewTypeValidator implements Validator {

        @Override
        public boolean canBeApplied(int pos, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            try {
                if (forViewTypes.size() == 0)
                    return true;
                if (parent.getAdapter() != null) {
                    int viewType = parent.getAdapter().getItemViewType(pos);
                    return forViewTypes.contains(viewType);
                }
                return true;
            } catch (Throwable e) {
                e.printStackTrace();
                return true;
            }
        }
    }
}
