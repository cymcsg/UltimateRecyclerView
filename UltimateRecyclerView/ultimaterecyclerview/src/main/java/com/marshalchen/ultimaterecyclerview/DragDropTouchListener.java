/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.marshalchen.ultimaterecyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Implementation of RecyclerView.OnItemTouchListener that allows reordering items in RecyclerView by dragging and dropping.
 * Instance of this class should be added to RecylcerView using {@link RecyclerView#addOnItemTouchListener(RecyclerView.OnItemTouchListener)} method.
 * Use something like this:
 * dragDropTouchListener = new DragDropTouchListener(recyclerView, this) {
 *       Override
 *       protected void onItemSwitch(RecyclerView recyclerView, int from, int to) {
 *           adapter.swapPositions(from, to);
 *           adapter.notifyItemChanged(to);
 *           adapter.notifyItemChanged(from);
 *        Override
 *        protected void onItemDrop(RecyclerView recyclerView, int position) {
 *       }
 *  };
 *  }
 * recyclerView.addOnItemTouchListener(dragDropTouchListener);
 *
 * Actual drag is started by calling {@link #startDrag()} somewhere later, for eg. in long touch listener
 */
public abstract class DragDropTouchListener implements RecyclerView.OnItemTouchListener {
    private static final String LOG_TAG = "DRAG-DROP";
    private static final int MOVE_DURATION = 150;

    private RecyclerView recyclerView;
    private Activity activity;
    private Drawable dragHighlight;
    private DisplayMetrics displayMetrics;

    private final int scrollAmount;
    private int downY = -1;
    private int downX = -1;
    private View mobileView;
    private int mobileViewStartY = -1;
    private int mobileViewCurrentPos = -1;
    private int activePointerId;
    private boolean dragging;
    private boolean enabled = true;


    public DragDropTouchListener(RecyclerView recyclerView, Activity activity) {
        this.recyclerView = recyclerView;
        this.activity = activity;
        this.displayMetrics = recyclerView.getResources().getDisplayMetrics();
        this.scrollAmount = (int) (50 / displayMetrics.density);
        this.dragHighlight = recyclerView.getResources().getDrawable(R.drawable.drag_frame);

    }

    public DragDropTouchListener(RecyclerView recyclerView, Activity activity, Drawable dragHighlight) {
        this(recyclerView, activity);
        this.dragHighlight = dragHighlight;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent event) {
        if (!enabled) return false;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                return down(event);

            case MotionEvent.ACTION_MOVE:
                return dragging && move(event);

            case MotionEvent.ACTION_UP:
                return up(event);

            case MotionEvent.ACTION_CANCEL:
                return cancel(event);

        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent event) {
        if (!dragging) return;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE:
                move(event);
                break;

            case MotionEvent.ACTION_UP:
                up(event);
                break;

            case MotionEvent.ACTION_CANCEL:
                cancel(event);
                break;

        }
    }


    /**
     * Call this to indicate drag start
     */
    public void startDrag() {
        View viewUnder = recyclerView.findChildViewUnder(downX, downY);
        if (viewUnder == null) return;
        dragging = true;

        mobileViewCurrentPos = recyclerView.getChildPosition(viewUnder);

        int[] viewRawCoords = getViewRawCoords(viewUnder);
        mobileView = copyViewAsImage(viewUnder);
        mobileView.setX(viewRawCoords[0]);
        mobileView.setY(viewRawCoords[1]);
        mobileViewStartY = viewRawCoords[1];

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        activity.addContentView(mobileView, lp);
        mobileView.bringToFront();
        viewUnder.setVisibility(View.INVISIBLE);

    }

    private boolean down(MotionEvent event) {
        activePointerId = event.getPointerId(0);
        downY = (int) event.getY();
        downX = (int) event.getX();
        return false;
    }


    private boolean move(MotionEvent event) {
        if (activePointerId == -1) {
            return false;
        }

        int pointerIndex = event.findPointerIndex(activePointerId);
        int currentY = (int) event.getY(pointerIndex);
        int deltaY = currentY - downY;
        int mobileViewY = mobileViewStartY + deltaY;
        mobileView.setY(mobileViewY);

        switchViewsIfNeeded();
        scrollIfNeeded();
        return true;
    }

    private void switchViewsIfNeeded() {
        int pos = mobileViewCurrentPos;
        int abovePos = pos - 1;
        int belowPos = pos + 1;

        View aboveView = getViewByPosition(abovePos);
        View belowView = getViewByPosition(belowPos);

        int mobileViewY = (int) mobileView.getY();

        if (aboveView != null && aboveView.getTop() > -1 && mobileViewY < aboveView.getTop()) {
            Log.d(LOG_TAG, String.format("Got aboveView with top = %s, for position = %s, %s", aboveView.getTop(), abovePos, aboveView));
            doSwitch(aboveView, pos, abovePos);
        }
        if (belowView != null && belowView.getTop() > -1 && mobileViewY > belowView.getTop()) {
            Log.d(LOG_TAG, String.format("Got belowView with top = %s, for position = %s, %s", belowView.getTop(), belowPos, belowView));
            doSwitch(belowView, pos, belowPos);
        }

    }

    private void doSwitch(final View switchView, final int originalViewPos, final int switchViewPos) {
        View originalView = getViewByPosition(originalViewPos);
        int switchViewTop = switchView.getTop();
        int originalViewTop = originalView.getTop();
        int delta = originalViewTop - switchViewTop;

        onItemSwitch(recyclerView, originalViewPos, switchViewPos);

        switchView.setVisibility(View.INVISIBLE);
        originalView.setVisibility(View.VISIBLE);

        originalView.setTranslationY(-delta);
        originalView.animate().translationYBy(delta).setDuration(MOVE_DURATION);

        mobileViewCurrentPos = switchViewPos;

    }

    private boolean up(MotionEvent event) {
        if (dragging) {
            onItemDrop(recyclerView, mobileViewCurrentPos);
        }
        reset();
        return false;
    }

    private boolean cancel(MotionEvent event) {
        reset();
        return false;
    }

    private void reset() {
        //Animate mobile view back to original position
        final View view = getViewByPosition(mobileViewCurrentPos);
        if (view != null && mobileView != null) {
            float y = getViewRawCoords(view)[1];
            mobileView.animate().y(y).setDuration(MOVE_DURATION).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.VISIBLE);
                    if (mobileView != null) {
                        ViewGroup parent = (ViewGroup) mobileView.getParent();
                        parent.removeView(mobileView);
                        mobileView = null;
                    }

                }
            });

        }

        dragging = false;
        mobileViewStartY = -1;
        mobileViewCurrentPos = -1;

    }

    private View getViewByPosition(int position) {
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(position);
        return viewHolder == null ? null : viewHolder.itemView;
    }


    private boolean scrollIfNeeded() {
        int height = recyclerView.getHeight();
        int hoverViewTop = (int) mobileView.getY();
        int hoverHeight = mobileView.getHeight();

        if (hoverViewTop <= 0) {
            recyclerView.scrollBy(0, -scrollAmount);
            return true;
        }

        if (hoverViewTop + hoverHeight >= height) {
            recyclerView.scrollBy(0, scrollAmount);
            return true;
        }

        return false;
    }


    //Creates screenshot of a view
    private ImageView copyViewAsImage(View v) {
        //Clear ripple effect to not get into screenshot,
        // need something more clever here
        if (v instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) v;
            Drawable foreground = frameLayout.getForeground();
            if (foreground != null) foreground.setVisible(false, false);
        } else {
            if (v.getBackground() != null) v.getBackground().setVisible(false, false);
        }


        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);

        //Drag highlight, usually border
        if (dragHighlight != null) {
            dragHighlight.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            dragHighlight.draw(canvas);
        }

        ImageView imageView = new ImageView(recyclerView.getContext());
        imageView.setImageBitmap(bitmap);
        return imageView;
    }


    private int[] getViewRawCoords(View locateView) {
        View globalView = activity.findViewById(android.R.id.content);
        int topOffset = displayMetrics.heightPixels - globalView.getMeasuredHeight();
        int[] loc = new int[2];
        locateView.getLocationOnScreen(loc);
        loc[1] = loc[1] - topOffset;
        return loc;
    }


    /**
     * Enable/disable drag/drop
     *
     * @param enabled     na
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Implementation usually do 2 things: change positions of items in RecyclerView.Adapter and notify it about changes
     *
     * @param recyclerView view the item is being dragged in
     * @param from         original (start) drag position within adapter
     * @param to           new drag position withing adapter
     */
    protected abstract void onItemSwitch(RecyclerView recyclerView, int from, int to);

    /**
     * Item is dropped at given position
     *
     * @param recyclerView view the item is being dropped in
     * @param position     position of a drop within adapter
     */
    protected abstract void onItemDrop(RecyclerView recyclerView, int position);


    public void setCustomDragHighlight(Drawable dragHighlight) {
        this.dragHighlight = dragHighlight;
    }
}
