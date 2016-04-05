package com.marshalchen.ultimaterecyclerview.demo.griddemo;

import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateGridLayoutAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.modules.JRitem;
import com.marshalchen.ultimaterecyclerview.demo.rvComponents.itemGridCellBinder;

import java.util.List;

/**
 * Created by hesk on 3/2/16.
 */
public class GridJRAdapter extends UltimateGridLayoutAdapter<JRitem, itemGridCellBinder> {

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
        return itemGridCellBinder.layout;
    }

    /**
     * this is the Normal View Holder initiation
     *
     * @param view view
     * @return holder
     */
    @Override
    protected itemGridCellBinder newViewHolder(View view) {
        return new itemGridCellBinder(view, true);
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
    protected void withBindHolder(itemGridCellBinder holder, JRitem data, int position) {

    }

    @Override
    protected void bindNormal(itemGridCellBinder b, JRitem jRitem, int position) {
        b.textViewSample.setText(jRitem.train_name);
        b.imageViewSample.setImageResource(jRitem.photo_id);
    }


    @Override
    public UltimateRecyclerviewViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new UltimateRecyclerviewViewHolder(parent);
    }

    @Override
    public itemGridCellBinder newFooterHolder(View view) {
        return new itemGridCellBinder(view, false);
    }

    @Override
    public itemGridCellBinder newHeaderHolder(View view) {
        return new itemGridCellBinder(view, false);
    }
}
