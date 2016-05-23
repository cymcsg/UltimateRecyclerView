package com.marshalchen.ultimaterecyclerview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    protected Handler timer = new Handler();
    protected UltimateRecyclerView.CustomRelativeWrapper customHeaderView = null;
    protected View customLoadMoreView = null;
    protected View customLoadMoreItemView = null;
    private boolean customHeader = false;
    /**
     * this watches how many times does this loading more triggered
     */
    private int loadmoresetingswatch = 0;
    public boolean enabled_custom_load_more_view = false;
    protected int mEmptyViewPolicy;
    protected int mEmptyViewOnInitPolicy;

    /**
     * Lock used to modify the content of list. Any write operation
     * performed on the array should be synchronized on this lock.
     */
    protected final Object mLock = new Object();


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
    public final void setCustomLoadMoreView(@Nullable View customview) {
        customLoadMoreView = customview;
    }


    public final View getCustomLoadMoreView() {
        return customLoadMoreView;
    }

    /**
     * the get function to get load more
     *
     * @return determine this is a get function
     */
    public final boolean enableLoadMore() {
        return enabled_custom_load_more_view;
    }


    private class delayenableloadmore implements Runnable {
        private boolean enabled;

        public delayenableloadmore(final boolean b) {
            enabled = b;
        }

        @Override
        public void run() {
            if (!enabled && loadmoresetingswatch > 0 && customLoadMoreView != null) {
                final int displaySize = getItemCount();
                final int dataSize = getAdapterItemCount();
                if (dataSize > 0 && customLoadMoreItemView != null) {
                    notifyItemRemoved(displaySize - 1);
                }
                detectDispatchLoadMoreDisplay(getAdapterItemCount(), getItemCount());
            }
            enabled_custom_load_more_view = enabled;
            if (enabled && customLoadMoreView == null) {
                enabled_custom_load_more_view = false;
            }
            if (enabled) {
                revealDispatchLoadMoreView();
            }
        }
    }

    public delayenableloadmore cbloadmore;

    /**
     * as the set function to switching load more feature
     *
     * @param b bool
     */
    public final void enableLoadMore(final boolean b) {
        cbloadmore = new delayenableloadmore(b);
    }

    public final void internalExecuteLoadingView() {
        if (cbloadmore != null) {
            timer.post(cbloadmore);
            loadmoresetingswatch++;
            cbloadmore = null;
        }
    }

    /**
     * Called by RecyclerView when it stops observing this Adapter.
     *
     * @param recyclerView The RecyclerView instance which stopped observing this adapter.
     * @see #onAttachedToRecyclerView(RecyclerView)
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        timer.removeCallbacks(cbloadmore);
    }

    public final void setEmptyViewPolicy(final int policy) {
        mEmptyViewPolicy = policy;
    }

    public final void setEmptyViewOnInitPolicy(final int policy) {
        mEmptyViewOnInitPolicy = policy;
    }

    public final int getEmptyViewPolicy() {
        return mEmptyViewPolicy;
    }

    public final int getEmptyViewInitPolicy() {
        return mEmptyViewOnInitPolicy;
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
            VH viewHolder = newFooterHolder(customLoadMoreView);
            /**
             * this is only for the first time rendering of the adapter
             */
            customLoadMoreItemView = viewHolder.itemView;
            if (getAdapterItemCount() == 0) {
                removeDispatchLoadMoreView();
            }
            if (enabled_custom_load_more_view && getAdapterItemCount() > 0) {
                revealDispatchLoadMoreView();
            }
            return viewHolder;
        } else if (viewType == VIEW_TYPES.HEADER) {
            return newHeaderHolder(customHeaderView);
        } else if (viewType == VIEW_TYPES.ADVIEW) {
            return getAdViewHolder(customHeaderView);
        } else if (viewType == VIEW_TYPES.CUSTOMVIEW) {
            return newCustomViewHolder(customHeaderView);
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
    public VH newCustomViewHolder(View view) {
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
     * @param view with no binding view of nothing
     * @return v
     */
    public abstract VH newFooterHolder(View view);


    public abstract VH newHeaderHolder(View view);

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

    /**
     * retrieve the amount of the total items in the urv for display that will be including all data items as well as the decorative items
     *
     * @return the int
     */
    @Override
    public int getItemCount() {
        return getAdapterItemCount() + totalAdditionalItems();
    }

    public int getAdditionalItems() {
        return totalAdditionalItems();
    }

    protected int totalAdditionalItems() {
        int offset = 0;
        if (hasHeaderView()) offset++;
        if (enableLoadMore()) offset++;
        return offset;
    }

    /**
     * Returns the number of items in the adapter bound to the parent RecyclerView.
     *
     * @return The number of data items in the bound adapter
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
        if (enableLoadMore() && to == getItemCount() - 1) return;
        if (hasHeaderView() && to == 0) return;
        if (hasHeaderView() && from == 0) return;
        if (enableLoadMore() && from == getItemCount() - 1) return;
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
        try {
            Iterator<T> id = insert_data.iterator();
            int g = getItemCount();
            //   if (hasHeaderView()) g--;
            if (enableLoadMore()) g--;
            final int start = g;
            synchronized (mLock) {
                while (id.hasNext()) {
                    original_list.add(original_list.size(), id.next());
                }
            }
            if (insert_data.size() == 1) {
                notifyItemInserted(start);
            } else if (insert_data.size() > 1) {
                notifyItemRangeInserted(start, insert_data.size());
            }
            if (enabled_custom_load_more_view) {
                revealDispatchLoadMoreView();
            }
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
        if (hasHeaderView() && position == 0) return;
        if (enableLoadMore() && position == getItemCount() - 1) return;
        if (list.size() > 0) {
            synchronized (mLock) {
                list.remove(hasHeaderView() ? position - 1 : position);
            }
            removeNotifyExternal(position);
            notifyItemRemoved(position);
        }
    }

    protected void removeNotifyExternal(final int pos) {

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
        int data_size_before_remove = list.size();
        final int display_size_before_remove = getItemCount();
        synchronized (mLock) {
            list.clear();
        }
        notifyAfterRemoveAllData(data_size_before_remove, display_size_before_remove);
    }

    /**
     * @param data_size_before_remove    data size
     * @param display_size_before_remove display item size
     * @return TRUE for this is done and no more further processing
     */
    protected boolean detectDispatchLoadMoreDisplay(final int data_size_before_remove, final int display_size_before_remove) {
        if (data_size_before_remove == 0) {
            if (display_size_before_remove == 2) {

                if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_KEEP_HEADER_AND_LOARMORE) {

                } else if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_KEEP_HEADER) {
                    removeDispatchLoadMoreView();
                } else if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_CLEAR_ALL) {
                    removeDispatchLoadMoreView();
                }

            } else if (display_size_before_remove == 1) {

                if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_KEEP_HEADER_AND_LOARMORE) {

                } else if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_KEEP_HEADER) {
                    removeDispatchLoadMoreView();
                } else if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_CLEAR_ALL) {
                    removeDispatchLoadMoreView();
                }

                return true;

            } else if (display_size_before_remove == 0) {
                if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_KEEP_HEADER_AND_LOARMORE) {
                    notifyDataSetChanged();
                } else if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_KEEP_HEADER) {
                    notifyDataSetChanged();
                } else if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_SHOW_LOADMORE_ONLY) {
                    notifyDataSetChanged();
                }
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    protected void revealDispatchLoadMoreView() {
        if (customLoadMoreItemView != null) {
            if (customLoadMoreItemView.getVisibility() != View.VISIBLE) {
                customLoadMoreItemView.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void removeDispatchLoadMoreView() {
        if (customLoadMoreItemView != null) {
            if (customLoadMoreItemView.getVisibility() != View.GONE) {
                customLoadMoreItemView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * works on API v23
     * there is a high  chance to crash this
     *
     * @param data_size_before_remove    original size before removed
     * @param display_size_before_remove the counts for display items
     *                                   <code>
     *                                   http://stackoverflow.com/questions/30220771/recyclerview-inconsistency-detected-invalid-item-position</code>
     */

    protected void notifyAfterRemoveAllData(final int data_size_before_remove, final int display_size_before_remove) {
        try {
            final int notify_start_item = hasHeaderView() ? 1 : 0;
            final int notifiy_n_plus = hasHeaderView() ? data_size_before_remove + 1 : data_size_before_remove;
            if (detectDispatchLoadMoreDisplay(data_size_before_remove, display_size_before_remove))
                return;

            if (data_size_before_remove == 0) return;

            if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_KEEP_HEADER_AND_LOARMORE) {
                if (hasHeaderView())
                    notifyItemRangeChanged(notify_start_item, data_size_before_remove);
                else {
                    notifyDataSetChanged();
                }
            } else if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_KEEP_HEADER) {
                notifyItemRangeRemoved(notify_start_item, data_size_before_remove);
                removeDispatchLoadMoreView();
            } else if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_CLEAR_ALL) {
                notifyItemRangeRemoved(0, notifiy_n_plus);
                removeDispatchLoadMoreView();
            } else if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_SHOW_LOADMORE_ONLY) {
                notifyItemRangeRemoved(0, notifiy_n_plus);
                revealDispatchLoadMoreView();
            } else {
                notifyItemRangeRemoved(0, notifiy_n_plus);
            }
        } catch (Exception e) {
            String o = e.fillInStackTrace().getCause().getMessage().toString();
            Log.d("fillInStackTrace", o + " : ");
        }
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
        public static final int SECTION = 6;
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


/*
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHelper.clear(holder.itemView);

    }
    */


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
