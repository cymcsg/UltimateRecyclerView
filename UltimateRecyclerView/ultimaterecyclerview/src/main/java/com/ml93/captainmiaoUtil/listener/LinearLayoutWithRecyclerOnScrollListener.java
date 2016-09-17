package com.ml93.captainmiaoUtil.listener;

/*
 * Copyright (C) 2015 Jorge Castillo Pérez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * modify from https://github.com/JorgeCastilloPrz/Mirage
 */


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Jorge Castillo Pérez
 *
 * modify at 2015/08/23
 */
public abstract class LinearLayoutWithRecyclerOnScrollListener extends RecyclerOnScrollListener {
    public static String TAG = LinearLayoutWithRecyclerOnScrollListener.class.getSimpleName();


    // The minimum amount of items to have below your current scroll position before loading more.
    private int visibleThreshold = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private LinearLayoutManager mLinearLayoutManager;

    public abstract void onLoadMore(int pagination, int pageSize);


    public LinearLayoutWithRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager, int pagination, int pageSize) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.pagination = pagination;
        this.pageSize = pageSize;
    }

    public LinearLayoutWithRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!isLoading()) {
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

            //totalItemCount > visibleItemCount load more
            if (loadMoreEnable && !loading && totalItemCount > visibleItemCount && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached
                loading = true;
                pagination++;
                onLoadMore(pagination, pageSize);
            }
        }
    }


    public boolean checkCanDoRefresh() {
        //fixed https://github.com/captain-miao/RecyclerViewUtils/issues/5
        if(mLinearLayoutManager.getItemCount() == 0) return true;
        int firstVisiblePosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        if(firstVisiblePosition == 0) {
            View firstVisibleView = mLinearLayoutManager.findViewByPosition(firstVisiblePosition);
            int top = firstVisibleView.getTop();
            return top >= 0;
        } else {
            return false;
        }

        //it's also work.(but I can't test enough)
        //since support library 22.1, suggest using ViewCompat.canScrollVertically()
        //but not recyclerView reference
        //return !ViewCompat.canScrollVertically(recyclerView, -1);
    }


}
