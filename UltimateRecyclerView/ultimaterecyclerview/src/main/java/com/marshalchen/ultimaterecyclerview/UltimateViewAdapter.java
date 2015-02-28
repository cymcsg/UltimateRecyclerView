package com.marshalchen.ultimaterecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * An abstract adapter which can be extended for Recyclerview
 */
public abstract class UltimateViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private View customLoadMoreView = null;

    /**
     * Set the header view of the adapter.
     */
    public void setCustomHeaderView(UltimateRecyclerView.CustomRelativeWrapper customHeaderView) {
        this.customHeaderView = customHeaderView;
    }

    public UltimateRecyclerView.CustomRelativeWrapper getCustomHeaderView() {
        return customHeaderView;
    }

    protected UltimateRecyclerView.CustomRelativeWrapper customHeaderView = null;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPES.FOOTER) {
//            if (customLoadMoreView == null) {
//                View v = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.bottom_progressbar, parent, false);
//                return new ProgressBarViewHolder(v);
//            } else {
            return new ProgressBarViewHolder(customLoadMoreView);
            //   }
        } else if (viewType == VIEW_TYPES.HEADER) {
            if (customHeaderView != null)
                return new ProgressBarViewHolder(customHeaderView);
        }


        return onCreateViewHolder(parent);

    }


    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    public void setCustomLoadMoreView(View view) {
        customLoadMoreView = view;
    }

    public View getCustomLoadMoreView() {
        return customLoadMoreView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && customLoadMoreView != null) {
            return VIEW_TYPES.FOOTER;
        } else if (position == 0 && customHeaderView != null) {
            return VIEW_TYPES.HEADER;
        } else
            return VIEW_TYPES.NORMAL;
    }


    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        int headerOrFooter = 0;
        if (customHeaderView != null) headerOrFooter++;
        if (customLoadMoreView != null) headerOrFooter++;
        return getAdapterItemCount() + headerOrFooter;
    }

    /**
     * Returns the number of items in the adapter bound to the parent RecyclerView.
     *
     * @return The number of items in the bound adapter
     */
    public abstract int getAdapterItemCount();


    public void toggleSelection(int pos) {
        notifyItemChanged(pos);
    }


    public void clearSelection(int pos) {
        notifyItemChanged(pos);
    }

    public void setSelected(int pos) {
        notifyItemChanged(pos);
    }


    public void swapPositions(List<?> list, int from, int to) {
        if (customHeaderView != null) {
            from--;
            to--;
        }
        Collections.swap(list, from, to);
    }


    /**
     * Insert a item to the list of the adapter
     *
     * @param list
     * @param object
     * @param position
     * @param <T>
     */
    public <T> void insert(List<T> list, T object, int position) {
        list.add(position, object);
        if (customHeaderView != null) position++;
        notifyItemInserted(position);
        //  notifyItemChanged(position + 1);
    }

    /**
     * Remove a item of  the list of the adapter
     *
     * @param list
     * @param position
     */
    public void remove(List<?> list, int position) {
        list.remove(customHeaderView != null ? position - 1 : position);
        notifyItemRemoved(position);
    }

    /**
     * Clear the list of the adapter
     *
     * @param list
     */
    public void clear(List<?> list) {
        int size = list.size();
        list.clear();
        notifyItemRangeRemoved(0, size);
    }


    class ProgressBarViewHolder extends RecyclerView.ViewHolder {
        public ProgressBarViewHolder(View itemView) {
            super(itemView);
        }

    }

    private class VIEW_TYPES {
        public static final int NORMAL = 0;
        public static final int HEADER = 1;
        public static final int FOOTER = 2;
    }


}
