package com.marshalchen.ultimaterecyclerview.quickAdapter;


import android.app.Activity;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.layoutmanagers.ScrollSmoothLineaerLayoutManager;

import java.util.List;


/**
 * this is the simple switchable adapter for easy implementation
 * Created by hesk on 4/8/15.
 */
public class BiAdAdapterSwitcher<
        //DATA TYPE
        T,
        //The view holder
        B extends AdItemHolder,
        //THE regular adapter
        EASY extends easyRegularAdapter<T, B>,
        //THE Viewgroup from the google Adview
        V extends ViewGroup,
        //the admobdapter
        ADMOB extends AdmobAdapter<V,T,B>>

{
    protected UltimateRecyclerView listview;
    protected EASY noad;
    protected ADMOB withad;
    protected onLoadMore loading_more;
    protected boolean with_the_ad, auto_disable_loadmore = false;
    protected int page_now = 1, max_pages = 3, layoutLoadMoreResId = 0, container_data_items;
    protected RecyclerView.LayoutManager mManager;

    public void setMaxPages(final int n) {
        max_pages = n;
    }

    public BiAdAdapterSwitcher(UltimateRecyclerView view, EASY adapter_without_ad, ADMOB adapter_with_ad) {
        this.listview = view;
        this.noad = adapter_without_ad;
        this.withad = adapter_with_ad;
        this.mManager = view.getLayoutManager();
    }

    public BiAdAdapterSwitcher EnableAutoDisableLoadMoreByMaxPages() {
        auto_disable_loadmore = true;
        return this;
    }

    public void init(final boolean adenabled) {
        this.with_the_ad = adenabled;
      /*  if (layoutLoadMoreResId != 0) {
            listview.enableLoadmore();
        }
        */
        listview.setAdapter(adenabled ? this.withad : this.noad);
        emptyViewControl();
    }


    protected void emptyViewControl() {
        container_data_items = with_the_ad ? withad.getItemCount() : noad.getItemCount();
        if (container_data_items > 0) {
            listview.hideEmptyView();
        } else {
            listview.showEmptyView();
        }
    }

    public void scrollToTop() {
        if (mManager == null) mManager = listview.getLayoutManager();
        if (mManager == null) return;

        if (mManager instanceof LinearLayoutManager) {
            LinearLayoutManager mLayoutManager = (LinearLayoutManager) mManager;
            if (mLayoutManager.getStackFromEnd() && mLayoutManager.canScrollVertically()) {
                mLayoutManager.scrollToPosition(1);
            }
        } else if (mManager instanceof ScrollSmoothLineaerLayoutManager) {
            LinearLayoutManager mLayoutManager = (ScrollSmoothLineaerLayoutManager) mManager;
            if (mLayoutManager.getStackFromEnd() && mLayoutManager.canScrollVertically()) {
                mLayoutManager.scrollToPosition(1);
            }
        }
    }

    public interface onLoadMore {
        boolean request_start(int current_page_no, int itemsCount, final int maxLastVisiblePosition, final BiAdAdapterSwitcher this_module, final boolean onRefresh);
    }

    protected Runnable refresh_default = new Runnable() {
        @Override
        public void run() {
            reset();
            if (loading_more != null) {
                final boolean success_onStart = loading_more.request_start(1, 0, 0, BiAdAdapterSwitcher.this, true);
                if (success_onStart) {
                    page_now = 2;
                    max_pages = 3;
                    //notifyDataSetChanged();

                } else {
                    if (auto_disable_loadmore) listview.disableLoadmore();
                    /** not okay, maybe consider to disable load more. **/
                }
            }
            listview.setRefreshing(false);
        }
    };

    protected BiAdAdapterSwitcher setCustomOnFresh(Runnable h) {
        this.refresh_default = h;
        return this;
    }

    /**
     * will implement more functions later
     *
     * @param delay_trigger na
     * @return switchableadapter object
     */

    public BiAdAdapterSwitcher onEnableRefresh(final int delay_trigger) {
        listview.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(refresh_default, delay_trigger);
            }
        });
        return this;
    }

    public void removeALL() {
        reset();
    }

    /**
     * once it is called, the list will restart from the zero scroll
     */
    public void notifyDataSetChanged() {
        if (with_the_ad) {
            withad.notifyDataSetChanged();
        } else {
            noad.notifyDataSetChanged();
        }
    }

    private void reset() {
        if (with_the_ad) {
            withad.removeAll();
        } else {
            noad.removeAll();
        }


    }

    public void load_more_data(final List<T> new_data_list) {
        if (with_the_ad) {
            insert_default(withad, new_data_list);
        } else {
            insert_default(noad, new_data_list);
        }
    }

    public void load_more_data_at_zero(final List<T> new_data_list) {
        if (with_the_ad) {
            insert_default(withad, new_data_list);
        } else {
            insert_default(noad, new_data_list);
        }
    }

    public BiAdAdapterSwitcher onEnableLoadmore(
            final @LayoutRes int layoutResId,
            final int delay_trigger,
            final onLoadMore loading_more_trigger_interface) {
        loading_more = loading_more_trigger_interface;
      /*  listview.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(final int itemsCount, final int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Log.d("loadmore", maxLastVisiblePosition + " position");
                        if (loading_more != null) {
                            final boolean request_success = loading_more.request_start(page_now, itemsCount, maxLastVisiblePosition, BiAdAdapterSwitcher.this, false);

                            if (request_success && page_now < max_pages) {

                                if (auto_disable_loadmore && !listview.isLoadMoreEnabled()) {
                                    listview.reenableLoadmore();
                                }

                                page_now++;
                            } else {
                                Log.d("loadmore", "request failed position");
                                if (auto_disable_loadmore) listview.disableLoadmore();
                            }
                        }
                    }
                }, delay_trigger);
            }
        });*/
        this.layoutLoadMoreResId = layoutResId;
        return this;
    }

    private View getV(final @LayoutRes int layoutResId) {
        return LayoutInflater.from(listview.getContext()).inflate(layoutResId, null);
    }


    private void insert_default(EASY sd, List<T> list) {
        sd.insert(list);
    }

    private void insert_default(ADMOB sd, List<T> list) {
        sd.insert(list);
    }

    public static <V extends ViewGroup> void maximum_size(LinearLayout l, V suppose_tobe_Adview, Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        double ratio = ((float) (width)) / 300.0;
        int height = (int) (ratio * 50);
        suppose_tobe_Adview.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, height));
        l.addView(suppose_tobe_Adview);

    }
}
