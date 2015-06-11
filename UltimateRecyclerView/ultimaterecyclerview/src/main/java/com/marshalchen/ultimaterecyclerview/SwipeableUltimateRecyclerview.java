package com.marshalchen.ultimaterecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;

import com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListView;
import com.marshalchen.ultimaterecyclerview.swipelistview.SwipeListViewListener;
import com.marshalchen.ultimaterecyclerview.ui.VerticalSwipeRefreshLayout;
import com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton;

/**
 * Created by MarshalChen on 15-6-5.
 */
public class SwipeableUltimateRecyclerview extends UltimateRecyclerView  {
    public SwipeableUltimateRecyclerview(Context context) {
        super(context);
    }

    public SwipeableUltimateRecyclerview(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((SwipeListView)mRecyclerView).init(attrs);
    }

    public SwipeableUltimateRecyclerview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ((SwipeListView)mRecyclerView).init(attrs);
    }

    @Override
    protected void initViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.swipeable_ultimate_recycler_view_layout, this);
        mRecyclerView = (SwipeListView) view.findViewById(R.id.ultimate_list);

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

        if (mFloatingButtonId != 0) {
            mFloatingButtonView = mFloatingButtonViewStub.inflate();
            mFloatingButtonView.setVisibility(View.VISIBLE);
        }


    }

    public void setSwipeListViewListener(SwipeListViewListener swipeListViewListener) {
        ((SwipeListView)mRecyclerView).swipeListViewListener = swipeListViewListener;
    }
}
