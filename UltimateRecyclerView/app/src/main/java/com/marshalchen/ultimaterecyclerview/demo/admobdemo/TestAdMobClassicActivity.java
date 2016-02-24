package com.marshalchen.ultimaterecyclerview.demo.admobdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.modules.FastBinding;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;

import java.util.ArrayList;

/**
 * Created by hesk on 20/5/15.
 */
public class TestAdMobClassicActivity extends AppCompatActivity {

    private UltimateRecyclerView ultimateRecyclerView;
    private ZeroStickyAdvertistmentAdapter simpleRecyclerViewAdapter = null;
    private LinearLayoutManager linearLayoutManager;
    private int moreNum = 2;
    private ActionMode actionMode;

    private Toolbar toolbar;
    private boolean isDrag = true;

    private boolean admob_test_mode = false;

    private AdView createadmob() {
        AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        mAdView.setAdUnitId("/1015938/Hypebeast_App_320x50");
        mAdView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // Create an ad request.
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        if (admob_test_mode)
            // Optionally populate the ad request builder.
            adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        // Start loading the ad.
        mAdView.loadAd(adRequestBuilder.build());
        return mAdView;
    }

    private void enableSwipe() {


    }

    private void enableRefresh() {
        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRecyclerViewAdapter.removeAll();
                        ultimateRecyclerView.setRefreshing(false);
                    }
                }, 1000);
            }
        });

    }

    private void enableLoadMore() {
        ultimateRecyclerView.setLoadMoreView(R.layout.custom_bottom_progressbar);
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(final int itemsCount, final int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ArrayList<String> li = new ArrayList<String>();
                        li.add("No. ====" + itemsCount + "=======");
                        SampleDataboxset.genItems(10, li);
                        simpleRecyclerViewAdapter.insert(li);
                    }
                }, 1000);
            }
        });
    }

    private void enableEmptyView() {
        ultimateRecyclerView.setEmptyView(
                R.layout.empty_view,
                UltimateRecyclerView.EMPTY_CLEAR_ALL,
                UltimateRecyclerView.STARTWITH_ONLINE_ITEMS);
    }

    private void enableClick() {


    }

    private void impleAddDrop() {
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleRecyclerViewAdapter.insert(SampleDataboxset.newListFromGen(2));
            }
        });
        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleRecyclerViewAdapter.removeAt(3);
            }
        });
        findViewById(R.id.toggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleRecyclerViewAdapter.removeAll();
                ultimateRecyclerView.showEmptyView();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ultimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.ultimate_recycler_view);
        ultimateRecyclerView.setHasFixedSize(false);

        /**
         * wokring example 1 implementation of Admob banner with static Adview
         */
        //  simpleRecyclerViewAdapter = new admobdfpadapter(createadmob(), 5, stringList);
        /**
         * working example 2 with multiple called Adviews
         */
        simpleRecyclerViewAdapter = new ZeroStickyAdvertistmentAdapter(createadmob(), SampleDataboxset.newListFromGen());
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
        ultimateRecyclerView.setItemViewCacheSize(3);
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#f5f5ff"));
        enableEmptyView();
        enableRefresh();
        enableLoadMore();
        enableClick();
        impleAddDrop();
    }

    private void toggleSelection(int position) {
        simpleRecyclerViewAdapter.toggleSelection(position);
        actionMode.setTitle("Selected " + "1");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FastBinding.startactivity(this, item.getItemId());
        return super.onOptionsItemSelected(item);
    }

}
