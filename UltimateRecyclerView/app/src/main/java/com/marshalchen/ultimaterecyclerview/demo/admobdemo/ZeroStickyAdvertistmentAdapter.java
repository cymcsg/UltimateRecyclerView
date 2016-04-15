package com.marshalchen.ultimaterecyclerview.demo.admobdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.ads.AdView;
import com.marshalchen.ultimaterecyclerview.quickAdapter.AdmobAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.demo.R;

import java.util.List;

/**
 * Created by hesk on 20/5/15.
 */
public class ZeroStickyAdvertistmentAdapter extends AdmobAdapter<AdView, String, SingleItemCommonLayout> {


    public ZeroStickyAdvertistmentAdapter(AdView v, List<String> f) {
        super(v, false, 12, f);
    }

    public ZeroStickyAdvertistmentAdapter(AdView v, int e, List<String> f, AdviewListener listener) {
        super(v, false, e, f, listener);
    }

    @Override
    protected int getNormalLayoutResId() {
        return SingleItemCommonLayout.layout;
    }

    @Override
    protected SingleItemCommonLayout newViewHolder(View mview) {
        return new SingleItemCommonLayout(mview, VIEW_TYPES.NORMAL);
    }


    /**
     * binding normal view holder
     *
     * @param holder   holder class
     * @param data     data
     * @param position position
     */
    @Override
    protected void withBindHolder(SingleItemCommonLayout holder, String data, int position) {
        holder.textViewSample.setText(data);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        // TextView textView = (TextView) viewHolder.itemView;
        // textView.setText(String.valueOf(getItem(i).charAt(0)));
        //  viewHolder.itemView.setBackgroundColor(Color.parseColor("#AA70DB93"));
    }

/*

    @Override
    public long getHeaderId(int position) {
        if (position == 0) {
            return -1;
        } else {
            return getItem(position).charAt(0);
        }
    }
*/


}
