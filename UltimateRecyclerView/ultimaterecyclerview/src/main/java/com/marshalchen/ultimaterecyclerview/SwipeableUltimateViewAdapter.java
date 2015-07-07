package com.marshalchen.ultimaterecyclerview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperAdapter;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerImpl;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface;

import java.util.Collections;
import java.util.List;

/**
 * An abstract adapter which can be extended for Recyclerview
 */
public abstract class SwipeableUltimateViewAdapter<VH extends UltimateRecyclerviewViewHolder> extends UltimateViewAdapter implements SwipeItemManagerInterface {

    protected SwipeItemManagerImpl mItemManger = new SwipeItemManagerImpl(this);


    @Override
    public void onBindViewHolder(UltimateRecyclerviewViewHolder holder, int position) {
        mItemManger.updateConvertView(holder, position);
    }


}
