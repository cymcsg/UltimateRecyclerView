package com.marshalchen.ultimaterecyclerview.demo.rvComponents;

import android.view.View;

import com.marshalchen.ultimaterecyclerview.demo.modules.TimeLineModel;
import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;
import com.marshalchen.ultimaterecyclerview.ui.timelineview.TimelineView;

import java.util.List;

/**
 * Created by zJJ on 4/27/2016.
 */
public class TimeLineAdapter extends easyRegularAdapter<TimeLineModel, itemNode> {

    public TimeLineAdapter(List<TimeLineModel> feedList) {
        super(feedList);
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }


    @Override
    protected int getNormalLayoutResId() {
        return itemNode.layout;
    }

    @Override
    protected itemNode newViewHolder(View view) {
        return new itemNode(view, VIEW_TYPES.NORMAL);
    }

    @Override
    protected void withBindHolder(itemNode holder, TimeLineModel data, int position) {
        holder.name.setText("name：" + data.getName() + "    age：" + data.getAge());
    }
}