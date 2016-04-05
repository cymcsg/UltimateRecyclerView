package com.marshalchen.ultimaterecyclerview.demo.rvComponents;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.demo.R;

/**
 * Created by hesk on 3/2/16.
 */
public class itemGridCellBinder extends UltimateRecyclerviewViewHolder {
    public static final int layout = R.layout.grid_item;
    public TextView textViewSample;
    public ImageView imageViewSample;
    public View item_view;

    public itemGridCellBinder(View itemView, boolean isItem) {
        super(itemView);
        if (isItem) {
            textViewSample = (TextView) itemView.findViewById(R.id.example_row_tv_title);
            imageViewSample = (ImageView) itemView.findViewById(R.id.example_row_iv_image);
            item_view = itemView.findViewById(R.id.planview);
        }
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }
}