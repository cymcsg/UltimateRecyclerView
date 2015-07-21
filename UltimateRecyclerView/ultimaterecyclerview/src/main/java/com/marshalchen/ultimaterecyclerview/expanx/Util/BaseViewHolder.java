package com.marshalchen.ultimaterecyclerview.expanx.Util;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;

/**
 * Author Ultimate Enhancer Hesk
 * PersonalWebsite jjhesk on github
 * Description
 */
public class BaseViewHolder<T extends ExpandableItemData> extends UltimateRecyclerviewViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        getMarginBy();
    }

    protected View getView(final Context m, final ViewGroup parent, final @LayoutRes int layout) {
        return LayoutInflater.from(m).inflate(layout, parent, false);
    }

    protected int itemMargin, itemMarginRes;
    protected int offsetMargin, offsetMarginRes;


    protected void getMarginBy() {
        //  itemView.getContext().obtainStyledAttributes(Theme.)
        itemMargin = itemView.getContext().
                getResources().getDimensionPixelSize(R.dimen.item_margin);
        offsetMargin = itemView.getContext().
                getResources().getDimensionPixelSize(R.dimen.expand_size);
    }

    protected RelativeLayout.LayoutParams getParamsLayoutOffset(ImageView image, T itemData) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) image.getLayoutParams();
        params.leftMargin = itemMargin * itemData.getTreeDepth() + offsetMargin;
        return params;
    }

    protected RelativeLayout.LayoutParams getParamsLayoutOffset(RelativeLayout layout, ExpandableItemData itemData) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        params.leftMargin = itemMargin * itemData.getTreeDepth() + offsetMargin;
        return params;
    }

    protected RelativeLayout.LayoutParams getParamsLayoutOffset(TextView layout, T itemData) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        params.leftMargin = itemMargin * itemData.getTreeDepth() + offsetMargin;
        return params;
    }


    protected RelativeLayout.LayoutParams getParamsLayout(TextView layout, T itemData) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        params.leftMargin = itemMargin * itemData.getTreeDepth();
        //    layout.setLayoutParams();
        return params;
        //   layout.setMar
    }

    protected RelativeLayout.LayoutParams getParamsLayout(ImageView image, T itemData) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) image.getLayoutParams();
        params.leftMargin = itemMargin * itemData.getTreeDepth();
        return params;
    }

    protected RelativeLayout.LayoutParams getParamsLayout(RelativeLayout layout, T itemData) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        params.leftMargin = itemMargin * itemData.getTreeDepth();
        return params;
    }


}
