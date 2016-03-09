package com.marshalchen.ultimaterecyclerview;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;

import java.util.List;

/**
 * Created by hesk on 27/1/16.
 */
public abstract class UltimateGridLayoutAdapter<DATA, BINDER extends UltimateRecyclerviewViewHolder> extends easyRegularAdapter<DATA, BINDER> {
    private boolean mValid = true;
    private int span_columns;

    public UltimateGridLayoutAdapter(List<DATA> items) {
        super(items);
        span_columns = 1;
    }

    public void setSpanColumns(int columns) {
        span_columns = columns;
    }


    private int normalDataConv(final int rpos) {
        int orgin = rpos;
        int size = getAdapterItemCount();
        if (hasHeaderView()) orgin -= 1;
        int out = orgin >= size - 1 ? size - 1 : orgin;
        return out;
    }


    @Override
    public UltimateRecyclerviewViewHolder getViewHolder(View view) {
        UltimateRecyclerviewViewHolder g = new UltimateRecyclerviewViewHolder(view);
        return g;
    }

    protected View getViewById(@LayoutRes final int layoutId, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return view;
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new UltimateRecyclerviewViewHolder(parent);
    }

    @Override
    public int getAdapterItemCount() {
        return mValid ? super.getAdapterItemCount() : 0;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public long generateHeaderId(int position) {
        return View.generateViewId();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int current_type = getItemViewType(position);
        if (VIEW_TYPES.HEADER == current_type) {
            onBindHeaderViewHolder(holder, position);
        } else if (VIEW_TYPES.NORMAL == current_type) {
            bindNormal((BINDER) holder, getItem(getItemDataPosFromInternalPos(position)), position);
        } else if (VIEW_TYPES.FOOTER == current_type) {
            onFooterCustomerization(holder, position);
        }
    }

    protected void onFooterCustomerization(RecyclerView.ViewHolder view, int position) {

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
