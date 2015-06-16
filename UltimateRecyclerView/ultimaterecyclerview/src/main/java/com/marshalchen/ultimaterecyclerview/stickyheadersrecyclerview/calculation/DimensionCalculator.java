package com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.calculation;

import android.graphics.Rect;
import android.view.View;

import static android.view.ViewGroup.LayoutParams;
import static android.view.ViewGroup.MarginLayoutParams;

/**
 * Helper to calculate various view dimensions
 */
public class DimensionCalculator {

  /**
   * Returns {@link Rect} representing margins for any view.
   *
   * @param view for which to get margins
   * @return margins for the given view. All 0 if the view does not support margins
   */
  public Rect getMargins(View view) {
    LayoutParams layoutParams = view.getLayoutParams();

    if (layoutParams instanceof MarginLayoutParams) {
      MarginLayoutParams marginLayoutParams = (MarginLayoutParams) layoutParams;
      return getMarginRect(marginLayoutParams);
    } else {
      return new Rect();
    }
  }

  /**
   * Converts {@link MarginLayoutParams} into a representative {@link Rect}
   *
   * @param marginLayoutParams margins to convert to a Rect
   * @return Rect representing margins, where {@link MarginLayoutParams#leftMargin} is equivalent to
   * {@link Rect#left}, etc.
   */
  private Rect getMarginRect(MarginLayoutParams marginLayoutParams) {
    return new Rect(
        marginLayoutParams.leftMargin,
        marginLayoutParams.topMargin,
        marginLayoutParams.rightMargin,
        marginLayoutParams.bottomMargin
    );
  }

}
