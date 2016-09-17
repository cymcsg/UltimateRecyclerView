package com.ml93.captainmiaoUtil.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

/**
 * Created by hesk on 2016/9/15.
 * Custom layout for the Parallax Header.
 */
public class CustomRelativeWrapper extends RelativeLayout {

    private int mOffset;
    private boolean isParallaxHeader;
    private float mScrollMultiplier = 0.5f;

    public CustomRelativeWrapper(Context context) {
        super(context);
        isParallaxHeader = false;
    }

    public void embedView(View content) {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(content, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setParallax(boolean parallax) {
        isParallaxHeader = parallax;
    }

    public final boolean isParallaxHeader() {
        return isParallaxHeader;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (isParallaxHeader) {
            canvas.clipRect(new Rect(getLeft(), getTop(), getRight(), getBottom() + mOffset));
        }
        super.dispatchDraw(canvas);
    }

    public void setClipY(int offset) {
        mOffset = offset;
        invalidate();
    }

    /**
     * Translates the adapter in Y
     *
     * @param of              offset in px
     * @param mParallaxScroll object
     * @param headerView      object
     */
    public void translateHeader(float of, UltimateRecyclerView.OnParallaxScroll mParallaxScroll, final RecyclerView.ViewHolder headerView) {
        float ofCalculated = of * mScrollMultiplier;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && of < getHeight()) {
            setTranslationY(ofCalculated);
        } else if (of < getHeight()) {
            TranslateAnimation anim = new TranslateAnimation(0, 0, ofCalculated, ofCalculated);
            anim.setFillAfter(true);
            anim.setDuration(0);
            startAnimation(anim);
        }
        setClipY(Math.round(ofCalculated));
        if (mParallaxScroll != null) {
            float left;
            if (headerView != null) {
                left = Math.min(1, ((ofCalculated) / (getHeight() * mScrollMultiplier)));
            } else {
                left = 1;
            }
            mParallaxScroll.onParallaxScroll(left, of, this);
        }
    }
}