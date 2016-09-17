package com.marshalchen.ultimaterecyclerview.demo.FragmentTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.griddemo.TypedAdapter;
import com.marshalchen.ultimaterecyclerview.demo.modules.JRitem;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;
import com.ml93.captainmiaoUtil.ui.floatingactionbutton.JellyBeanFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 2016/9/15.
 */

public class FragmentURV extends Fragment {
    private UltimateRecyclerView listuv;
    private TypedAdapter mGridAdapter;
    private GridLayoutManager mGridLayoutManager;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listuv = (UltimateRecyclerView) view.findViewById(R.id.example_lv_list);
        // mGridAdapter = new TypedAdapter(SampleDataboxset.genJRList(2));
        mGridAdapter = new TypedAdapter();
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mGridAdapter.getItemViewType(position) == UltimateViewAdapter.VIEW_TYPES.NORMAL) {
                    return 1;
                } else {
                    return mGridLayoutManager.getSpanCount();
                }
            }
        });
        listuv.setLoadMoreView(R.layout.custom_bottom_progressbar);
        listuv.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                //   Log.d(TAG, itemsCount + " :: " + itemsCount);
                listuv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mGridAdapter.insert(SampleDataboxset.genJRList(5));
                    }
                }, 2000);
            }
        });
        listuv.setHasFixedSize(true);
        listuv.setSaveEnabled(true);
        listuv.setClipToPadding(true);
        listuv.setNormalHeader(setupHeaderView());
        listuv.setLayoutManager(mGridLayoutManager);
        listuv.postDelayed(new Runnable() {
            @Override
            public void run() {
                listuv.mSwipeRefreshLayout.setEnabled(true);
                listuv.setRefreshing(false);
                mGridAdapter.insert(getJRList());
            }
        }, 3300);
        listuv.setAdapter(mGridAdapter);
        listuv.setRefreshing(true);
        listuv.setItemAnimator(new DefaultItemAnimator());
        otherbuttons((JellyBeanFloatingActionButton) view.findViewById(R.id.custom_urv_add_floating_button));
    }

    private void otherbuttons(JellyBeanFloatingActionButton button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FragmentGridHeader.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private List<JRitem> getJRList() {
        List<JRitem> team = new ArrayList<>();
        //you can make your own test for starting-zero-data
        team = SampleDataboxset.genJRList(4);
        return team;
    }

    private View setupHeaderView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.header_love, null, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.swipe_list_view_activity, container, false);
    }

}
