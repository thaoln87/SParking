package com.tma.sparking.interfaces;

import android.view.View;

/**
 * Created by ntmhanh on 5/26/2017.
 */

public interface RecyclerOnItemClickListener {
    /**
     * Called when an item is clicked.
     *
     * @param childView View of the item that was clicked.
     * @param position  Position of the item that was clicked.
     */
    void onItemClick(View childView, int position);
}
