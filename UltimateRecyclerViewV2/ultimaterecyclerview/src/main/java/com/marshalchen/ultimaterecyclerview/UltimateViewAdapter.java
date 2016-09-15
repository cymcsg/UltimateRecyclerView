package com.marshalchen.ultimaterecyclerview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.common.BaseLoadMoreFooterView;
import com.marshalchen.ultimaterecyclerview.common.CustomRelativeWrapper;
import com.marshalchen.ultimaterecyclerview.common.UnRecyclableViewHolder;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperAdapter;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * An abstract adapter which can be extended for Recyclerview
 */
public abstract class UltimateViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>, ItemTouchHelperAdapter {
    protected Handler timer = new Handler();

    public static final int NO_POSITION = -1;
    public static final long NO_ID = -1;
    public static final int INVALID_TYPE = -1;
    private static final int VIEW_TYPE_MAX_COUNT = 1000;//header or footer max view type :1000
    private static final int HEADER_VIEW_TYPE_OFFSET = 0;
    private static final int FOOTER_VIEW_TYPE_OFFSET = HEADER_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT;
    private static final int FOOTER_LOAD_MORE_VIEW_TYPE = FOOTER_VIEW_TYPE_OFFSET + 1;//the bottom view
    private static final int CONTENT_VIEW_TYPE_OFFSET = FOOTER_LOAD_MORE_VIEW_TYPE + VIEW_TYPE_MAX_COUNT;


    protected CustomRelativeWrapper customHeaderView = null;
    //  protected View customLoadMoreView = null;
    //  protected View customLoadMoreItemView = null;

    //header view and footer view can't be recycled
    private List<RecyclerView.ViewHolder> mHeaderViews = new ArrayList<>();
    //store the ViewHolder for remove header view
    private Map<View, RecyclerView.ViewHolder> mHeaderViewHolderMap = new HashMap<>();
    private int mHeaderSize;

    //header view and footer view can't be recycled
    private List<RecyclerView.ViewHolder> mFooterViews = new ArrayList<>();
    //store the ViewHolder for remove footer view
    private Map<View, RecyclerView.ViewHolder> mFooterViewHolderMap = new HashMap<>();
    private int mFooterSize;

    //load more footer view
    private BaseLoadMoreFooterView mLoadMoreFooterView;

    private boolean customHeader = false;
    /**
     * this watches how many times does this loading more triggered
     */
    //  private int loadmoresetingswatch = 0;
    public boolean enabled_custom_load_more_view = false;
    protected int mEmptyViewPolicy;
    protected int mEmptyViewOnInitPolicy;

    /**
     * Lock used to modify the content of list. Any write operation
     * performed on the array should be synchronized on this lock.
     */
    protected final Object mLock = new Object();

    public boolean hasHeaderView() {
        return customHeader;
    }


  /*  private class delayenableloadmore implements Runnable {
        private boolean enabled;

        public delayenableloadmore(final boolean b) {
            enabled = b;
        }

        @Override
        public void run() {
            if (!enabled && customLoadMoreView != null) {
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
*/

    /**
     * as the set function to switching load more feature
     *
     * @param b bool
     */
    public final void enableLoadMore(final boolean b) {
        // cbloadmore = new delayenableloadmore(b);
    }

    public final void executeInternalFootViewActionQueue() {
        /*if (cbloadmore != null) {
            timer.post(cbloadmore);
            loadmoresetingswatch++;
            cbloadmore = null;
        }*/
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
        // timer.removeCallbacks(cbloadmore);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
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

    protected VH onCreateExtViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * the basic view holder creation
     *
     * @param parent   coming from the bottom api
     * @param viewType coming the bottom api as well
     * @return expected a typed view holder
     */
    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //allow extensions
        VH presume = onCreateExtViewHolder(parent, viewType);
        if (presume != null) {
            return presume;
        }


        if (viewType == VIEW_TYPES.FOOTER) {
            /*VH viewHolder = newFooterHolder(customLoadMoreView);
            customLoadMoreItemView = viewHolder.itemView;
            if (getAdapterItemCount() == 0) {
                removeDispatchLoadMoreView();
            }
            if (enabled_custom_load_more_view && getAdapterItemCount() > 0) {
                revealDispatchLoadMoreView();
            }*/
            //  return mFooterViews.get(viewType - FOOTER_VIEW_TYPE_OFFSET);
        } else if (viewType == VIEW_TYPES.FOOTER_LOADMORE) {
            return new UnRecyclableViewHolder(mLoadMoreFooterView);
        } else if (viewType == VIEW_TYPES.HEADER) {
            // return newHeaderHolder(customHeaderView);
            return mHeaderViews.get(0);
        } else if (viewType == VIEW_TYPES.ADVIEW) {
            return getAdViewHolder(customHeaderView);
        } else if (viewType == VIEW_TYPES.CUSTOMVIEW) {
            return newCustomViewHolder(customHeaderView);
        } else if (viewType == VIEW_TYPES.NOVIEW) {
            return getNoViewHolder(customHeaderView);
        }

        return null;
    }

    public void addHeaderView(View view, boolean notifyDataChange) {
        RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(view) {
        };
        viewHolder.setIsRecyclable(false);
        mHeaderViewHolderMap.put(view, viewHolder);
        mHeaderViews.add(viewHolder);
        mHeaderSize = mHeaderViews.size();
        if (notifyDataChange) {
            notifyItemInserted(mHeaderSize - 1);
        }
    }

    public void addHeaderView(View view) {
        addHeaderView(view, true);
    }

    public void removeHeaderView(View view, boolean notifyDataChange) {
        RecyclerView.ViewHolder viewHolder = mHeaderViewHolderMap.get(view);
        if (mHeaderViews.remove(viewHolder)) {
            mHeaderSize = mHeaderViews.size();
            mHeaderViewHolderMap.remove(view);
            if (notifyDataChange) {
                notifyDataSetChanged();
            }
        }
    }

    public void removeHeaderView(View view) {
        removeHeaderView(view, true);
    }

    public void addFooterView(View view, boolean notifyDataChange) {
        RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(view) {
        };
        viewHolder.setIsRecyclable(false);
        mFooterViewHolderMap.put(view, viewHolder);
        mFooterViews.add(viewHolder);
        mFooterSize = mFooterViews.size();
        if (notifyDataChange) {
            notifyItemInserted(mFooterSize - 1);
        }
    }

    public void addFooterView(View view) {
        addFooterView(view, true);
    }

    public void removeFooterView(View view, boolean notifyDataChange) {
        RecyclerView.ViewHolder viewHolder = mFooterViewHolderMap.get(view);
        if (viewHolder != null && mFooterViews.remove(viewHolder)) {
            mFooterSize = mFooterViews.size();
            mFooterViewHolderMap.remove(view);
            if (notifyDataChange) {
                notifyDataSetChanged();
            }
        }
    }

    public void removeFooterView(View view) {
        removeFooterView(view, true);
    }

    public BaseLoadMoreFooterView getLoadMoreFooterView() {
        return mLoadMoreFooterView;
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
     * for all NORMAL type holder
     *
     * @param parent view group parent
     * @return vh
     */
    public abstract VH onCreateViewHolder(ViewGroup parent);

    //Content itemViewViewType
    public int getContentViewType(int dataListIndex) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderSize > 0 && position < mHeaderSize) {
            return HEADER_VIEW_TYPE_OFFSET + position;//header view has different viewType
        } else if (position >= mHeaderSize && position < getAdapterItemCount() + mHeaderSize) {
            //content view type
            int contentViewType = getContentViewType(position - mHeaderSize);
            if (contentViewType >= 0) {
                return CONTENT_VIEW_TYPE_OFFSET + contentViewType;
            } else {
                throw new IllegalArgumentException("contentViewType must >= 0");
            }
        } else if (mFooterSize > 0 && position >= (getAdapterItemCount() + mHeaderSize)
                && position < (getAdapterItemCount() + mHeaderSize + mFooterSize)) {
            //footer view type
            return FOOTER_VIEW_TYPE_OFFSET + (position - mHeaderSize - getAdapterItemCount());//footer view has different viewType
        } else if (isShowingLoadMoreView() && position == (getAdapterItemCount() + mHeaderSize + mFooterSize)) {
            //load more  view type
            return FOOTER_LOAD_MORE_VIEW_TYPE;
        }
        return INVALID_TYPE;


        //  int k = getAdapterItemCount();
      /*  if (getAdapterItemCount() == 0) {
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
        }*/
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
        if (isShowingLoadMoreView()) offset++;
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
        if (isShowingLoadMoreView() && to == getItemCount() - 1) return;
        if (hasHeaderView() && to == 0) return;
        if (hasHeaderView() && from == 0) return;
        if (isShowingLoadMoreView() && from == getItemCount() - 1) return;
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
            if (isShowingLoadMoreView()) g--;
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
        if (isShowingLoadMoreView() && position == getItemCount() - 1) return;
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
       /* if (customLoadMoreItemView != null) {
            if (customLoadMoreItemView.getVisibility() != View.VISIBLE) {
                customLoadMoreItemView.setVisibility(View.VISIBLE);
            }
        }*/
    }

    protected void removeDispatchLoadMoreView() {
       /* if (customLoadMoreItemView != null) {
            if (customLoadMoreItemView.getVisibility() != View.GONE) {
                customLoadMoreItemView.setVisibility(View.GONE);
            }
        }*/
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
        if (isShowingLoadMoreView() && position >= getItemCount() - 1) return -1;
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
        public static final int FOOTER_LOADMORE = 7;
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
        if (isShowingLoadMoreView() && getItemViewType(position) == VIEW_TYPES.FOOTER) return;
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

    public void setLoadMoreFooterView(BaseLoadMoreFooterView mLoadMoreFooterView) {
        this.mLoadMoreFooterView = mLoadMoreFooterView;
    }

    public void showLoadMoreView() {
        if (mLoadMoreFooterView == null) {
            throw new IllegalArgumentException("mLoadMoreFooterView is null, you can call setLoadMoreFooterView()");
        }
        mLoadMoreFooterView.showLoading();
        if (enabled_custom_load_more_view) {
            //notifyItemChanged(getItemCount());
        } else {
            this.enabled_custom_load_more_view = true;
            notifyItemInserted(getItemCount());
        }
    }

    public void showNoMoreDataView() {
        if (mLoadMoreFooterView == null) {
            throw new IllegalArgumentException("mLoadMoreFooterView is null, you can call setLoadMoreFooterView()");
        }
        mLoadMoreFooterView.showNoMoreData();
        if (enabled_custom_load_more_view) {
            //notifyItemChanged(getItemCount());
        } else {
            this.enabled_custom_load_more_view = true;
            notifyItemInserted(getItemCount());
        }
    }

    public void hideFooterView() {
        if (mLoadMoreFooterView == null) {
            throw new IllegalArgumentException("mLoadMoreFooterView is null, must call setLoadMoreFooterView()");
        }
        if (enabled_custom_load_more_view) {
            this.enabled_custom_load_more_view = false;
            //for java.lang.IllegalStateException: Added View has RecyclerView as parent but view is not a real child.
            //https://github.com/captain-miao/RecyclerViewUtils/issues/3
            notifyDataSetChanged();
        }
    }

    public boolean isShowingLoadMoreView() {
        return enabled_custom_load_more_view;
    }
}
