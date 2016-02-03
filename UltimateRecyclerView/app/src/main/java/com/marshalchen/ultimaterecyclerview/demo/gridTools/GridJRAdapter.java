package com.marshalchen.ultimaterecyclerview.demo.gridTools;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateGridLayoutAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.modules.JRitem;

import java.util.List;

/**
 * Created by hesk on 3/2/16.
 */
public class GridJRAdapter extends UltimateGridLayoutAdapter<JRitem, GridStringAdapter.HolderGirdCell> {

    public GridJRAdapter() {
        super();
    }

    public GridJRAdapter(List<JRitem> hand) {
        super(hand);
    }

    @Override
    public UltimateRecyclerviewViewHolder getViewHolder(View view) {
        UltimateRecyclerviewViewHolder g = new UltimateRecyclerviewViewHolder(view);
        return g;
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    protected void bindNormal(GridStringAdapter.HolderGirdCell b, JRitem jRitem, int position) {
        b.textViewSample.setText(jRitem.train_name);
        b.imageViewSample.setImageResource(jRitem.photo_id);
    }

    @Override
    public HolderGirdCell onCreateViewHolder(ViewGroup parent) {
        return new HolderGirdCell(getViewById(R.layout.grid_item, parent), true);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new UltimateRecyclerviewViewHolder(parent);
    }


    public class HolderGirdCell extends UltimateRecyclerviewViewHolder {
        TextView textViewSample;
        ImageView imageViewSample;
        View item_view;

        public HolderGirdCell(View itemView, boolean isItem) {
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


}
