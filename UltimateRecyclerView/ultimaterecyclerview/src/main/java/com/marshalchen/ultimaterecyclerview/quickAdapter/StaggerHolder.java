package com.marshalchen.ultimaterecyclerview.quickAdapter;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

/**
 * only Stagger layout use only
 * Created by hesk on 5/4/16.
 */
public abstract class StaggerHolder extends UltimateRecyclerviewViewHolder {
    private final int innerType;
    protected final StaggeredGridLayoutManager.LayoutParams fullSpanLayout = new
            StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public StaggerHolder(View itemView, int type) {
        super(itemView);
        innerType = type;
        fullSpanLayout.setFullSpan(true);
        if (innerType == UltimateViewAdapter.VIEW_TYPES.NORMAL) {
            bindNormal(itemView);
        } else if (innerType == UltimateViewAdapter.VIEW_TYPES.ADVIEW) {
            bindAd(itemView);
        } else if (innerType == UltimateViewAdapter.VIEW_TYPES.HEADER) {
            itemView.setLayoutParams(fullSpanLayout);
            bindHeader(itemView);
        } else if (innerType == UltimateViewAdapter.VIEW_TYPES.FOOTER) {
            itemView.setLayoutParams(fullSpanLayout);
            bindFooter(itemView);
        }
    }

    protected abstract void bindHeader(View view);

    protected abstract void bindFooter(View view);

    protected abstract void bindNormal(View view);

    protected abstract void bindAd(View view);
}
