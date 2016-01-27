package com.marshalchen.ultimaterecyclerview.demo;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.Display;
import android.view.LayoutInflater;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;
import com.marshalchen.ultimaterecyclerview.uiUtils.BasicGridLayoutManager;

/**
 * Created by hesk on 24/8/15.
 */
public class GridLayoutRVTest extends AppCompatActivity {
    private UltimateRecyclerView mUltimateRecyclerView;
    private GridLayoutRVAdapter mGridAdapter = null;
    private BasicGridLayoutManager mGridLayoutManager;
    private int moreNum = 2, columns = 2;
    private ActionMode actionMode;
    private Toolbar mToolbar;
    boolean isDrag = true;
    private ItemTouchHelper mItemTouchHelper;

    @LayoutRes
    protected int getMainLayout() {
        return R.layout.floatingbutton_grid_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getMainLayout());
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mUltimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.ultimate_recycler_view);
        mGridAdapter = new GridLayoutRVAdapter(SampleDataboxset.newListFromGen(67));
        mGridLayoutManager = new BasicGridLayoutManager(this, columns, mGridAdapter);
        mUltimateRecyclerView.setLayoutManager(mGridLayoutManager);
        mUltimateRecyclerView.setHasFixedSize(true);
        mUltimateRecyclerView.setSaveEnabled(true);
        mUltimateRecyclerView.setClipToPadding(false);
        mUltimateRecyclerView.setAdapter(mGridAdapter);
        mUltimateRecyclerView.enableLoadmore();
        mGridAdapter.setCustomLoadMoreView(LayoutInflater.from(this).inflate(R.layout.custom_bottom_progressbar, null));
        // mGridAdapter.setCustomHeaderView(new UltimateRecyclerView.CustomRelativeWrapper(this));
        mUltimateRecyclerView.setParallaxHeader(getLayoutInflater().inflate(R.layout.stick_header_item, mUltimateRecyclerView, false));
        mUltimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                Log.d("start to load more", itemsCount + " :: " + itemsCount);
            }
        });
     /*   mUltimateRecyclerView.setParallaxHeader(LayoutInflater.from(this).inflate(R.layout.empty_view, null));*/
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
