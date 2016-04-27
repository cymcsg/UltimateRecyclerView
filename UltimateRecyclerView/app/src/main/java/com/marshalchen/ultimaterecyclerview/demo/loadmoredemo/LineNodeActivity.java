package com.marshalchen.ultimaterecyclerview.demo.loadmoredemo;

import android.graphics.Color;
import android.os.Handler;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.modules.TimeLineModel;
import com.marshalchen.ultimaterecyclerview.demo.rvComponents.TimeLineAdapter;
import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zJJ on 4/27/2016.
 */
public class LineNodeActivity extends BasicFunctions {
    @Override
    protected void onLoadmore() {

    }

    @Override
    protected void onFireRefresh() {

    }


    public static void insertMoreWhole(easyRegularAdapter sd, int howmany) {
        ArrayList<TimeLineModel> items = new ArrayList<>();
        addAmount(howmany, items);
        /**
         * additional patch for the additional item
         */
        int at = sd.getAdapterItemCount();
        sd.insert(items);
        sd.notifyDataSetChanged();
    }

    protected static void addAmount(int howMany, ArrayList<TimeLineModel> list) {
        for (int i = 0; i < howMany; i++) {
            UUID uuid = UUID.randomUUID();
            TimeLineModel time = new TimeLineModel();
            time.setAge(uuid.variant());
            time.setName(uuid.toString());
            list.add(time);
        }
    }

    @Override
    protected void addButtonTrigger() {
        insertMoreWhole(simpleRecyclerViewAdapter, 3);
    }

    @Override
    protected void removeButtonTrigger() {

    }

    private TimeLineAdapter simpleRecyclerViewAdapter = null;
    private Handler time_count = new Handler();

    @Override
    protected void doURV(UltimateRecyclerView urv) {
        ArrayList<TimeLineModel> list = new ArrayList<>();
        TimeLineModel time = new TimeLineModel();
        time.setAge(139);
        time.setName("England");
        list.add(time);
        TimeLineModel time2 = new TimeLineModel();
        time2.setAge(359);
        time2.setName("Japan");
        list.add(time2);
        TimeLineModel time3 = new TimeLineModel();
        time3.setAge(339);
        time3.setName("HK");
        list.add(time3);
        addAmount(29, list);
        ultimateRecyclerView.setHasFixedSize(true);
        simpleRecyclerViewAdapter = new TimeLineAdapter(list);
        //currently we only support linearlayout option but we have tested anything for the grid layout yet
        configLinearLayoutManager(ultimateRecyclerView);
        enableEmptyViewPolicy();
        // enableLoadMore();
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#ff6f36cf"));
        //enableRefresh();
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
    }
}
