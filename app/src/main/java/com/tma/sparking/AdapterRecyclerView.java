package com.tma.sparking;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntmhanh on 5/25/2017.
 */


public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.RecyclerViewHolder> {
    private List<String> listData = new ArrayList<>();
    private int selected_position = 0;
    public AdapterRecyclerView(List<String> listData) {
        this.listData = listData;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.recyclerview_hours, parent, false);
        return new RecyclerViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        if (position > -1 && position < 9) {
            holder.txtHeader.setText(listData.get(position));

            if (selected_position == position) {
                holder.txtHeader.setTextColor(Color.GREEN);
                holder.txtHeader.setTextSize(20);
            } else {
                holder.txtHeader.setTextColor(Color.BLACK);
                holder.txtHeader.setTextSize(15);

            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Updating old as well as new positions
                    notifyItemChanged(selected_position);
                    selected_position = position;
                    notifyItemChanged(selected_position);

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtHeader;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtHeader = (TextView) itemView.findViewById(R.id.txtHeader);
        }

    }
}