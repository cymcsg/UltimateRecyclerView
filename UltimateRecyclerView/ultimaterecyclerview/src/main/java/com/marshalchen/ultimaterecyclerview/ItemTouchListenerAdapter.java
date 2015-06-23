/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package com.marshalchen.ultimaterecyclerview;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * As RecyclerView does not have standard way to add click listeners to the items,
 * this RecyclerView.OnItemTouchListener intercepts touch events and translates them to simple onItemClick and onItemLongClick callbacks.
 * <p/>
 * Simply add it as follows:
 * <pre>
 * {@code
 *     recyclerView.addOnItemTouchListener(new ItemTouchListenerAdapter(recyclerView, this));
 *
 * }
 * </pre>
 */
public  class ItemTouchListenerAdapter extends GestureDetector.SimpleOnGestureListener implements RecyclerView.OnItemTouchListener {


    public interface RecyclerViewOnItemClickListener {
        void onItemClick(RecyclerView parent, View clickedView, int position);

        void onItemLongClick(RecyclerView parent, View clickedView, int position);
    }

    private RecyclerViewOnItemClickListener listener;
    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;

    public ItemTouchListenerAdapter(RecyclerView recyclerView, RecyclerViewOnItemClickListener listener) {
        if (recyclerView == null || listener == null) {
            throw new IllegalArgumentException("RecyclerView and Listener arguments can not be null");
        }
        this.recyclerView = recyclerView;
        this.listener = listener;
        this.gestureDetector = new GestureDetector(recyclerView.getContext(), this);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }

    @Override
    public void  onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
    @Override
    public void onShowPress(MotionEvent e) {
        View view = getChildViewUnder(e);
        if (view != null) {
            view.setPressed(true);
        }
    }

    /**
     * case out and fix the bug from offseted number from AdmobAdapter
     * fixed by jjhesk
     * one more thing is that the first item display in the list got to be clickable. 
     * 
     * @param position input position
     * @return AdmobAdapter.POSITION_ON_AD meaning that the touch position is on the position of Adview
     */
    private int shiftAdjustInt(int position) {
        if (recyclerView.getAdapter() instanceof AdmobAdapter && position > 0 ) {
            AdmobAdapter adp = (AdmobAdapter) recyclerView.getAdapter();
            return adp.isPosOnAdView(position) ? AdmobAdapter.POSITION_ON_AD : adp.getFinalShiftPosition(position);
        } else {
            return position;
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        View view = getChildViewUnder(e);
        if (view == null) return false;

        view.setPressed(false);
        int position = shiftAdjustInt(recyclerView.getChildPosition(view));
        if (position != AdmobAdapter.POSITION_ON_AD) {
            listener.onItemClick(recyclerView, view, position);
        }
        return true;
    }

    public void onLongPress(MotionEvent e) {
        View view = getChildViewUnder(e);
        if (view == null) return;
        int position = shiftAdjustInt(recyclerView.getChildPosition(view));
        if (position != AdmobAdapter.POSITION_ON_AD) {
            listener.onItemLongClick(recyclerView, view, position);
        }
        view.setPressed(false);
    }

    @Nullable
    private View getChildViewUnder(MotionEvent e) {
        return recyclerView.findChildViewUnder(e.getX(), e.getY());
    }

}
