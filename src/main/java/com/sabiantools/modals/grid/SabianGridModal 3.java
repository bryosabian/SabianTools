package com.sabiantools.modals.grid;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.sabiantools.R;
import com.sabiantools.controls.recyclerview.SabianDividerDecorator;
import com.sabiantools.utilities.SabianUtilities;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SabianGridModal extends Dialog {

    private ArrayList<SabianGridModalItem> items = new ArrayList<>();
    private ViewGroup vgBody;
    private String title;
    private boolean animate = true;
    private int spanCount = 3;

    public SabianGridModal(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sabian_grid_modal_layout);
        init_elements();
    }

    private void init_elements() {
        TextView txtTitle = (TextView) findViewById(R.id.sct_SabianModalTitle);
        vgBody = (ViewGroup) findViewById(R.id.rll_SabianModalContainer);
        RecyclerView rclHistoryList = (RecyclerView) findViewById(R.id.lst_GridModalList);


        rclHistoryList.addItemDecoration(new SabianDividerDecorator(getContext(),
                R.drawable.control_sabian_list_divider));

        if (!SabianUtilities.IsStringEmpty(title)) {
            txtTitle.setText(title);
        } else {
            txtTitle.setText("Items");
        }

        GridLayoutManager manager = new GridLayoutManager(getContext(), spanCount);
        rclHistoryList.setLayoutManager(manager);

        SabianGridModalAdapter adapter = new SabianGridModalAdapter(items);
        rclHistoryList.setAdapter(adapter);
    }

    public SabianGridModal setItems(List<SabianGridModalItem> items) {
        this.items = new ArrayList<>(items);
        return this;
    }

    public SabianGridModal addItem(SabianGridModalItem item) {
        if (items == null)
            items = new ArrayList<>();
        items.add(item);
        return this;
    }

    public SabianGridModal setTitle(String title) {
        this.title = title;
        return this;
    }

    public SabianGridModal setAnimate(boolean animate) {
        this.animate = animate;
        return this;
    }

    public SabianGridModal setSpanCount(int spanCount) {
        this.spanCount = spanCount;
        return this;
    }

    @Override
    public void show() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        super.show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getWindow().setAttributes(lp);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (animate) {
            Animation anim = AnimationUtils.loadAnimation(getContext(), com.sabiantools.R.anim.modal_popup_show);
            vgBody.startAnimation(anim);
        }
    }
}
