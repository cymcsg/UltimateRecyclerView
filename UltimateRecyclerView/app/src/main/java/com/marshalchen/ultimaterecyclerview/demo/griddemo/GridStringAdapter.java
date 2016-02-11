package com.marshalchen.ultimaterecyclerview.demo.griddemo;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateGridLayoutAdapter;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;

import java.util.List;

/**
 * Created by hesk on 24/8/15.
 */
public class GridStringAdapter extends UltimateGridLayoutAdapter<String, HolderGirdCell> {

    public GridStringAdapter() {
        super();
    }

    public GridStringAdapter(List<String> hand) {
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
    public HolderGirdCell onCreateViewHolder(ViewGroup parent) {
        return new HolderGirdCell(getViewById(R.layout.grid_item, parent), true);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new UltimateRecyclerviewViewHolder(parent);
    }

    @Override
    protected void bindNormal(HolderGirdCell b, String s, int position) {
        b.textViewSample.setText(s);
        b.imageViewSample.setImageResource(SampleDataboxset.getGirlImageRandom());
    }


    //https://gist.github.com/yqritc/ccca77dc42f2364777e1
    public static class GridSpan extends GridLayoutManager.SpanSizeLookup {
        final private int columns;
        final private int intervalRow;
        final private GridStringAdapter mGridAdapter;

        public GridSpan(int col, int intervalRow, GridStringAdapter mGridAdapter) {
            this.columns = col;
            this.intervalRow = intervalRow;
            this.mGridAdapter = mGridAdapter;
        }

        /**
         * Returns the number of span occupied by the item at <code>position</code>.
         *
         * @param position The adapter position of the item
         * @return The number of spans occupied by the item at the provided position
         */
        @Override
        public int getSpanSize(int position) {
            if (position == mGridAdapter.getAdapterItemCount()) {
                return columns;
            } else {
                int mIntervalHeader = columns * intervalRow;
                int h = position % mIntervalHeader == 0 ? columns : 1;
                return h;
            }
        }
    }

}
