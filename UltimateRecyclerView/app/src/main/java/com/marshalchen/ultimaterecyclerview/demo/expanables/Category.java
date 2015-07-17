package com.marshalchen.ultimaterecyclerview.demo.expanables;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.expanx.SmartItem;
import com.marshalchen.ultimaterecyclerview.expanx.Util.easyTemplateParent;

/**
 * Created by hesk on 16/7/15.
 */
public class Category extends easyTemplateParent<SmartItem, RelativeLayout, TextView> {
    public Category(View itemView) {
        super(itemView);
    }
}
