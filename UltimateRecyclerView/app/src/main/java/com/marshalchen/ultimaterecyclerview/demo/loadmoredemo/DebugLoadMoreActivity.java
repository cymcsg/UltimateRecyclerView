package com.marshalchen.ultimaterecyclerview.demo.loadmoredemo;

import android.graphics.Color;

import com.marshalchen.ultimaterecyclerview.ui.swipe.SwipeableRecyclerViewTouchListener;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.rvComponents.sectionZeroAdapter;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;
import com.marshalchen.ultimaterecyclerview.ui.swipe.defaultRegularSwipe;

import java.util.ArrayList;

/**
 * Created by hesk on 7/1/2015.
 */
public class DebugLoadMoreActivity extends BasicFunctions {

    private sectionZeroAdapter simpleRecyclerViewAdapter = null;

    @Override
    protected void enableEmptyViewPolicy() {
        //  ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER_AND_LOARMORE);
        //    ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER);
        ultimateRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_SHOW_LOADMORE_ONLY);
    }


    @Override
    protected void onLoadmore() {

        //    SampleDataboxset.insertMore(simpleRecyclerViewAdapter, 2);

        SampleDataboxset.insertMoreWhole(simpleRecyclerViewAdapter, 2);

        //  linearLayoutManager.scrollToPositionWithOffset(maxLastVisiblePosition, -1);

        //  linearLayoutManager.scrollToPosition(maxLastVisiblePosition);
        /**
         * this is the example for making the function test for loading more and disable loading more
         */
                        /* if (isEnableAutoLoadMore) {
                            ultimateRecyclerView.enableLoadmore();
                        } else {
                            ultimateRecyclerView.disableLoadmore();
                        }*/
    }

    @Override
    protected void enableSwipe() {
        super.enableSwipe();
        ultimateRecyclerView.addOnItemTouchListener(new SwipeableRecyclerViewTouchListener(ultimateRecyclerView.mRecyclerView, new defaultRegularSwipe<>(simpleRecyclerViewAdapter)));
    }

    @Override
    protected void addButtonTrigger() {
        simpleRecyclerViewAdapter.insertFirst("rand added item");
        ultimateRecyclerView.reenableLoadmore();
    }

    @Override
    protected void removeButtonTrigger() {
        simpleRecyclerViewAdapter.removeLast();
    }

    @Override
    protected void onFireRefresh() {
        // simpleRecyclerViewAdapter.insertLast(moreNum++ + "  Refresh things");
        ultimateRecyclerView.setRefreshing(false);
        //   ultimateRecyclerView.scrollBy(0, -50);
        linearLayoutManager.scrollToPosition(0);
        //ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
        //simpleRecyclerViewAdapter.notifyDataSetChanged();
        simpleRecyclerViewAdapter.removeAll();
        ultimateRecyclerView.disableLoadmore();
        //ultimateRecyclerView.showEmptyView();
    }

    @Override
    protected void doURV(UltimateRecyclerView ultimateRecyclerView) {
        ultimateRecyclerView.setHasFixedSize(false);
        simpleRecyclerViewAdapter = new sectionZeroAdapter(new ArrayList<String>());
        configLinearLayoutManager(ultimateRecyclerView);
        enableParallaxHeader();
        enableEmptyViewPolicy();
        enableLoadMore();
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#ffff66ff"));
        enableRefresh();
        // enableScrollControl();
        // enableSwipe();
        // enableItemClick();
        //ultimateRecyclerView.setItemViewCacheSize(simpleRecyclerViewAdapter.getAdditionalItems());
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
    }


    private void toggleSelection(int position) {
        simpleRecyclerViewAdapter.toggleSelection(position);
        actionMode.setTitle("Selected " + "1");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    //
/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FastBinding.startactivity(this, item.getItemId());
        return super.onOptionsItemSelected(item);
    }*/

  /*  enum Type {
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
    }*/

}
