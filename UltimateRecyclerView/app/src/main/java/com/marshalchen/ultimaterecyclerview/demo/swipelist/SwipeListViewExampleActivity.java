/*
 * Copyright (C) 2013 47 Degrees, LLC
 *  http://47deg.com
 *  hello@47deg.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.marshalchen.ultimaterecyclerview.demo.swipelist;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.ItemTouchListenerAdapter;
import com.marshalchen.ultimaterecyclerview.RecyclerItemClickListener;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface;

import java.util.ArrayList;
import java.util.List;

public class SwipeListViewExampleActivity extends FragmentActivity {

    private SwipeAdapter adapter;
    private List<String> data;

    private UltimateRecyclerView swipeListView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.swipe_list_view_activity);

        data = new ArrayList<String>();

        adapter = new SwipeAdapter(data);
        adapter.setMode(SwipeItemManagerInterface.Mode.Single);
        swipeListView = (UltimateRecyclerView) findViewById(R.id.example_lv_list);
        mLayoutManager = new LinearLayoutManager(this);
        swipeListView.setLayoutManager(mLayoutManager);
//        swipeListView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                URLogs.d("click");
//            }
//        }));
        data.add("aa");
        data.add("bb");
        data.add("cc");
        data.add("dd");
        data.add("aa");
        data.add("bb");
        data.add("cc");
        data.add("dd");
        data.add("aa");
        data.add("bb");
        data.add("cc");
        data.add("dd");

        swipeListView.setAdapter(adapter);


    }


}
