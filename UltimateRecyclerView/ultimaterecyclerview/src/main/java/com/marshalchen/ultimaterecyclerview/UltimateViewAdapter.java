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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            if (customLoadMoreView == null) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.bottom_progressbar, parent, false);
                return new ProgressBarViewHolder(v);
            } else {
                return new ProgressBarViewHolder(customLoadMoreView);
            }
        }


        return onCreateViewHolder(parent);

    }

    public void setCustomLoadMoreView(View view) {
        customLoadMoreView = view;
    }

    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return 1;
        } else
            return super.getItemViewType(position);
    }


    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return getAdapterItemCount() + 1;
    }

    /**
     * Returns the number of items in the adapter bound to the parent RecyclerView.
     *
     * @return The number of items in the bound adapter
     */
    public abstract int getAdapterItemCount();

//    public void insertItem(int position) {
//        insert(position);
//        notifyItemInserted(position);
//    }
//
//    public abstract void insert(int position);


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
        Collections.swap(list, from, to);
    }

    public <T> void insert(List<T> list, T object, int position) {
        list.add(position, object);
        notifyItemInserted(position);
        notifyItemChanged(position + 1);
    }

    public void remove(List<?> list, int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

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


}
