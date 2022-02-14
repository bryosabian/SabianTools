package com.sabiantools.modals.grid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sabiantools.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SabianGridModalAdapter extends RecyclerView.Adapter<SabianGridModalItemHolder> {

    private List<SabianGridModalItem> items;

    public SabianGridModalAdapter(List<SabianGridModalItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public SabianGridModalItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sabian_grid_modal_item_layout, parent, false);
        return new SabianGridModalItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SabianGridModalItemHolder holder, int position) {
        holder.bindItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
