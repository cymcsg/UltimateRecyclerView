package com.marshalchen.ultimaterecyclerview.appPaginator;

import android.app.Fragment;
import android.os.Build;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ProgressBar;

import com.marshalchen.ultimaterecyclerview.R;

/**
 * Created by hesk on 15/2/16.
 */
public class paginator extends Fragment {
    private int currentPage, totalPages, pagePerItems, landscape_common_colums = 4, portrait_common_colums = 2;
    private String tag_keyword, fullEndPoint, searchKeyword;
    private boolean enable_load_more, is_new_search;
    protected ProgressBar mProgress;

    protected void getProgressbar(View view, @IdRes final int progress_bar_id) {
        try {
            mProgress = (ProgressBar) view.findViewById(progress_bar_id);
        } catch (Exception e) {
            //unable to find loading progress bar
        }
    }

    protected void showLoadingCircle() {
        if (mProgress != null) {
            mProgress.setVisibility(View.VISIBLE);
            mProgress.animate().alpha(1f);
        }
    }

    protected void hideLoadingCircle() {
        if (mProgress != null && mProgress.getVisibility() == View.VISIBLE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mProgress.animate().alpha(0f).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.setVisibility(View.INVISIBLE);
                    }
                });
            } else {
                mProgress.setVisibility(View.INVISIBLE);
            }
        }
    }

    protected int getItemsShownPerPage() {
        return 16;
    }

    protected void initPaginator() {
        currentPage = 1;
        totalPages = 1;
        pagePerItems = getItemsShownPerPage();
        enable_load_more = false;
        is_new_search = false;
    }


    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getTagKeyword() {
        return tag_keyword;
    }

    public void setTagKeyword(String tag_keyword) {
        this.tag_keyword = tag_keyword;
    }

    public String getFullEndPoint() {
        return fullEndPoint;
    }

    public void setFullEndPoint(String fullEndPoint) {
        this.fullEndPoint = fullEndPoint;
    }

    public boolean isNewSearch() {
        return is_new_search;
    }

    public void setEnablNewSearch(boolean is_new_search) {
        this.is_new_search = is_new_search;
    }

    protected int getCurrentPage() {
        return currentPage;
    }

    protected int getTotalPages() {
        return totalPages;
    }

    protected int getPagePerItems() {
        return pagePerItems;
    }

    protected void setCurrentPage(int n) {
        currentPage = n;
    }

    protected void nextPage() {
        if (currentPage < totalPages) {
            enable_load_more = true;
            currentPage++;
        }
    }

    protected void setTotalPages(int n) {
        totalPages = n;
        if (currentPage >= totalPages) {
            enable_load_more = false;
        } else {
            enable_load_more = true;
        }
    }

    protected boolean shouldEnableLoadMore() {
        return enable_load_more;
    }

    public void triggerNewSearchKeyWord(String word) {
        setEnablNewSearch(true);
        setSearchKeyword(word);
    }

}
