package com.marshalchen.ultimaterecyclerview.demo.griddemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.modules.JRitem;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;
import com.marshalchen.ultimaterecyclerview.grid.BasicGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 24/8/15.
 */
public class GridLayoutRVTest extends AppCompatActivity {
    private UltimateRecyclerView listuv;
    private GridJRAdapter mGridAdapter = null;
    private BasicGridLayoutManager mGridLayoutManager;
    private int moreNum = 2, columns = 2;
    private ActionMode actionMode;
    private Toolbar mToolbar;
    boolean isDrag = true;
    private ItemTouchHelper mItemTouchHelper;
    public static final String TAG = "GLV";

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
        mGridAdapter = new GridJRAdapter(getJRList());
        mGridAdapter.setSpanColumns(columns);
        mGridLayoutManager = new BasicGridLayoutManager(this, columns, mGridAdapter);
        listuv.setLayoutManager(mGridLayoutManager);
        listuv.setHasFixedSize(true);
        listuv.setSaveEnabled(true);
        listuv.setClipToPadding(false);
        listuv.setAdapter(mGridAdapter);
        listuv.setItemAnimator(new DefaultItemAnimator());

        // mGridAdapter.setCustomLoadMoreView(LayoutInflater.from(this).inflate(R.layout.custom_bottom_progressbar, null));
        listuv.setNormalHeader(setupHeaderView());
        final Handler f = new Handler();
        listuv.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                //   Log.d(TAG, itemsCount + " :: " + itemsCount);
                f.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mGridAdapter.insert(SampleDataboxset.genJRList(13));
                        // listuv.disableLoadmore();
                        // listuv.disableLoadmore();
                    }
                }, 2000);
            }
        });

        // listuv.enableLoadmore();
        //    listuv.disableLoadmore();
        listuv.setLoadMoreView(R.layout.custom_bottom_progressbar);
        harn_controls();
    }

    private List<JRitem> getJRList() {
        List<JRitem> team = new ArrayList<>();
        //you can make your own test for starting-zero-data
        //   team = SampleDataboxset.genJRList(2);
        return team;
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

    private void harn_controls() {
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridAdapter.insert(SampleDataboxset.genJRList(4));
            }
        });

        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridAdapter.removeLast();
            }
        });

        findViewById(R.id.delall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridAdapter.removeAll();
            }
        });
        findViewById(R.id.add_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridAdapter.insertFirst(SampleDataboxset.genJRSingle());
            }
        });

        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  mGridAdapter.notifyDataSetChanged();
            }
        });


    }

}
