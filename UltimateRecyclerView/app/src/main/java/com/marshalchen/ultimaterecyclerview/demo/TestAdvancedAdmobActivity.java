package com.marshalchen.ultimaterecyclerview.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.marshalchen.ultimaterecyclerview.quickAdapter.BiAdAdapterSwitcher;

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

    public static class adap extends simpleAdmobAdapter<String, VMoler, LinearLayout> {

        public adap(LinearLayout v, boolean insertOnce, int setInterval, List<String> L, AdviewListener listener) {
            super(v, insertOnce, setInterval, L, listener);
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


    private LinearLayout createadmob() {

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

        adSize = AdSize.MEDIUM_RECTANGLE;
        AdView mAdView = new AdView(this);
        mAdView.setAdSize(adSize);
        mAdView.setAdUnitId("/1015938/Hypebeast_App_320x50");


        // Create an ad request.
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        if (admob_test_mode)
            // Optionally populate the ad request builder.
            adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        // Start loading the ad.
        mAdView.loadAd(adRequestBuilder.build());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        double ratio = ((float) (width)) / 300.0;
        int height = (int) (ratio * 250);
        ViewCompat.setScaleX(mAdView, (float) ratio);
        ViewCompat.setScaleY(mAdView, (float) ratio);
        LinearLayout LL = new LinearLayout(this);
        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        l.gravity = Gravity.CENTER;

        mAdView.setLayoutParams(l);
        LL.addView(mAdView);
        return LL;

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
    private BiAdAdapterSwitcher imple_switch_view(final UltimateRecyclerView rv) {
        final adap adp1 = new adap(createadmob(), false, 4, new ArrayList<String>(),
                new AdmobAdapter.AdviewListener() {
                    @Override
                    public LinearLayout onGenerateAdview() {
                        return createadmob();
                    }
                });
        final regular adp2 = new regular(new ArrayList<String>());
        final BiAdAdapterSwitcher switchable = new BiAdAdapterSwitcher(rv, adp2, adp1);
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
        final BiAdAdapterSwitcher sw = imple_switch_view(ultimateRecyclerView)
                .onEnableRefresh(100)
                .onEnableLoadmore(R.layout.custom_bottom_progressbar, 100, new BiAdAdapterSwitcher.onLoadMore() {
                    @Override
                    public boolean request_start(int current_page_no, int itemsCount, int maxLastVisiblePosition, BiAdAdapterSwitcher this_module) {
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
                 *  without advertisement configurations
                 */
                sw.init(false);
            }

        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *  example 2 implementation enhancement of list view
                 *  with advertisement configuration
                 */
                sw.init(true);
            }
        });
    }

}
