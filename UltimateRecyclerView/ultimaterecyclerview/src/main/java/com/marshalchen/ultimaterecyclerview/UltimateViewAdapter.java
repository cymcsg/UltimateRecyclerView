package com.marshalchen.ultimaterecyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import java.util.Collections;
import java.util.List;

/**
 * An abstract adapter which can be extended for Recyclerview
 */
public abstract class UltimateViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private View customLoadMoreView = null;
//    private View customHeaderView = null;
//
//    public void setCustomHeaderView(View customHeaderView) {
//        this.customHeaderView = customHeaderView;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            if (customLoadMoreView == null) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.bottom_progressbar, parent, false);
                return new ProgressBarViewHolder(v);
            } else {
                return new ProgressBarViewHolder(customLoadMoreView);
            }
        } else if (viewType == 2) {
            if (mHeader != null) return new ProgressBarViewHolder(mHeader);
        }


        return onCreateViewHolder(parent);

    }

    public void setCustomLoadMoreView(View view) {
        customLoadMoreView = view;
    }

    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return 1;
        } else if (position == 0) return 2;
        else
            return super.getItemViewType(position);
    }


    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return getAdapterItemCount() + 1;
    }

    /**
     * Returns the number of items in the adapter bound to the parent RecyclerView.
     *
     * @return The number of items in the bound adapter
     */
    public abstract int getAdapterItemCount();

//    public void insertItem(int position) {
//        insert(position);
//        notifyItemInserted(position);
//    }
//
//    public abstract void insert(int position);


    public void toggleSelection(int pos) {
        notifyItemChanged(pos);
    }


    public void clearSelection(int pos) {
        notifyItemChanged(pos);
    }

    public void setSelected(int pos) {
        notifyItemChanged(pos);
    }


    public void swapPositions(List<?> list, int from, int to) {
        Collections.swap(list, from, to);
    }

    public <T> void insert(List<T> list, T object, int position) {
        list.add(position, object);
        notifyItemInserted(position);
        notifyItemChanged(position + 1);
    }

    public void remove(List<?> list, int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void clear(List<?> list) {
        int size = list.size();
        list.clear();
        notifyItemRangeRemoved(0, size);
    }

    private CustomRelativeWrapper mHeader;
    private int mTotalYScrolled;
    private final float SCROLL_MULTIPLIER = 0.5f;
    private OnParallaxScroll mParallaxScroll;
    public void setParallaxHeader(View header, RecyclerView view) {
        // mRecyclerView = view;
        mHeader = new CustomRelativeWrapper(header.getContext());
        mHeader.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mHeader.addView(header, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mHeader != null) {
                    mTotalYScrolled += dy;
                    translateHeader(mTotalYScrolled);
                }
            }
        });
    }

    public void setOnParallaxScroll(OnParallaxScroll parallaxScroll) {
        mParallaxScroll = parallaxScroll;
        mParallaxScroll.onParallaxScroll(0, 0, mHeader);
    }
    public void translateHeader(float of) {
        float ofCalculated = of * SCROLL_MULTIPLIER;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mHeader.setTranslationY(ofCalculated);
        } else {
            TranslateAnimation anim = new TranslateAnimation(0, 0, ofCalculated, ofCalculated);
            anim.setFillAfter(true);
            anim.setDuration(0);
            mHeader.startAnimation(anim);
        }
        mHeader.setClipY(Math.round(ofCalculated));
        if (mParallaxScroll != null) {
            float left = Math.min(1, ((ofCalculated) / (mHeader.getHeight() * SCROLL_MULTIPLIER)));
            mParallaxScroll.onParallaxScroll(left, of, mHeader);
        }
    }
    public interface OnParallaxScroll {
        /**
         * Event triggered when the parallax is being scrolled.
         *
         * @param percentage
         * @param offset
         * @param parallax
         */
        void onParallaxScroll(float percentage, float offset, View parallax);
    }
    static class CustomRelativeWrapper extends RelativeLayout {

        private int mOffset;

        public CustomRelativeWrapper(Context context) {
            super(context);
        }

        @Override
        protected void dispatchDraw(Canvas canvas) {
            canvas.clipRect(new Rect(getLeft(), getTop(), getRight(), getBottom() + mOffset));
            super.dispatchDraw(canvas);
        }

        public void setClipY(int offset) {
            mOffset = offset;
            invalidate();
        }
    }

    class ProgressBarViewHolder extends RecyclerView.ViewHolder {
        public ProgressBarViewHolder(View itemView) {
            super(itemView);
        }

    }


}
