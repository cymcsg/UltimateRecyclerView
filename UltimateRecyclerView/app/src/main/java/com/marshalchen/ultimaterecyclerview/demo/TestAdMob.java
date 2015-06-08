package com.marshalchen.ultimaterecyclerview.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.marshalchen.ultimaterecyclerview.AdmobAdapter;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.modules.admobdfpadapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 20/5/15.
 */
public class TestAdMob extends AppCompatActivity {

    UltimateRecyclerView ultimateRecyclerView;
    admobdfpadapter simpleRecyclerViewAdapter = null;
    LinearLayoutManager linearLayoutManager;
    int moreNum = 2;
    private ActionMode actionMode;

    Toolbar toolbar;
    boolean isDrag = true;

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

    private void enableRefreshAndLoadMore() {
        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRecyclerViewAdapter.insert(moreNum++ + "  Refresh things", 0);
                        ultimateRecyclerView.setRefreshing(false);
                        //   ultimateRecyclerView.scrollBy(0, -50);
                        linearLayoutManager.scrollToPosition(0);
//                        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
//                        simpleRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());


                    }
                }, 1000);
            }
        });

    }

    private void enableClick() {


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
        final List<String> stringList = new ArrayList<>();

        stringList.add("111");
        stringList.add("aaa");
        stringList.add("222");
        stringList.add("33");
        stringList.add("44");
        stringList.add("55");
        stringList.add("66");
        stringList.add("11771");
        stringList.add("a33");
        stringList.add("a44");
        stringList.add("a55");
        stringList.add("a66");
        stringList.add("a11771");
        stringList.add("g33");
        stringList.add("gb44");
        stringList.add("n55");
        stringList.add("n66");
        stringList.add("e11771");

        /**
         * wokring example 1 implementation of Admob banner with static Adview
         */
        //  simpleRecyclerViewAdapter = new admobdfpadapter(createadmob(), 5, stringList);
        /**
         * working example 2 with multiple called Adviews
         */
        simpleRecyclerViewAdapter = new admobdfpadapter(createadmob(), 5, stringList, new AdmobAdapter.AdviewListener() {
            @Override
            public AdView onGenerateAdview() {
                return createadmob();
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);

        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#ffffff"));
        enableRefreshAndLoadMore();
        enableClick();
        // enableSwipe();

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //simpleRecyclerViewAdapter.insert("newly added item", 1);


                stringList.add("a55");
                stringList.add("a66");
                stringList.add("a11771");
                stringList.add("g33");
                stringList.add("gb44");
                stringList.add("n55");
                stringList.add("n66");
                stringList.add("e11771");


                simpleRecyclerViewAdapter.notifyDataSetChanged();

            }
        });

        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // simpleRecyclerViewAdapter.remove(1);
            }
        });

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


}
