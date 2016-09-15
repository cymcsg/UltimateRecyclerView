package com.marshalchen.ultimaterecyclerview.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.widget.RelativeLayout;

/**
 * Created by hesk on 2016/9/15.
 * Custom layout for the Parallax Header.
 */
public class CustomRelativeWrapper extends RelativeLayout {

    private int mOffset;
    private boolean isParallaxHeader;

    public CustomRelativeWrapper(Context context) {
        super(context);
        isParallaxHeader = false;
    }

    public void setParallax(boolean parallax) {
        isParallaxHeader = parallax;
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
}