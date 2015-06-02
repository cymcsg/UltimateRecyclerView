package com.marshalchen.ultimaterecyclerview.demo.modules;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateDifferentViewTypeAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.multiViewTypes.DataBinder;

import java.util.List;

/**
 * Created by cym on 15/5/18.
 */
public class Sample1Binder extends DataBinder<Sample1Binder.ViewHolder> {
    List<String> dataSet;
    public Sample1Binder(UltimateDifferentViewTypeAdapter dataBindAdapter,List<String> dataSet) {
        super(dataBindAdapter);
        this.dataSet=dataSet;
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.simple_binder1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position) {
        holder.mTitleText.setText("Sample1Binder   "+dataSet.get(position));

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
            mTitleText = (TextView) view.findViewById(R.id.title_type1);
            mImageView = (ImageView) view.findViewById(R.id.image_type1);
            mContent = (TextView) view.findViewById(R.id.content_type1);
        }
    }
}
