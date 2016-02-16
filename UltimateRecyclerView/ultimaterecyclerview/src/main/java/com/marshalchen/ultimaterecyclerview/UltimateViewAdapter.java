package com.marshalchen.ultimaterecyclerview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperAdapter;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * An abstract adapter which can be extended for Recyclerview
 */
public abstract class UltimateViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>, ItemTouchHelperAdapter {

    protected UltimateRecyclerView.CustomRelativeWrapper customHeaderView = null;
    protected View customLoadMoreView = null;
    private boolean customHeader = false;
    private int loadmoresetingswatch = 0;
    public boolean enabled_custom_load_more_view = false;


    /**
     * Set the header view of the adapter.
     *
     * @param customHeaderView na
     */
    public void setCustomHeaderView(UltimateRecyclerView.CustomRelativeWrapper customHeaderView) {
        this.customHeaderView = customHeaderView;
        customHeader = true;
    }

    public UltimateRecyclerView.CustomRelativeWrapper getCustomHeaderView() {
        return customHeaderView;
    }

    public boolean hasHeaderView() {
        return customHeader;
    }

    /**
     * Using a custom LoadMoreView
     *
     * @param customview the inflated view
     */
    public void setCustomLoadMoreView(@Nullable View customview) {
        customLoadMoreView = customview;
    }


    public View getCustomLoadMoreView() {
        return customLoadMoreView;
    }

    /**
     * the get function to get load more
     *
     * @return determine this is a get function
     */
    public boolean enableLoadMore() {
        return enabled_custom_load_more_view;
    }

    /**
     * as the set function to switching load more feature
     *
     * @param b bool
     */
    public void enableLoadMore(boolean b) {
        enabled_custom_load_more_view = b;
        if (loadmoresetingswatch > 0 && !b && customLoadMoreView != null) {
            notifyItemRemoved(getItemCount() - 1);
        }
        loadmoresetingswatch++;
    }


    /**
     * the basic view holder creation
     *
     * @param parent   coming from the bottom api
     * @param viewType coming the bottom api as well
     * @return expected a typed view holder
     */
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPES.FOOTER) {
            VH viewHolder = getViewHolder(customLoadMoreView);
            if (getAdapterItemCount() == 0)
                viewHolder.itemView.setVisibility(View.INVISIBLE);
            return viewHolder;
        } else if (viewType == VIEW_TYPES.HEADER) {
            return getViewHolder(customHeaderView);
        } else if (viewType == VIEW_TYPES.ADVIEW) {
            return getAdViewHolder(customHeaderView);
        } else if (viewType == VIEW_TYPES.CUSTOMVIEW) {
            return getCustomViewHolder(customHeaderView);
        } else if (viewType == VIEW_TYPES.NOVIEW) {
            return getNoViewHolder(customHeaderView);
        }
        return onCreateViewHolder(parent);
    }

    /**
     * requirement: ADVIEW
     *
     * @param view v
     * @return holder for this ADVIEW
     */
    public VH getAdViewHolder(View view) {
        return null;
    }

    /**
     * requirement: CUSTOMVIEW
     *
     * @param view v
     * @return v
     */
    public VH getCustomViewHolder(View view) {
        return null;
    }

    /**
     * requirement: NOVIEW
     *
     * @param view v
     * @return v
     */
    public VH getNoViewHolder(View view) {
        return null;
    }


    /**
     * requirement: FOOTER, HEADER. it does not bind and need to do that in the header binding
     *
     * @param view v
     * @return v
     */
    public abstract VH getViewHolder(View view);

    /**
     * for all NORMAL type holder
     *
     * @param parent view group parent
     * @return vh
     */
    public abstract VH onCreateViewHolder(ViewGroup parent);


    @Override
    public int getItemViewType(int position) {
        //  int k = getAdapterItemCount();
        if (getAdapterItemCount() == 0) {
            if (position == 0) {
                if (enableLoadMore() && hasHeaderView()) {
                    //both
                    return VIEW_TYPES.FOOTER;
                } else if (!enableLoadMore() && hasHeaderView()) {
                    //only header
                    return VIEW_TYPES.HEADER;
                } else if (enableLoadMore() && !hasHeaderView()) {
                    //only load more
                    return VIEW_TYPES.FOOTER;
                } else {
                    return VIEW_TYPES.NOVIEW;
                }
            } else if (position == 1) {
                if (enableLoadMore() && hasHeaderView()) {
                    //both
                    return VIEW_TYPES.FOOTER;
                } else if (!enableLoadMore() && hasHeaderView()) {
                    //only header
                    return VIEW_TYPES.NOVIEW;
                } else if (enableLoadMore() && !hasHeaderView()) {
                    //only load more
                    return VIEW_TYPES.NOVIEW;
                } else {
                    return VIEW_TYPES.NOVIEW;
                }
            } else {
                return VIEW_TYPES.NOVIEW;
            }
        } else if (getAdapterItemCount() > 0) {
            int last_item = getItemCount() - 1;
            if (position == last_item && enableLoadMore()) {
                return VIEW_TYPES.FOOTER;
            } else if (position == 0 && hasHeaderView()) {
                return VIEW_TYPES.HEADER;
            } else if (isOnCustomView(position)) {
                return VIEW_TYPES.ADVIEW;
            } else if (isOnAdView(position)) {
                return VIEW_TYPES.ADVIEW;
            } else {
                return VIEW_TYPES.NORMAL;
            }
        } else {
            return VIEW_TYPES.NORMAL;
        }
    }

    protected boolean isOnCustomView(final int pos) {
        return false;
    }

    protected boolean isOnAdView(final int pos) {
        return false;
    }

    @Override
    public int getItemCount() {
        int offset = 0;
        if (hasHeaderView()) offset++;
        if (enableLoadMore()) offset++;
        // boolean a = enableLoadMore();
        // boolean b = hasHeaderView();
        return getAdapterItemCount() + offset;
    }

    /**
     * Returns the number of items in the adapter bound to the parent RecyclerView.
     *
     * @return The number of items in the bound adapter
     */
    public abstract int getAdapterItemCount();


    public final void toggleSelection(int pos) {
        notifyItemChanged(pos);
    }


    public final void clearSelection(int pos) {
        notifyItemChanged(pos);
    }

    public final void setSelected(int pos) {
        notifyItemChanged(pos);
    }

    /**
     * Swap the item of list
     *
     * @param list data list
     * @param from position from
     * @param to   position to
     */
    public void swapPositions(List<?> list, int from, int to) {
        if (hasHeaderView()) {
            from--;
            to--;
        }
        Collections.swap(list, from, to);
    }


    /**
     * Insert a item to the list of the adapter
     *
     * @param list     data list
     * @param object   object T
     * @param position position
     * @param <T>      in T
     */
    public final <T> void insertInternal(List<T> list, T object, final int position) {
        list.add(position, object);
        int g = position;
        if (hasHeaderView()) g++;
        notifyItemInserted(g);
    }


    public final <T> void insertFirstInternal(List<T> list, T item) {
        insertInternal(list, item, 0);
    }

    public final <T> void insertLastInternal(List<T> list, T item) {
        insertInternal(list, item, getAdapterItemCount());
    }


    /**
     * insert the new item list after the whole list
     *
     * @param insert_data   new list
     * @param original_list original copy
     * @param <T>           the type
     */
    public final <T> void insertInternal(List<T> insert_data, List<T> original_list) {
        Iterator<T> id = insert_data.iterator();
        int g = getItemCount();
        if (enableLoadMore() && hasHeaderView()) g--;
        final int start = g;
        while (id.hasNext()) {
            original_list.add(original_list.size(), id.next());
        }
        int notify_count = insert_data.size();
        //  if (enableLoadMore()) notify_count++;
        try {
            notifyItemRangeInserted(start, notify_count);
        } catch (Exception e) {
            String o = e.fillInStackTrace().getCause().getMessage().toString();
            Log.d("fillInStackTrace", o + " : ");
        }
    }

    /**
     * Remove a item of  the list of the adapter
     *
     * @param list     na
     * @param position na
     * @param <T>      na
     */
    public final <T> void removeInternal(List<T> list, int position) {
        if (list.size() > 0) {
            list.remove(hasHeaderView() ? position - 1 : position);
            notifyItemRemoved(position);
        }
    }

    public final <T> void removeFirstInternal(List<T> list) {
        removeInternal(list, 0);
    }

    public final <T> void removeLastInternal(List<T> list) {
        removeInternal(list, getAdapterItemCount() - 1);
    }

    /**
     * Clear the list of the adapter
     *
     * @param list data list
     * @param <T>  na
     */
    public final <T> void clearInternal(List<T> list) {
        int size = list.size();
        list.clear();
        notifyItemRangeRemoved(0, size);
    }

    /**
     * remove all items
     *
     * @param list na
     * @param <T>  na
     */
    public final <T> void removeAllInternal(List<T> list) {
        clearInternal(list);
    }

    @Override
    public long getHeaderId(int position) {
        if (hasHeaderView() && position == 0) return -1;
        if (enableLoadMore() && position >= getItemCount() - 1) return -1;
        if (getAdapterItemCount() > 0) {
            return generateHeaderId(hasHeaderView() ? position - 1 : position);
        } else return -1;
    }

    public abstract long generateHeaderId(int position);


    public static class VIEW_TYPES {
        public static final int NORMAL = 0;
        public static final int HEADER = 1;
        //this is the default loading footer
        public static final int FOOTER = 2;
        //this is the customized footer
        public static final int NOVIEW = 3;
        //this is specialized Ad view
        public static final int ADVIEW = 4;
        public static final int CUSTOMVIEW = 5;
    }

    protected enum AdapterAnimationType {
        AlphaIn,
        SlideInBottom,
        ScaleIn,
        SlideInLeft,
        SlideInRight,
    }

    /**
     * Animations when loading the adapter
     *
     * @param view the view
     * @param type the type of the animation
     * @return the animator in array
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected Animator[] getAdapterAnimations(View view, AdapterAnimationType type) {
        if (type == AdapterAnimationType.ScaleIn) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", .5f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", .5f, 1f);
            return new ObjectAnimator[]{scaleX, scaleY};
        } else if (type == AdapterAnimationType.AlphaIn) {
            return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", .5f, 1f)};
        } else if (type == AdapterAnimationType.SlideInBottom) {
            return new Animator[]{
                    ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)
            };
        } else if (type == AdapterAnimationType.SlideInLeft) {
            return new Animator[]{
                    ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0)
            };
        } else if (type == AdapterAnimationType.SlideInRight) {
            return new Animator[]{
                    ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0)
            };
        }
        return null;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        if (hasHeaderView() && getItemViewType(position) == VIEW_TYPES.HEADER) return;
        if (enableLoadMore() && getItemViewType(position) == VIEW_TYPES.FOOTER) return;
        notifyDataSetChanged();
    }


    protected OnStartDragListener mDragStartListener = null;

    /**
     * Listener for manual initiation of a drag.
     */
    public interface OnStartDragListener {

        /**
         * Called when a view is requesting a start of a drag.
         *
         * @param viewHolder The holder of the view to drag.
         */
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }
}
