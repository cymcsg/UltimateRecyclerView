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

    /**
     * the view
     *
     * @param itemView the view context
     */
    public itemNode(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.tx_name);
        mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
    }

    /**
     * this is the initialization of the node
     *
     * @param viewTypeLine the type of node to redraw
     */
    public void init(int viewTypeLine) {
        mTimelineView.initLine(viewTypeLine);
    }
}
