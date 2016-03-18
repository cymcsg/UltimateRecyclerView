package com.marshalchen.ultimaterecyclerview.demo.admobdemo;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.quickAdapter.AdItemHolder;

/**
 * Created by hesk on 24/2/16.
 */
public class SingleItemCommonLayout extends AdItemHolder {
    public TextView textViewSample;
    public ImageView imageViewSample;
    public ProgressBar progressBarSample;
    public RelativeLayout item_view;
    public SingleItemCommonLayout(View itemView, int type) {
        super(itemView, type);
    }

    @Override
    protected void bindNormal(View view) {
        textViewSample = (TextView) itemView.findViewById(R.id.str_textview_holder);
        imageViewSample = (ImageView) itemView.findViewById(R.id.str_image_holder);
        progressBarSample = (ProgressBar) itemView.findViewById(R.id.str_progress_holder);
        item_view = (RelativeLayout) itemView.findViewById(R.id.str_item_view);

    }

    @Override
    protected void bindAd(View view) {

    }


    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }
}
