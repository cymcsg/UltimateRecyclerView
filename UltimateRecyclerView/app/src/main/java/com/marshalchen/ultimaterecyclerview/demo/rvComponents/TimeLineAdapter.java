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
    protected int getNormalLayoutResId() {
        return itemNode.layout;
    }

    @Override
    protected itemNode newViewHolder(View view) {
        return new itemNode(view);
    }

    @Override
    protected void withBindHolder(itemNode holder, TimeLineModel data, int position) {
        holder.name.setText("name：" + data.getName() + " age：" + data.getAge());
        holder.init(TimelineView.getTimeLineViewType(position, getItemCount()));
    }
}