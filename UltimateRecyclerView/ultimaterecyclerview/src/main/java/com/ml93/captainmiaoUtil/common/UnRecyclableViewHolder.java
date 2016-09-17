package com.ml93.captainmiaoUtil.common;

import android.content.Context;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

/**
 * @author YanLu
 * @since 16/4/10
 */
public class UnRecyclableViewHolder extends UltimateRecyclerviewViewHolder {
    private CustomRelativeWrapper header;

    public UnRecyclableViewHolder(View itemView) {
        super(itemView);
        setIsRecyclable(false);
        if (itemView instanceof CustomRelativeWrapper) {
            header = (CustomRelativeWrapper) itemView;
        }
    }

    @Override
    public void onViewRecycled() {
        super.onViewRecycled();
    }

    @Override
    protected void updateView(Context context, Object object) {
        super.updateView(context, object);
    }

    @Override
    public void onViewAttachedToWindow() {
        super.onViewAttachedToWindow();
        if (header != null) {

        }
    }

    @Override
    public void onViewDetachedFromWindow() {
        super.onViewDetachedFromWindow();
        if (header != null) {

        }
    }
}
