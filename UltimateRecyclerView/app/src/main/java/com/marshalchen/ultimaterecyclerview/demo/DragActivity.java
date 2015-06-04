package com.marshalchen.ultimaterecyclerview.demo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
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

import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
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
import com.marshalchen.ultimaterecyclerview.demo.modules.ExampleDataProvider;
import com.marshalchen.ultimaterecyclerview.draggable.RecyclerViewDragDropManager;
import com.marshalchen.ultimaterecyclerview.draggable.RecyclerViewTouchActionGuardManager;
import com.marshalchen.ultimaterecyclerview.swipeable.RecyclerViewSwipeManager;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;


public class DragActivity extends ActionBarActivity implements ActionMode.Callback {

    RecyclerView ultimateRecyclerView;
    DragAdatper simpleRecyclerViewAdapter = null;
    LinearLayoutManager linearLayoutManager;
    int moreNum = 2;
    private ActionMode actionMode;

    Toolbar toolbar;
    boolean isDrag = true;
    private RecyclerViewSwipeManager mRecyclerViewSwipeManager;
    private RecyclerViewDragDropManager mRecyclerViewDragDropManager;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewTouchActionGuardManager mRecyclerViewTouchActionGuardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ultimateRecyclerView = (RecyclerView) findViewById(R.id.ultimate_recycler_view);
        ultimateRecyclerView.setHasFixedSize(false);
        List<String> stringList = new ArrayList<>();

//        stringList.add("111");
//        stringList.add("aaa");
//        stringList.add("222");
//        stringList.add("33");
//        stringList.add("44");
//        stringList.add("55");
//        stringList.add("66");
//        stringList.add("11771");
        stringList.add("aa");
        stringList.add("bb");
        stringList.add("cc");
        stringList.add("dd");
        List<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(4);
        data.add(15);
        simpleRecyclerViewAdapter = new DragAdatper(ultimateRecyclerView, data);


        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);

        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);


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
