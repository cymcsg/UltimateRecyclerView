package com.marshalchen.ultimaterecyclerview.demo.griddemo;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.demo.modules.JRitem;
import com.marshalchen.ultimaterecyclerview.demo.rvComponents.itemGridCellBinder;
import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by hesk on 2016/9/16.
 */

public class TypedAdapter extends easyRegularAdapter<JRitem, itemGridCellBinder> {
    public TypedAdapter() {
        super();
    }

    public TypedAdapter(List<JRitem> list) {
        super(list);
    }

    public TypedAdapter(JRitem... list) {
        super(list);
    }

    @Override
    protected int getNormalLayoutResId() {
        return itemGridCellBinder.layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public long generateHeaderId(int position) {
        return View.generateViewId();
    }

    @Override
    protected itemGridCellBinder newViewHolder(View view) {
        return new itemGridCellBinder(view, true);
    }

    @Override
    protected void withBindHolder(itemGridCellBinder holder, JRitem data, int position) {
        holder.textViewSample.setText(data.train_name);
        holder.imageViewSample.setImageResource(data.photo_id);
    }


}
