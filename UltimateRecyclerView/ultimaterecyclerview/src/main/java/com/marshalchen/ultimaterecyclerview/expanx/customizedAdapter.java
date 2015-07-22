package com.marshalchen.ultimaterecyclerview.expanx;

import android.content.Context;

import com.marshalchen.ultimaterecyclerview.expanx.Util.child;
import com.marshalchen.ultimaterecyclerview.expanx.Util.parent;

/**
 * Created by hesk on 14/7/15.
 */
public abstract class customizedAdapter<G extends parent<SmartItem>, T extends child<SmartItem>> extends LinearExpanxURVAdapter<SmartItem, G, T> {

    public customizedAdapter(Context context) {
        super(context, EXPANDABLE_ITEMS, true);
    }

}
