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
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;
import com.marshalchen.ultimaterecyclerview.grid.BasicGridLayoutManager;

/**
 * Created by hesk on 24/8/15.
 */
public class GridLayoutRVTest extends AppCompatActivity {
    private UltimateRecyclerView listuv;
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
        listuv = (UltimateRecyclerView) findViewById(R.id.ultimate_recycler_view);
        mGridAdapter = new GridLayoutRVAdapter();
        mGridLayoutManager = new BasicGridLayoutManager(this, columns, mGridAdapter);
        listuv.setLayoutManager(mGridLayoutManager);
        listuv.setHasFixedSize(true);
        listuv.setSaveEnabled(true);
        listuv.setClipToPadding(false);
        listuv.setAdapter(mGridAdapter);
        listuv.enableLoadmore();
        mGridAdapter.setCustomLoadMoreView(LayoutInflater.from(this).inflate(R.layout.custom_bottom_progressbar, null));
        listuv.setNormalHeader(setupHeaderView());
        listuv.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                Log.d("start to load more", itemsCount + " :: " + itemsCount);
                mGridAdapter.insert(SampleDataboxset.newListFromGen(67));
            }
        });
        addremovecontrol();
    }


    private void dimension_columns() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density;
        columns = Math.round(dpWidth / 300);
    }


    private View setupHeaderView() {
        View custom_header = LayoutInflater.from(this).inflate(R.layout.header_love, null, false);


        return custom_header;
    }

    private void addremovecontrol() {
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridAdapter.insert("newly added item");
            }
        });

        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridAdapter.removeLast();
            }
        });
    }

}
