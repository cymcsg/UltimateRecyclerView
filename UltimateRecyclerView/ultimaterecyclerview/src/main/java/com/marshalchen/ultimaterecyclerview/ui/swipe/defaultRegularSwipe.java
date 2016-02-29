package com.marshalchen.ultimaterecyclerview.ui.swipe;

import android.support.v7.widget.RecyclerView;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;
import com.marshalchen.ultimaterecyclerview.swipe.SimpleSwipeListener;

/**
 * Created by hesk on 19/2/16.
 */
public class defaultRegularSwipe<T extends easyRegularAdapter> implements SwipeableRecyclerViewTouchListener.SwipeListener {

    private T mAdapter;

    public defaultRegularSwipe(T adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean canSwipe(int position) {
        if (mAdapter.enableLoadMore() && position == mAdapter.getItemCount() - 1) return false;
        if (mAdapter.hasHeaderView() && position == 0) return false;
        return true;
    }

    @Override
    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            mAdapter.removeAt(position);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            mAdapter.removeAt(position);
        }
        mAdapter.notifyDataSetChanged();
    }

}
