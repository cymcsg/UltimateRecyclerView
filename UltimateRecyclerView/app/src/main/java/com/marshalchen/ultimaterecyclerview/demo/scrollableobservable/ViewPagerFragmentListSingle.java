package com.marshalchen.ultimaterecyclerview.demo.scrollableobservable;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.R;

/**
 * Created by hesk on 12/6/15.
 */
public class ViewPagerFragmentListSingle extends BaseFragment {
    protected ObservableScrollViewCallbacks parent_fragment;

    public static String TAG = "thisWork";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.listurv, container, false);

        final UltimateRecyclerView recyclerView = (UltimateRecyclerView) view.findViewById(R.id.scroll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setRefreshing(false);
        setDummyData(recyclerView);
        ViewPagerTabFragmentParentFragment parentFragment = (ViewPagerTabFragmentParentFragment) getParentFragment();
        ViewGroup parentFView = (ViewGroup) parentFragment.getView();
        if (parentFragment != null) {
            recyclerView.setTouchInterceptionViewGroup((ViewGroup) parentFView.findViewById(R.id.container));
            if (parentFragment instanceof ObservableScrollViewCallbacks) {
                recyclerView.setScrollViewCallbacks(parentFragment);
                Log.d(TAG, "this is ObservableScrollViewCallbacks");
            }
        }

        return view;
    }
}
