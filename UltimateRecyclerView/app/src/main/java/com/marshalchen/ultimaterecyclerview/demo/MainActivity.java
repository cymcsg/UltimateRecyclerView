package com.marshalchen.ultimaterecyclerview.demo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import com.marshalchen.ultimaterecyclerview.Logs;
import com.marshalchen.ultimaterecyclerview.SwipeToDismissTouchListener;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.animators.BaseItemAnimator;
import com.marshalchen.ultimaterecyclerview.animators.*;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements ActionMode.Callback {

    UltimateRecyclerView ultimateRecyclerView;
    SimpleAdapter simpleRecyclerViewAdapter = null;
    LinearLayoutManager linearLayoutManager;
    int moreNum = 100;
    private ActionMode actionMode;
    DragDropTouchListener dragDropTouchListener;
    ItemTouchListenerAdapter itemTouchListenerAdapter;
    Toolbar toolbar;
    boolean isDrag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ultimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.ultimate_recycler_view);
        ultimateRecyclerView.setHasFixedSize(false);
        List<String> stringList = new ArrayList<>();
        simpleRecyclerViewAdapter = new SimpleAdapter(stringList);
        simpleRecyclerViewAdapter.setCustomLoadMoreView(LayoutInflater.from(this)
                .inflate(R.layout.custom_bottom_progressbar, null));
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
        ultimateRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRecyclerViewAdapter.insert("Refresh things", 0);
                        ultimateRecyclerView.setRefreshing(false);
                        //   ultimateRecyclerView.scrollBy(0, -50);
                        linearLayoutManager.scrollToPosition(0);
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
                        linearLayoutManager.scrollToPosition(maxLastVisiblePosition + 1);
                    }
                }, 1000);
            }
        });

        itemTouchListenerAdapter = new ItemTouchListenerAdapter(ultimateRecyclerView.mRecyclerView,
                new ItemTouchListenerAdapter.RecyclerViewOnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View clickedView, int position) {
                        Logs.d("onItemClick()");
                        if (actionMode != null && isDrag) {
                            toggleSelection(position);
                        }
                    }

                    @Override
                    public void onItemLongClick(RecyclerView parent, View clickedView, int position) {
                        Logs.d("onItemLongClick()" + isDrag);
                        if (isDrag) {
                            Logs.d("onItemLongClick()" + isDrag);
                            toolbar.startActionMode(MainActivity.this);
                            toggleSelection(position);
                            dragDropTouchListener.startDrag();
                            ultimateRecyclerView.enableSwipeRefresh(false);
                        }

                    }
                });
        ultimateRecyclerView.mRecyclerView.addOnItemTouchListener(itemTouchListenerAdapter);

        ultimateRecyclerView.setSwipeToDismissCallback(new SwipeToDismissTouchListener.DismissCallbacks() {
            @Override
            public SwipeToDismissTouchListener.SwipeDirection dismissDirection(int position) {
                return SwipeToDismissTouchListener.SwipeDirection.BOTH;
            }

            @Override
            public void onDismiss(RecyclerView view, List<SwipeToDismissTouchListener.PendingDismissData> dismissData) {
                for (SwipeToDismissTouchListener.PendingDismissData data : dismissData) {
                    simpleRecyclerViewAdapter.remove(data.position);
                }
            }

            @Override
            public void onResetMotion() {
                isDrag = true;
            }

            @Override
            public void onTouchDown() {
                isDrag = false;

            }
        });


        dragDropTouchListener = new DragDropTouchListener(ultimateRecyclerView.mRecyclerView, this) {
            @Override
            protected void onItemSwitch(RecyclerView recyclerView, int from, int to) {
                simpleRecyclerViewAdapter.swapPositions(from, to);
                simpleRecyclerViewAdapter.clearSelection(from);
                simpleRecyclerViewAdapter.notifyItemChanged(to);
                if (actionMode != null) actionMode.finish();
                Logs.d("switch----");
            }

            @Override
            protected void onItemDrop(RecyclerView recyclerView, int position) {
                Logs.d("drop----");
                ultimateRecyclerView.enableSwipeRefresh(true);
            }
        };
        dragDropTouchListener.setCustomDragHighlight(getResources().getDrawable(R.drawable.custom_drag_frame));
        ultimateRecyclerView.mRecyclerView.addOnItemTouchListener(dragDropTouchListener);


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
                Logs.d("selected---" + Type.values()[position].getTitle());
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

    }

    private void toggleSelection(int position) {
        simpleRecyclerViewAdapter.toggleSelection(position);
        actionMode.setTitle("Selected " + "1");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        Logs.d("actionmode---" + (mode == null));
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
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SwipeBottomActivity.class);
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
