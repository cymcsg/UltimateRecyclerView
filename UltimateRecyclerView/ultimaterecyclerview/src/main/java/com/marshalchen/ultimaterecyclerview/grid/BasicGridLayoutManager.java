package com.marshalchen.ultimaterecyclerview.grid;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

/**
 * Created by hesk on 24/8/15.
 */
public class BasicGridLayoutManager extends GridLayoutManager {
    private final UltimateViewAdapter mAdapter;
    protected int headerSpan = 2;


    protected GridLayoutManager.SpanSizeLookup mSpanSizeLookUp = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            if (mAdapter.getItemViewType(position) == UltimateViewAdapter.VIEW_TYPES.FOOTER) {
                return getSpanCount();
            } else if (mAdapter.getItemViewType(position) == UltimateViewAdapter.VIEW_TYPES.HEADER) {
                return getSpanCount();
            } else
                return getNormalSpanCount(position);
        }
    };

    protected int getSpanInterval(int position) {
        int mIntervalHeader = getSpanCount() * 10;
        int h = position % mIntervalHeader == 0 ? getSpanCount() : 1;
        return h;

    }

    protected int getHeaderSpanCount(int n) {
        return headerSpan;
    }

    protected int getNormalSpanCount(int item_position) {
        return 1;
    }

    protected GridLayoutManager.SpanSizeLookup decideSpanSizeCal() {
        return mSpanSizeLookUp;
    }

    public BasicGridLayoutManager(Context context, int spanCount, UltimateViewAdapter mAdapter) {
        super(context, spanCount);
        this.mAdapter = mAdapter;
        setSpanSizeLookup(decideSpanSizeCal());
    }

    public BasicGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout, UltimateViewAdapter mAdapter) {
        super(context, spanCount, orientation, reverseLayout);
        this.mAdapter = mAdapter;
        setSpanSizeLookup(decideSpanSizeCal());
    }
}
