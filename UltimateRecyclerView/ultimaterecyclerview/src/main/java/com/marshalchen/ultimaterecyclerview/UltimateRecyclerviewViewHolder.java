package com.marshalchen.ultimaterecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.draggable.DraggableItemViewHolder;
import com.marshalchen.ultimaterecyclerview.swipeable.RecyclerViewSwipeManager;
import com.marshalchen.ultimaterecyclerview.swipeable.SwipeableItemViewHolder;

/**
 * Created by cym on 15-6-2.
 */
public class UltimateRecyclerviewViewHolder extends RecyclerView.ViewHolder implements SwipeableItemViewHolder,DraggableItemViewHolder {
    private int mDragStateFlags;
    private int mSwipeStateFlags;
    private int mSwipeResult = RecyclerViewSwipeManager.RESULT_NONE;
    private int mAfterSwipeReaction = RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_DEFAULT;
    private float mSwipeAmount;
    private float mMaxLeftSwipeAmount = RecyclerViewSwipeManager.OUTSIDE_OF_THE_WINDOW_LEFT;
    private float mMaxRightSwipeAmount = RecyclerViewSwipeManager.OUTSIDE_OF_THE_WINDOW_RIGHT;

    public UltimateRecyclerviewViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setSwipeStateFlags(int flags) {
        mSwipeStateFlags = flags;
    }

    @Override
    public int getSwipeStateFlags() {
        return mSwipeStateFlags;
    }

    @Override
    public void setSwipeResult(int result) {
        mSwipeResult = result;
    }

    @Override
    public int getSwipeResult() {
        return mSwipeResult;
    }

    @Override
    public int getAfterSwipeReaction() {
        return mAfterSwipeReaction;
    }

    @Override
    public void setAfterSwipeReaction(int reaction) {
        mAfterSwipeReaction = reaction;
    }

    @Override
    public float getSwipeItemSlideAmount() {
        return mSwipeAmount;
    }

    @Override
    public void setSwipeItemSlideAmount(float amount) {
        mSwipeAmount = amount;
    }

    @Override
    public void setMaxLeftSwipeAmount(float amount) {
        mMaxLeftSwipeAmount = amount;
    }

    @Override
    public float getMaxLeftSwipeAmount() {
        return mMaxLeftSwipeAmount;
    }

    @Override
    public void setMaxRightSwipeAmount(float amount) {
        mMaxRightSwipeAmount = amount;
    }

    @Override
    public float getMaxRightSwipeAmount() {
        return mMaxRightSwipeAmount;
    }

    /**
     * Gets the container view for the swipeable area.
     *
     * @return The container view instance.
     */
    @Override
    public View getSwipeableContainerView() {
        return null;
    }

    @Override
    public void setDragStateFlags(int flags) {
        mDragStateFlags = flags;
    }

    @Override
    public int getDragStateFlags() {
        return mDragStateFlags;
    }

}