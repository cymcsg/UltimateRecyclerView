package com.ml93.captainmiaoUtil.ItemDecoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.R;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

/**
 * Created by hesk on 2016/9/17.
 */

public class CardViewDecoration extends RecyclerView.ItemDecoration {
    final static float SHADOW_MULTIPLIER = 1.5f;
    /*
      * This helper is set by CardView implementations. <p> Prior to API 17, canvas.drawRoundRect is expensive; which is
 	 * why we need this interface to draw efficient rounded rectangles before 17.
 	 */
    //static RoundRectHelper sRoundRectHelper;
    private Paint mPaint;
    private Paint mCornerShadowPaint;
    private Paint mEdgeShadowPaint;
    private final RectF mPreShadowBounds;
    private float mCornerRadius;
    private Path mCornerShadowPath;
    private float mShadowSize;
    private boolean mDirty = true;
    private final int mShadowStartColor;
    private final int mShadowEndColor;
    private float mPadding;

    public CardViewDecoration(Context resources, int backgroundColor, float radius) {
        mShadowStartColor = ContextCompat.getColor(resources, R.color.cardview_shadow_start_color);
        mShadowEndColor = ContextCompat.getColor(resources, R.color.cardview_shadow_end_color);
        mShadowSize = resources.getResources().getDimension(R.dimen.cardview_shadow_size) * SHADOW_MULTIPLIER;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(backgroundColor);
        mCornerShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mCornerShadowPaint.setStyle(Paint.Style.FILL);
        mCornerShadowPaint.setDither(true);
        mCornerRadius = radius;
        mPreShadowBounds = new RectF();
        mEdgeShadowPaint = new Paint(mCornerShadowPaint);

        buildShadowCorners();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Rect bounds = new Rect();
        float edgeShadowTop = -mCornerRadius - mShadowSize;

        RecyclerView.LayoutManager lm = parent.getLayoutManager();
        float size16dp = 16f;
        int padding16dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size16dp, parent.getContext().getResources().getDisplayMetrics());

        for (int i = 0; i < parent.getChildCount(); i++) {
            int save = c.save();

            // using decorated values, remove what we set before
            View child = parent.getChildAt(i);
            bounds.set(lm.getDecoratedLeft(child) + padding16dp - (int) mPadding,
                    lm.getDecoratedTop(child),
                    lm.getDecoratedRight(child) - padding16dp + (int) mPadding,
                    lm.getDecoratedBottom(child));

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewAdapterPosition();
            int viewType = parent.getAdapter().getItemViewType(position);

            if (viewType == UltimateViewAdapter.VIEW_TYPES.HEADER) {
                bounds.top = (int) (bounds.top + padding16dp - mPadding);

                // LT
                c.translate(bounds.left + mCornerRadius, bounds.top + mCornerRadius);
                c.drawPath(mCornerShadowPath, mCornerShadowPaint);
                c.drawRect(0, edgeShadowTop, bounds.width() - 2 * mCornerRadius, -mCornerRadius, mEdgeShadowPaint);

                // RT
                c.rotate(90f);
                c.translate(0, -bounds.width() + 2 * mCornerRadius);
                c.drawPath(mCornerShadowPath, mCornerShadowPaint);
                c.drawRect(0, edgeShadowTop, bounds.height() - mCornerRadius, -mCornerRadius, mEdgeShadowPaint);

                // LBorder
                c.rotate(180f);
                c.translate(-bounds.height(), -bounds.width() + 2 * mCornerRadius);
                c.drawRect(mCornerRadius, edgeShadowTop, bounds.height(), -mCornerRadius, mEdgeShadowPaint);

            } else {
                if (parent.getAdapter().getItemViewType(position + 1) == UltimateViewAdapter.VIEW_TYPES.HEADER) {
                    bounds.bottom = (int) (bounds.bottom - padding16dp + mPadding);

                    // last item before next header
                    c.rotate(180f);
                    c.translate(-bounds.left - bounds.width() + mCornerRadius, -bounds.top - bounds.height() + mCornerRadius);

                    c.drawPath(mCornerShadowPath, mCornerShadowPaint);
                    c.drawRect(0, edgeShadowTop, bounds.width() - 2 * mCornerRadius, -mCornerRadius, mEdgeShadowPaint);

                    // RT / Right border
                    c.rotate(90f);
                    c.translate(0, -bounds.width() + 2 * mCornerRadius);
                    c.drawPath(mCornerShadowPath, mCornerShadowPaint);
                    c.drawRect(0, edgeShadowTop, bounds.height() - mCornerRadius, -mCornerRadius, mEdgeShadowPaint);

                    // Left border
                    c.rotate(180f);
                    c.translate(-bounds.height(), -bounds.width() + 2 * mCornerRadius);
                    c.drawRect(mCornerRadius, edgeShadowTop, bounds.height(), -mCornerRadius, mEdgeShadowPaint);
                } else {
                    // Right border
                    c.translate(bounds.left, bounds.top);
                    c.rotate(90f);
                    c.translate(0, -bounds.width() + mCornerRadius);
                    c.drawRect(0, edgeShadowTop, bounds.height(), -mCornerRadius, mEdgeShadowPaint);

                    // Left border
                    c.rotate(180f);
                    c.translate(-bounds.height(), -bounds.width() + 2 * mCornerRadius);
                    c.drawRect(0, edgeShadowTop, bounds.height(), -mCornerRadius, mEdgeShadowPaint);
                }
            }
            c.restoreToCount(save);
        }
    }

    private void buildShadowCorners() {
        //mPadding = (float) (Math.sqrt((double) mCornerRadius * (double) mCornerRadius * 2) - (double) mCornerRadius);
        mPadding = 0f;

        RectF innerBounds = new RectF(-mCornerRadius, -mCornerRadius, mCornerRadius, mCornerRadius);
        RectF outerBounds = new RectF(innerBounds);
        outerBounds.inset(-mShadowSize, -mShadowSize);

        if (mCornerShadowPath == null) {
            mCornerShadowPath = new Path();
        } else {
            mCornerShadowPath.reset();
        }
        mCornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
        mCornerShadowPath.moveTo(-mCornerRadius, 0);
        mCornerShadowPath.rLineTo(-mShadowSize, 0);
        // outer arc
        mCornerShadowPath.arcTo(outerBounds, 180f, 90f, false);
        // inner arc
        mCornerShadowPath.arcTo(innerBounds, 270f, -90f, false);
        mCornerShadowPath.close();

        float startRatio = mCornerRadius / (mCornerRadius + mShadowSize);
        mCornerShadowPaint.setShader(new RadialGradient(0, 0, mCornerRadius + mShadowSize, new int[]{
                mShadowStartColor, mShadowStartColor, mShadowEndColor}, new float[]{0f, startRatio, 1f},
                Shader.TileMode.CLAMP));

        // we offset the content shadowSize/2 pixels up to make it more realistic.
        // this is why edge shadow shader has some extra space
        // When drawing bottom edge shadow, we use that extra space.
        mEdgeShadowPaint.setShader(new LinearGradient(0, -mCornerRadius + mShadowSize, 0, -mCornerRadius - mShadowSize,
                new int[]{mShadowStartColor, mShadowStartColor, mShadowEndColor}, new float[]{0f, .5f, 1f},
                Shader.TileMode.CLAMP));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        Resources resources = parent.getContext().getResources();

        float size16dp = 16f;
        int padding16dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size16dp, resources.getDisplayMetrics());

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        int position = params.getViewAdapterPosition();
        int viewType = parent.getAdapter().getItemViewType(position);

        if (viewType == UltimateViewAdapter.VIEW_TYPES.HEADER) {
            // header
            outRect.set(0, (int) (padding16dp), 0, 0);
        } else {
            if (parent.getAdapter().getItemViewType(position + 1) == UltimateViewAdapter.VIEW_TYPES.HEADER) {
                // last item before next header
                outRect.set(0, 0, 0, (int) (padding16dp));
            }
        }
        //outRect.inset((int) size16dp, 0);
        outRect.left = (int) padding16dp;
        outRect.right = (int) padding16dp;
    }
}
