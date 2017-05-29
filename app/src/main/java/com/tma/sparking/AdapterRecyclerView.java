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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ntmhanh on 5/25/2017.
 */


public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.RecyclerViewHolder> {

    private List<TimeParking> listData = new ArrayList<>();
    private int selected_position = 0;
    private int theNumberOfHours = 1;
    RecyclerOnItemClickListener mItemClickListener;


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
            theNumberOfHours = position + 1;
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
                    theNumberOfHours = position + 1;
                    notifyItemChanged(selected_position);
                    if (mItemClickListener != null){
                        mItemClickListener.onItemClick(v, theNumberOfHours);
                    }
                }
            });
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void SetOnItemClickListener(final RecyclerOnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txtHours) TextView txtHeader;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }

        }
    }


}