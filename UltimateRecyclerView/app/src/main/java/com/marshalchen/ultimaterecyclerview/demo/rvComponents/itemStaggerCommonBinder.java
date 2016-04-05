package com.marshalchen.ultimaterecyclerview.demo.rvComponents;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.quickAdapter.StaggerHolder;

/**
 * Created by hesk on 5/4/16.
 */
public class itemStaggerCommonBinder extends StaggerHolder {
    public static final int layout = R.layout.rv_item_stagger;

    public TextView textViewSample;
    public ImageView imageViewSample;
    public ProgressBar progressBarSample;
    public RelativeLayout item_view;

    public itemStaggerCommonBinder(View itemView, int type) {
        super(itemView, type);
    }

    @Override
    protected void bindHeader(View view) {

    }

    @Override
    protected void bindFooter(View view) {

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
}
