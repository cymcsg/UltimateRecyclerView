package com.marshalchen.ultimaterecyclerview.grid;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Add padding around elements within a RecyclerView using GridLayoutManager
 */

/**
 * Created by hesk on 27/1/16.
 */
public class GridPaddingDecorator extends RecyclerView.ItemDecoration {
    private int mSpanCt;
    private int mVPadding;
    private int mHPadding;

    /**
     * @param vPadding  The space to be placed inbetween rows
     * @param hPadding  The space to be placed inbetween columns
     * @param spanCount The number of spans the GridLayoutManager will use
     */
    public GridPaddingDecorator(int vPadding, int hPadding, int spanCount) {
        mSpanCt = spanCount;
        mVPadding = vPadding;
        mHPadding = hPadding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int totalItems = parent.getAdapter().getItemCount();

        //for items not in the bottomRow
        if (position < totalItems - mSpanCt)
            outRect.bottom = mVPadding;

        //for items preceding the rightmost column
        if (position % mSpanCt < mSpanCt - 1)
            outRect.right = mHPadding;

    }
}