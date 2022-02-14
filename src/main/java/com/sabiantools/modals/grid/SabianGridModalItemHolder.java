package com.sabiantools.modals.grid;

import android.util.TypedValue;
import android.view.View;

import com.sabiantools.R;
import com.sabiantools.controls.SabianCondensedText;

import androidx.recyclerview.widget.RecyclerView;

public class SabianGridModalItemHolder extends RecyclerView.ViewHolder {
    private SabianCondensedText txtTitle;
    private SabianGridModalItem item;

    public SabianGridModalItemHolder(View itemView) {
        super(itemView);
        txtTitle = (SabianCondensedText) itemView.findViewById(R.id.sct_SabianGridModalItemText);
    }

    public void bindItem(SabianGridModalItem item) {
        this.item = item;
        txtTitle.setText(item.getTitle());
        if (item.isBold()) {
            txtTitle.setRoboto("Bold");
        } else {
            txtTitle.setRoboto("Regular");
        }
        if (item.getTextSize() > -1) {
            txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, item.getTextSize());
        }
    }
}
