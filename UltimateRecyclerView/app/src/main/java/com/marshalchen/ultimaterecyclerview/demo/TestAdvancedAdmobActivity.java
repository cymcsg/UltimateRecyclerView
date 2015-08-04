package com.marshalchen.ultimaterecyclerview.demo;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.marshalchen.ultimaterecyclerview.AdmobAdapter;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;
import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;
import com.marshalchen.ultimaterecyclerview.quickAdapter.simpleAdmobAdapter;
import com.marshalchen.ultimaterecyclerview.quickAdapter.switchableadapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 4/8/15.
 */
public class TestAdvancedAdmobActivity extends AppCompatActivity {
    private UltimateRecyclerView ultimateRecyclerView;
    private boolean admob_test_mode = false;
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;

    public static class adap extends simpleAdmobAdapter<String, VMoler, AdView> {

        public adap(AdView adview, boolean insertOnce, int setInterval, List<String> L) {
            super(adview, insertOnce, setInterval, L);
        }

        public adap(AdView adview, boolean insertOnce, int setInterval, List<String> L, AdviewListener listener) {
            super(adview, insertOnce, setInterval, L, listener);
        }

        @Override
        protected void withBindHolder(VMoler var1, String var2, int var3) {
            bindthisInhere(var1, var2, var3);
        }

        @Override
        protected int getNormalLayoutResId() {
            return R.layout.recycler_view_adapter;
        }


        @Override
        protected VMoler newViewHolder(View mview) {
            return new VMoler(mview);
        }

        @Override
        public UltimateRecyclerviewViewHolder getViewHolder(View view) {
            return new UltimateRecyclerviewViewHolder(view);
        }
    }

    public static class regular extends easyRegularAdapter<String, VMoler> {

        public regular(List list) {
            super(list);
        }

        @Override
        protected int getNormalLayoutResId() {
            return R.layout.recycler_view_adapter;
        }

        @Override
        protected VMoler newViewHolder(View view) {
            return new VMoler(view);
        }

        @Override
        public UltimateRecyclerviewViewHolder getViewHolder(View view) {
            return new UltimateRecyclerviewViewHolder(view);
        }

        @Override
        protected void withBindHolder(VMoler holderm, String data, int position) {
            bindthisInhere(holderm, data, position);
        }
    }

    private static void bindthisInhere(VMoler d, String data, int pos) {
        d.textViewSample.setText(data);
    }


    private AdView createadmob() {

        AdSize adSize = AdSize.SMART_BANNER;

        DisplayMetrics dm = getResources().getDisplayMetrics();

        double density = dm.density * 160;
        double x = Math.pow(dm.widthPixels / density, 2);
        double y = Math.pow(dm.heightPixels / density, 2);
        double screenInches = Math.sqrt(x + y);

        if (screenInches > 8) { // > 728 X 90
            adSize = AdSize.LEADERBOARD;
        } else if (screenInches > 6) { // > 468 X 60
            adSize = AdSize.MEDIUM_RECTANGLE;
        } else { // > 320 X 50
            adSize = AdSize.BANNER;
        }


        AdView mAdView = new AdView(this);
        mAdView.setAdSize(adSize);
        mAdView.setAdUnitId("/1015938/Hypebeast_App_320x50");
        mAdView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // Create an ad request.
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        if (admob_test_mode)
            // Optionally populate the ad request builder.
            adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        // Start loading the ad.
        mAdView.loadAd(adRequestBuilder.build());

        return mAdView;
    }


    public static class VMoler extends UltimateRecyclerviewViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        public TextView textViewSample;
        public ImageView imageViewSample;
        public ProgressBar progressBarSample;

        public VMoler(View itemView) {
            super(itemView);
            textViewSample = (TextView) itemView.findViewById(R.id.textview);
            imageViewSample = (ImageView) itemView.findViewById(R.id.imageview);
            progressBarSample = (ProgressBar) itemView.findViewById(R.id.progressbar);
            progressBarSample.setVisibility(View.GONE);
        }

        @Override
        public void onClick(@NonNull View v) {
            URLogs.d(textViewSample.getText() + " clicked!");
        }

        @Override
        public boolean onLongClick(@NonNull View v) {
            URLogs.d(textViewSample.getText() + " long clicked!");
            return true;
        }


    }


    /**
     * example 1 implementation of the switch view
     */
    private switchableadapter imple_switch_view(final UltimateRecyclerView rv) {
        final adap adp1 = new adap(createadmob(), false, 4, new ArrayList<String>(), new AdmobAdapter.AdviewListener() {
            @Override
            public AdView onGenerateAdview() {
                return createadmob();
            }
        });
        final regular adp2 = new regular(new ArrayList<String>());
        final switchableadapter switchable = new switchableadapter(rv, adp2, adp1);
        return switchable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ultimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.ultimate_recycler_view);
        ultimateRecyclerView.setHasFixedSize(false);

        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#ffffff"));


        /**
         *  example 2 implementation enhancement of list view
         */
        final switchableadapter sw = imple_switch_view(ultimateRecyclerView)
                .onEnableRefresh(100)
                .onEnableLoadmore(R.layout.custom_bottom_progressbar, 100, new switchableadapter.onLoadMore() {
                    @Override
                    public boolean request_start(int current_page_no, int itemsCount, int maxLastVisiblePosition, switchableadapter this_module) {
                        this_module.load_more_data(SampleDataboxset.newList());
                        return true;
                    }
                });

        TextView b = (TextView) findViewById(R.id.del);
        b.setText("with Ad");
        TextView a = (TextView) findViewById(R.id.add);
        a.setText("with out Ad");
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *  example 2 implementation enhancement of list view
                 */
                sw.init(false);
            }

        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *  example 2 implementation enhancement of list view
                 */
                sw.init(true);
            }
        });
    }

}
