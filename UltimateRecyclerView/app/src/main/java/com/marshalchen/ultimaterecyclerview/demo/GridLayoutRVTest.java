package com.marshalchen.ultimaterecyclerview.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.Display;
import android.view.LayoutInflater;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;
import java.util.List;
import static com.marshalchen.ultimaterecyclerview.demo.GridAdapter.*;

/**
 * Created by hesk on 24/8/15.
 */
public class GridLayoutRVTest extends AppCompatActivity {
    private UltimateRecyclerView mUltimateRecyclerView;
    private GridAdapter mGridAdapter = null;
    private GridLayoutManager mGridLayoutManager;
    private int moreNum = 2, columns = 3;
    private ActionMode actionMode;
    private Toolbar mToolbar;
    boolean isDrag = true;
    private ItemTouchHelper mItemTouchHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mUltimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.ultimate_recycler_view);
        mUltimateRecyclerView.setHasFixedSize(false);
        final List<String> stringList = SampleDataboxset.newListFromGen(100);
        mGridAdapter = new GridAdapter(stringList);
        mGridLayoutManager = new GridLayoutManager(this, columns);
        mGridLayoutManager.setSpanSizeLookup(new GridAdapter.GridSpan(columns, 10));
        mUltimateRecyclerView.setHasFixedSize(true);
        mUltimateRecyclerView.setLayoutManager(mGridLayoutManager);
        mUltimateRecyclerView.setAdapter(mGridAdapter);
        mUltimateRecyclerView.enableLoadmore();
        mGridAdapter
          .setCustomLoadMoreView(LayoutInflater.from(this)
          .inflate(R.layout.custom_bottom_progressbar, null));


    }


    private void dimension_columns() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density;
        columns = Math.round(dpWidth / 300);
    }
}
