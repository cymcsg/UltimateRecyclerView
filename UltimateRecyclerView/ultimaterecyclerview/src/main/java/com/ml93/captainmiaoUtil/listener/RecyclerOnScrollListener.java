package com.ml93.captainmiaoUtil.listener;

import android.support.v7.widget.RecyclerView;

/**
 * @author YanLu
 * @since 16/4/23
 */
public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener{

    protected boolean loading = false;
    protected boolean loadMoreEnable = true;

    protected int pageSize = 15;
    protected int pagination = 1;

    public abstract boolean checkCanDoRefresh();
    public abstract void onLoadMore(int pagination, int pageSize);


    public void loadComplete() {
        loading = false;
    }

    public synchronized boolean isLoading() {
        return loading;
    }

    public void setLoadMoreEnable(boolean loadMoreEnable) {
        this.loadMoreEnable = loadMoreEnable;
    }

    public int getPagination() {
        return pagination;
    }

    public void setPagination(int pagination) {
        this.pagination = pagination;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
