package com.marshalchen.ultimaterecyclerview.demo.admobdemo;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.quickAdapter.AdItemHolder;

/**
 * Created by hesk on 24/2/16.
 */
public class SingleItemHolder extends AdItemHolder implements
        View.OnClickListener, View.OnLongClickListener {
    public TextView textViewSample, num;
    public ImageView imageViewSample;
    public ProgressBar progressBarSample;

    public SingleItemHolder(View itemView, int type) {
        super(itemView, type);
    }

    @Override
    protected void bindNormal(View view) {
        textViewSample = (TextView) itemView.findViewById(R.id.textview);
        num = (TextView) itemView.findViewById(R.id.numb_coun);
        imageViewSample = (ImageView) itemView.findViewById(R.id.imageview);
        progressBarSample = (ProgressBar) itemView.findViewById(R.id.progressbar);
        progressBarSample.setVisibility(View.GONE);
    }

    @Override
    protected void bindAd(View view) {

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
