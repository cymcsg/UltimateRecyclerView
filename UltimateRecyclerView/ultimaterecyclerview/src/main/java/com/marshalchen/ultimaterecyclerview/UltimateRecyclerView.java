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

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.marshalchen.ultimaterecyclerview.ui.DividerItemDecoration;
import com.marshalchen.ultimaterecyclerview.ui.VerticalSwipeRefreshLayout;
import com.marshalchen.ultimaterecyclerview.ui.emptyview.emptyViewOnShownListener;
import com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton;
import com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionsMenu;
import com.marshalchen.ultimaterecyclerview.uiUtils.RecyclerViewPositionHelper;
import com.marshalchen.ultimaterecyclerview.uiUtils.SavedStateScrolling;


/**
 * UltimateRecyclerView is a recyclerview which contains many features like  swipe to dismiss,animations,drag drop etc.
 */
public class UltimateRecyclerView extends FrameLayout implements Scrollable {
    /**
     * TRIGGERED ON NOTIFIY ITEMS
     */
    public static int EMPTY_CLEAR_ALL = 0;
    public static int EMPTY_SHOW_LOADMORE_ONLY = 1;
    public static int EMPTY_KEEP_HEADER = 2;
    public static int EMPTY_KEEP_HEADER_AND_LOARMORE = 3;

    /**
     * TRIGGERED ON SETTING ADAPTER TO THE URV
     */
    public static int STARTWITH_OFFLINE_ITEMS = 0;
    public static int STARTWITH_ONLINE_ITEMS = 1;

    private int policy_empty, policy_init;
    public RecyclerView mRecyclerView;
    protected FloatingActionButton defaultFloatingActionButton;
    private OnLoadMoreListener onLoadMoreListener;
    private int lastVisibleItemPosition;
    protected RecyclerView.OnScrollListener mOnScrollListener;
    protected LAYOUT_MANAGER_TYPE layoutManagerType;
    private boolean automaticLoadMoreEnabled = false;
    protected int mPadding;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    //protected int mEmptyViewPolicy;
    protected boolean mClipToPadding;
    private UltimateViewAdapter mAdapter;
    // Fields that should be saved onSaveInstanceState
    private int mPrevFirstVisiblePosition;
    private int mPrevFirstVisibleChildHeight = -1;
    private int mPrevScrolledChildrenHeight;
    private int mPrevScrollY;
    private int mScrollY;
    private SparseIntArray mChildrenHeights = new SparseIntArray();

    // Fields that don't need to be saved onSaveInstanceState
    private ObservableScrollState mObservableScrollState;
    private ObservableScrollViewCallbacks mCallbacks;
    //private ScrollState mScrollState;
    private boolean mFirstScroll;
    private boolean mDragging;
    private boolean mIntercepted;
    private boolean mIsLoadMoreWidgetEnabled = false;
    private MotionEvent mPrevMoveEvent;
    private ViewGroup mTouchInterceptionViewGroup;
    /**
     * custom load more progress bar
     */
    private View mLoadMoreView;
    /**
     * empty view group
     */
    protected ViewStub mEmpty;
    protected View mEmptyView;
    protected int mEmptyId;
    protected emptyViewOnShownListener mEmptyViewListener;


    /**
     * the floating button group
     */
    protected ViewStub mFloatingButtonViewStub;
    protected View mFloatingButtonView;
    protected int mFloatingButtonId;

    protected int[] defaultSwipeToDismissColors = null;
    public int showLoadMoreItemNum = 3;

    public VerticalSwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerViewPositionHelper mRecyclerViewHelper;
    private CustomRelativeWrapper mHeader;
    private int mTotalYScrolled;

    private final float SCROLL_MULTIPLIER = 0.5f;
    private OnParallaxScroll mParallaxScroll;
    private static boolean isParallaxHeader = false;
    private LayoutInflater inflater;

    /**
     * control to show the loading view first when list is initiated at the beginning
     * true - assume there is a buffer to load things before and the adapter suppose zero data at the beignning
     * false - assume there is data to show at the beginning level
     */
    private boolean isFirstLoadingOnlineAdapter = false;
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

    public void setRecylerViewBackgroundColor(@ColorInt int color) {
        mRecyclerView.setBackgroundColor(color);
    }

    protected void initViews() {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        /**
         * empty view setup
         */
        mEmpty = (ViewStub) view.findViewById(R.id.emptyview);
        if (mEmptyId != 0) {
            mEmpty.setLayoutResource(mEmptyId);
            mEmptyView = mEmpty.inflate();
            mEmpty.setVisibility(View.GONE);
        }

        /**
         * floating button setup
         */
        mFloatingButtonViewStub = (ViewStub) view.findViewById(R.id.floatingActionViewStub);
        mFloatingButtonViewStub.setLayoutResource(mFloatingButtonId);
    }

    /**
     * retrieve the empty view from the core
     *
     * @return the view item
     */
    public View getEmptyView() {
        return mEmptyView;
    }

    private void setPolicies(final int policyEmtpyView, final int policyInitialization) {
        //  setPolices(policyEmtpyView, policyInitialization);
        policy_empty = policyEmtpyView;
        policy_init = policyInitialization;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    private void setEmptyView(@LayoutRes final int emptyResourceId) {
        if (mEmptyView == null && emptyResourceId > 0) {
            mEmptyId = emptyResourceId;
            mEmpty.setLayoutResource(emptyResourceId);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mEmpty.setLayoutInflater(inflater);
            }
            mEmptyView = mEmpty.inflate();
        } else {
            Log.d(VIEW_LOG_TAG, "unabled to set empty view because the empty has been set");
        }
    }

    private void setEmptyView(@Nullable View mInflatedView) {
        if (mInflatedView != null)
            mEmptyView = mInflatedView;
    }

    /**
     * Set custom empty view.The view will be shown if the adapter is null or the size of the adapter is zero.
     * You can customize it as loading view.
     *
     * @param emptyResourceId the Resource Id from the empty view
     * @param emptyViewPolicy the Resource Id from the empty view
     */
    public final void setEmptyView(@LayoutRes int emptyResourceId, final int emptyViewPolicy) {
        //  mEmptyViewPolicy = emptyViewPolicy;
        setEmptyView(emptyResourceId);
        setPolicies(emptyViewPolicy, UltimateRecyclerView.STARTWITH_OFFLINE_ITEMS);
        mEmpty.setVisibility(View.GONE);
    }

    public final void setEmptyView(@LayoutRes int emptyResourceId, final int emptyViewPolicy, final int mEmptyViewInitPolicy) {
        setEmptyView(emptyResourceId);
        setPolicies(emptyViewPolicy, mEmptyViewInitPolicy);
    }

    public final void setEmptyView(@LayoutRes int emptyResourceId, final int emptyViewPolicy, final emptyViewOnShownListener listener) {
        setEmptyView(emptyResourceId);
        setPolicies(emptyViewPolicy, UltimateRecyclerView.STARTWITH_OFFLINE_ITEMS);
        mEmptyViewListener = listener;
    }

    public final void setEmptyView(@LayoutRes int emptyResourceId, final int emptyViewPolicy, final int emptyViewInitPolicy, final emptyViewOnShownListener listener) {
        setEmptyView(emptyResourceId);
        setPolicies(emptyViewPolicy, emptyViewInitPolicy);
        mEmptyViewListener = listener;
    }

    /**
     * Show the custom or default empty view
     * You can customize it as loading view
     *
     * @return is the empty shown
     */
    public boolean showEmptyView() {
        if (mEmpty != null && mEmptyView != null && mAdapter != null) {
            if (mAdapter.getEmptyViewPolicy() == EMPTY_CLEAR_ALL || mAdapter.getEmptyViewPolicy() == EMPTY_KEEP_HEADER) {
                mEmpty.setVisibility(View.VISIBLE);
                if (mEmptyViewListener != null) {
                    mEmptyViewListener.onEmptyViewShow(mEmptyView);
                }
            }
            return true;
        } else {
            Log.d(VIEW_LOG_TAG, "it is unable to show empty view");
            return false;
        }
    }

    /**
     * Hide the custom or default empty view
     */
    public void hideEmptyView() {
        if (mEmpty != null && mEmptyView != null) {
            mEmpty.setVisibility(View.GONE);
        } else {
            Log.d(VIEW_LOG_TAG, "there is no such empty view");
        }
    }

    public void setLoadMoreView(View mlayoutView) {
        if (mLoadMoreView != null) {
            Log.d(VIEW_LOG_TAG, "The loading more layout has already been initiated.");
            return;
        }
        if (mlayoutView == null) {
            mLoadMoreView = LayoutInflater.from(getContext()).inflate(R.layout.bottom_progressbar, null);
            Log.d(VIEW_LOG_TAG, "Layout Resource view is null. This system will use the default loading view instead.");
        } else {
            mLoadMoreView = mlayoutView;
        }
    }

    /**
     * setting up the loading more layout
     *
     * @param layout the res layout
     */
    public void setLoadMoreView(@LayoutRes final int layout) {
        if (layout > 0) {
            mLoadMoreView = LayoutInflater.from(getContext()).inflate(layout, null);
        } else {
            Log.d(VIEW_LOG_TAG, "Layout Resource Id is not found for load more view for ulitmaterecyclerview");
        }
    }

    /**
     * Show the custom floating button view.
     */
    public void showFloatingButtonView() {
        if (mFloatingButtonId != 0 && mFloatingButtonView == null) {
            mFloatingButtonView = mFloatingButtonViewStub.inflate();
            mFloatingButtonView.setVisibility(View.VISIBLE);
        } else {
            Log.d(VIEW_LOG_TAG, "floating button cannot be inflated because it has inflated already");
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
            //mEmptyViewPolicy = EMPTY_VIEW_POLICY_EMPTY_SHOW;
        } finally {
            typedArray.recycle();
        }
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

    private int[] mlastPositionsStaggeredGridLayout;

    private void scroll_load_more_detection(RecyclerView recyclerView) {

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManagerType == null) {
            if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            } else if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else {
                throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        mTotalItemCount = layoutManager.getItemCount();
        mVisibleItemCount = layoutManager.getChildCount();

        switch (layoutManagerType) {
            case LINEAR:
                mFirstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                lastVisibleItemPosition = mRecyclerViewHelper.findLastVisibleItemPosition();
                break;
            case GRID:
                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager ly = (GridLayoutManager) layoutManager;
                    lastVisibleItemPosition = ly.findLastVisibleItemPosition();
                    mFirstVisibleItem = ly.findFirstVisibleItemPosition();
                }
                break;
            case STAGGERED_GRID:
                if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager sy = (StaggeredGridLayoutManager) layoutManager;

                    if (mlastPositionsStaggeredGridLayout == null)
                        mlastPositionsStaggeredGridLayout = new int[sy.getSpanCount()];

                    sy.findLastVisibleItemPositions(mlastPositionsStaggeredGridLayout);
                    lastVisibleItemPosition = findMax(mlastPositionsStaggeredGridLayout);

                    sy.findFirstVisibleItemPositions(mlastPositionsStaggeredGridLayout);
                    mFirstVisibleItem = findMin(mlastPositionsStaggeredGridLayout);
                }
                break;
        }

        if (automaticLoadMoreEnabled) {

            if (mTotalItemCount > previousTotal) {
                automaticLoadMoreEnabled = false;
                previousTotal = mTotalItemCount;
            }
        }

        boolean bottomEdgeHit = (mTotalItemCount - mVisibleItemCount) <= mFirstVisibleItem;

        if (bottomEdgeHit) {
            if (mIsLoadMoreWidgetEnabled) {
                /**auto activate load more**/
                if (!automaticLoadMoreEnabled) {
                    onLoadMoreListener.loadMore(mRecyclerView.getAdapter().getItemCount(), lastVisibleItemPosition);
                    automaticLoadMoreEnabled = true;
                }
            }
            mAdapter.internalExecuteLoadingView();
            previousTotal = mTotalItemCount;
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
                    if (isParallaxHeader)
                        translateHeader(mTotalYScrolled);
                }

                scroll_load_more_detection(recyclerView);

                enableShoworHideToolbarAndFloatingButton(recyclerView);
            }
        };

        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }


    /**
     * If you have used {@link #disableLoadmore()} and want to enable loading more again,you can use this method.
     */
    public void reenableLoadmore() {
        if (mAdapter != null && mLoadMoreView != null) {
            mAdapter.enableLoadMore(true);
        }
        mIsLoadMoreWidgetEnabled = true;
    }

    public boolean isLoadMoreEnabled() {
        return mIsLoadMoreWidgetEnabled;
    }

    /**
     * Remove loading more scroll listener
     */
    public void disableLoadmore() {
        mIsLoadMoreWidgetEnabled = false;
        if (mAdapter != null && mLoadMoreView != null) {
            mAdapter.enableLoadMore(false);
        }
    }


    protected void enableShoworHideToolbarAndFloatingButton(RecyclerView recyclerView) {
        if (mCallbacks != null) {
            if (getChildCount() > 0) {
                int firstVisiblePosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));
                int lastVisiblePosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(recyclerView.getChildCount() - 1));
                try {
                    for (int i = firstVisiblePosition, j = 0; i <= lastVisiblePosition; i++, j++) {
                        int childHeight = 0;
                        View child = recyclerView.getChildAt(j);
                        if (mChildrenHeights.indexOfKey(i) < 0 || (child != null && child.getHeight() != mChildrenHeights.get(i))) {
                            if (child != null)
                                childHeight = child.getHeight();
                        }
                        mChildrenHeights.put(i, childHeight);
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                    //todo: need to solve this issue when the first child is missing from the scroll. Please also see the debug from the RV error.
                    //todo: 07-01 11:50:36.359  32348-32348/com.marshalchen.ultimaterecyclerview.demo D/RVerror? Attempt to invoke virtual method 'int android.view.View.getHeight()' on a null object reference
                    URLogs.e(e, "");
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

                    if (mPrevScrollY < mScrollY) {
                        //down
                        if (mFirstScroll) { // first scroll down , mPrevScrollY == 0, reach here.
                            mFirstScroll = false;
                            mObservableScrollState = ObservableScrollState.STOP;
                        }
                        mObservableScrollState = ObservableScrollState.UP;
                    } else if (mScrollY < mPrevScrollY) {
                        //up
                        mObservableScrollState = ObservableScrollState.DOWN;
                    } else {
                        mObservableScrollState = ObservableScrollState.STOP;
                    }
                    if (mFirstScroll) {
                        mFirstScroll = false;
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
     * Note that it still calls onAdapterChanged callbacks.
     *
     * @param adapter                       The new adapter to set, or null to set no adapter.
     * @param removeAndRecycleExistingViews If set to true, RecyclerView will recycle all existing Views. If adapters have stable ids and/or you want to animate the disappearing views, you may prefer to set this to false.
     */
    public void swapAdapter(UltimateViewAdapter adapter, boolean removeAndRecycleExistingViews) {
        mRecyclerView.swapAdapter(adapter, removeAndRecycleExistingViews);
        setAdapterInternal(adapter);
    }

    public void setAdapter(UltimateViewAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        setAdapterInternal(adapter);
    }

    /**
     * Add an {@link RecyclerView.ItemDecoration} to this RecyclerView. Item decorations can affect both measurement and drawing of individual item views. Item decorations are ordered. Decorations placed earlier in the list will be run/queried/drawn first for their effects on item views. Padding added to views will be nested; a padding added by an earlier decoration will mean further item decorations in the list will be asked to draw/pad within the previous decoration's given area.
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
     * uses an instance of {@link android.support.v7.widget.DefaultItemAnimator}. Whether item animations are enabled for the RecyclerView depends on the ItemAnimator and whether
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
     * @param listener SwipeRefreshLayout
     */
    public void setDefaultOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {

        mSwipeRefreshLayout.setEnabled(true);
        if (defaultSwipeToDismissColors != null && defaultSwipeToDismissColors.length > 0) {
            mSwipeRefreshLayout.setColorSchemeColors(defaultSwipeToDismissColors);
        } else {
            mSwipeRefreshLayout.setColorSchemeResources(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);

        }

        mSwipeRefreshLayout.setOnRefreshListener(listener);
    }

    /**
     * Set the color resources used in the progress animation from color resources. The first color will also be the color of the bar that grows in response to a user swipe gesture.
     *
     * @param colors colors in array
     */
    public void setDefaultSwipeToRefreshColorScheme(int... colors) {
        mSwipeRefreshLayout.setColorSchemeColors(colors);
    }

    /**
     * Set the load more listener of recyclerview
     *
     * @param onLoadMoreListener load listen
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    /**
     * Set the layout manager to the recycler
     *
     * @param manager lm
     */
    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

    /**
     * Get the adapter of UltimateRecyclerview
     *
     * @return ad
     */
    public RecyclerView.Adapter getAdapter() {
        return mRecyclerView.getAdapter();
    }


    /**
     * Set a UltimateViewAdapter or the subclass of UltimateViewAdapter to the recyclerview
     *
     * @param adapter the adapter in normal
     */
    private void setAdapterInternal(UltimateViewAdapter adapter) {
        mAdapter = adapter;
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(false);
        if (mAdapter != null)
            mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    super.onItemRangeChanged(positionStart, itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onChanged() {
                    super.onChanged();
                    updateHelperDisplays();
                }
            });

        mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(mRecyclerView);

        mAdapter.setEmptyViewPolicy(policy_empty);
        mAdapter.setEmptyViewOnInitPolicy(policy_init);

        if (mAdapter.getAdapterItemCount() == 0 && policy_init == UltimateRecyclerView.STARTWITH_OFFLINE_ITEMS) {
            showEmptyView();
        }

        if (policy_init == UltimateRecyclerView.STARTWITH_ONLINE_ITEMS) {
            hideEmptyView();
        }

        if (mAdapter.getCustomLoadMoreView() == null && mLoadMoreView != null) {
            mAdapter.setCustomLoadMoreView(mLoadMoreView);
            mAdapter.enableLoadMore(true);
            mAdapter.notifyDataSetChanged();
            mIsLoadMoreWidgetEnabled = true;
        }

        if (mHeader != null) {
            mAdapter.setCustomHeaderView(mHeader);
            //getLayoutManager().ignoreView(mHeader);
        }
    }


    private void updateHelperDisplays() {
        automaticLoadMoreEnabled = false;
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(false);
        if (mAdapter == null)
            return;
        /**
         * fixed by jjHesk
         * + empty layout is NONE
         * + getItemCount is zero
         */
        if (!isFirstLoadingOnlineAdapter) {
            if (mAdapter.getAdapterItemCount() == 0) {

                mEmpty.setVisibility(mEmptyView == null ? View.VISIBLE : View.GONE);


            } else if (mEmptyId != 0) {
                implementLoadMorebehavior();
                mEmpty.setVisibility(View.GONE);
            }
        } else {
            isFirstLoadingOnlineAdapter = false;
            setRefreshing(false);
            implementLoadMorebehavior();
        }

    }

    private void implementLoadMorebehavior() {
        if (mAdapter.getCustomLoadMoreView() != null) {
            if (mAdapter.enableLoadMore()) {
                mAdapter.getCustomLoadMoreView().setVisibility(View.VISIBLE);
            } else {
                mAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
            }
        }
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        mRecyclerView.setHasFixedSize(hasFixedSize);
    }

    /**
     * Notify the widget that refresh state has changed. Do not call this when refresh is triggered by a swipe gesture.
     *
     * @param refreshing enable the refresh loading icon
     */
    public void setRefreshing(boolean refreshing) {
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(refreshing);
    }


    /**
     * Enable or disable the SwipeRefreshLayout.
     * Default is false
     *
     * @param isSwipeRefresh is now operating in refresh
     */
    public void enableDefaultSwipeRefresh(boolean isSwipeRefresh) {
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setEnabled(isSwipeRefresh);
    }


    public interface OnLoadMoreListener {
        void loadMore(int itemsCount, final int maxLastVisiblePosition);
    }

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID,
        PUZZLE,
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


    /**
     * allow resource layout id to be introduced
     *
     * @param mLayout res id
     */
    public void setParallaxHeader(@LayoutRes int mLayout) {
        View h_layout = LayoutInflater.from(getContext()).inflate(mLayout, null);
        setParallaxHeader(h_layout);
    }

    /**
     * Set the parallax header of the recyclerview
     *
     * @param header the view
     */
    public void setParallaxHeader(View header) {
        mHeader = new CustomRelativeWrapper(header.getContext());
        mHeader.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mHeader.addView(header, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        isParallaxHeader = true;
    }


    /**
     * Set the normal header of the recyclerview
     *
     * @param header na
     */
    public void setNormalHeader(View header) {
        setParallaxHeader(header);
        isParallaxHeader = false;
    }

    /**
     * Set the on scroll method of parallax header
     *
     * @param parallaxScroll na
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
            if (isParallaxHeader)
                canvas.clipRect(new Rect(getLeft(), getTop(), getRight(), getBottom() + mOffset));
            super.dispatchDraw(canvas);
        }

        public void setClipY(int offset) {
            mOffset = offset;
            invalidate();
        }

    }

    /**
     * the observable scroll view call backs
     *
     * @param listener listener to set
     */
    public void setScrollViewCallbacks(ObservableScrollViewCallbacks listener) {
        mCallbacks = listener;
    }

    public void setItemViewCacheSize(final int off_screen_items) {
        mRecyclerView.setItemViewCacheSize(off_screen_items);
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
        RecyclerView.LayoutManager layoutManager = getLayoutManager();

        /**
         * enhanced and store the previous scroll position
         */
        if (layoutManager != null) {
            int count = layoutManager.getChildCount();
            if (mPrevScrollY != RecyclerView.NO_POSITION && mPrevScrollY < count) {
                layoutManager.scrollToPosition(mPrevScrollY);
            }
        }

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
        URLogs.d("ev---" + ev);
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
        return ViewCompat.getTranslationY(mToolbar) == 0;
    }

    public boolean toolbarIsHidden(Toolbar mToolbar) {
        return ViewCompat.getTranslationY(mToolbar) == -mToolbar.getHeight();
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void moveToolbar(final Toolbar mToolbar, final UltimateRecyclerView ultimateRecyclerView, final int screenheight, float toTranslationY) {
        if (ViewCompat.getTranslationY(mToolbar) == toTranslationY) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(ViewCompat.getTranslationY(mToolbar), toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                ViewCompat.setTranslationY(mToolbar, translationY);
                ViewCompat.setTranslationY((View) ultimateRecyclerView, translationY);
                // FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ((View) ultimateRecyclerView).getLayoutParams();
                MarginLayoutParams layoutParams = (MarginLayoutParams) ((View) ultimateRecyclerView).getLayoutParams();
                layoutParams.height = (int) -translationY + screenheight - layoutParams.topMargin;
                ((View) ultimateRecyclerView).requestLayout();
            }
        });
        animator.start();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void moveView(final View mView, final UltimateRecyclerView ultimateRecyclerView, final int screenheight, float toTranslationY) {
        if (ViewCompat.getTranslationY(mView) == toTranslationY) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(ViewCompat.getTranslationY(mView), toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                ViewCompat.setTranslationY(mView, translationY);
                ViewCompat.setTranslationY((View) ultimateRecyclerView, translationY);
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
