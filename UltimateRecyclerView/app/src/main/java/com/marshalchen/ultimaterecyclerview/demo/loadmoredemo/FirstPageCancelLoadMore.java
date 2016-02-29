package com.marshalchen.ultimaterecyclerview.demo.loadmoredemo;

import android.graphics.Color;
import android.os.Handler;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.basicdemo.sectionZeroAdapter;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;

import java.util.ArrayList;

/**
 * Created by hesk on 25/2/16.
 */
public class FirstPageCancelLoadMore extends BasicFunctions {
    private sectionZeroAdapter simpleRecyclerViewAdapter = null;
    private Handler time_count = new Handler();

    @Override
    protected void enableEmptyViewPolicy() {
        //  ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER_AND_LOARMORE);
        //    ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER);
        ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER_AND_LOARMORE);
    }

    @Override
    protected void onLoadmore() {
        time_count.postDelayed(new Runnable() {
            @Override
            public void run() {
                SampleDataboxset.insertMoreWhole(simpleRecyclerViewAdapter, 2);
                ultimateRecyclerView.disableLoadmore();
            }
        }, 1000);
    }

    @Override
    protected void onFireRefresh() {
        // simpleRecyclerViewAdapter.insertLast(moreNum++ + "  Refresh things");
        ultimateRecyclerView.setRefreshing(false);
        //   ultimateRecyclerView.scrollBy(0, -50);
        linearLayoutManager.scrollToPosition(0);
        //ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
        //simpleRecyclerViewAdapter.notifyDataSetChanged();
        simpleRecyclerViewAdapter.removeAll();
        ultimateRecyclerView.disableLoadmore();
        ultimateRecyclerView.showEmptyView();
    }

    @Override
    protected void doURV(UltimateRecyclerView urv) {
        ultimateRecyclerView.setHasFixedSize(false);
        simpleRecyclerViewAdapter = new sectionZeroAdapter(new ArrayList<String>());
        ultimateRecyclerView.setLayoutManager(setupLinearLayoutMgr());
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
        enableEmptyViewPolicy();
        enableLoadMore();
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#ff4fcccf"));
        enableRefresh();
    }

    @Override
    protected void addButtonTrigger() {
        SampleDataboxset.insertMoreWhole(simpleRecyclerViewAdapter, 3);
        ultimateRecyclerView.reenableLoadmore();
    }

    @Override
    protected void removeButtonTrigger() {
        simpleRecyclerViewAdapter.removeAll();
    }

    @Override
    protected void toggleButtonTrigger() {
        if (!status_progress) {
            isEnableAutoLoadMore = !isEnableAutoLoadMore;
            if (isEnableAutoLoadMore) {
                ultimateRecyclerView.reenableLoadmore();
            }
        }
    }
}
