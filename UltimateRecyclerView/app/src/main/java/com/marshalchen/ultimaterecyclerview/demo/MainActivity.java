package com.marshalchen.ultimaterecyclerview.demo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
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
import com.marshalchen.ultimaterecyclerview.SwipeableRecyclerViewTouchListener;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator;
import com.marshalchen.ultimaterecyclerview.animators.*;
import com.marshalchen.ultimaterecyclerview.demo.scrollableobservable.ScrollObservablesActivity;
import com.marshalchen.ultimaterecyclerview.demo.swipelist.SwipeListViewExampleActivity;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ActionMode.Callback {

    UltimateRecyclerView ultimateRecyclerView;
    SimpleAdapter simpleRecyclerViewAdapter = null;
    LinearLayoutManager linearLayoutManager;
    int moreNum = 2;
    private ActionMode actionMode;

    Toolbar toolbar;
    boolean isDrag = true;

    DragDropTouchListener dragDropTouchListener;

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
        simpleRecyclerViewAdapter = new SimpleAdapter(stringList);

        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);

        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(simpleRecyclerViewAdapter);
        ultimateRecyclerView.addItemDecoration(headersDecor);
//        ultimateRecyclerView.setEmptyView(getResources().getIdentifier("empty_view","layout",getPackageName()));
//        ultimateRecyclerView.showEmptyView();
        ultimateRecyclerView.enableLoadmore();
        simpleRecyclerViewAdapter.setCustomLoadMoreView(LayoutInflater.from(this)
                .inflate(R.layout.custom_bottom_progressbar, null));

        ultimateRecyclerView.setParallaxHeader(getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false));
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

        ultimateRecyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

            }

            @Override
            public void onDownMotionEvent() {

            }

            @Override
            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {
//                if (observableScrollState == ObservableScrollState.DOWN) {
//                    ultimateRecyclerView.showToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
//                    ultimateRecyclerView.showFloatingActionMenu();
//                } else if (observableScrollState == ObservableScrollState.UP) {
//                    ultimateRecyclerView.hideToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
//                    ultimateRecyclerView.hideFloatingActionMenu();
//                } else if (observableScrollState == ObservableScrollState.STOP) {
//                }
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
                            simpleRecyclerViewAdapter.remove(position);
                        }
                        simpleRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            simpleRecyclerViewAdapter.remove(position);
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
                            dragDropTouchListener.startDrag();
                            ultimateRecyclerView.enableDefaultSwipeRefresh(false);
                        }

                    }
                });
        ultimateRecyclerView.mRecyclerView.addOnItemTouchListener(itemTouchListenerAdapter);

        dragDropTouchListener = new DragDropTouchListener(ultimateRecyclerView.mRecyclerView, this) {
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }

            @Override
            protected void onItemSwitch(RecyclerView recyclerView, int from, int to) {
                if (from > 0 && to > 0) {
                    simpleRecyclerViewAdapter.swapPositions(from, to);
//                    //simpleRecyclerViewAdapter.clearSelection(from);
//                    simpleRecyclerViewAdapter.notifyItemChanged(to);
                    //simpleRecyclerViewAdapter.remove(position);
                    //  simpleRecyclerViewAdapter.notifyDataSetChanged();
                    URLogs.d("switch----");
                    //    simpleRecyclerViewAdapter.insert(simpleRecyclerViewAdapter.remove(););
                }

            }

            @Override
            protected void onItemDrop(RecyclerView recyclerView, int position) {
                URLogs.d("drop----");
                ultimateRecyclerView.enableDefaultSwipeRefresh(true);
                simpleRecyclerViewAdapter.notifyDataSetChanged();
            }
        };
        dragDropTouchListener.setCustomDragHighlight(getResources().getDrawable(R.drawable.custom_drag_frame));
        ultimateRecyclerView.mRecyclerView.addOnItemTouchListener(dragDropTouchListener);


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
                simpleRecyclerViewAdapter.insert("newly added item", 1);
            }
        });

        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleRecyclerViewAdapter.remove(1);
            }
        });

//        ultimateRecyclerView.addItemDecoration(
//                new HorizontalDividerItemDecoration.Builder(this).build());

//        ultimateRecyclerView.setCustomSwipeToRefresh();
//        final StoreHouseHeader header = new StoreHouseHeader(this);
//        //   header.setPadding(0, 15, 0, 0);
//
//        header.initWithString("Marshal Chen");
//        //  header.initWithStringArray(R.array.akta);
//        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(header);
//        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(header);
//
//        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
//                boolean canbePullDown = PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view2);
//                return canbePullDown;
//            }
//
//            @Override
//            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
//                ptrFrameLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        simpleRecyclerViewAdapter.insert("Refresh things", 0);
//                        //   ultimateRecyclerView.scrollBy(0, -50);
//                        linearLayoutManager.scrollToPosition(0);
//                        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
//                    }
//                }, 1800);
//            }
//        });

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_bottom) {
            Intent intent = new Intent(this, MultiViewTypesActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_custom) {
            Intent intent = new Intent(this, CustomSwipeToRefreshRefreshActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.admob) {
            Intent intent = new Intent(this, TestAdMob.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.scrollactivity) {
            Intent intent = new Intent(this, ScrollObservablesActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.swipe_and_drag) {
            Intent intent = new Intent(this, SwipeListViewExampleActivity.class);
            startActivity(intent);
            return true;
        }
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
