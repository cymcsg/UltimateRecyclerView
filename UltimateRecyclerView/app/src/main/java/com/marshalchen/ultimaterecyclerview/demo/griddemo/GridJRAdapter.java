package com.marshalchen.ultimaterecyclerview.demo.griddemo;

import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateGridLayoutAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.modules.JRitem;

import java.util.List;

/**
 * Created by hesk on 3/2/16.
 */
public class GridJRAdapter extends UltimateGridLayoutAdapter<JRitem, HolderGirdCell> {

    public GridJRAdapter(List<JRitem> hand) {
        super(hand);
    }

    /**
     * the layout id for the normal data
     *
     * @return the ID
     */
    @Override
    protected int getNormalLayoutResId() {
        return R.layout.grid_item;
    }

    /**
     * this is the Normal View Holder initiation
     *
     * @param view view
     * @return holder
     */
    @Override
    protected HolderGirdCell newViewHolder(View view) {
        return new HolderGirdCell(view, true);
    }

    @Override
    public HolderGirdCell getViewHolder(View view) {
        return new HolderGirdCell(view, false);
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    /**
     * binding normal view holder
     *
     * @param holder   holder class
     * @param data     data
     * @param position position
     */
    @Override
    protected void withBindHolder(HolderGirdCell holder, JRitem data, int position) {

    }

    @Override
    protected void bindNormal(HolderGirdCell b, JRitem jRitem, int position) {
        b.textViewSample.setText(jRitem.train_name);
        b.imageViewSample.setImageResource(jRitem.photo_id);
    }


    @Override
    public UltimateRecyclerviewViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new UltimateRecyclerviewViewHolder(parent);
    }

}
