package com.marshalchen.ultimaterecyclerview.demo.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateDifferentViewTypeAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.multiViewTypes.DataBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cym on 15/5/18.
 */
public class Sample2Binder extends DataBinder<Sample2Binder.ViewHolder> {
    private List<String> mList;
    public Sample2Binder(UltimateDifferentViewTypeAdapter dataBindAdapter,List<String> mList) {
        super(dataBindAdapter);
        this.mList=mList;
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycler_view_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position) {
        holder.mTitleText.setText("Sample type 2   "+mList.get(position));
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class ViewHolder extends UltimateRecyclerviewViewHolder {

        TextView mTitleText;
        ImageView mImageView;
        TextView mContent;

        public ViewHolder(View view) {
            super(view);
            mTitleText = (TextView) view.findViewById(R.id.textview);
            mImageView = (ImageView) view.findViewById(R.id.imageview);

        }
    }
    private List<String> mDataSet = new ArrayList<>();
    public void addAll(List<String> dataSet) {
        mDataSet.addAll(dataSet);
        notifyBinderDataSetChanged();
    }
}
