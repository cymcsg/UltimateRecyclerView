package com.ml93.captainmiaoUtil.listener;

/**
 * @author YanLu
 * @since 15/11/1
 */
public interface RefreshRecyclerViewListener {

    /**
     * Called when pull to refresh.
     */
    void onRefresh();

    /**
     * Called when scroll to recyclerView end.
     *
     * @param pagination page at
     * @param pageSize   page size
     */
    void onLoadMore(final int pagination, final int pageSize);
}
