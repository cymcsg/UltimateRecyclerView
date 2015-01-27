package com.marshalchen.ultimaterecyclerview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by cym on 15-1-20.
 */
public class UltimateRecyclerView extends FrameLayout {
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private OnLoadMoreListener onLoadMoreListener;
    private int lastVisibleItemPosition;
    protected RecyclerView.OnScrollListener mOnScrollListener;
    protected LAYOUT_MANAGER_TYPE layoutManagerType;
    private boolean isLoadingMore = false;
    protected int ITEM_LEFT_TO_LOAD_MORE = 10;
    private int currentScrollState = 0;

    public UltimateRecyclerView(Context context) {
        super(context);
        initViews();
    }

    public UltimateRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public UltimateRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
//        View v = LayoutInflater.from(getContext()).inflate(R.layout.ultimate_recycler_view_layout, this);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ultimate_recycler_view_layout, this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.ultimate_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        // mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        if (mRecyclerView != null) {
            //  mRecyclerView.setClipToPadding(mClipToPadding);
            mOnScrollListener = new RecyclerView.OnScrollListener() {
                private int[] lastPositions;

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    //  int lastVisibleItemPosition = -1;
                    if (layoutManagerType == null) {
                        if (layoutManager instanceof LinearLayoutManager) {
                            layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
                        } else if (layoutManager instanceof GridLayoutManager) {
                            layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
                        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                            layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
                        } else {
                            throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
                        }
                    }

                    switch (layoutManagerType) {
                        case LINEAR:
                            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                            break;
                        case GRID:
                            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                            break;
                        case STAGGERED_GRID:
                            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                            if (lastPositions == null)
                                lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];

                            staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                            lastVisibleItemPosition = findMax(lastPositions);
                            break;
                    }


//                    if (((totalItemCount - lastVisibleItemPosition) <= ITEM_LEFT_TO_LOAD_MORE ||
//                            (totalItemCount - lastVisibleItemPosition) == 0 && totalItemCount > visibleItemCount)
//                            && !isLoadingMore) {
//
//                        isLoadingMore = true;
//                        if (onLoadMoreListener != null) {
//                            isLoadingMore = false;
//                            // mMoreProgress.setVisibility(View.VISIBLE);
//                            onLoadMoreListener.loadMore(mRecyclerView.getAdapter().getItemCount(), ITEM_LEFT_TO_LOAD_MORE, lastVisibleItemPosition);
//                        }
//                    }

                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    currentScrollState = newState;
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    //      Logs.d("count---" + totalItemCount + "   " + lastVisibleItemPosition + "   " + visibleItemCount + "   state   " + currentScrollState + "   " + RecyclerView.SCROLL_STATE_IDLE);
                    if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE &&
                            (lastVisibleItemPosition) >= totalItemCount - 1) && !isLoadingMore) {
                        Logs.d("loading more~~~~");
                        isLoadingMore = true;
                        if (onLoadMoreListener != null) {
                            isLoadingMore = false;
                            // mMoreProgress.setVisibility(View.VISIBLE);
                            onLoadMoreListener.loadMore(mRecyclerView.getAdapter().getItemCount(), ITEM_LEFT_TO_LOAD_MORE, lastVisibleItemPosition);
                        }
                    }

                }
            };
            mRecyclerView.setOnScrollListener(mOnScrollListener);
        }


    }


    /**
     * Set the listener when refresh is triggered and enable the SwipeRefreshLayout
     *
     * @param listener
     */
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(listener);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    /**
     * Set the layout manager to the recycler
     *
     * @param manager
     */
    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

    /**
     * Set the adapter to the recycler
     * Automatically hide the progressbar
     * Set the refresh to false
     * If adapter is empty, then the emptyview is shown
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setRefreshing(false);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                update();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                update();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                update();
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                update();
            }

            @Override
            public void onChanged() {
                super.onChanged();
                update();
            }

            private void update() {
                //   mProgress.setVisibility(View.GONE);
                isLoadingMore = false;
                mSwipeRefreshLayout.setRefreshing(false);
//                if (mRecyclerView.getAdapter().getItemCount() == 0 && mEmptyId != 0) {
//                    mEmpty.setVisibility(View.VISIBLE);
//                } else if (mEmptyId != 0) {
//                    mEmpty.setVisibility(View.GONE);
//                }
            }

        });
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        mRecyclerView.setHasFixedSize(hasFixedSize);
    }

    /**
     * Notify the widget that refresh state has changed. Do not call this when refresh is triggered by a swipe gesture.
     *
     * @param refreshing
     */
    public void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    public void enableSwipeRefresh(boolean isSwipeRefresh) {
        mSwipeRefreshLayout.setEnabled(isSwipeRefresh);
    }

    private void initAttrs() {

    }

    private void setOnMoreListener() {

        if (onLoadMoreListener != null) {
            onLoadMoreListener.loadMore(mRecyclerView.getAdapter().getItemCount(), ITEM_LEFT_TO_LOAD_MORE, lastVisibleItemPosition);

        }
    }

    public interface OnLoadMoreListener {

        public void loadMore(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition);
    }

    public static enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    private int findMax(int[] lastPositions) {
        int max = Integer.MIN_VALUE;
        for (int value : lastPositions) {
            if (value > max)
                max = value;
        }
        return max;
    }
}
