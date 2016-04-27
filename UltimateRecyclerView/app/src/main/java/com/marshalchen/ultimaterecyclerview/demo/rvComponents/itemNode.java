package com.marshalchen.ultimaterecyclerview.demo.rvComponents;

import android.view.View;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.ui.timelineview.TimelineView;

/**
 * Created by zJJ on 4/27/2016.
 */
public class itemNode extends UltimateRecyclerviewViewHolder {
    public static final int layout = R.layout.item_node;
    public TimelineView mTimelineView;
    public TextView name;

    public itemNode(View itemView, int viewType) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.tx_name);
        mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
        mTimelineView.initLine(viewType);
    }

}
