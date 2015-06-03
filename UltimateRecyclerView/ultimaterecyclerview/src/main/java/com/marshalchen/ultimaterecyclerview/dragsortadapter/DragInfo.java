package com.marshalchen.ultimaterecyclerview.dragsortadapter;

import android.graphics.Point;
import android.graphics.PointF;

public class DragInfo {
  private final long itemId;
  private final Point shadowSize;
  private final Point shadowTouchPoint;
  private final PointF dragPoint;

  public DragInfo(long itemId, Point shadowSize, Point shadowTouchPoint, PointF dragPoint) {
    this.itemId = itemId;
    this.shadowSize = new Point(shadowSize);
    this.shadowTouchPoint = new Point(shadowTouchPoint);
    this.dragPoint = dragPoint;
  }

  public  long itemId() {
    return itemId;
  }

  public boolean shouldScrollLeft() {
    return dragPoint.x < shadowTouchPoint.x;
  }

  public boolean shouldScrollRight(int parentWidth) {
    return dragPoint.x > (parentWidth - (shadowSize.x - shadowTouchPoint.x));
  }

  public boolean shouldScrollUp() {
    return dragPoint.y < shadowTouchPoint.y;
  }

  public boolean shouldScrollDown(int parentHeight) {
    return dragPoint.y > (parentHeight - (shadowSize.y - shadowTouchPoint.y));
  }

  public void setDragPoint(float x, float y) {
    dragPoint.set(x, y);
  }
}
