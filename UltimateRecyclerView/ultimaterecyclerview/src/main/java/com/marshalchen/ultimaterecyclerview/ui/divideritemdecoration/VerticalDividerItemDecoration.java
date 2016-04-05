package com.marshalchen.ultimaterecyclerview.ui.divideritemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yqritc on 2015/01/15.
 */
public class VerticalDividerItemDecoration extends FlexibleDividerDecoration {

    private MarginProvider mMarginProvider;

    protected VerticalDividerItemDecoration(Builder builder) {
        super(builder);
        mMarginProvider = builder.mMarginProvider;
    }

    @Override
    protected Rect getDividerBound(int position, RecyclerView parent, View child) {
        Rect bounds = new Rect(0, 0, 0, 0);
        int transitionX = (int) ViewCompat.getTranslationX(child);
        int transitionY = (int) ViewCompat.getTranslationY(child);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        bounds.top = parent.getPaddingTop() +
                mMarginProvider.dividerTopMargin(position, parent) + transitionY;
        bounds.bottom = parent.getHeight() - parent.getPaddingBottom() -
                mMarginProvider.dividerBottomMargin(position, parent) + transitionY;

        int dividerSize = getDividerSize(position, parent);
        if (mDividerType == DividerType.DRAWABLE) {
            bounds.left = child.getRight() + params.leftMargin + transitionX;
            bounds.right = bounds.left + dividerSize;
        } else {
            bounds.left = child.getRight() + params.leftMargin + dividerSize / 2 + transitionX;
            bounds.right = bounds.left;
        }

        return bounds;
    }

    @Override
    protected void setItemOffsets(Rect outRect, int position, RecyclerView parent) {
        outRect.set(0, 0, getDividerSize(position, parent), 0);
    }

    private int getDividerSize(int position, RecyclerView parent) {
        if (mPaintProvider != null) {
            return (int) mPaintProvider.dividerPaint(position, parent).getStrokeWidth();
        } else if (mSizeProvider != null) {
            return mSizeProvider.dividerSize(position, parent);
        } else if (mDrawableProvider != null) {
            Drawable drawable = mDrawableProvider.drawableProvider(position, parent);
            return drawable.getIntrinsicWidth();
        }
        throw new RuntimeException("failed to get size");
    }

    /**
     * Interface for controlling divider margin
     */
    public interface MarginProvider {

        /**
         * Returns top margin of divider.
         *
         * @param position Divider position
         * @param parent   RecyclerView
         * @return top margin
         */
        int dividerTopMargin(int position, RecyclerView parent);

        /**
         * Returns bottom margin of divider.
         *
         * @param position Divider position
         * @param parent   RecyclerView
         * @return bottom margin
         */
        int dividerBottomMargin(int position, RecyclerView parent);
    }

    public static class Builder extends FlexibleDividerDecoration.Builder<Builder> {

        private MarginProvider mMarginProvider = new MarginProvider() {
            @Override
            public int dividerTopMargin(int position, RecyclerView parent) {
                return 0;
            }

            @Override
            public int dividerBottomMargin(int position, RecyclerView parent) {
                return 0;
            }
        };

        public Builder(Context context) {
            super(context);
        }

        public Builder margin(final int topMargin, final int bottomMargin) {
            return marginProvider(new MarginProvider() {
                @Override
                public int dividerTopMargin(int position, RecyclerView parent) {
                    return topMargin;
                }

                @Override
                public int dividerBottomMargin(int position, RecyclerView parent) {
                    return bottomMargin;
                }
            });
        }

        public Builder margin(int verticalMargin) {
            return margin(verticalMargin, verticalMargin);
        }

        public Builder marginResId(@DimenRes int topMarginId, @DimenRes int bottomMarginId) {
            return margin(mResources.getDimensionPixelSize(topMarginId),
                    mResources.getDimensionPixelSize(bottomMarginId));
        }

        public Builder marginResId(@DimenRes int verticalMarginId) {
            return marginResId(verticalMarginId, verticalMarginId);
        }

        public Builder marginProvider(MarginProvider provider) {
            mMarginProvider = provider;
            return this;
        }

        public VerticalDividerItemDecoration build() {
            checkBuilderParams();
            return new VerticalDividerItemDecoration(this);
        }
    }
}