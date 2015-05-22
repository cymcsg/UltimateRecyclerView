package com.marshalchen.ultimaterecyclerview.helpers;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;


/**
 * This is the half implementation of the list fragment
 * There we have already done alot of the hard things. This is the aim for the average Joe to do the job much easier and faster.
 * This is the Normal Linear List Fragment
 * Created by hesk on 30/4/15.
 */
public abstract class listFragment<E, adapter extends UltimateViewAdapter> extends Fragment {
    /**
     * preset something that might be useful
     */
    public final static String
            PAGENUM = "PAGE_NUMBER",
            POS = "PAGE_POSITION",
            LOADURL = "LOAD_URL",
            TAG = "LISTFEED";

    private UltimateRecyclerView pager;
    private adapter madapter;
    private LinearLayoutManager linearLayoutManager;


    public listFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getListId(), container, false);
    }

    /**
     * the must do task for setting the pointer to the source list
     *
     * @return the arraylist
     */
    protected abstract ArrayList<E> getListSource();

    /**
     * if nothing is declared normal the item list will be used as default
     * {@docRoot android.R.layout.simple_list_item_2} will be used
     *
     * @return the pointer location in ID
     */
    protected int getListId() {
        return android.R.layout.simple_list_item_2;
    }

    /**
     * allocate the list id of the actual view
     *
     * @return the pointer location in ID
     */
    protected int getListViewId() {
        return android.R.id.list;
    }

    /**
     * creating a new adapter off from UltimateViewAdapter
     *
     * @return the adapter to be used
     */
    protected abstract adapter getAdapter();

    /**
     * You can fill nothing here if you do not think there is features you want to do on to this
     *
     * @param listview the actual  {@link UltimateRecyclerView} that has been already initialized
     */
    protected abstract void configUltimateRecyclerView(final UltimateRecyclerView listview);

    /**
     * the additional settings when {@literal setAdapter}  has been set
     */
    protected void additional_init() {
    }

    /**
     * before the setLayoutManager has been called
     */
    protected void additional_init_after() {
    }

    /**
     * pointing the layout ID location
     *
     * @return the ID of the layout for loading more
     */
    protected int CustomLoadMoreLayoutId() {
        return -1;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(View v, Bundle b) {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        madapter = getAdapter();
        pager = (UltimateRecyclerView) v.findViewById(getListViewId());
        configUltimateRecyclerView(pager);
        additional_init();
        /**
         * LOAD MORE ADD LAYOUT
         */
        if (CustomLoadMoreLayoutId() > 0) {
            View newlayout = LayoutInflater.from(getActivity()).inflate(CustomLoadMoreLayoutId(), null);
            madapter.setCustomLoadMoreView(newlayout);
        }
        pager.setLayoutManager(linearLayoutManager);
        pager.setAdapter(madapter);
        if (CustomLoadMoreLayoutId() > 0) {
            pager.enableLoadmore();
        }
        additional_init_after();
    }
}
