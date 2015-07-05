package com.marshalchen.ultimaterecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperViewHolder;


/**
 * Created by MarshalChen on 15-6-2.
 */
public class UltimateRecyclerviewViewHolder extends RecyclerView.ViewHolder  implements
        ItemTouchHelperViewHolder {

    public UltimateRecyclerviewViewHolder(View itemView) {
        super(itemView);
    }


    @Override
    public void onItemSelected() {
        
    }

    @Override
    public void onItemClear() {

    }
}