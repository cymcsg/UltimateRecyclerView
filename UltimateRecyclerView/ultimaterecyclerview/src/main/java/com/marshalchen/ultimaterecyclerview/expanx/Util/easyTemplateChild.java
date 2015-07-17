package com.marshalchen.ultimaterecyclerview.expanx.Util;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.R;
import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;

/**
 * on 16/7/15.
 * ultimate created by jjHesk
 * based on library https://github.com/jjhesk/BringItBackAdvanceSlidingMenu
 */
public abstract class easyTemplateChild<T extends ExpandableItemData, B extends TextView, H extends RelativeLayout> extends child<T> {

    public B text;
    public H relativeLayout;
    private int offsetMargin, itemMargin;
    private boolean capitalized = false;
    private boolean countenabled = true;

    public easyTemplateChild(View itemView, int itemMargin, int expandSize) {
        this(itemView);
        this.itemMargin = itemMargin;
        this.offsetMargin = expandSize;
    }

    public easyTemplateChild(View itemView) {
        super(itemView);
        text = (B) itemView.findViewById(R.id.exp_section_title);
        relativeLayout = (H) itemView.findViewById(R.id.exp_section_ripple_wrapper_click);
        itemMargin = itemView.getContext().getResources()
                .getDimensionPixelSize(R.dimen.item_margin);
        offsetMargin = itemView.getContext().getResources()
                .getDimensionPixelSize(R.dimen.expand_size);
    }


    protected void forceTitleCapitalized(boolean b) {
        capitalized = b;
    }

    @Override
    public void bindView(final T itemData, int position) {

        if (capitalized) {
            text.setText(itemData.getText().toUpperCase());
        } else {
            text.setText(itemData.getText());
        }

        text.setLayoutParams(getParamsLayoutOffset(text, itemData));
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // item = itemData;
                onChildItemClick(itemData.getText(), itemData.getPath());
            }
        });
    }


    @Override
    public void onChildItemClick(String title, String path) {
        String[] v = path.split("/");
        if (v.length > 1) {
            request_api(v, title);
        }
    }

    protected void request_api(final String[] n, final String title) {

    }
}
