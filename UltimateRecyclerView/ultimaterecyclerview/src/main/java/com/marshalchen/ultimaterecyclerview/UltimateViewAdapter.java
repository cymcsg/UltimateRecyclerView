package com.marshalchen.ultimaterecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.Collections;
import java.util.List;

/**
 * An abstract adapter which can be extended for Recyclerview
 */
public abstract class UltimateViewAdapter<AdviewComponent extends ViewGroup, T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {


    protected int ad_position = -1;
    protected UltimateRecyclerView.CustomRelativeWrapper customHeaderView = null;
    protected View customLoadMoreView = null;
    protected AdviewComponent advertise_view = null;

    /**
     * Set the header view of the adapter.
     */
    public void setCustomHeaderView(UltimateRecyclerView.CustomRelativeWrapper customHeaderView) {
        this.customHeaderView = customHeaderView;
    }

    public UltimateRecyclerView.CustomRelativeWrapper getCustomHeaderView() {
        return customHeaderView;
    }

    /**
     * Set advertisement view cell
     *
     * @param _adview                 the ad view in the list adapter
     * @param position_of_this_adview the position of the ad
     */
    public void setAdBanner(AdviewComponent _adview, int position_of_this_adview) {
        advertise_view = _adview;
        ad_position = position_of_this_adview;
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                notifyDataSetChanged();
            }
        });
    }

    /**
     * data binding related position shifting
     *
     * @param currentPos machine loaded position in int
     * @return the final confirmed position for data binding
     */
    protected int positionAdjustments(int currentPos) {
        int shift = 0;
        if (customHeaderView != null) shift--;
        if (ad_position > 0 && currentPos >= ad_position) shift--;
        int finalShift = currentPos + shift;
        return finalShift;
    }

    /**
     * this is the mask to calculate whether the position of the item should be proceeded with data binding
     *
     * @param currentPos machine loaded position in int
     * @param list       the source data list
     * @return the decision to tell whether the data binding should be taken place
     */
    protected boolean maskBindConditions(int currentPos, List<T> list) {
        if (currentPos < getItemCount())
            if (customHeaderView != null) {
                return currentPos <= list.size() && currentPos > 0;
            } else {
                return currentPos < list.size();
            }
        else {
            return false;
        }
    }

    /**
     * create the view holder by data type
     *
     * @param parent   this is the parent list
     * @param viewType the type of the viewholder should be taken place
     * @return ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new UltimateRecyclerviewViewHolder(customLoadMoreView);
        switch (viewType) {
            case VIEW_TYPES.FOOTER:
                if (getAdapterItemCount() == 0)
                    viewHolder.itemView.setVisibility(View.GONE);
                return viewHolder;
            case VIEW_TYPES.HEADER:
                if (customHeaderView != null)
                    return viewHolder;
            case VIEW_TYPES.CHANGED_FOOTER:
                if (getAdapterItemCount() == 0)
                    viewHolder.itemView.setVisibility(View.GONE);
                return viewHolder;

            case VIEW_TYPES.ADVERTISE:

                /**
                 * Disable focus for sub-views of the AdView to avoid problems with
                 * trackpad navigation of the list.
                 */
                for (int i = 0; i < advertise_view.getChildCount(); i++) {
                    advertise_view.getChildAt(i).setFocusable(false);
                }
                advertise_view.setFocusable(false);
                RecyclerView.ViewHolder adview_holder = new UltimateRecyclerviewViewHolder(advertise_view);
                return adview_holder;
            default:
                return onCreateViewHolder(parent);
        }
    }

    /**
     * the view holder will be implemented
     *
     * @param parent the parent view group
     * @return typed object UltimateRecyclerviewViewHolder
     */
    public abstract UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent);

    /**
     * Using a custom LoadMoreView
     *
     * @param customview the custom view
     */
    public void setCustomLoadMoreView(View customview) {
        customLoadMoreView = customview;
    }

    /**
     * Changing the loadmore view
     *
     * @param customview the custom view
     */
    public void swipeCustomLoadMoreView(View customview) {
        customLoadMoreView = customview;
        isLoadMoreChanged = true;
    }

    public View getCustomLoadMoreView() {
        return customLoadMoreView;
    }

    public boolean isLoadMoreChanged = false;

    /**
     * get the item from the view type
     *
     * @param position the position of the current item
     * @return the structured method from the implementation
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && customLoadMoreView != null) {
            if (isLoadMoreChanged) {
                return VIEW_TYPES.CHANGED_FOOTER;
            } else {
                return VIEW_TYPES.FOOTER;
            }
        } else if (position == 0 && customHeaderView != null) {
            return VIEW_TYPES.HEADER;
        } else if (position == ad_position) {
            return VIEW_TYPES.ADVERTISE;
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
        if (advertise_view != null) headerOrFooter++;
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

    /**
     * Swap the item of list
     *
     * @param list the array list
     * @param from from the position start
     * @param to   to the position target to be swapped
     */
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
     * @param list     the list of the data array
     * @param object   anything in type and set to be array list
     * @param position the position to insert the item before
     * @param <T>      anything in type
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
     * @param list     the list of the data array
     * @param position the position of to remove the item
     */
    public void remove(List<?> list, int position) {
        list.remove(customHeaderView != null ? position - 1 : position);
        notifyItemRemoved(position);
    }

    /**
     * Clear the list of the adapter
     *
     * @param list the list of the data array
     */
    public void clear(List<?> list) {
        int size = list.size();
        list.clear();
        notifyItemRangeRemoved(0, size);
    }


    protected class UltimateRecyclerviewViewHolder extends RecyclerView.ViewHolder {
        public UltimateRecyclerviewViewHolder(View itemView) {
            super(itemView);
        }

    }

    protected class VIEW_TYPES {
        public static final int NORMAL = 0;
        public static final int HEADER = 1;
        public static final int FOOTER = 2;
        public static final int CHANGED_FOOTER = 3;
        public static final int ADVERTISE = 4;
    }


}
