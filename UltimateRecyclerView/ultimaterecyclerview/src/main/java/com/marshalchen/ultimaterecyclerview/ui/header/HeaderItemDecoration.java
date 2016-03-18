package com.marshalchen.ultimaterecyclerview.ui.header;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by hesk on 18/3/16.
 */
public class HeaderItemDecoration extends RecyclerView.ItemDecoration {
    private int mHeaderHeight;
    private int mNumberOfChildren;
    private boolean mReversed;

    public HeaderItemDecoration(RecyclerView.LayoutManager layoutManager, int height, boolean isReserse) {
        if (layoutManager.getClass() == LinearLayoutManager.class) {
            mNumberOfChildren = 1;
        } else if (layoutManager.getClass() == GridLayoutManager.class) {
            mNumberOfChildren = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            mNumberOfChildren = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        mHeaderHeight = height;
        mReversed = isReserse;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int value = (parent.getChildLayoutPosition(view) < mNumberOfChildren) ? mHeaderHeight : 0;
        if (mReversed) {
            outRect.bottom = value;
        } else {
            outRect.top = value;
        }
    }
}