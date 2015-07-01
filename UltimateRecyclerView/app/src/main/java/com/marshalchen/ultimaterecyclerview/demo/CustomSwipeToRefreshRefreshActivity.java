package com.marshalchen.ultimaterecyclerview.demo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
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

import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
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

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


public class CustomSwipeToRefreshRefreshActivity extends AppCompatActivity implements ActionMode.Callback {

    CustomUltimateRecyclerview ultimateRecyclerView;
    SimpleAnimationAdapter simpleRecyclerViewAdapter = null;
    LinearLayoutManager linearLayoutManager;
    int moreNum = 100;
    private ActionMode actionMode;

    Toolbar toolbar;
    boolean isDrag = true;
    View floatingButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_refresh_activity);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ultimateRecyclerView = (CustomUltimateRecyclerview) findViewById(R.id.custom_ultimate_recycler_view);
        ultimateRecyclerView.setHasFixedSize(false);
        floatingButton = findViewById(R.id.custom_urv_add_floating_button);
        List<String> stringList = new ArrayList<>();
        simpleRecyclerViewAdapter = new SimpleAnimationAdapter(stringList);

        stringList.add("111");
        stringList.add("aaa");
        stringList.add("222");
        stringList.add("33");
        stringList.add("44");
        stringList.add("55");
        stringList.add("66");
        stringList.add("11771");
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);

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
        // ultimateRecyclerView.hideDefaultFloatingActionButton();
        // ultimateRecyclerView.hideFloatingActionMenu();
        ultimateRecyclerView.displayCustomFloatingActionView(false);
        ultimateRecyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

            }

            @Override
            public void onDownMotionEvent() {

            }

            @Override
            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {
                if (observableScrollState == ObservableScrollState.DOWN) {
                    //  ultimateRecyclerView.showToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
                    //  ultimateRecyclerView.showView(floatingButton, ultimateRecyclerView, getScreenHeight());
                } else if (observableScrollState == ObservableScrollState.UP) {
//                    ultimateRecyclerView.hideToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
//                    ultimateRecyclerView.hideFloatingActionMenu();
                    //   ultimateRecyclerView.hideView(floatingButton, ultimateRecyclerView, getScreenHeight());

                } else if (observableScrollState == ObservableScrollState.STOP) {
                }
            }
        });



        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        for (Type type : Type.values()) {
            spinnerAdapter.add(type.getTitle());
        }
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                URLogs.d("selected---" + Type.values()[position].getTitle());
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

        ultimateRecyclerView.setCustomSwipeToRefresh();

        // refreshingMaterial();
        refreshingString();

    }

    void refreshingString() {
        storeHouseHeader = new StoreHouseHeader(this);
        //   header.setPadding(0, 15, 0, 0);

        storeHouseHeader.initWithString("Marshal Chen");
        //  header.initWithStringArray(R.array.akta);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                boolean canbePullDown = PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view2);
                return canbePullDown;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRecyclerViewAdapter.insert("Refresh things", 0);
                        //   ultimateRecyclerView.scrollBy(0, -50);
                        linearLayoutManager.scrollToPosition(0);
                        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
                        changeHeaderHandler.sendEmptyMessageDelayed(0, 500);
                    }
                }, 1800);
            }
        });

    }


//    void refreshingRental() {
//        rentalsSunHeaderView = new RentalsSunHeaderView(this);
//        rentalsSunHeaderView.setUp(ultimateRecyclerView.mPtrFrameLayout);
//
//        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(materialHeader);
//        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(storeHouseHeader);
//        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(rentalsSunHeaderView);
//        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(rentalsSunHeaderView);
//        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
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
//                        changeHeaderHandler.sendEmptyMessageDelayed(3, 500);
//                    }
//                }, 1800);
//            }
//        });
//
//    }

    void refreshingMaterial() {
        materialHeader = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        materialHeader.setColorSchemeColors(colors);
        materialHeader.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        materialHeader.setPadding(0, 15, 0, 10);
        materialHeader.setPtrFrameLayout(ultimateRecyclerView.mPtrFrameLayout);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(materialHeader);

        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRecyclerViewAdapter.insert("Refresh things", 0);
                        //   ultimateRecyclerView.scrollBy(0, -50);
                        linearLayoutManager.scrollToPosition(0);
                        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
                        //   changeHeaderHandler.sendEmptyMessageDelayed(2, 500);
                    }
                }, 1800);
            }
        });
    }

    Handler changeHeaderHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    refreshingStringArray();
                    break;
                case 1:
                    //  refreshingMaterial();
                    refreshingString();
                    break;
                case 2:
                    // refreshingString();
                    break;
                case 3:
                    refreshingString();
                    break;
                case 4:
                    break;
            }
        }
    };
    private int mLoadTime = 0;
    StoreHouseHeader storeHouseHeader;
    MaterialHeader materialHeader;
  //  RentalsSunHeaderView rentalsSunHeaderView;

    void refreshingStringArray() {
        storeHouseHeader = new StoreHouseHeader(this);
        storeHouseHeader.setPadding(0, 15, 0, 0);

        // using string array from resource xml file
        storeHouseHeader.initWithStringArray(R.array.storehouse);

        ultimateRecyclerView.mPtrFrameLayout.setDurationToCloseHeader(1500);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);


        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
//        ultimateRecyclerView.mPtrFrameLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
//            }
//        }, 100);

        // change header after loaded
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(new PtrUIHandler() {


            @Override
            public void onUIReset(PtrFrameLayout frame) {
                mLoadTime++;
                if (mLoadTime % 2 == 0) {
                    storeHouseHeader.setScale(1);
                    storeHouseHeader.initWithStringArray(R.array.storehouse);
                } else {
                    storeHouseHeader.setScale(0.5f);
                    storeHouseHeader.initWithStringArray(R.array.akta);
                }
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {

            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

            }
        });

        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // frame.refreshComplete();
                        simpleRecyclerViewAdapter.insert("Refresh things", 0);
                        //   ultimateRecyclerView.scrollBy(0, -50);
                        linearLayoutManager.scrollToPosition(0);
                        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
                        if (mLoadTime % 2 == 0)
                            changeHeaderHandler.sendEmptyMessageDelayed(1, 500);
                    }
                }, 2000);
            }
        });
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
        }

        return super.onOptionsItemSelected(item);
    }

    enum Type {
        FadeIn("FadeIn", new FadeInAnimator()),
        FadeInDown("FadeInDown", new FadeInDownAnimator()),
        FadeInUp("FadeInUp", new FadeInUpAnimator()),
        FadeInLeft("FadeInLeft", new FadeInLeftAnimator()),
        FadeInRight("FadeInRight", new FadeInRightAnimator()),
        Landing("Landing", new LandingAnimator()),
        ScaleIn("ScaleIn", new ScaleInAnimator()),
        ScaleInTop("ScaleInTop", new ScaleInTopAnimator()),
        ScaleInBottom("ScaleInBottom", new ScaleInBottomAnimator()),
        ScaleInLeft("ScaleInLeft", new ScaleInLeftAnimator()),
        ScaleInRight("ScaleInRight", new ScaleInRightAnimator()),
        FlipInTopX("FlipInTopX", new FlipInTopXAnimator()),
        FlipInBottomX("FlipInBottomX", new FlipInBottomXAnimator()),
        FlipInLeftY("FlipInLeftY", new FlipInLeftYAnimator()),
        FlipInRightY("FlipInRightY", new FlipInRightYAnimator()),
        SlideInLeft("SlideInLeft", new SlideInLeftAnimator()),
        SlideInRight("SlideInRight", new SlideInRightAnimator()),
        SlideInDown("SlideInDown", new SlideInDownAnimator()),
        SlideInUp("SlideInUp", new SlideInUpAnimator()),
        OvershootInRight("OvershootInRight", new OvershootInRightAnimator()),
        OvershootInLeft("OvershootInLeft", new OvershootInLeftAnimator());

        private String mTitle;
        private BaseItemAnimator mAnimator;

        Type(String title, BaseItemAnimator animator) {
            mTitle = title;
            mAnimator = animator;
        }

        public BaseItemAnimator getAnimator() {
            return mAnimator;
        }

        public String getTitle() {
            return mTitle;
        }
    }


}
