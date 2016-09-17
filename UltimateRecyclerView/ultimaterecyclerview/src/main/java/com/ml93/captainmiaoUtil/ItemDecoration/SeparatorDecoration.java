package com.ml93.captainmiaoUtil.ItemDecoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.R;

/**
 * Created by hesk on 2016/9/17.
 */

public class SeparatorDecoration extends RecyclerView.ItemDecoration {

    private final Paint mPaint;

    private final float mMarginLeft;
    private final float mMarginRight;

    public SeparatorDecoration(@ColorInt int color,
                               @FloatRange(from = 0, fromInclusive = false) float width,
                               @FloatRange(from = 0) float marginLeft,
                               @FloatRange(from = 0) float marginRight) {
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setStrokeWidth(width);
        mMarginLeft = marginLeft;
        mMarginRight = marginRight;
    }

    /**
     * A builder to easily create the decoration.
     *
     * @param context a context to use
     * @return the builder
     */
    public static Builder with(@NonNull Context context) {
        return new Builder(context);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

        // we want to retrieve the position in the list
        final int position = params.getViewAdapterPosition();

        // and add a separator to any view but the last one
        if (position < state.getItemCount()) {
            outRect.set(0, 0, 0, (int) mPaint.getStrokeWidth()); // left, top, right, bottom
        } else {
            outRect.setEmpty(); // 0, 0, 0, 0
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        // we set the stroke width before, so as to correctly draw the line we have to offset by width / 2
        final int offset = (int) (mPaint.getStrokeWidth() / 2);

        // this will iterate over every visible view
        for (int i = 0; i < parent.getChildCount(); i++) {
            // get the view
            final View view = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

            // get the position
            final int position = params.getViewAdapterPosition();

            // and finally draw the separator
            if (position < state.getItemCount()) {
                c.drawLine(view.getLeft() + mMarginLeft, view.getBottom() + offset,
                        view.getRight() - mMarginRight, view.getBottom() + offset, mPaint);
            }
        }
    }

    /**
     * Builder to create a SeparatorDecoration.
     */
    public static class Builder {
        private final Context mContext;

        private float mWidth;
        private int mColor;
        private float mMarginLeft = 0f;
        private float mMarginRight = 0f;

        /**
         * Create the builder with a context to configure a SeparatorDecoration.
         *
         * @param context the context
         */
        public Builder(@NonNull Context context) {
            mContext = context;
            color(getDefaultColor());
            width(1f);
        }

        /**
         * Set the separator color from a resource.
         *
         * @param colorResId the resource id to use
         * @return the builder
         */
        public Builder colorFromResources(@ColorRes int colorResId) {
            mColor = mContext.getResources().getColor(colorResId);
            return this;
        }

        /**
         * Set the separator color from a color.
         *
         * @param color the color to use
         * @return the builder
         * @see android.graphics.Color
         */
        public Builder color(@ColorInt int color) {
            mColor = color;
            return this;
        }

        /**
         * Set the width of the separator.
         *
         * @param width the width in dp
         * @return the builder
         */
        public Builder width(@FloatRange(from = 0, fromInclusive = false) float width) {
            mWidth = width;
            return this;
        }

        /**
         * Set the margin of the separator
         *
         * @param margin the margin in dp
         * @return the builder
         */
        public Builder setMargin(float margin) {
            setMargin(margin, margin);
            return this;
        }

        /**
         * Set the margin of the separator
         *
         * @param left  the left margin in dp
         * @param right the right margin in dp
         * @return the builder
         */
        public Builder setMargin(float left, float right) {
            final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            mMarginLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    left, metrics);
            mMarginRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    right, metrics);
            return this;
        }

        @ColorInt
        private int getDefaultColor() {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = mContext.getTheme();
            theme.resolveAttribute(R.attr.colorControlHighlight, typedValue, true);
            return typedValue.data;
        }

        /**
         * Get the configured SeparatorDecoration.
         *
         * @return the separatorDecoration
         * @see SeparatorDecoration
         */
        public SeparatorDecoration build() {
            return new SeparatorDecoration(mColor, mWidth, mMarginLeft, mMarginRight);
        }
    }
}
