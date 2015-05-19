package com.marshalchen.ultimaterecyclerview.demo.modules;

import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateDifferentViewTypeAdapter;
import com.marshalchen.ultimaterecyclerview.demo.R;

import java.util.ArrayList;
import java.util.List;

public class MultiViewTypesRecyclerViewAdapter extends UltimateDifferentViewTypeAdapter {
    private List<String> mDataset;

    private enum SwipedState {
        SHOWING_PRIMARY_CONTENT,
        SHOWING_SECONDARY_CONTENT
    }

    private List<SwipedState> mItemSwipedStates;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends UltimateRecyclerviewViewHolder {
        // each data item is just a string in this case
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MultiViewTypesRecyclerViewAdapter(List<String> dataSet) {
        mDataset = dataSet;
        mItemSwipedStates = new ArrayList<>();
        for (int i = 0; i < dataSet.size(); i++) {
            mItemSwipedStates.add(i, SwipedState.SHOWING_PRIMARY_CONTENT);
        }

        putBinder(SampleViewType.SAMPLE1, new Sample1Binder(this));
        putBinder(SampleViewType.SAMPLE2, new Sample2Binder(this));
        putBinder(SampleViewType.SAMPLE3, new Sample1Binder(this));
        ((Sample2Binder) getDataBinder(SampleViewType.SAMPLE2)).addAll(dataSet);
    }

//    // Create new views (invoked by the layout manager)
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent,
//                                                   int viewType) {
//        // Create a new view which is basically just a ViewPager in this case
//        ViewPager v = (ViewPager) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_item, parent, false);
//        ViewPagerAdapter adapter = new ViewPagerAdapter();
//
//        ((ViewPager) v.findViewById(R.id.viewPager)).setAdapter(adapter);
//
//        //Perhaps the first most crucial part. The ViewPager loses its width information when it is put
//        //inside a RecyclerView. It needs to be explicitly resized, in this case to the width of the
//        //screen. The height must be provided as a fixed value.
//        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
//        v.getLayoutParams().width = displayMetrics.widthPixels;
//        v.requestLayout();
//
//        ViewHolder vh = new ViewHolder(v);
//        return vh;
//    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//        ((TextView) holder.mView.findViewById(R.id.txt)).setText(mDataset[position]);
//
//        Log.i("MyAdapter", "PagePosition " + position + " set to " + mItemSwipedStates.get(position).ordinal());
//        ((ViewPager) holder.mView).setCurrentItem(mItemSwipedStates.get(position).ordinal());
//
//        ((ViewPager) holder.mView).setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            int previousPagePosition = 0;
//
//            @Override
//            public void onPageScrolled(int pagePosition, float positionOffset, int positionOffsetPixels) {
//                if (pagePosition == previousPagePosition)
//                    return;
//
//                switch (pagePosition) {
//                    case 0:
//                        mItemSwipedStates.set(position, SwipedState.SHOWING_PRIMARY_CONTENT);
//                        break;
//                    case 1:
//                        mItemSwipedStates.set(position, SwipedState.SHOWING_SECONDARY_CONTENT);
//                        break;
//
//                }
//                previousPagePosition = pagePosition;
//
//                Log.i("MyAdapter", "PagePosition " + position + " set to " + mItemSwipedStates.get(position).ordinal());
//            }
//
//            @Override
//            public void onPageSelected(int pagePosition) {
//                //This method keep incorrectly firing as the RecyclerView scrolls.
//                //Use the one above instead
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getAdapterItemCount() {
        return 0;
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public Enum getEnumFromPosition(int position) {
        if (position == 1) {
            return SampleViewType.SAMPLE1;
        } else if (position == 3) {
            return SampleViewType.SAMPLE3;
        } else {
            return SampleViewType.SAMPLE2;
        }
    }

    @Override
    public Enum getEnumFromOrdinal(int ordinal) {
        return SampleViewType.values()[ordinal];
    }
    enum SampleViewType {
        SAMPLE1, SAMPLE2, SAMPLE3
    }


}