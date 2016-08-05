package com.marshalchen.ultimaterecyclerview.demo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.modules.FastBinding;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.SimpleItemTouchHelperCallback;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.*;

/**
 * Created by Marshal Chen on 3/8/2016.
 */
public class LauncherActivity extends AppCompatActivity implements ActionMode.Callback {
    UltimateRecyclerView ultimateRecyclerView;
    SimpleAdapter simpleRecyclerViewAdapter = null;
    LinearLayoutManager linearLayoutManager;
    int moreNum = 2;
    private ActionMode actionMode;

    Toolbar toolbar;
    boolean isDrag = true;

    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

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
        simpleRecyclerViewAdapter = new SimpleAdapter(stringList);

        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);

        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(simpleRecyclerViewAdapter);
        ultimateRecyclerView.addItemDecoration(headersDecor);
////        ultimateRecyclerView.setEmptyView(getResources().getIdentifier("empty_view","layout",getPackageName()));
//        ultimateRecyclerView.showEmptyView();
        //  ultimateRecyclerView.enableLoadmore();
        //simpleRecyclerViewAdapter.setCustomLoadMoreView(LayoutInflater.from(this)
        //        .inflate(R.layout.custom_bottom_progressbar, null));

        ultimateRecyclerView.setLoadMoreView(LayoutInflater.from(this)
                .inflate(R.layout.custom_bottom_progressbar, null));
        ultimateRecyclerView.setParallaxHeader(getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false));
//        //   ultimateRecyclerView.setNormalHeader(getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false));
        ultimateRecyclerView.setOnParallaxScroll(new UltimateRecyclerView.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {
                Drawable c = toolbar.getBackground();
                c.setAlpha(Math.round(127 + percentage * 128));
                toolbar.setBackgroundDrawable(c);
            }
        });
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#ffffff"));
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

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(simpleRecyclerViewAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(ultimateRecyclerView.mRecyclerView);
        simpleRecyclerViewAdapter.setOnDragStartListener(new SimpleAdapter.OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }
        });
        ultimateRecyclerView.reenableLoadmore();
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                        // linearLayoutManager.scrollToPositionWithOffset(maxLastVisiblePosition,-1);
                        //   linearLayoutManager.scrollToPosition(maxLastVisiblePosition);

                    }
                }, 1000);
            }
        });

//        ultimateRecyclerView.setDefaultSwipeToRefreshColorScheme(getResources().getColor(android.R.color.holo_blue_bright),
//                getResources().getColor(android.R.color.holo_green_light),
//                getResources().getColor(android.R.color.holo_orange_light),
//                getResources().getColor(android.R.color.holo_red_light));

//        ultimateRecyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
//            @Override
//            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
//
//            }
//
//            @Override
//            public void onDownMotionEvent() {
//
//            }
//
//            @Override
//            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {
////                if (observableScrollState == ObservableScrollState.DOWN) {
////                    ultimateRecyclerView.showToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
////                    ultimateRecyclerView.showFloatingActionMenu();
////                } else if (observableScrollState == ObservableScrollState.UP) {
////                    ultimateRecyclerView.hideToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
////                    ultimateRecyclerView.hideFloatingActionMenu();
////                } else if (observableScrollState == ObservableScrollState.STOP) {
////                }
//                URLogs.d("onUpOrCancelMotionEvent");
//                if (observableScrollState == ObservableScrollState.UP) {
//                    ultimateRecyclerView.hideToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
//                    ultimateRecyclerView.hideFloatingActionMenu();
//                } else if (observableScrollState == ObservableScrollState.DOWN) {
//                    ultimateRecyclerView.showToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
//                    ultimateRecyclerView.showFloatingActionMenu();
//                }
//            }
//        });

        ultimateRecyclerView.showFloatingButtonView();
//
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
                ultimateRecyclerView.getItemAnimator().setAddDuration(500);
                ultimateRecyclerView.getItemAnimator().setRemoveDuration(500);
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleRecyclerViewAdapter.insert("newly added item", 1);
            }
        });

        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleRecyclerViewAdapter.remove(1);
            }
        });

//
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

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        URLogs.d("actionmode---" + (mode == null));
        mode.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
        //  return false;
    }

    /**
     * Called to refresh an action mode's action menu whenever it is invalidated.
     *
     * @param mode ActionMode being prepared
     * @param menu Menu used to populate action buttons
     * @return true if the menu or action mode was updated, false otherwise.
     */
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // swipeToDismissTouchListener.setEnabled(false);
        this.actionMode = mode;
        return false;
    }


    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {
        this.actionMode = null;
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
        FadeIn(new FadeInAnimator(new OvershootInterpolator(1f))),
        FadeInDown(new FadeInDownAnimator(new OvershootInterpolator(1f))),
        FadeInUp(new FadeInUpAnimator(new OvershootInterpolator(1f))),
        FadeInLeft(new FadeInLeftAnimator(new OvershootInterpolator(1f))),
        FadeInRight(new FadeInRightAnimator(new OvershootInterpolator(1f))),
        Landing(new LandingAnimator(new OvershootInterpolator(1f))),
        ScaleIn(new ScaleInAnimator(new OvershootInterpolator(1f))),
        ScaleInTop(new ScaleInTopAnimator(new OvershootInterpolator(1f))),
        ScaleInBottom(new ScaleInBottomAnimator(new OvershootInterpolator(1f))),
        ScaleInLeft(new ScaleInLeftAnimator(new OvershootInterpolator(1f))),
        ScaleInRight(new ScaleInRightAnimator(new OvershootInterpolator(1f))),
        FlipInTopX(new FlipInTopXAnimator(new OvershootInterpolator(1f))),
        FlipInBottomX(new FlipInBottomXAnimator(new OvershootInterpolator(1f))),
        FlipInLeftY(new FlipInLeftYAnimator(new OvershootInterpolator(1f))),
        FlipInRightY(new FlipInRightYAnimator(new OvershootInterpolator(1f))),
        SlideInLeft(new SlideInLeftAnimator(new OvershootInterpolator(1f))),
        SlideInRight(new SlideInRightAnimator(new OvershootInterpolator(1f))),
        SlideInDown(new SlideInDownAnimator(new OvershootInterpolator(1f))),
        SlideInUp(new SlideInUpAnimator(new OvershootInterpolator(1f))),
        OvershootInRight(new OvershootInRightAnimator(1.0f)),
        OvershootInLeft(new OvershootInLeftAnimator(1.0f));

        private BaseItemAnimator mAnimator;

        Type(BaseItemAnimator animator) {
            mAnimator = animator;
        }

        public BaseItemAnimator getAnimator() {
            return mAnimator;
        }
    }
}
