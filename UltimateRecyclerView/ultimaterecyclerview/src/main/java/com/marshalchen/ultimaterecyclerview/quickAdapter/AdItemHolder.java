package com.marshalchen.ultimaterecyclerview.quickAdapter;

import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

/**
 * Created by hesk on 24/2/16.
 * Linear or Grid layout use only
 */
public abstract class AdItemHolder extends UltimateRecyclerviewViewHolder {
    private final int innerType;

    public AdItemHolder(View itemView, int type) {
        super(itemView);
        innerType = type;
        if (innerType == UltimateViewAdapter.VIEW_TYPES.NORMAL) {
            bindNormal(itemView);
        } else if (innerType == UltimateViewAdapter.VIEW_TYPES.ADVIEW) {
            bindAd(itemView);
        }
    }

    protected abstract void bindNormal(View view);

    protected abstract void bindAd(View view);
}
