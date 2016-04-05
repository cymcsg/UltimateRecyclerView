package com.marshalchen.ultimaterecyclerview.appPaginator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;
import com.marshalchen.ultimaterecyclerview.layoutmanagers.ScrollSmoothLineaerLayoutManager;

/**
 * Created by hesk on 30/6/15.
 */
public abstract class catelogLinear<adapter extends easyRegularAdapter, binder extends UltimateRecyclerviewViewHolder> extends paginator {
    public static String TAG = "catelog";
    public final static String BRAND_NAME = "BrandName", SLUG = "slug", REQUEST_TYPE = "typerequest";
    public UltimateRecyclerView listview_layout;
    public static String URL = "data_url";
    public static String FRAGMENTTITLE = "fragment_title";
    public static String SAVELOADDATA = "item_list";
    public static String HASSAVEFILTER = "filter";
    protected LinearLayoutManager mLayoutManager;
    protected adapter madapter;

    protected abstract adapter getAdatperWithdata();

    protected abstract void setUltimateRecyclerViewExtra(final UltimateRecyclerView listview, final adapter madapter);

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getFragmentResId(), container, false);
    }

    @IdRes
    protected int getUltimate_recycler_viewResId() {
        return R.id.urv_main_list;
    }

    @IdRes
    protected int getRefresherProgressBarId() {
        return R.id.urv_main_progress_bar;
    }

    /**
     * step 1:
     * takes the arguement form the intent bundle and determine if there is a need to queue a loading process. If that is a yes then we need to load up the data before displaying the list out.
     *
     * @param r and the data bundle
     * @return tells if  there is a loading process to be done before hand
     */
    protected abstract boolean onArguments(final Bundle r);

    /**
     * step 2:
     * this is the call for the loading the data stream externally
     *
     * @param confirmAdapter the adapter
     */
    protected abstract void loadDataInitial(final adapter confirmAdapter);


    protected void renderviewlayout(View view) throws Exception {
        listview_layout = (UltimateRecyclerView) view.findViewById(getUltimate_recycler_viewResId());
        listview_layout.setHasFixedSize(true);
        listview_layout.setSaveEnabled(true);
        if (mLayoutManager == null) {
            mLayoutManager = new ScrollSmoothLineaerLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false, getSmoothDuration());
        }
        listview_layout.setLayoutManager(mLayoutManager);
        listview_layout.setAdapter(madapter = getAdatperWithdata());
        getProgressbar(view);
        setUltimateRecyclerViewExtra(listview_layout, madapter);
    }

    protected int getSmoothDuration() {
        return 300;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        try {
            renderviewlayout(view);
            if (getArguments() != null) {
                if (onArguments(getArguments())) {
                    showLoadingCircle();
                    initPaginator();
                    loadDataInitial(madapter);
                }
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

    }


}