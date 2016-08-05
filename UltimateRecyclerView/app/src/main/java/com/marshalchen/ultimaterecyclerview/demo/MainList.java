package com.marshalchen.ultimaterecyclerview.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.marshalchen.ultimaterecyclerview.demo.admobdemo.TestAdMobClassicActivity;
import com.marshalchen.ultimaterecyclerview.demo.admobdemo.TestAdvancedAdmobActivity;
import com.marshalchen.ultimaterecyclerview.demo.dragdemo.DragActivity;
import com.marshalchen.ultimaterecyclerview.demo.expandemo.TestExpandableRV;
import com.marshalchen.ultimaterecyclerview.demo.griddemo.GridLayoutRVTest;
import com.marshalchen.ultimaterecyclerview.demo.griddemo.GridTestOnlyOnePage;
import com.marshalchen.ultimaterecyclerview.demo.loadmoredemo.DebugLoadMoreActivity;
import com.marshalchen.ultimaterecyclerview.demo.loadmoredemo.DebugNoHeaderLoadMoreActivity;
import com.marshalchen.ultimaterecyclerview.demo.loadmoredemo.FinalEmptyViewDisplayActivity;
import com.marshalchen.ultimaterecyclerview.demo.loadmoredemo.FirstPageCancelLoadMore;
import com.marshalchen.ultimaterecyclerview.demo.loadmoredemo.LineNodeActivity;
import com.marshalchen.ultimaterecyclerview.demo.loadmoredemo.PullToRefreshActivity;
import com.marshalchen.ultimaterecyclerview.demo.loadmoredemo.SliderHeader;
import com.marshalchen.ultimaterecyclerview.demo.loadmoredemo.StaggerLoadMoreActivity;
import com.marshalchen.ultimaterecyclerview.demo.loadmoredemo.SwipeListViewExampleActivity;
import com.marshalchen.ultimaterecyclerview.demo.multiitemdemo.MultiViewTypesActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by hesk on 23/5/16.
 */
public class MainList extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView mList;
    LinkedHashMap<String, Class> data = new LinkedHashMap<>();
    ArrayList<Class> o = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = (ListView) findViewById(android.R.id.list);
        ArrayList<String> items = new ArrayList<>();
        initList();
        for (Map.Entry<String, Class> entry : data.entrySet()) {
            String key = entry.getKey();
            items.add(key);
            o.add(entry.getValue());
        }
        mList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));
        mList.setOnItemClickListener(this);
    }

    public void initList() {
        data.put("LauncherActivity", LauncherActivity.class);
        data.put("Debug Grid Test", GridLayoutRVTest.class);
        data.put("Issue #374, #363 enabled and disable load more off screen", GridTestOnlyOnePage.class);
        data.put("Debug load more", DebugLoadMoreActivity.class);
        data.put("Debug no header", DebugNoHeaderLoadMoreActivity.class);
        data.put("Debug Final Empty", FinalEmptyViewDisplayActivity.class);
        data.put("Debug First Page Cancel Load More", FirstPageCancelLoadMore.class);
        data.put("Debug Line Node", LineNodeActivity.class);
        data.put("Debug Pull to refresh", PullToRefreshActivity.class);
        data.put("Debug Admob classic", TestAdMobClassicActivity.class);
        data.put("Debug Admob advanced", TestAdvancedAdmobActivity.class);
        data.put("Debug Slider Header", SliderHeader.class);
        data.put("Debug Stagger Load More", StaggerLoadMoreActivity.class);
        data.put("Debug Swipe List", SwipeListViewExampleActivity.class);
        data.put("Debug expandable", TestExpandableRV.class);
        data.put("Debug multi view types", MultiViewTypesActivity.class);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, o.get(position));
        startActivity(intent);
    }


}
