package com.marshalchen.ultimaterecyclerview.ui.header;

import android.widget.RelativeLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by hesk on 18/3/16.
 */
public class RecyclerViewHeader extends RelativeLayout {
    private RecyclerView mRecycler;

    private int mDownScroll;
    private int mCurrentScroll;
    private boolean mReversed;
    private boolean mAlreadyAligned;
    private boolean mRecyclerWantsTouchEvent;
    private RecyclerView.ItemDecoration mDecoration;

    /**
     * Inflates layout from <code>xml</code> and encapsulates it with <code>RecyclerViewHeader</code>.
     *
     * @param context   application context.
     * @param layoutRes layout resource to be inflated.
     * @return <code>RecyclerViewHeader</code> view object.
     */
    public static RecyclerViewHeader fromXml(Context context, @LayoutRes int layoutRes) {
        RecyclerViewHeader header = new RecyclerViewHeader(context);
        View.inflate(context, layoutRes, header);
        return header;
    }

    public static RecyclerViewHeader fromXml(Context context, @LayoutRes int layoutRes, RecyclerView.ItemDecoration decoration) {
        RecyclerViewHeader header = new RecyclerViewHeader(context);
        View.inflate(context, layoutRes, header);
        header.setDecor(decoration);
        return header;
    }

    public RecyclerViewHeader(Context context) {
        super(context);
    }

    public RecyclerViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void setDecor(RecyclerView.ItemDecoration decor) {
        mDecoration = decor;
    }

    /**
     * Attaches <code>RecyclerViewHeader</code> to <code>RecyclerView</code>.
     * This method will perform necessary actions to properly align the header within <code>RecyclerView</code>.
     * Be sure that <code>setLayoutManager(...)</code> has been called for <code>RecyclerView</code> before calling this method.
     * Also, if you were planning to use <code>setOnScrollListener(...)</code> method for your <code>RecyclerView</code>, be sure to do it before calling this method.
     *
     * @param recycler <code>RecyclerView</code> to attach <code>RecyclerViewHeader</code> to.
     */
    public void attachTo(RecyclerView recycler) {
        attachTo(recycler, false);
    }

    /**
     * Attaches <code>RecyclerViewHeader</code> to <code>RecyclerView</code>.
     * Be sure that <code>setLayoutManager(...)</code> has been called for <code>RecyclerView</code> before calling this method.
     * Also, if you were planning to use <code>setOnScrollListener(...)</code> method for your <code>RecyclerView</code>, be sure to do it before calling this method.
     *
     * @param recycler             <code>RecyclerView</code> to attach <code>RecyclerViewHeader</code> to.
     * @param headerAlreadyAligned If set to <code>false</code>, method will perform necessary actions to properly align
     *                             the header within <code>RecyclerView</code>. If set to <code>true</code> method will assume,
     *                             that user has already aligned <code>RecyclerViewHeader</code> properly.
     */
    public void attachTo(RecyclerView recycler, boolean headerAlreadyAligned) {
        validateRecycler(recycler, headerAlreadyAligned);

        mRecycler = recycler;
        mAlreadyAligned = headerAlreadyAligned;
        mReversed = isLayoutManagerReversed(recycler);

        setupAlignment(recycler);
        setupHeader(recycler);
    }

    private boolean isLayoutManagerReversed(RecyclerView recycler) {
        boolean reversed = false;
        RecyclerView.LayoutManager manager = recycler.getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            reversed = ((LinearLayoutManager) manager).getReverseLayout();
        } else if (manager instanceof StaggeredGridLayoutManager) {
            reversed = ((StaggeredGridLayoutManager) manager).getReverseLayout();
        }
        return reversed;
    }

    private void setupAlignment(RecyclerView recycler) {
        if (!mAlreadyAligned) {
            //setting alignment of header
            ViewGroup.LayoutParams currentParams = getLayoutParams();
            FrameLayout.LayoutParams newHeaderParams;
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            int gravity = (mReversed ? Gravity.BOTTOM : Gravity.TOP) | Gravity.CENTER_HORIZONTAL;
            if (currentParams != null) {
                newHeaderParams = new FrameLayout.LayoutParams(getLayoutParams()); //to copy all the margins
                newHeaderParams.width = width;
                newHeaderParams.height = height;
                newHeaderParams.gravity = gravity;
            } else {
                newHeaderParams = new FrameLayout.LayoutParams(width, height, gravity);
            }
            RecyclerViewHeader.this.setLayoutParams(newHeaderParams);

            //setting alignment of recycler
            FrameLayout newRootParent = new FrameLayout(recycler.getContext());
            newRootParent.setLayoutParams(recycler.getLayoutParams());
            ViewParent currentParent = recycler.getParent();
            if (currentParent instanceof ViewGroup) {
                int indexWithinParent = ((ViewGroup) currentParent).indexOfChild(recycler);

                ((ViewGroup) currentParent).removeViewAt(indexWithinParent);
                recycler.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                newRootParent.addView(recycler);
                newRootParent.addView(RecyclerViewHeader.this);
                ((ViewGroup) currentParent).addView(newRootParent, indexWithinParent);
            }
        }
    }

    @SuppressLint("NewApi")
    private void setupHeader(final RecyclerView recycler) {
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mCurrentScroll += dy;
                RecyclerViewHeader.this.setTranslationY(-mCurrentScroll);
            }
        });

        RecyclerViewHeader.this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = RecyclerViewHeader.this.getHeight();
                if (height > 0) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        RecyclerViewHeader.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        RecyclerViewHeader.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    if (mAlreadyAligned) {
                        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
                        height += params.topMargin;
                        height += params.bottomMargin;
                    }
                    if (mDecoration == null) {
                        recycler.addItemDecoration(new HeaderItemDecoration(recycler.getLayoutManager(), height, mReversed), 0);
                    } else {
                        recycler.addItemDecoration(mDecoration, 0);
                    }
                }
            }
        });
    }

    private void validateRecycler(RecyclerView recycler, boolean headerAlreadyAligned) {
        RecyclerView.LayoutManager layoutManager = recycler.getLayoutManager();
        if (layoutManager == null) {
            throw new IllegalStateException("Be sure to call RecyclerViewHeader constructor after setting your RecyclerView's LayoutManager.");
        } else if (layoutManager.getClass() != LinearLayoutManager.class    //not using instanceof on purpose
                && layoutManager.getClass() != GridLayoutManager.class
                && !(layoutManager instanceof StaggeredGridLayoutManager)) {
            throw new IllegalArgumentException("Currently RecyclerViewHeader supports only LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager.");
        }

        if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() != LinearLayoutManager.VERTICAL) {
                throw new IllegalArgumentException("Currently RecyclerViewHeader supports only VERTICAL orientation LayoutManagers.");
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (((StaggeredGridLayoutManager) layoutManager).getOrientation() != StaggeredGridLayoutManager.VERTICAL) {
                throw new IllegalArgumentException("Currently RecyclerViewHeader supports only VERTICAL orientation StaggeredGridLayoutManagers.");
            }
        }

        if (!headerAlreadyAligned) {
            ViewParent parent = recycler.getParent();
            if (parent != null &&
                    !(parent instanceof LinearLayout) &&
                    !(parent instanceof FrameLayout) &&
                    !(parent instanceof RelativeLayout)) {
                throw new IllegalStateException("Currently, NOT already aligned RecyclerViewHeader " +
                        "can only be used for RecyclerView with a parent of one of types: LinearLayout, FrameLayout, RelativeLayout.");
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mRecyclerWantsTouchEvent = mRecycler.onInterceptTouchEvent(ev);
        if (mRecyclerWantsTouchEvent && ev.getAction() == MotionEvent.ACTION_DOWN) {
            mDownScroll = mCurrentScroll;
        }
        return mRecyclerWantsTouchEvent || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (mRecyclerWantsTouchEvent) {
            int scrollDiff = mCurrentScroll - mDownScroll;
            MotionEvent recyclerEvent =
                    MotionEvent.obtain(event.getDownTime(), event.getEventTime(), event.getAction(),
                            event.getX(), event.getY() - scrollDiff, event.getMetaState());
            mRecycler.onTouchEvent(recyclerEvent);
            return false;
        }
        return super.onTouchEvent(event);
    }


}
