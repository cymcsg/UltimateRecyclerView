package com.marshalchen.ultimaterecyclerview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerImpl;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout;

import java.util.Collections;
import java.util.List;

/**
 * An abstract adapter which can be extended for Recyclerview
 */
public abstract class SwipeableUltimateViewAdapter<T>
        extends easyRegularAdapter<T, UltimateRecyclerviewViewHolder>
        implements SwipeItemManagerInterface {

    public SwipeableUltimateViewAdapter(List<T> list) {
        super(list);
    }


    protected SwipeItemManagerImpl mItemManger = new SwipeItemManagerImpl(this);

    /**
     * binding normal view holder
     *
     * @param holder   holder class
     * @param data     data
     * @param position position
     */
    @Override
    protected void withBindHolder(UltimateRecyclerviewViewHolder holder, T data, int position) {
        mItemManger.updateConvertView(holder, position);
    }

    @Override
    protected void onBindAdViewHolder(RecyclerView.ViewHolder holder, int pos) {
        mItemManger.updateConvertView((UltimateRecyclerviewViewHolder) holder, pos);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int pos) {
        mItemManger.updateConvertView((UltimateRecyclerviewViewHolder) holder, pos);
    }

    @Override
    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int pos) {
      //  mItemManger.updateConvertView((UltimateRecyclerviewViewHolder) holder, pos);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int pos) {
       // mItemManger.updateConvertView((UltimateRecyclerviewViewHolder) holder, pos);
    }

    @Override
    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        mItemManger.closeAllExcept(layout);
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        mItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    @Override
    public SwipeItemManagerImpl.Mode getMode() {
        return mItemManger.getMode();
    }

    @Override
    public void setMode(SwipeItemManagerImpl.Mode mode) {
        mItemManger.setMode(mode);
    }

    public static class BaseSwipeableViewHolder extends RecyclerView.ViewHolder {

        public SwipeLayout swipeLayout = null;
        public SwipeLayout.OnLayout onLayoutListener = null;
        public SwipeLayout.SwipeListener swipeMemory = null;
        public int position = -1;

        public BaseSwipeableViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.recyclerview_swipe);
        }
    }


    @Override
    public void insert(List<T> new_data) {
        super.insert(new_data);
        closeAllExcept(null);
    }


    @Override
    public void removeAll() {
        super.removeAll();
    }
}
