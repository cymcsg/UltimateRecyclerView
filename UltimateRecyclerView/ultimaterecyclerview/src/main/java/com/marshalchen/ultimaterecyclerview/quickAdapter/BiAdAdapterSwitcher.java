package com.marshalchen.ultimaterecyclerview.quickAdapter;


import android.app.Activity;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;


/**
 * this is the simple switchable adapter for easy implementation
 * Created by hesk on 4/8/15.
 */
public class BiAdAdapterSwitcher<
        T,
        B extends UltimateRecyclerviewViewHolder,
        EASY extends easyRegularAdapter<T, B>,
        V extends ViewGroup,
        ADMOB extends simpleAdmobAdapter<T, B, V>> {
    private UltimateRecyclerView listview;
    private EASY noad;
    private ADMOB withad;
    private onLoadMore loading_more;
    private boolean with_the_ad;
    private int page_now = 1;

    public BiAdAdapterSwitcher(UltimateRecyclerView view, EASY adapter_without_ad, ADMOB adapter_with_ad) {
        this.listview = view;
        this.noad = adapter_without_ad;
        this.withad = adapter_with_ad;
    }

    public void init(final boolean adenabled) {
        this.with_the_ad = adenabled;
        this.listview.setAdapter(adenabled ? this.withad : this.noad);
    }

    public interface onLoadMore {
        boolean request_start(int current_page_no, int itemsCount, final int maxLastVisiblePosition, final BiAdAdapterSwitcher this_module);
    }

    private void enableRefreshAndLoadMore() {
        this.listview.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // simpleRecyclerViewAdapter.insert(moreNum++ + "  Refresh things");
                        //   this.listview.setRefreshing(false);
                        // linearLayoutManager.scrollToPosition(0);
                    }
                }, 1000);
            }
        });


    }

    /**
     * will implement more functions later
     *
     * @return switchableadapter object
     */
    public BiAdAdapterSwitcher onEnableRefresh(final int delay_trigger) {
        this.listview.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reset();
                        listview.setRefreshing(false);
                        if (loading_more != null) {
                            final boolean ok = loading_more.request_start(1, 0, 0, BiAdAdapterSwitcher.this);
                            if (ok) {
                                page_now = 1;
                            } else {
                                /** not okay, maybe consider to disable load more. **/
                            }
                        }
                    }
                }, delay_trigger);
            }
        });
        return this;
    }

    private void reset() {
        if (with_the_ad) {
            // withad.notifyItemRangeRemoved(0,withad.getItemCount());
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

    public void load_more_data_zero(final List<T> new_data_list) {
        if (with_the_ad) {
            insert_default(withad, new_data_list);
        } else {
            insert_default(noad, new_data_list);
        }
    }

    public BiAdAdapterSwitcher onEnableLoadmore(final @LayoutRes int layoutResId, final int delay_trigger, final onLoadMore loading_more_trigger_interface) {
        this.loading_more = loading_more_trigger_interface;
        this.listview.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(final int itemsCount, final int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Log.d("loadmore", maxLastVisiblePosition + " position");
                        if (loading_more != null) {
                            final boolean ok = loading_more.request_start(page_now, itemsCount, maxLastVisiblePosition, BiAdAdapterSwitcher.this);
                            if (ok) {
                                page_now++;
                            } else {
                                /** not okay, maybe consider to disable load more. **/
                            }
                        }
                    }
                }, delay_trigger);
            }
        });


        if (with_the_ad) {
            withad.setCustomLoadMoreView(getV(layoutResId));
        } else
            noad.setCustomLoadMoreView(getV(layoutResId));
        this.listview.enableLoadmore();
        return this;
    }

    private View getV(final @LayoutRes int layoutResId) {
        return LayoutInflater.from(this.listview.getContext()).inflate(layoutResId, null);
    }


    private void insert_default(EASY sd, List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            sd.insert(list.get(i));
        }
    }

    private void insert_default(ADMOB sd, List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            sd.insert(list.get(i));
        }
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
