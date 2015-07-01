package com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.*;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

public class StickyRecyclerHeadersTouchListener implements RecyclerView.OnItemTouchListener {
  private final GestureDetector mTapDetector;
  private final RecyclerView mRecyclerView;
  private final com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration mDecor;
  private OnHeaderClickListener mOnHeaderClickListener;

  public interface OnHeaderClickListener {
    public void onHeaderClick(View header, int position, long headerId);
  }

  public StickyRecyclerHeadersTouchListener(final RecyclerView recyclerView,
      final com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration decor) {
    mTapDetector = new GestureDetector(recyclerView.getContext(), new SingleTapDetector());
    mRecyclerView = recyclerView;
    mDecor = decor;
  }

  public com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter getAdapter() {
    if (mRecyclerView.getAdapter() instanceof com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter) {
      return (com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter) mRecyclerView.getAdapter();
    } else {
      throw new IllegalStateException("A RecyclerView with " +
          StickyRecyclerHeadersTouchListener.class.getSimpleName() +
          " requires a " + StickyRecyclerHeadersAdapter.class.getSimpleName());
    }
  }


  public void setOnHeaderClickListener(OnHeaderClickListener listener) {
    mOnHeaderClickListener = listener;
  }

  @Override
  public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
    return mOnHeaderClickListener != null && mTapDetector.onTouchEvent(e);
  }

  @Override
  public void onTouchEvent(RecyclerView view, MotionEvent e) { /* do nothing? */ }
  @Override
  public void  onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

  }
  private class SingleTapDetector extends GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
      int position = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
      if (position != -1) {
        View headerView = mDecor.getHeaderView(mRecyclerView, position);
        long headerId = getAdapter().getHeaderId(position);
        mOnHeaderClickListener.onHeaderClick(headerView, position, headerId);
        mRecyclerView.playSoundEffect(SoundEffectConstants.CLICK);
        headerView.onTouchEvent(e);
        return true;
      }
      return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
      return true;
    }
  }
}
