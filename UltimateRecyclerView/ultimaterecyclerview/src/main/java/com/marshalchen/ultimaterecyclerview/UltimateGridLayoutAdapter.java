package com.marshalchen.ultimaterecyclerview;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hesk on 27/1/16.
 */
public abstract class UltimateGridLayoutAdapter<DATA, BINDER extends UltimateRecyclerviewViewHolder> extends UltimateViewAdapter {
    final protected List<DATA> list = new ArrayList<>();

    public UltimateGridLayoutAdapter() {

    }

    @Override
    public UltimateRecyclerviewViewHolder getViewHolder(View view) {
        UltimateRecyclerviewViewHolder g = new UltimateRecyclerviewViewHolder(view);
        return g;
    }

    @LayoutRes
    protected abstract int getItemLayoutId();

    protected View getViewById(@LayoutRes final int layoutId, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(), parent, false);
        return view;
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new UltimateRecyclerviewViewHolder(parent);
    }

    @Override
    public int getAdapterItemCount() {
        return list.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (VIEW_TYPES.HEADER == getItemViewType(position)) {
            onBindHeaderViewHolder(holder, position);
        } else if (VIEW_TYPES.NORMAL == getItemViewType(position)) {
            bindNormal((BINDER) holder, list.get(position), position);
        }
    }

    /**
     * normally you dont need to do anything for this implementation.
     *
     * @param holder   the data holder
     * @param position position to render
     */
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Log.d("ascc", position + " : ");
    }

    protected abstract void bindNormal(BINDER b, DATA data, final int position);

    public void insert(List<DATA> list) {
        Iterator<DATA> id = list.iterator();
        final int start = list.size();
        while (id.hasNext()) {
            list.add(list.size(), id.next());
        }
        notifyItemRangeInserted(start, list.size());
    }

    public void insert(DATA item) {
        insert(list, item, 0);
    }

    public void insertFirst(DATA item) {
        insert(list, item, 0);
    }

    public void removeLast() {
        list.remove(list.size() - 1);
        notifyItemRemoved(list.size() - 1);
    }

    public static class GridSpan extends GridLayoutManager.SpanSizeLookup {
        final private int columns;
        final private int intervalRow;
        final private UltimateGridLayoutAdapter mGridAdapter;

        public GridSpan(int col, int intervalRow, UltimateGridLayoutAdapter mGridAdapter) {
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
