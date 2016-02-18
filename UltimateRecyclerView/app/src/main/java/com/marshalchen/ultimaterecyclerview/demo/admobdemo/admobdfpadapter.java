package com.marshalchen.ultimaterecyclerview.demo.admobdemo;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.marshalchen.ultimaterecyclerview.AdmobAdapter;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.basicdemo.binderCommon;

import java.util.List;

/**
 * Created by hesk on 20/5/15.
 */
public class admobdfpadapter extends AdmobAdapter<AdView, String, binderCommon> {
    public admobdfpadapter(AdView v, int e, List<String> f) {
        super(v, false, e, f);
    }

    public admobdfpadapter(AdView v, int e, List<String> f, AdviewListener listener) {
        super(v, false, e, f, listener);
    }


    @Override
    protected int getNormalLayoutResId() {
        return R.layout.recycler_view_adapter;
    }

    @Override
    protected binderCommon newViewHolder(View mview) {
        return new binderCommon(mview, true);
    }

    /**
     * no sticky header
     *
     * @param position pos
     * @return none
     */
    @Override
    public long generateHeaderId(int position) {
        URLogs.d("position--" + position + "   " + getItem(position));
    /*    if (getItem(position).length() > 0)
            return getItem(position).charAt(0);
        else
            */
        return -1;
    }


    @Override
    public void onBindViewHolder(UltimateRecyclerviewViewHolder holder, int position) {
        if (onActionToBindData(position, list)) {
            ((binderCommon) holder).textViewSample.setText((String) list.get(getDataArrayPosition(position)));
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stick_header_item, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        TextView textView = (TextView) viewHolder.itemView;
        textView.setText(String.valueOf(getItem(i).charAt(0)));
        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AA70DB93"));
    }

    public String getItem(int position) {
        return (String) super.getItem(position);
    }


    @Override
    public long getHeaderId(int position) {
        if (position == 0) {
            return -1;
        } else {
            return getItem(position).charAt(0);
        }
    }


    @Override
    public UltimateRecyclerviewViewHolder getViewHolder(View view) {
        return new UltimateRecyclerviewViewHolder(view);
    }

    public void swapPositions(int from, int to) {
        swapPositions(list, from, to);
    }
}
