package com.marshalchen.ultimaterecyclerview.swipe;


import android.support.v7.widget.RecyclerView;


import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SwipeItemMangerImpl is a helper class to help maintain swipe status
 */
public class SwipeItemManagerImpl implements SwipeItemManagerInterface {
    public final int INVALID_POSITION = -1;

    private Mode mode = Mode.Single;

    protected int mOpenPosition = INVALID_POSITION;

    protected Set<Integer> mOpenPositions = new HashSet<>();
    protected Set<SwipeLayout> mShownLayouts = new HashSet<>();

    protected RecyclerView.Adapter mAdapter;

    public SwipeItemManagerImpl(RecyclerView.Adapter adapter) {
        if (adapter == null)
            throw new IllegalArgumentException("Adapter can not be null");

        if (!(adapter instanceof SwipeItemManagerInterface))
            throw new IllegalArgumentException("adapter should implement the SwipeAdapterInterface");

        this.mAdapter = adapter;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        mOpenPositions.clear();
        mShownLayouts.clear();
        mOpenPosition = INVALID_POSITION;
    }


    private void initialize(UltimateRecyclerviewViewHolder targetViewHolder, int position) {
        targetViewHolder.onLayoutListener = new OnLayoutListener(position);
        targetViewHolder.swipeMemory = new SwipeMemory(position);
        targetViewHolder.position = position;

        targetViewHolder.swipeLayout.addSwipeListener(targetViewHolder.swipeMemory);
        targetViewHolder.swipeLayout.addOnLayoutListener(targetViewHolder.onLayoutListener);
    }


    public void updateConvertView(UltimateRecyclerviewViewHolder targetViewHolder, int position) {
        if (targetViewHolder.onLayoutListener == null) {
            initialize(targetViewHolder, position);
        }

        SwipeLayout swipeLayout = targetViewHolder.swipeLayout;
        if (swipeLayout == null)
            throw new IllegalStateException("can not find SwipeLayout in target view");

        mShownLayouts.add(swipeLayout);

        ((SwipeMemory) targetViewHolder.swipeMemory).setPosition(position);
        ((OnLayoutListener) targetViewHolder.onLayoutListener).setPosition(position);
        targetViewHolder.position = position;
    }

    @Override
    public void openItem(int position) {
        if (mode == Mode.Multiple) {
            if (!mOpenPositions.contains(position))
                mOpenPositions.add(position);
        } else {
            mOpenPosition = position;
        }
    }

    @Override
    public void closeItem(int position) {
        if (mode == Mode.Multiple) {
            mOpenPositions.remove(position);
        } else {
            if (mOpenPosition == position)
                mOpenPosition = INVALID_POSITION;
        }
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        for (SwipeLayout s : mShownLayouts) {
            if (s != layout)
                s.close();
        }
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        mShownLayouts.remove(layout);
    }

    @Override
    public List<Integer> getOpenItems() {
        if (mode == SwipeItemManagerInterface.Mode.Multiple) {
            return new ArrayList<>(mOpenPositions);
        } else {
            return Arrays.asList(mOpenPosition);
        }
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return new ArrayList<>(mShownLayouts);
    }

    @Override
    public boolean isOpen(int position) {
        if (mode == Mode.Multiple) {
            return mOpenPositions.contains(position);
        } else {
            return mOpenPosition == position;
        }
    }

    private class OnLayoutListener implements SwipeLayout.OnLayout {

        private int position;

        OnLayoutListener(int position) {
            this.position = position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onLayout(SwipeLayout v) {
            if (isOpen(position)) {
                v.open(false, false);
            } else {
                v.close(false, false);
            }
        }

    }

    private class SwipeMemory extends SimpleSwipeListener {

        private int position;

        SwipeMemory(int position) {
            this.position = position;
        }

        @Override
        public void onClose(SwipeLayout layout) {
            if (mode == Mode.Multiple) {
                mOpenPositions.remove(position);
            } else {
                mOpenPosition = INVALID_POSITION;
            }
        }

        @Override
        public void onStartOpen(SwipeLayout layout) {
            if (mode == Mode.Single) {
                closeAllExcept(layout);
            }
        }

        @Override
        public void onOpen(SwipeLayout layout) {
            if (mode == SwipeItemManagerInterface.Mode.Multiple)
                mOpenPositions.add(position);
            else {
                closeAllExcept(layout);
                mOpenPosition = position;
            }
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

}


