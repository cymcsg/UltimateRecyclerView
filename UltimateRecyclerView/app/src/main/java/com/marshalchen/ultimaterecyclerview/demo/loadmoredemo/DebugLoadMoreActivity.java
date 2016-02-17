package com.marshalchen.ultimaterecyclerview.demo.loadmoredemo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.marshalchen.ultimaterecyclerview.DragDropTouchListener;
import com.marshalchen.ultimaterecyclerview.ItemTouchListenerAdapter;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.SwipeableRecyclerViewTouchListener;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator;
import com.marshalchen.ultimaterecyclerview.animators.FadeInAnimator;
import com.marshalchen.ultimaterecyclerview.animators.FadeInDownAnimator;
import com.marshalchen.ultimaterecyclerview.animators.FadeInLeftAnimator;
import com.marshalchen.ultimaterecyclerview.animators.FadeInRightAnimator;
import com.marshalchen.ultimaterecyclerview.animators.FadeInUpAnimator;
import com.marshalchen.ultimaterecyclerview.animators.FlipInBottomXAnimator;
import com.marshalchen.ultimaterecyclerview.animators.FlipInLeftYAnimator;
import com.marshalchen.ultimaterecyclerview.animators.FlipInRightYAnimator;
import com.marshalchen.ultimaterecyclerview.animators.FlipInTopXAnimator;
import com.marshalchen.ultimaterecyclerview.animators.LandingAnimator;
import com.marshalchen.ultimaterecyclerview.animators.OvershootInLeftAnimator;
import com.marshalchen.ultimaterecyclerview.animators.OvershootInRightAnimator;
import com.marshalchen.ultimaterecyclerview.animators.ScaleInAnimator;
import com.marshalchen.ultimaterecyclerview.animators.ScaleInBottomAnimator;
import com.marshalchen.ultimaterecyclerview.animators.ScaleInLeftAnimator;
import com.marshalchen.ultimaterecyclerview.animators.ScaleInRightAnimator;
import com.marshalchen.ultimaterecyclerview.animators.ScaleInTopAnimator;
import com.marshalchen.ultimaterecyclerview.animators.SlideInDownAnimator;
import com.marshalchen.ultimaterecyclerview.animators.SlideInLeftAnimator;
import com.marshalchen.ultimaterecyclerview.animators.SlideInRightAnimator;
import com.marshalchen.ultimaterecyclerview.animators.SlideInUpAnimator;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.basicdemo.sectionZeroAdapter;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;
import com.marshalchen.ultimaterecyclerview.demo.modules.FastBinding;
import com.marshalchen.ultimaterecyclerview.uiUtils.ScrollSmoothLineaerLayoutManager;

import java.util.ArrayList;

/**
 * Created by hesk on 7/1/2015.
 */
public class DebugLoadMoreActivity extends AppCompatActivity {

    private UltimateRecyclerView ultimateRecyclerView;
    private sectionZeroAdapter simpleRecyclerViewAdapter = null;
    private LinearLayoutManager linearLayoutManager;
    private int moreNum = 2;
    private ActionMode actionMode;

    Toolbar toolbar;
    boolean isDrag = true, isEnableAutoLoadMore = true, status_progress = false;

    DragDropTouchListener dragDropTouchListener;

    protected void enableParallaxHeader() {
        ultimateRecyclerView.setParallaxHeader(getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false));
        ultimateRecyclerView.setOnParallaxScroll(new UltimateRecyclerView.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {
                Drawable c = toolbar.getBackground();
                c.setAlpha(Math.round(127 + percentage * 128));
                toolbar.setBackgroundDrawable(c);
            }
        });
    }

    protected void enableLoadMore() {
        // StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(simpleRecyclerViewAdapter);
        // ultimateRecyclerView.addItemDecoration(headersDecor);
        ultimateRecyclerView.enableLoadmore();
        simpleRecyclerViewAdapter.setCustomLoadMoreView(LayoutInflater.from(this).inflate(R.layout.custom_bottom_progressbar, null));
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                status_progress = true;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        SampleDataboxset.insertMore(simpleRecyclerViewAdapter, 2);
                        //  linearLayoutManager.scrollToPositionWithOffset(maxLastVisiblePosition, -1);
                        //  linearLayoutManager.scrollToPosition(maxLastVisiblePosition);
                        /**
                         * this is the example for making the function test for loading more and disable loading more
                         */
                        if (isEnableAutoLoadMore) {
                            ultimateRecyclerView.enableLoadmore();
                        } else {
                            ultimateRecyclerView.disableLoadmore();
                        }
                        status_progress = false;
                    }
                }, 2500);
            }
        });

    }

    protected void enableRefresh() {
        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRecyclerViewAdapter.insertLast(moreNum++ + "  Refresh things");
                        ultimateRecyclerView.setRefreshing(false);
                        //   ultimateRecyclerView.scrollBy(0, -50);
                        linearLayoutManager.scrollToPosition(0);
                        //ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
                        //simpleRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
        //        ultimateRecyclerView.setDefaultSwipeToRefreshColorScheme(getResources().getColor(android.R.color.holo_blue_bright),
//                getResources().getColor(android.R.color.holo_green_light),
//                getResources().getColor(android.R.color.holo_orange_light),
//                getResources().getColor(android.R.color.holo_red_light));

    }

    protected LinearLayoutManager setupLinearLayoutMgr() {
        linearLayoutManager = new ScrollSmoothLineaerLayoutManager(this, LinearLayoutManager.VERTICAL, false, 300);
        return linearLayoutManager;
    }


    protected void enableScrollControl() {
        ultimateRecyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
                URLogs.d("onScrollChanged: " + dragging);
            }

            @Override
            public void onDownMotionEvent() {

            }

            @Override
            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {
                URLogs.d("onUpOrCancelMotionEvent");
                if (observableScrollState == ObservableScrollState.UP) {
                    ultimateRecyclerView.hideToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
                    ultimateRecyclerView.hideFloatingActionMenu();
                } else if (observableScrollState == ObservableScrollState.DOWN) {
                    ultimateRecyclerView.showToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
                    ultimateRecyclerView.showFloatingActionMenu();
                }
            }
        });

        ultimateRecyclerView.showFloatingButtonView();
    }

    private void setupUltimateRecyclerView() {
        ultimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.ultimate_recycler_view);
        ultimateRecyclerView.setHasFixedSize(false);
        simpleRecyclerViewAdapter = new sectionZeroAdapter(new ArrayList<String>());
        ultimateRecyclerView.setLayoutManager(setupLinearLayoutMgr());
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
        // enableParallaxHeader();
        enableLoadMore();
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#ffff66ff"));
        enableRefresh();
        enableScrollControl();

        ultimateRecyclerView.addOnItemTouchListener(new SwipeableRecyclerViewTouchListener(ultimateRecyclerView.mRecyclerView,
                new SwipeableRecyclerViewTouchListener.SwipeListener() {
                    @Override
                    public boolean canSwipe(int position) {
                        if (position > 0)
                            return true;
                        else return false;
                    }

                    @Override
                    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            simpleRecyclerViewAdapter.removeAt(position);
                        }
                        simpleRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            simpleRecyclerViewAdapter.removeAt(position);
                        }
                        simpleRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }));


        ItemTouchListenerAdapter itemTouchListenerAdapter = new ItemTouchListenerAdapter(ultimateRecyclerView.mRecyclerView,
                new ItemTouchListenerAdapter.RecyclerViewOnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View clickedView, int position) {
                    }

                    @Override
                    public void onItemLongClick(RecyclerView parent, View clickedView, int position) {
                        URLogs.d("onItemLongClick()" + isDrag);
                        if (isDrag) {
                            URLogs.d("onItemLongClick()" + isDrag);
                            //   dragDropTouchListener.startDrag();
                            //   ultimateRecyclerView.enableDefaultSwipeRefresh(false);
                        }

                    }
                });
        ultimateRecyclerView.mRecyclerView.addOnItemTouchListener(itemTouchListenerAdapter);
    }

    private void setupSpinnerSelection() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        for (Type type : Type.values()) {
            spinnerAdapter.add(type.name());
        }
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ultimateRecyclerView.setItemAnimator(Type.values()[position].getAnimator());
                ultimateRecyclerView.getItemAnimator().setAddDuration(300);
                ultimateRecyclerView.getItemAnimator().setRemoveDuration(300);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleRecyclerViewAdapter.insertFirst("rand added item");
                // - simpleRecyclerViewAdapter.in
            }
        });

        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleRecyclerViewAdapter.removeLast();
            }
        });

        findViewById(R.id.toggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status_progress) {
                    isEnableAutoLoadMore = !isEnableAutoLoadMore;
                    if (isEnableAutoLoadMore) {
                        ultimateRecyclerView.enableLoadmore();
                    }
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadmore);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setupUltimateRecyclerView();
        setupSpinnerSelection();
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

    enum Type {
        FadeIn(new FadeInAnimator()),
        FadeInDown(new FadeInDownAnimator()),
        FadeInUp(new FadeInUpAnimator()),
        FadeInLeft(new FadeInLeftAnimator()),
        FadeInRight(new FadeInRightAnimator()),
        Landing(new LandingAnimator()),
        ScaleIn(new ScaleInAnimator()),
        ScaleInTop(new ScaleInTopAnimator()),
        ScaleInBottom(new ScaleInBottomAnimator()),
        ScaleInLeft(new ScaleInLeftAnimator()),
        ScaleInRight(new ScaleInRightAnimator()),
        FlipInTopX(new FlipInTopXAnimator()),
        FlipInBottomX(new FlipInBottomXAnimator()),
        FlipInLeftY(new FlipInLeftYAnimator()),
        FlipInRightY(new FlipInRightYAnimator()),
        SlideInLeft(new SlideInLeftAnimator()),
        SlideInRight(new SlideInRightAnimator()),
        SlideInDown(new SlideInDownAnimator()),
        SlideInUp(new SlideInUpAnimator()),
        OvershootInRight(new OvershootInRightAnimator()),
        OvershootInLeft(new OvershootInLeftAnimator());

        private BaseItemAnimator mAnimator;

        Type(BaseItemAnimator animator) {
            mAnimator = animator;
        }

        public BaseItemAnimator getAnimator() {
            return mAnimator;
        }
    }

}
