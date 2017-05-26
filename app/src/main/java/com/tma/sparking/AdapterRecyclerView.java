package com.tma.sparking;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tma.sparking.interfaces.RecyclerOnItemClickListener;
import com.tma.sparking.models.TimeParking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntmhanh on 5/25/2017.
 */


public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.RecyclerViewHolder> {

    private List<TimeParking> listData = new ArrayList<>();
    private int selected_position = 0;
    private static RecyclerOnItemClickListener mItemClickListener;


   public AdapterRecyclerView(List<TimeParking> listData) {
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
            TimeParking timeParking = listData.get(position);
            holder.txtHeader.setText(timeParking.timeText);

            if (selected_position == position) {
                holder.txtHeader.setTextColor(Color.parseColor("#55da3b"));
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
                    if (mItemClickListener != null){
                        mItemClickListener.onItemClick(v, position);
                    }
                }
            });
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void SetOnItemClickListener(RecyclerOnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtHeader;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtHeader = (TextView) itemView.findViewById(R.id.txtHours);
        }

    }


}