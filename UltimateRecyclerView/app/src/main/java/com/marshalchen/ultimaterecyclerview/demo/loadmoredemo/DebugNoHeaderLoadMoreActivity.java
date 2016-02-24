package com.marshalchen.ultimaterecyclerview.demo.loadmoredemo;

import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.basicdemo.sectionZeroAdapter;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;

import java.util.ArrayList;

/**
 * Created by hesk on 19/2/16.
 */
public class DebugNoHeaderLoadMoreActivity extends BasicFunctions {
    private sectionZeroAdapter simpleRecyclerViewAdapter = null;

    @Override
    protected void enableEmptyViewPolicy() {
        //  ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER_AND_LOARMORE);
        //    ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER);
        ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER_AND_LOARMORE);
    }

    @Override
    protected void onLoadmore() {
        SampleDataboxset.insertMoreWhole(simpleRecyclerViewAdapter, 2);
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
        // enableParallaxHeader();
        enableEmptyViewPolicy();
        enableLoadMore();
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#ff4fcccf"));
        enableRefresh();
        // enableScrollControl();
        // enableSwipe();
        // enableItemClick();
        ultimateRecyclerView.setItemViewCacheSize(simpleRecyclerViewAdapter.getAdditionalItems());

    }

    @Override
    protected void addButtonTrigger() {
        simpleRecyclerViewAdapter.insertLast("++ new Item");
        ultimateRecyclerView.reenableLoadmore();
    }

    @Override
    protected void removeButtonTrigger() {
        simpleRecyclerViewAdapter.removeLast();
    }


}
