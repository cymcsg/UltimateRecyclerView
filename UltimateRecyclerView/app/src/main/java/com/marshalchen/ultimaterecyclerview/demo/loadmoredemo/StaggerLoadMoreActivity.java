package com.marshalchen.ultimaterecyclerview.demo.loadmoredemo;

import android.graphics.Color;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;
import com.marshalchen.ultimaterecyclerview.demo.rvComponents.sectionZeroAdapter;
import com.marshalchen.ultimaterecyclerview.demo.rvComponents.staggerAdapter;

import java.util.ArrayList;

/**
 * Created by hesk on 5/4/16.
 */
public class StaggerLoadMoreActivity extends BasicFunctions {

    staggerAdapter simpleRecyclerViewAdapter = null;

    @Override
    protected void onLoadmore() {
        SampleDataboxset.insertMoreWhole(simpleRecyclerViewAdapter, 7);
    }

    @Override
    protected void enableEmptyViewPolicy() {
        //  ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER_AND_LOARMORE);
        //    ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER);
        ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER_AND_LOARMORE);
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
        simpleRecyclerViewAdapter = new staggerAdapter(new ArrayList<String>());
        //configLinearLayoutManager(ultimateRecyclerView);
        configStaggerLayoutManager(ultimateRecyclerView, simpleRecyclerViewAdapter);
        // enableParallaxHeader();
        enableEmptyViewPolicy();
        enableLoadMore();
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#ff4fcccf"));
        enableRefresh();
        // enableScrollControl();
        // enableSwipe();
        // enableItemClick();
        ultimateRecyclerView.setItemViewCacheSize(simpleRecyclerViewAdapter.getAdditionalItems());


        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
    }


    @Override
    protected void addButtonTrigger() {
        simpleRecyclerViewAdapter.insertLast("++ New Item");
        ultimateRecyclerView.reenableLoadmore();
    }

    @Override
    protected void removeButtonTrigger() {
        simpleRecyclerViewAdapter.removeLast();
    }


}
