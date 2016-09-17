package com.ml93.captainmiaoUtil.ItemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

/**
 * Created by David on 17.06.2015
 */

public class DividerDecoration extends RecyclerView.ItemDecoration {

    private final Paint mPaint;
    private int mHeightDp;

    public DividerDecoration(Context context) {
        this(context, Color.argb((int) (255 * 0.2), 0, 0, 0), 1f);
    }

    public DividerDecoration(Context context, int color, float heightDp) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
        mHeightDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightDp, context.getResources().getDisplayMetrics());
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (hasDividerOnBottom(view, parent, state)) {
            outRect.set(0, 0, 0, mHeightDp);
        } else {
            outRect.setEmpty();
        }
    }

    private boolean hasDividerOnBottom(View view, RecyclerView parent, RecyclerView.State state) {
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        return position < state.getItemCount() && parent.getAdapter().getItemViewType(position) != UltimateViewAdapter.VIEW_TYPES.HEADER
                && parent.getAdapter().getItemViewType(position + 1) != UltimateViewAdapter.VIEW_TYPES.HEADER;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (hasDividerOnBottom(view, parent, state)) {
                c.drawRect(view.getLeft(), view.getBottom(), view.getRight(), view.getBottom() + mHeightDp, mPaint);
            }
        }
    }
}
