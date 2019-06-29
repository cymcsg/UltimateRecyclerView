package com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.util;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Interface for getting the orientation of a RecyclerView from its LayoutManager
 */
public interface OrientationProvider {

  public int getOrientation(RecyclerView recyclerView);

}
