package com.sabiantools.modals.grid;

public class SabianGridModalItem {
    private String title;
    private boolean isBold;
    private float textSize = -1;

    public float getTextSize() {
        return textSize;
    }

    public SabianGridModalItem setTextSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public SabianGridModalItem(String title) {
        this.title = title;
        isBold = false;
    }

    public SabianGridModalItem(String title, boolean isBold) {
        this.title = title;
        this.isBold = isBold;
    }

    public SabianGridModalItem(String title, boolean isBold, float textSize) {
        this.title = title;
        this.isBold = isBold;
        this.textSize = textSize;
    }

    public SabianGridModalItem(String title, float textSize) {
        this.title = title;
        this.textSize = textSize;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBold() {
        return isBold;
    }
}
