package com.ml93.captainmiaoUtil.ItemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.DimenRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by hesk on 2016/9/17.
 */

public class HeaderDecoration extends RecyclerView.ItemDecoration {

    private final View mView;
    private final boolean mHorizontal;
    private final float mParallax;
    private final float mShadowSize;
    private final int mColumns;
    private final Paint mShadowPaint;

    public HeaderDecoration(View view, boolean scrollsHorizontally, float parallax, float shadowSize, int columns) {
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

    public static Builder with(Context context) {
        return new Builder(context);
    }

    public static Builder with(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            return new Builder(recyclerView.getContext(), manager.getSpanCount());
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
            return new Builder(recyclerView.getContext(), manager.getOrientation() == LinearLayoutManager.HORIZONTAL);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
            return new Builder(recyclerView.getContext(), manager.getSpanCount());
        } else {
            return new Builder(recyclerView.getContext());
        }
    }


    public static class Builder {
        private Context mContext;

        private View mView;
        private boolean mHorizontal;
        private float mParallax = 1f;
        private float mShadowSize;
        private int mColumns = 1;

        public Builder(@NonNull Context context) {
            mContext = context;
        }

        public Builder(@NonNull Context context, @IntRange(from = 1) int columns) {
            mContext = context;
            mColumns = columns;
        }

        public Builder(@NonNull Context context, boolean horizontal) {
            mContext = context;
            mHorizontal = horizontal;
        }


        public Builder setView(@NonNull View view) {
            mView = view;
            return this;
        }

        public Builder inflate(@LayoutRes int layoutRes) {
            mView = LayoutInflater.from(mContext).inflate(layoutRes, null, false);
            return this;
        }

        /**
         * Adds a parallax effect.
         *
         * @param parallax the multiplier to use, 0f would be the view standing still, 1f moves along with the first item.
         * @return this builder
         */
        public Builder parallax(@FloatRange(from = 0f, to = 1f) float parallax) {
            mParallax = parallax;
            return this;
        }

        public Builder scrollsHorizontally(boolean isHorizontal) {
            mHorizontal = isHorizontal;
            return this;
        }

        public Builder dropShadowDp(@IntRange(from = 0, to = 80) int dp) {
            mShadowSize = mContext.getResources().getDisplayMetrics().density * dp;
            return this;
        }

        public Builder dropShadow(@DimenRes int dimenResource) {
            mShadowSize = mContext.getResources().getDimensionPixelSize(dimenResource);
            return this;
        }

        public HeaderDecoration build() {
            if (mView == null) {
                throw new IllegalStateException("View must be set with either setView or inflate");
            }
            return new HeaderDecoration(mView, mHorizontal, mParallax, mShadowSize * 1.5f, mColumns);
        }

        public Builder columns(@IntRange(from = 1) int columns) {
            mColumns = columns;
            return this;
        }
    }
}