/*
 * Copyright(c) 2015 Marshal Chen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.marshalchen.ultimaterecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.marshalchen.ultimaterecyclerview.Utils.SavedStateScrolling;
import com.marshalchen.ultimaterecyclerview.ui.DividerItemDecoration;
import com.marshalchen.ultimaterecyclerview.ui.VerticalSwipeRefreshLayout;
import com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton;
import com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionsMenu;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * UltimateRecyclerView is a recyclerview which contains many features like  swipe to dismiss,animations,drag drop etc.
 */
public class UltimateRecyclerView extends FrameLayout implements Scrollable {
    public RecyclerView mRecyclerView;

    protected FloatingActionButton defaultFloatingActionButton;
    private OnLoadMoreListener onLoadMoreListener;
    private int lastVisibleItemPosition;
    protected RecyclerView.OnScrollListener mOnScrollListener;
    protected LAYOUT_MANAGER_TYPE layoutManagerType;
    private boolean isLoadingMore = false;
    protected int mPadding;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected boolean mClipToPadding;
    private UltimateViewAdapter mAdapter;


    // Fields that should be saved onSaveInstanceState
    private int mPrevFirstVisiblePosition;
    private int mPrevFirstVisibleChildHeight = -1;
    private int mPrevScrolledChildrenHeight;
    private int mPrevScrollY;
    private int mScrollY;
    private SparseIntArray mChildrenHeights = new SparseIntArray();
    ;

    // Fields that don't need to be saved onSaveInstanceState
    private ObservableScrollState mObservableScrollState;
    private ObservableScrollViewCallbacks mCallbacks;
    //private ScrollState mScrollState;
    private boolean mFirstScroll;
    private boolean mDragging;
    private boolean mIntercepted;
    private MotionEvent mPrevMoveEvent;
    private ViewGroup mTouchInterceptionViewGroup;


    protected ViewStub mEmpty;
    protected View mEmptyView;
    protected int mEmptyId;

    protected ViewStub mFloatingButtonViewStub;
    protected View mFloatingButtonView;
    protected int mFloatingButtonId;
    protected int[] defaultSwipeToDismissColors = null;
    public int showLoadMoreItemNum = 3;

    public VerticalSwipeRefreshLayout mSwipeRefreshLayout;

    // added by Sevan Joe to support scrollbars
    private static final int SCROLLBARS_NONE = 0;
    private static final int SCROLLBARS_VERTICAL = 1;
    private static final int SCROLLBARS_HORIZONTAL = 2;
    private int mScrollbarsStyle;


    private int mVisibleItemCount = 0;
    private int mTotalItemCount = 0;
    private int previousTotal = 0;
    private int mFirstVisibleItem;

    public UltimateRecyclerView(Context context) {
        super(context);
        initViews();
    }

    public UltimateRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initViews();
    }

    public UltimateRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initViews();
    }

    public void setRecylerViewBackgroundColor(int color) {
        mRecyclerView.setBackgroundColor(color);
    }

    protected void initViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ultimate_recycler_view_layout, this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.ultimate_list);
        mSwipeRefreshLayout = (VerticalSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        setScrollbars();
        mSwipeRefreshLayout.setEnabled(false);

        if (mRecyclerView != null) {

            mRecyclerView.setClipToPadding(mClipToPadding);
            if (mPadding != -1.1f) {
                mRecyclerView.setPadding(mPadding, mPadding, mPadding, mPadding);
            } else {
                mRecyclerView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
            }
        }

        defaultFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.defaultFloatingActionButton);
        setDefaultScrollListener();

        mEmpty = (ViewStub) view.findViewById(R.id.emptyview);
        mFloatingButtonViewStub = (ViewStub) view.findViewById(R.id.floatingActionViewStub);

        mEmpty.setLayoutResource(mEmptyId);

        mFloatingButtonViewStub.setLayoutResource(mFloatingButtonId);

        if (mEmptyId != 0)
            mEmptyView = mEmpty.inflate();
        mEmpty.setVisibility(View.GONE);


    }

    public void setEmptyView(int emptyResourceId) {
        mEmptyId = emptyResourceId;

        mEmpty.setLayoutResource(mEmptyId);
        if (mEmptyId != 0)
            mEmptyView = mEmpty.inflate();
        mEmpty.setVisibility(View.GONE);
    }

    public void showEmptyView() {
        if (mEmptyId != 0)
            mEmpty.setVisibility(View.VISIBLE);
    }

    public void hideEmptyView() {
        if (mEmptyId != 0)
            mEmpty.setVisibility(View.GONE);
    }

    public void showFloatingButtonView() {
        if (mFloatingButtonId != 0) {
            mFloatingButtonView = mFloatingButtonViewStub.inflate();
            mFloatingButtonView.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Add ScrollBar of Recyclerview
     */
    protected void setScrollbars() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (mScrollbarsStyle) {
            case SCROLLBARS_VERTICAL:
                mSwipeRefreshLayout.removeView(mRecyclerView);
                View verticalView = inflater.inflate(R.layout.vertical_recycler_view, mSwipeRefreshLayout, true);
                mRecyclerView = (RecyclerView) verticalView.findViewById(R.id.ultimate_list);
                break;
            case SCROLLBARS_HORIZONTAL:
                mSwipeRefreshLayout.removeView(mRecyclerView);
                View horizontalView = inflater.inflate(R.layout.horizontal_recycler_view, mSwipeRefreshLayout, true);
                mRecyclerView = (RecyclerView) horizontalView.findViewById(R.id.ultimate_list);
                break;
            default:
                break;
        }
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.UltimateRecyclerview);

        try {
            mPadding = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPadding, -1.1f);
            mPaddingTop = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingTop, 0.0f);
            mPaddingBottom = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingBottom, 0.0f);
            mPaddingLeft = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingLeft, 0.0f);
            mPaddingRight = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingRight, 0.0f);
            mClipToPadding = typedArray.getBoolean(R.styleable.UltimateRecyclerview_recyclerviewClipToPadding, false);
            mEmptyId = typedArray.getResourceId(R.styleable.UltimateRecyclerview_recyclerviewEmptyView, 0);
            mFloatingButtonId = typedArray.getResourceId(R.styleable.UltimateRecyclerview_recyclerviewFloatingActionView, 0);
            mScrollbarsStyle = typedArray.getInt(R.styleable.UltimateRecyclerview_recyclerviewScrollbars, SCROLLBARS_NONE);
            int colorList = typedArray.getResourceId(R.styleable.UltimateRecyclerview_recyclerviewDefaultSwipeColor, 0);
            if (colorList != 0) {
                defaultSwipeToDismissColors = getResources().getIntArray(colorList);
            }
        } finally {
            typedArray.recycle();
        }
    }


    protected void setDefaultScrollListener() {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mOnScrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mHeader != null) {
                    mTotalYScrolled += dy;
                    translateHeader(mTotalYScrolled);
                }
                enableShoworHideToolbarAndFloatingButton(recyclerView);
            }
        };

        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    private void setObserableScrollListener() {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                enableShoworHideToolbarAndFloatingButton(recyclerView);
            }
        };
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    /**
     * Enable loading more of the recyclerview
     */
    public void enableLoadmore() {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mOnScrollListener = new RecyclerView.OnScrollListener() {
            private int[] lastPositions;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mHeader != null) {
                    mTotalYScrolled += dy;
                    translateHeader(mTotalYScrolled);
                }
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                mVisibleItemCount = layoutManager.getChildCount();
                mTotalItemCount = layoutManager.getItemCount();

                if (layoutManagerType == null) {
                    if (layoutManager instanceof GridLayoutManager) {
                        layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
                    } else {
                        throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
                    }
                }

                switch (layoutManagerType) {
                    case LINEAR:
                    case GRID:
                        lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        mFirstVisibleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                        break;
                    case STAGGERED_GRID:
                        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                        if (lastPositions == null)
                            lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];

                        staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                        lastVisibleItemPosition = findMax(lastPositions);

                        staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions);
                        mFirstVisibleItem = findMin(lastPositions);
                        break;
                }

                if (isLoadingMore) {
                    if (mTotalItemCount > previousTotal) {
                        isLoadingMore = false;
                        previousTotal = mTotalItemCount;
                    }
                }

                if (!isLoadingMore && (mTotalItemCount - mVisibleItemCount)
                        <= mFirstVisibleItem) {
                    onLoadMoreListener.loadMore(mRecyclerView.getAdapter().getItemCount(), lastVisibleItemPosition);
                    isLoadingMore = true;
                    previousTotal = mTotalItemCount;
                }

                enableShoworHideToolbarAndFloatingButton(recyclerView);

            }

        };
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        if (mAdapter != null && mAdapter.getCustomLoadMoreView() == null)
            mAdapter.setCustomLoadMoreView(LayoutInflater.from(getContext())
                    .inflate(R.layout.bottom_progressbar, null));
    }

    /**
     * Remove loading more scroll listener
     */
    public void disableLoadmore() {
        setDefaultScrollListener();
        mAdapter.swipeCustomLoadMoreView(LayoutInflater.from(getContext())
                .inflate(R.layout.empty_progressbar, null));
    }


    protected void enableShoworHideToolbarAndFloatingButton(RecyclerView recyclerView) {
        if (mCallbacks != null) {
            if (getChildCount() > 0) {
                int firstVisiblePosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));
                int lastVisiblePosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(recyclerView.getChildCount() - 1));
                for (int i = firstVisiblePosition, j = 0; i <= lastVisiblePosition; i++, j++) {
                    int childHeight = 0;
                    View child = recyclerView.getChildAt(j);
                    if (mChildrenHeights.indexOfKey(i) < 0 || (child != null && child.getHeight() != mChildrenHeights.get(i))) {
                        childHeight = child.getHeight();
                    }
                    mChildrenHeights.put(i, childHeight);
                }

                View firstVisibleChild = recyclerView.getChildAt(0);
                if (firstVisibleChild != null) {
                    if (mPrevFirstVisiblePosition < firstVisiblePosition) {
                        // scroll down
                        int skippedChildrenHeight = 0;
                        if (firstVisiblePosition - mPrevFirstVisiblePosition != 1) {
                            for (int i = firstVisiblePosition - 1; i > mPrevFirstVisiblePosition; i--) {
                                if (0 < mChildrenHeights.indexOfKey(i)) {
                                    skippedChildrenHeight += mChildrenHeights.get(i);
                                } else {
                                    // Approximate each item's height to the first visible child.
                                    // It may be incorrect, but without this, scrollY will be broken
                                    // when scrolling from the bottom.
                                    skippedChildrenHeight += firstVisibleChild.getHeight();
                                }
                            }
                        }
                        mPrevScrolledChildrenHeight += mPrevFirstVisibleChildHeight + skippedChildrenHeight;
                        mPrevFirstVisibleChildHeight = firstVisibleChild.getHeight();
                    } else if (firstVisiblePosition < mPrevFirstVisiblePosition) {
                        // scroll up
                        int skippedChildrenHeight = 0;
                        if (mPrevFirstVisiblePosition - firstVisiblePosition != 1) {
                            for (int i = mPrevFirstVisiblePosition - 1; i > firstVisiblePosition; i--) {
                                if (0 < mChildrenHeights.indexOfKey(i)) {
                                    skippedChildrenHeight += mChildrenHeights.get(i);
                                } else {
                                    // Approximate each item's height to the first visible child.
                                    // It may be incorrect, but without this, scrollY will be broken
                                    // when scrolling from the bottom.
                                    skippedChildrenHeight += firstVisibleChild.getHeight();
                                }
                            }
                        }
                        mPrevScrolledChildrenHeight -= firstVisibleChild.getHeight() + skippedChildrenHeight;
                        mPrevFirstVisibleChildHeight = firstVisibleChild.getHeight();
                    } else if (firstVisiblePosition == 0) {
                        mPrevFirstVisibleChildHeight = firstVisibleChild.getHeight();
                        mPrevScrolledChildrenHeight = 0;
                    }
                    if (mPrevFirstVisibleChildHeight < 0) {
                        mPrevFirstVisibleChildHeight = 0;
                    }
                    mScrollY = mPrevScrolledChildrenHeight - firstVisibleChild.getTop();
                    mPrevFirstVisiblePosition = firstVisiblePosition;

                    mCallbacks.onScrollChanged(mScrollY, mFirstScroll, mDragging);
                    if (mFirstScroll) {
                        mFirstScroll = false;
                    }

                    if (mPrevScrollY < mScrollY) {
                        //down
                        mObservableScrollState = ObservableScrollState.UP;
                    } else if (mScrollY < mPrevScrollY) {
                        //up
                        mObservableScrollState = ObservableScrollState.DOWN;
                    } else {
                        mObservableScrollState = ObservableScrollState.STOP;
                    }
                    mPrevScrollY = mScrollY;
                }
            }
        }
    }

    /**
     * Set a listener that will be notified of any changes in scroll state or position.
     *
     * @param customOnScrollListener to set or null to clear
     * @deprecated Use {@link #addOnScrollListener(RecyclerView.OnScrollListener)} and
     * {@link #removeOnScrollListener(RecyclerView.OnScrollListener)}
     */
    public void setOnScrollListener(RecyclerView.OnScrollListener customOnScrollListener) {
        mRecyclerView.setOnScrollListener(customOnScrollListener);
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener customOnScrollListener) {
        mRecyclerView.addOnScrollListener(customOnScrollListener);
    }

    public void removeOnScrollListener(RecyclerView.OnScrollListener customOnScrollListener) {
        mRecyclerView.removeOnScrollListener(customOnScrollListener);
    }

    public void addItemDividerDecoration(Context context) {
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
    }


    /**
     * Swaps the current adapter with the provided one. It is similar to
     * {@link #setAdapter(UltimateViewAdapter)} but assumes existing adapter and the new adapter uses the same
     * ViewHolder and does not clear the RecycledViewPool.
     * <p/>
     * Note that it still calls onAdapterChanged callbacks.
     *
     * @param adapter                       The new adapter to set, or null to set no adapter.
     * @param removeAndRecycleExistingViews If set to true, RecyclerView will recycle all existing
     *                                      Views. If adapters have stable ids and/or you want to
     *                                      animate the disappearing views, you may prefer to set
     *                                      this to false.
     */
    public void swapAdapter(UltimateViewAdapter adapter, boolean removeAndRecycleExistingViews) {
        mRecyclerView.swapAdapter(adapter, removeAndRecycleExistingViews);
    }

    /**
     * Add an {@link RecyclerView.ItemDecoration} to this RecyclerView. Item decorations can affect both measurement and drawing of individual item views.
     * <p>Item decorations are ordered. Decorations placed earlier in the list will be run/queried/drawn first for their effects on item views. Padding added to views will be nested; a padding added by an earlier decoration will mean further item decorations in the list will be asked to draw/pad within the previous decoration's given area.</p>
     *
     * @param itemDecoration Decoration to add
     */
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    /**
     * Add an {@link RecyclerView.ItemDecoration} to this RecyclerView. Item decorations can affect both measurement and drawing of individual item views.
     * <p>Item decorations are ordered. Decorations placed earlier in the list will be run/queried/drawn first for their effects on item views. Padding added to views will be nested; a padding added by an earlier decoration will mean further item decorations in the list will be asked to draw/pad within the previous decoration's given area.</p>
     *
     * @param itemDecoration Decoration to add
     * @param index          Position in the decoration chain to insert this decoration at. If this value is negative the decoration will be added at the end.
     */
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int index) {
        mRecyclerView.addItemDecoration(itemDecoration, index);
    }

    /**
     * Sets the {@link RecyclerView.ItemAnimator} that will handle animations involving changes
     * to the items in this RecyclerView. By default, RecyclerView instantiates and
     * uses an instance of {@link android.support.v7.widget.DefaultItemAnimator}. Whether item animations are
     * enabled for the RecyclerView depends on the ItemAnimator and whether
     * the LayoutManager {@link android.support.v7.widget.RecyclerView.LayoutManager#supportsPredictiveItemAnimations()
     * supports item animations}.
     *
     * @param animator The ItemAnimator being set. If null, no animations will occur
     *                 when changes occur to the items in this RecyclerView.
     */
    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }

    /**
     * Gets the current ItemAnimator for this RecyclerView. A null return value
     * indicates that there is no animator and that item changes will happen without
     * any animations. By default, RecyclerView instantiates and
     * uses an instance of {@link android.support.v7.widget.DefaultItemAnimator}.
     *
     * @return ItemAnimator The current ItemAnimator. If null, no animations will occur
     * when changes occur to the items in this RecyclerView.
     */
    public RecyclerView.ItemAnimator getItemAnimator() {
        return mRecyclerView.getItemAnimator();
    }

    /**
     * Set the listener when refresh is triggered and enable the SwipeRefreshLayout
     *
     * @param listener
     */
    public void setDefaultOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {

        mSwipeRefreshLayout.setEnabled(true);
        if (defaultSwipeToDismissColors != null && defaultSwipeToDismissColors.length > 0) {
            mSwipeRefreshLayout.setColorSchemeColors(defaultSwipeToDismissColors);
        } else {
            mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);

        }

        mSwipeRefreshLayout.setOnRefreshListener(listener);
    }

    /**
     * Set the color resources used in the progress animation from color resources. The first color will also be the color of the bar that grows in response to a user swipe gesture.
     *
     * @param colors
     */
    public void setDefaultSwipeToRefreshColorScheme(int... colors) {
        mSwipeRefreshLayout.setColorSchemeColors(colors);
    }

    /**
     * Set the load more listener of recyclerview
     *
     * @param onLoadMoreListener
     */
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
     * Get the adapter of UltimateRecyclerview
     *
     * @return
     */
    public RecyclerView.Adapter getAdapter() {
        return mRecyclerView.getAdapter();
    }

    /**
     * Set a UltimateViewAdapter or the subclass of UltimateViewAdapter to the recyclerview
     *
     * @param adapter
     */
    public void setAdapter(UltimateViewAdapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(false);
        if (mAdapter != null)
            mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
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
                    isLoadingMore = false;
                    if (mSwipeRefreshLayout != null)
                        mSwipeRefreshLayout.setRefreshing(false);
//
                    if (mAdapter == null)
                        return;

                    if (mRecyclerView.getAdapter().getItemCount() == 0 && mEmptyId != 0) {
                        mEmpty.setVisibility(View.VISIBLE);
                    } else if (mEmptyId != 0) {
                        mEmpty.setVisibility(View.GONE);
                    }
//                if (mRecyclerView.getAdapter().getItemCount() > 0 && mAdapter.getCustomHeaderView() != null
//                        && mAdapter.getCustomHeaderView().getVisibility() == View.GONE) {
//                    mAdapter.getCustomHeaderView().setVisibility(View.VISIBLE);
//                }
                    if (mAdapter.getAdapterItemCount() >= showLoadMoreItemNum && mAdapter.getCustomLoadMoreView() != null
                            && mAdapter.getCustomLoadMoreView().getVisibility() == View.GONE) {
                        mAdapter.getCustomLoadMoreView().setVisibility(View.VISIBLE);
                    }
                    if (mAdapter.getAdapterItemCount() < showLoadMoreItemNum && mAdapter.getCustomLoadMoreView() != null) {
                        mAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
                    }
                }

            });
        if ((adapter == null || mAdapter.getAdapterItemCount() == 0) && mEmptyId != 0) {
            mEmpty.setVisibility(View.VISIBLE);
        }
//        if (adapter == null || adapter.getAdapterItemCount() == 0) {
//            mAdapter.getCustomHeaderView().setVisibility(View.GONE);
//            mAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
//        }
    }

    /**
     * @param adapter
     * @deprecated Short for some ui effects
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
        if (mSwipeRefreshLayout != null)
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
                isLoadingMore = false;
                if (mSwipeRefreshLayout != null)
                    mSwipeRefreshLayout.setRefreshing(false);
//
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
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(refreshing);
    }


    /**
     * Enable or disable the SwipeRefreshLayout.
     * Default is false
     *
     * @param isSwipeRefresh
     */
    public void enableDefaultSwipeRefresh(boolean isSwipeRefresh) {
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setEnabled(isSwipeRefresh);
    }


    public interface OnLoadMoreListener {

        public void loadMore(int itemsCount, int maxLastVisiblePosition);
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

    private int findMin(int[] lastPositions) {
        int min = Integer.MAX_VALUE;
        for (int value : lastPositions) {
            if (value != RecyclerView.NO_POSITION && value < min)
                min = value;
        }
        return min;
    }


    private CustomRelativeWrapper mHeader;
    private int mTotalYScrolled;
    private final float SCROLL_MULTIPLIER = 0.5f;
    private OnParallaxScroll mParallaxScroll;

    /**
     * Set the parallax header of the recyclerview
     *
     * @param header
     */
    public void setParallaxHeader(View header) {
        mHeader = new CustomRelativeWrapper(header.getContext());
        mHeader.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mHeader.addView(header, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (mAdapter != null)
            mAdapter.setCustomHeaderView(mHeader);
    }

    /**
     * Set the on scroll method of parallax header
     *
     * @param parallaxScroll
     */
    public void setOnParallaxScroll(OnParallaxScroll parallaxScroll) {
        mParallaxScroll = parallaxScroll;
        mParallaxScroll.onParallaxScroll(0, 0, mHeader);
    }

    private void translateHeader(float of) {
        float ofCalculated = of * SCROLL_MULTIPLIER;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //Logs.d("ofCalculated    " + ofCalculated+"   "+mHeader.getHeight());
            mHeader.setTranslationY(ofCalculated);
        } else {
            TranslateAnimation anim = new TranslateAnimation(0, 0, ofCalculated, ofCalculated);
            anim.setFillAfter(true);
            anim.setDuration(0);
            mHeader.startAnimation(anim);
        }
        mHeader.setClipY(Math.round(ofCalculated));
        if (mParallaxScroll != null) {
            float left = Math.min(1, ((ofCalculated) / (mHeader.getHeight() * SCROLL_MULTIPLIER)));
            mParallaxScroll.onParallaxScroll(left, of, mHeader);
        }
    }

    public interface OnParallaxScroll {
        void onParallaxScroll(float percentage, float offset, View parallax);
    }

    /**
     * Custom layout for the Parallax Header.
     */
    public static class CustomRelativeWrapper extends RelativeLayout {

        private int mOffset;

        public CustomRelativeWrapper(Context context) {
            super(context);
        }

        @Override
        protected void dispatchDraw(Canvas canvas) {
            canvas.clipRect(new Rect(getLeft(), getTop(), getRight(), getBottom() + mOffset));
            super.dispatchDraw(canvas);
        }

        public void setClipY(int offset) {
            mOffset = offset;
            invalidate();
        }
    }

    public void setScrollViewCallbacks(ObservableScrollViewCallbacks listener) {
        mCallbacks = listener;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedStateScrolling ss = (SavedStateScrolling) state;
        mPrevFirstVisiblePosition = ss.prevFirstVisiblePosition;
        mPrevFirstVisibleChildHeight = ss.prevFirstVisibleChildHeight;
        mPrevScrolledChildrenHeight = ss.prevScrolledChildrenHeight;
        mPrevScrollY = ss.prevScrollY;
        mScrollY = ss.scrollY;
        mChildrenHeights = ss.childrenHeights;
        super.onRestoreInstanceState(ss.getSuperState());
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedStateScrolling ss = new SavedStateScrolling(superState);
        ss.prevFirstVisiblePosition = mPrevFirstVisiblePosition;
        ss.prevFirstVisibleChildHeight = mPrevFirstVisibleChildHeight;
        ss.prevScrolledChildrenHeight = mPrevScrolledChildrenHeight;
        ss.prevScrollY = mPrevScrollY;
        ss.scrollY = mScrollY;
        ss.childrenHeights = mChildrenHeights;
        return ss;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (mCallbacks != null) {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    mFirstScroll = mDragging = true;
                    mCallbacks.onDownMotionEvent();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mIntercepted = false;
                    mDragging = false;
                    mCallbacks.onUpOrCancelMotionEvent(mObservableScrollState);
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);

//        if (mCallbacks != null) {
//            URLogs.d("ev---"+ev.getActionMasked());
//            switch (ev.getActionMasked()) {
//                case MotionEvent.ACTION_DOWN:
//                    // Whether or not motion events are consumed by children,
//                    // flag initializations which are related to ACTION_DOWN events should be executed.
//                    // Because if the ACTION_DOWN is consumed by children and only ACTION_MOVEs are
//                    // passed to parent (this view), the flags will be invalid.
//                    // Also, applications might implement initialization codes to onDownMotionEvent,
//                    // so call it here.
//                    mFirstScroll = mDragging = true;
//                    mCallbacks.onDownMotionEvent();
//                    break;
//            }
//        }
//        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public void setTouchInterceptionViewGroup(ViewGroup viewGroup) {
        mTouchInterceptionViewGroup = viewGroup;
        setObserableScrollListener();
    }

    @Override
    public void scrollVerticallyTo(int y) {
        URLogs.d("vertically");
        View firstVisibleChild = getChildAt(0);
        if (firstVisibleChild != null) {
            int baseHeight = firstVisibleChild.getHeight();
            int position = y / baseHeight;
            scrollVerticallyToPosition(position);
        }
    }

    public void scrollVerticallyToPosition(int position) {
        RecyclerView.LayoutManager lm = getLayoutManager();

        if (lm != null && lm instanceof LinearLayoutManager) {
            ((LinearLayoutManager) lm).scrollToPositionWithOffset(position, 0);
        } else {
            lm.scrollToPosition(position);
        }
    }

    @Override
    public int getCurrentScrollY() {
        return mScrollY;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        URLogs.d("ev---"+ev);
        if (mCallbacks != null) {

            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mIntercepted = false;
                    mDragging = false;
                    mCallbacks.onUpOrCancelMotionEvent(mObservableScrollState);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mPrevMoveEvent == null) {
                        mPrevMoveEvent = ev;
                    }
                    float diffY = ev.getY() - mPrevMoveEvent.getY();
                    mPrevMoveEvent = MotionEvent.obtainNoHistory(ev);
                    if (getCurrentScrollY() - diffY <= 0) {
                        // Can't scroll anymore.

                        if (mIntercepted) {
                            // Already dispatched ACTION_DOWN event to parents, so stop here.
                            return false;
                        }

                        // Apps can set the interception target other than the direct parent.
                        final ViewGroup parent;
                        if (mTouchInterceptionViewGroup == null) {
                            parent = (ViewGroup) getParent();
                        } else {
                            parent = mTouchInterceptionViewGroup;
                        }

                        // Get offset to parents. If the parent is not the direct parent,
                        // we should aggregate offsets from all of the parents.
                        float offsetX = 0;
                        float offsetY = 0;
                        for (View v = this; v != null && v != parent; v = (View) v.getParent()) {
                            offsetX += v.getLeft() - v.getScrollX();
                            offsetY += v.getTop() - v.getScrollY();
                        }
                        final MotionEvent event = MotionEvent.obtainNoHistory(ev);
                        event.offsetLocation(offsetX, offsetY);

                        if (parent.onInterceptTouchEvent(event)) {
                            mIntercepted = true;

                            // If the parent wants to intercept ACTION_MOVE events,
                            // we pass ACTION_DOWN event to the parent
                            // as if these touch events just have began now.
                            event.setAction(MotionEvent.ACTION_DOWN);

                            // Return this onTouchEvent() first and set ACTION_DOWN event for parent
                            // to the queue, to keep events sequence.
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    parent.dispatchTouchEvent(event);
                                }
                            });
                            return false;
                        }
                        // Even when this can't be scrolled anymore,
                        // simply returning false here may cause subView's click,
                        // so delegate it to super.
                        return super.onTouchEvent(ev);
                    }
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    public boolean toolbarIsShown(Toolbar mToolbar) {
        return ViewHelper.getTranslationY(mToolbar) == 0;
    }

    public boolean toolbarIsHidden(Toolbar mToolbar) {
        return ViewHelper.getTranslationY(mToolbar) == -mToolbar.getHeight();
    }

    @Deprecated
    public void showToolbarAndFAB(Toolbar mToolbar, UltimateRecyclerView ultimateRecyclerView, int screenHeight) {
        showToolbar(mToolbar, ultimateRecyclerView, screenHeight);
        showDefaultFloatingActionButton();
    }

    @Deprecated
    public void hideToolbarAndFAB(Toolbar mToolbar, UltimateRecyclerView ultimateRecyclerView, int screenHeight) {
        hideToolbar(mToolbar, ultimateRecyclerView, screenHeight);
        hideDefaultFloatingActionButton();
    }

    public void showToolbar(Toolbar mToolbar, UltimateRecyclerView ultimateRecyclerView, int screenHeight) {
        moveToolbar(mToolbar, ultimateRecyclerView, screenHeight, 0);
    }

    public void hideToolbar(Toolbar mToolbar, UltimateRecyclerView ultimateRecyclerView, int screenHeight) {
        moveToolbar(mToolbar, ultimateRecyclerView, screenHeight, -mToolbar.getHeight());
    }

    public void showView(View mView, UltimateRecyclerView ultimateRecyclerView, int screenHeight) {
        moveView(mView, ultimateRecyclerView, screenHeight, 0);
    }

    public void hideView(View mView, UltimateRecyclerView ultimateRecyclerView, int screenHeight) {
        moveView(mView, ultimateRecyclerView, screenHeight, -mView.getHeight());
    }

    protected void moveToolbar(final Toolbar mToolbar, final UltimateRecyclerView ultimateRecyclerView, final int screenheight, float toTranslationY) {
        if (ViewHelper.getTranslationY(mToolbar) == toTranslationY) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getTranslationY(mToolbar), toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                ViewHelper.setTranslationY(mToolbar, translationY);
                ViewHelper.setTranslationY((View) ultimateRecyclerView, translationY);
                // FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ((View) ultimateRecyclerView).getLayoutParams();
                MarginLayoutParams layoutParams = (MarginLayoutParams) ((View) ultimateRecyclerView).getLayoutParams();
                layoutParams.height = (int) -translationY + screenheight - layoutParams.topMargin;
                ((View) ultimateRecyclerView).requestLayout();
            }
        });
        animator.start();
    }

    protected void moveView(final View mView, final UltimateRecyclerView ultimateRecyclerView, final int screenheight, float toTranslationY) {
        if (ViewHelper.getTranslationY(mView) == toTranslationY) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getTranslationY(mView), toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                ViewHelper.setTranslationY(mView, translationY);
                ViewHelper.setTranslationY((View) ultimateRecyclerView, translationY);
                // FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ((View) ultimateRecyclerView).getLayoutParams();
                MarginLayoutParams layoutParams = (MarginLayoutParams) ((View) ultimateRecyclerView).getLayoutParams();
                layoutParams.height = (int) -translationY + screenheight - layoutParams.topMargin;
                ((View) ultimateRecyclerView).requestLayout();
            }
        });
        animator.start();
    }


    public FloatingActionButton getDefaultFloatingActionButton() {
        return defaultFloatingActionButton;
    }

    public void setDefaultFloatingActionButton(FloatingActionButton defaultFloatingActionButton) {
        this.defaultFloatingActionButton = defaultFloatingActionButton;
    }

    public View getCustomFloatingActionView() {
        return mFloatingButtonView;
    }

//    public void setCustomFloatingActionView(View customFloatingActionView) {
//        this.floatingActionMenu = floatingActionMenu;
//    }

    public void showFloatingActionMenu() {
        if (mFloatingButtonView != null)
            ((FloatingActionsMenu) mFloatingButtonView).hide(false);
    }

    public void hideFloatingActionMenu() {
        if (mFloatingButtonView != null) ((FloatingActionsMenu) mFloatingButtonView).hide(true);
    }

    public void showFloatingActionButton() {
        if (mFloatingButtonView != null)
            ((FloatingActionButton) mFloatingButtonView).hide(false);
    }

    public void hideFloatingActionButton() {
        if (mFloatingButtonView != null) ((FloatingActionButton) mFloatingButtonView).hide(true);
    }

    public void showDefaultFloatingActionButton() {
        defaultFloatingActionButton.hide(false);
    }

    public void hideDefaultFloatingActionButton() {
        defaultFloatingActionButton.hide(true);
    }

    public void displayCustomFloatingActionView(boolean b) {
        if (mFloatingButtonView != null)
            mFloatingButtonView.setVisibility(b ? VISIBLE : INVISIBLE);
    }

    public void displayDefaultFloatingActionButton(boolean b) {
        defaultFloatingActionButton.setVisibility(b ? VISIBLE : INVISIBLE);
    }

    public void removeItemDecoration(RecyclerView.ItemDecoration decoration) {
        mRecyclerView.removeItemDecoration(decoration);
    }

    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecyclerView.addOnItemTouchListener(listener);
    }

    public void removeOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecyclerView.removeOnItemTouchListener(listener);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }
}
