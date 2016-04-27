package com.marshalchen.ultimaterecyclerview.demo.loadmoredemo;

import android.graphics.Color;
import android.view.LayoutInflater;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.rvComponents.sectionZeroAdapter;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;

import java.util.ArrayList;

/**
 * Created by hesk on 19/2/16.
 */
public class DebugNoHeaderLoadMoreActivity extends BasicFunctions {
    private sectionZeroAdapter simpleRecyclerViewAdapter = null;

    @Override
    protected void enableEmptyViewPolicy() {
        ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER_AND_LOARMORE);
        //    ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER);
        //   ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER_AND_LOARMORE);


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
        //  linearLayoutManager.scrollToPosition(0);
        ultimateRecyclerView.scrollVerticallyTo(0);
        //ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
        //simpleRecyclerViewAdapter.notifyDataSetChanged();
        simpleRecyclerViewAdapter.removeAll();
        ultimateRecyclerView.disableLoadmore();
        ultimateRecyclerView.showEmptyView();
    }

    @Override
    protected void doURV(UltimateRecyclerView ultimateRecyclerView) {
        //  ultimateRecyclerView.setInflater(LayoutInflater.from(getApplicationContext()));
        ultimateRecyclerView.setHasFixedSize(false);
        ArrayList<String> list = new ArrayList<>();
        list.add("o2fn31");
        list.add("of2n32");
        list.add("of3n36");
        simpleRecyclerViewAdapter = new sectionZeroAdapter(list);
        configLinearLayoutManager(ultimateRecyclerView);
        //enableParallaxHeader();
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
        simpleRecyclerViewAdapter.insertLast("++ new Item");
        ultimateRecyclerView.reenableLoadmore();
    }

    @Override
    protected void removeButtonTrigger() {
        simpleRecyclerViewAdapter.removeLast();
    }


}
