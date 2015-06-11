package com.marshalchen.ultimaterecyclerview.dragsortadapter;

import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.View;
import java.lang.ref.WeakReference;

import static java.lang.Float.MIN_VALUE;

public class DragManager implements View.OnDragListener {

  private final WeakReference<RecyclerView> recyclerViewRef;
  private final DragSortAdapter<?> adapter;
  private long draggingId = RecyclerView.NO_ID;
  private final PointF nextMoveTouchPoint = new PointF(MIN_VALUE, MIN_VALUE);
  @Nullable private DragInfo lastDragInfo;

  public DragManager(RecyclerView recyclerView, DragSortAdapter<?> adapter) {
    this.recyclerViewRef = new WeakReference<>(recyclerView);
    this.adapter = adapter;
  }

  @Override public boolean onDrag(View v, DragEvent event) {
    if (v != recyclerViewRef.get() || !(event.getLocalState() instanceof DragInfo)) {
      return false;
    }
    final RecyclerView recyclerView = (RecyclerView) v;
    final DragInfo dragInfo = (DragInfo) event.getLocalState();
    final long itemId = dragInfo.itemId();

    switch (event.getAction()) {
      case DragEvent.ACTION_DRAG_STARTED:
        draggingId = itemId;
          adapter.notifyItemChanged(recyclerView.findViewHolderForItemId(itemId).getAdapterPosition());
        break;

      case DragEvent.ACTION_DRAG_LOCATION:
        float x = event.getX();
        float y = event.getY();

        int fromPosition = adapter.getPositionForId(itemId);
        int toPosition = -1;

        View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
        if (child != null) {
          toPosition = recyclerView.getChildViewHolder(child).getAdapterPosition();
        }

        if (toPosition >= 0 && fromPosition != toPosition) {
          RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();

          boolean scheduleNextMove = nextMoveTouchPoint.equals(MIN_VALUE, MIN_VALUE);
          nextMoveTouchPoint.set(x, y);

          if (scheduleNextMove)
            animator.isRunning(new RecyclerView.ItemAnimator.ItemAnimatorFinishedListener() {
              @Override public void onAnimationsFinished() {
                if (nextMoveTouchPoint.equals(MIN_VALUE, MIN_VALUE)) { return; }

                final int fromPosition = adapter.getPositionForId(itemId);

                View child = recyclerView
                    .findChildViewUnder(nextMoveTouchPoint.x, nextMoveTouchPoint.y);
                if (child != null) {
                  final int toPosition =
                      recyclerView.getChildViewHolder(child).getAdapterPosition();
                  if (adapter.move(fromPosition, toPosition)) {

                    if (fromPosition == 0 || toPosition == 0) {
                      // fix for weird scrolling when animating first item
                      recyclerView.scrollToPosition(0);
                    }

                    recyclerView.post(new Runnable() {
                      @Override public void run() {
                        adapter.notifyItemMoved(fromPosition, toPosition);
                      }
                    });
                  }
                }

                // reset so we know to schedule listener again next time
                clearNextMove();
              }
            });
        }

        lastDragInfo = dragInfo;
        lastDragInfo.setDragPoint(x, y);
        adapter.handleDragScroll(recyclerView, dragInfo);
        break;

      case DragEvent.ACTION_DRAG_ENDED:
        draggingId = RecyclerView.NO_ID;
        lastDragInfo = null;

        // queue up the show animation until after all move animations are finished
        recyclerView.getItemAnimator().isRunning(
            new RecyclerView.ItemAnimator.ItemAnimatorFinishedListener() {
              @Override public void onAnimationsFinished() {
                int position = adapter.getPositionForId(itemId);

                RecyclerView.ViewHolder vh = recyclerView.findViewHolderForItemId(itemId);
                if (vh != null && vh.getAdapterPosition() != position) {
                  // if positions don't match, there's still an outstanding move animation
                  // so we try to reschedule the notifyItemChanged until after that
                  recyclerView.post(new Runnable() {
                    @Override public void run() {
                      recyclerView.getItemAnimator().isRunning(
                          new RecyclerView.ItemAnimator.ItemAnimatorFinishedListener() {
                            @Override public void onAnimationsFinished() {
                              adapter.notifyItemChanged(adapter.getPositionForId(itemId));
                            }
                          });
                    }
                  });
                } else {
                  adapter.notifyItemChanged(adapter.getPositionForId(itemId));
                }
              }
            });
        break;

      case DragEvent.ACTION_DROP:
        adapter.onDrop();
        break;

      case DragEvent.ACTION_DRAG_ENTERED:
        // probably not used?
        break;
      case DragEvent.ACTION_DRAG_EXITED:
        // TODO edge scrolling
        break;
    }
    return true;
  }

  public void clearNextMove() {
    nextMoveTouchPoint.set(MIN_VALUE, MIN_VALUE);
  }

 public  long getDraggingId() { return draggingId; }

  @Nullable public DragInfo getLastDragInfo() { return lastDragInfo; }
}
