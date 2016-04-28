package com.marshalchen.ultimaterecyclerview.ui.timelineview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.R;

/**
 * Created by zJJ on 4/27/2016.
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class TimelineView extends View {

    private Drawable mMarker;
    private Drawable mStartLine, mActionStartLine;
    private Drawable mEndLine, mActionEndLine;
    private int mMarkerSize;
    private int mLineSize;

    private Rect mBounds;
    private Context mContext;


    public TimelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.timeline_style);
        mMarker = typedArray.getDrawable(R.styleable.timeline_style_tls_marker);
        mStartLine = typedArray.getDrawable(R.styleable.timeline_style_tls_line);
        mEndLine = typedArray.getDrawable(R.styleable.timeline_style_tls_line);
        mMarkerSize = typedArray.getDimensionPixelSize(R.styleable.timeline_style_tls_marker_size, 25);
        mLineSize = typedArray.getDimensionPixelSize(R.styleable.timeline_style_tls_line_size, 2);
        typedArray.recycle();

        if (mMarker == null) {
            mMarker = ContextCompat.getDrawable(mContext, R.drawable.timelinedefaultmarker);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Width measurements of the width and height and the inside view of child controls
        int w = mMarkerSize + getPaddingLeft() + getPaddingRight();
        int h = mMarkerSize + getPaddingTop() + getPaddingBottom();

        // Width and height to determine the final view through a systematic approach to decision-making
        int widthSize = 0;
        int heightSize = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
            heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);
        }
        setMeasuredDimension(widthSize, heightSize);
        initDrawable();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // When the view is displayed when the callback
        // Positioning Drawable coordinates, then draw
        initDrawable();
    }

    private void initDrawable() {
        int pLeft = getPaddingLeft();
        int pRight = getPaddingRight();
        int pTop = getPaddingTop();
        int pBottom = getPaddingBottom();

        int width = getWidth();// Width of current custom view
        int height = getHeight();

        int cWidth = width - pLeft - pRight;// Circle width
        int cHeight = height - pTop - pBottom;

        int markSize = Math.min(mMarkerSize, Math.min(cWidth, cHeight));

        if (mMarker != null) {
            mMarker.setBounds(pLeft, pTop, pLeft + markSize, pTop + markSize);
            mBounds = mMarker.getBounds();
        }

        int centerX = mBounds.centerX();
        int lineLeft = centerX - (mLineSize >> 1);
        if (mActionStartLine != null) {
            mActionStartLine.setBounds(lineLeft, 0, mLineSize + lineLeft, mBounds.top);
        }

        if (mActionEndLine != null) {
            mActionEndLine.setBounds(lineLeft, mBounds.bottom, mLineSize + lineLeft, height);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMarker != null) {
            mMarker.draw(canvas);
        }
        if (mActionStartLine != null) {
            mActionStartLine.draw(canvas);
        }
        if (mActionEndLine != null) {
            mActionEndLine.draw(canvas);
        }
    }

    public void setMarker(Drawable marker) {
        mMarker = marker;
        initDrawable();
    }

    public void setStartLine(Drawable startline) {
        mStartLine = startline;
        initDrawable();
    }

    public void setEndLine(Drawable endLine) {
        mEndLine = endLine;
        initDrawable();
    }

    public void setMarkerSize(int markerSize) {
        mMarkerSize = markerSize;
        initDrawable();
    }

    public void setLineSize(int lineSize) {
        mLineSize = lineSize;
        initDrawable();
    }

    public void initLine(int viewType) {
        mActionStartLine = mStartLine;
        mActionEndLine = mEndLine;
        if (viewType == LineType.BEGIN) {
            //  setStartLine(null);
            mActionStartLine = null;
        } else if (viewType == LineType.END) {
            // setEndLine(null);
            mActionEndLine = null;
        } else if (viewType == LineType.ONLYONE) {
            // setStartLine(null);
            // setEndLine(null);
            mActionStartLine = null;
            mActionEndLine = null;
        }

        initDrawable();
        requestLayout();
    }

    public static int getTimeLineViewType(int position, int total_size) {
        if (total_size == 1) {
            return LineType.ONLYONE;
        } else if (position == 0) {
            return LineType.BEGIN;
        } else if (position == total_size - 1) {
            return LineType.END;
        } else {
            return LineType.NORMAL;
        }
    }
}
