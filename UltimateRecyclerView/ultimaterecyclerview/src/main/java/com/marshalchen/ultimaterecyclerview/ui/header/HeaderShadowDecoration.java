package com.marshalchen.ultimaterecyclerview.ui.header;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hesk on 18/3/16.
 */
public class HeaderShadowDecoration extends RecyclerView.ItemDecoration {

    private final View mView;
    private final boolean mHorizontal;
    private final float mParallax;
    private final float mShadowSize;
    private final int mColumns;
    private final Paint mShadowPaint;

    public HeaderShadowDecoration(View view, boolean scrollsHorizontally, float parallax, float shadowSize, int columns) {
        mView = view;
        mHorizontal = scrollsHorizontally;
        mParallax = parallax;
        mShadowSize = shadowSize;
        mColumns = columns;

        if (mShadowSize > 0) {
            mShadowPaint = new Paint();
            mShadowPaint.setShader(mHorizontal ?
                    new LinearGradient(mShadowSize, 0, 0, 0,
                            new int[]{Color.argb(55, 0, 0, 0), Color.argb(55, 0, 0, 0), Color.argb(3, 0, 0, 0)},
                            new float[]{0f, .5f, 1f},
                            Shader.TileMode.CLAMP) :
                    new LinearGradient(0, mShadowSize, 0, 0,
                            new int[]{Color.argb(55, 0, 0, 0), Color.argb(55, 0, 0, 0), Color.argb(3, 0, 0, 0)},
                            new float[]{0f, .5f, 1f},
                            Shader.TileMode.CLAMP));
        } else {
            mShadowPaint = null;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        // layout basically just gets drawn on the reserved space on top of the first view
        mView.layout(parent.getLeft(), 0, parent.getRight(), mView.getMeasuredHeight());

        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (parent.getChildAdapterPosition(view) == 0) {
                c.save();
                if (mHorizontal) {
                    c.clipRect(parent.getLeft(), parent.getTop(), view.getLeft(), parent.getBottom());
                    final int width = mView.getMeasuredWidth();
                    final float left = (view.getLeft() - width) * mParallax;
                    c.translate(left, 0);
                    mView.draw(c);
                    if (mShadowSize > 0) {
                        c.translate(view.getLeft() - left - mShadowSize, 0);
                        c.drawRect(parent.getLeft(), parent.getTop(), mShadowSize, parent.getBottom(), mShadowPaint);
                    }
                } else {
                    c.clipRect(parent.getLeft(), parent.getTop(), parent.getRight(), view.getTop());
                    final int height = mView.getMeasuredHeight();
                    final float top = (view.getTop() - height) * mParallax;
                    c.translate(0, top);
                    mView.draw(c);
                    if (mShadowSize > 0) {
                        c.translate(0, view.getTop() - top - mShadowSize);
                        c.drawRect(parent.getLeft(), parent.getTop(), parent.getRight(), mShadowSize, mShadowPaint);
                    }
                }
                c.restore();
                break;
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) < mColumns) {
            if (mHorizontal) {
                if (mView.getMeasuredWidth() <= 0) {
                    mView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST),
                            View.MeasureSpec.makeMeasureSpec(parent.getMeasuredHeight(), View.MeasureSpec.AT_MOST));
                }
                outRect.set(mView.getMeasuredWidth(), 0, 0, 0);
            } else {
                if (mView.getMeasuredHeight() <= 0) {
                    mView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST),
                            View.MeasureSpec.makeMeasureSpec(parent.getMeasuredHeight(), View.MeasureSpec.AT_MOST));
                }
                outRect.set(0, mView.getMeasuredHeight(), 0, 0);
            }
        } else {
            outRect.setEmpty();
        }
    }

}
