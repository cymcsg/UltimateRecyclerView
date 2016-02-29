package com.marshalchen.ultimaterecyclerview.demo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.basicdemo.sectionCommonAdapter;
import com.marshalchen.ultimaterecyclerview.demo.basicdemo.sectionZeroAdapter;
import com.marshalchen.ultimaterecyclerview.demo.modules.FastBinding;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.SimpleItemTouchHelperCallback;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ActionMode.Callback {

    UltimateRecyclerView ultimateRecyclerView;
    sectionCommonAdapter RVAdapter = null;
    LinearLayoutManager linearLayoutManager;
    int moreNum = 2;
    private ActionMode actionMode;

    Toolbar toolbar;
    boolean isDrag = true;

    private ItemTouchHelper mItemTouchHelper;


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

        stringList.add("E19");
        stringList.add("P32");
        stringList.add("G22");
        stringList.add("B33");
        stringList.add("T44");
        stringList.add("R55");
        stringList.add("B66");
        stringList.add("Q17");

        RVAdapter = new sectionCommonAdapter(stringList);

        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(RVAdapter);


        // ultimateRecyclerView.setEmptyView(getResources().getIdentifier("empty_view","layout",getPackageName()));
        // ultimateRecyclerView.showEmptyView();
        enableLoadMore();
        // enableHeader();
        //  enableParalax();
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#ffffff"));
        enableRefreshGoogleMaterialStyle();

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(RVAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(ultimateRecyclerView.mRecyclerView);
        RVAdapter.setOnDragStartListener(new sectionZeroAdapter.OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }
        });


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
//        ultimateRecyclerView.addOnItemTouchListener(new SwipeableRecyclerViewTouchListener(ultimateRecyclerView.mRecyclerView,
//                new SwipeableRecyclerViewTouchListener.SwipeListener() {
//                    @Override
//                    public boolean canSwipe(int position) {
//
//                        if (position > 0 && position < stringList.size())
//                            return true;
//                        else return false;
//                    }
//
//                    @Override
//                    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
//                        for (int position : reverseSortedPositions) {
//                            RVAdapter.remove(position);
//                        }
//                        RVAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
//                        for (int position : reverseSortedPositions) {
//                            RVAdapter.remove(position);
//                        }
//                        RVAdapter.notifyDataSetChanged();
//                    }
//                }));


      /*


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


        */
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RVAdapter.insertLast("newly added item");
            }
        });

        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RVAdapter.removeLast();
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
//                        RVAdapter.insert("Refresh things", 0);
//                        //   ultimateRecyclerView.scrollBy(0, -50);
//                        linearLayoutManager.scrollToPosition(0);
//                        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
//                    }
//                }, 1800);
//            }
//        });

    }



    private void enableHeader() {
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(RVAdapter);
        ultimateRecyclerView.addItemDecoration(headersDecor);
        //   ultimateRecyclerView.setParallaxHeader(getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false));
        ultimateRecyclerView.setNormalHeader(getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false));

    }

    private void enableParalax() {
        ultimateRecyclerView.setOnParallaxScroll(new UltimateRecyclerView.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {
                Drawable c = toolbar.getBackground();
                c.setAlpha(Math.round(127 + percentage * 128));
                toolbar.setBackgroundDrawable(c);
            }
        });
    }

    private void enableLoadMore() {
        ultimateRecyclerView.setLoadMoreView(R.layout.custom_bottom_progressbar);
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        RVAdapter.insertLast("more" + moreNum++);
                        RVAdapter.insertLast("more" + moreNum++);
                        RVAdapter.insertLast("more" + moreNum++);
                    }
                }, 1000);
            }
        });
    }

    private void enableRefreshGoogleMaterialStyle() {
        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moreNum++;
                        RVAdapter.insertLast(moreNum + "  Refresh things");
                        ultimateRecyclerView.setRefreshing(false);
                        //  ultimateRecyclerView.scrollBy(0, -50);
                        linearLayoutManager.scrollToPosition(0);
                        //  ultimateRecyclerView.setAdapter(RVAdapter);
                        //  RVAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ultimateRecyclerView.setDefaultSwipeToRefreshColorScheme(
                    getColor(android.R.color.holo_orange_light),
                    getColor(android.R.color.holo_orange_dark),
                    getColor(android.R.color.holo_red_dark),
                    getColor(android.R.color.holo_red_light));
        } else {
            ultimateRecyclerView.setDefaultSwipeToRefreshColorScheme(
                    getResources().getColor(android.R.color.holo_orange_light),
                    getResources().getColor(android.R.color.holo_orange_dark),
                    getResources().getColor(android.R.color.holo_red_dark),
                    getResources().getColor(android.R.color.holo_red_light));
        }
    }

    private void toggleSelection(int position) {
        RVAdapter.toggleSelection(position);
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

}
