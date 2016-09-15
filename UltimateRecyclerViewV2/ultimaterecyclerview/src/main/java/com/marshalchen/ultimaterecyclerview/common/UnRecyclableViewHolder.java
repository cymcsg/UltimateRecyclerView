package com.marshalchen.ultimaterecyclerview.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author YanLu
 * @since 16/4/10
 */
public class UnRecyclableViewHolder extends RecyclerView.ViewHolder{
    public UnRecyclableViewHolder(View itemView) {
        super(itemView);
        setIsRecyclable(false);
    }
}
