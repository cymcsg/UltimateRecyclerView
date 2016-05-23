package com.marshalchen.ultimaterecyclerview.demo.griddemo;

import android.os.Bundle;
import android.os.Handler;

import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;

/**
 * This is the fix the off screen detection issue
 * Created by hesk on 23/5/16.
 */
public class GridTestOnlyOnePage extends GridLayoutRVTest {

    Handler poster = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        poster.postDelayed(new Runnable() {
            @Override
            public void run() {
                listuv.reenableLoadmore();
                mGridAdapter.insert(SampleDataboxset.genJRList(5));
            }
        }, 1000);
    }

    @Override
    protected void afterAdd() {
        listuv.disableLoadmore();
    }
}
