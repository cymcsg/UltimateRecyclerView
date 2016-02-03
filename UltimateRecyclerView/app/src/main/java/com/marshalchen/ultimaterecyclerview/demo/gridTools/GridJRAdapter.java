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
public class GridJRAdapter extends UltimateGridLayoutAdapter<JRitem, HolderGirdCell> {

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
    protected void bindNormal(HolderGirdCell b, JRitem jRitem, int position) {
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

}
