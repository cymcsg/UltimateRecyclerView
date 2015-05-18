package com.marshalchen.ultimaterecyclerview.multiViewTypes;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Adapter class for managing a set of data binders
 *
 * Created by yqritc on 2015/03/01.
 */
abstract public class DataBindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getDataBinder(viewType).newViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int binderPosition = getBinderPosition(position);
        getDataBinder(viewHolder.getItemViewType()).bindViewHolder(viewHolder, binderPosition);
    }

    @Override
    public abstract int getItemCount();

    @Override
    public abstract int getItemViewType(int position);

    public abstract <T extends DataBinder> T getDataBinder(int viewType);

    public abstract int getPosition(DataBinder binder, int binderPosition);

    public abstract int getBinderPosition(int position);

    public void notifyBinderItemChanged(DataBinder binder, int binderPosition) {
        notifyItemChanged(getPosition(binder, binderPosition));
    }

    public abstract void notifyBinderItemRangeChanged(DataBinder binder, int positionStart,
            int itemCount);

    public void notifyBinderItemInserted(DataBinder binder, int binderPosition) {
        notifyItemInserted(getPosition(binder, binderPosition));
    }

    public void notifyBinderItemMoved(DataBinder binder, int fromPosition, int toPosition) {
        notifyItemMoved(getPosition(binder, fromPosition), getPosition(binder, toPosition));
    }

    public abstract void notifyBinderItemRangeInserted(DataBinder binder, int positionStart,
            int itemCount);

    public void notifyBinderItemRemoved(DataBinder binder, int binderPosition) {
        notifyItemRemoved(getPosition(binder, binderPosition));
    }

    public abstract void notifyBinderItemRangeRemoved(DataBinder binder, int positionStart,
            int itemCount);
}
