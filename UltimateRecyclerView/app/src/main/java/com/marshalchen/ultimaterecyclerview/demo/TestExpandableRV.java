package com.marshalchen.ultimaterecyclerview.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.expanables.ExpCustomAdapter;
import com.marshalchen.ultimaterecyclerview.demo.modules.FastBinding;

/**
 * Created by hesk on 16/7/15.
 */
public class TestExpandableRV extends AppCompatActivity {

    private static String[] sampledatagroup1 = {
            "peter", "http://google",
            "billy", "http://google",
            "lisa", "http://google",
            "visa", "http://google"
    };
    private static String[] sampledatagroup2 = {
            "mother", "http://google",
            "father", "http://google",
            "son", "http://google",
            "holy spirit", "http://google",
            "god the son", "http://google"
    };
    private static String[] sampledatagroup3 = {
            "SONY", "http://google",
            "LG", "http://google",
            "SAMSUNG", "http://google",
            "XIAOMI", "http://google",
            "HTC", "http://google"
    };


    private UltimateRecyclerView ultimateRecyclerView;
    private ExpCustomAdapter simpleRecyclerViewAdapter = null;
    private LinearLayoutManager linearLayoutManager;
    private int moreNum = 2;
    private ActionMode actionMode;

    private Toolbar toolbar;
    boolean isDrag = true;

    private boolean admob_test_mode = false;


    private void enableSwipe() {


    }


    private void addExpandableFeatures() {
        ultimateRecyclerView.getItemAnimator().setAddDuration(100);
        ultimateRecyclerView.getItemAnimator().setRemoveDuration(100);
        ultimateRecyclerView.getItemAnimator().setMoveDuration(200);
        ultimateRecyclerView.getItemAnimator().setChangeDuration(100);
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
         * this is the adapter for the expanx
         */
        simpleRecyclerViewAdapter = new ExpCustomAdapter(this);
        simpleRecyclerViewAdapter.addAll(ExpCustomAdapter.getPreCodeMenu(sampledatagroup1, sampledatagroup2, sampledatagroup3), 0);

        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#ffffff"));
        addExpandableFeatures();
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
