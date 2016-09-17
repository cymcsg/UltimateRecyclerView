package com.ml93.captainmiaoUtil.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ml93.captainmiaoUtil.listener.OnRecyclerItemClickListener;


/**
 * @author YanLu
 * @since 15/11/1
 */
public abstract class ClickableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    OnRecyclerItemClickListener mClickListener;

    public ClickableViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View v) {
        if(mClickListener != null){
            mClickListener.onClick(v, getAdapterPosition());
        }
    }

    public void addOnViewClickListener(View v){
        if(v != null) {
            v.setOnClickListener(this);
        }
    }
    public void addOnItemViewClickListener(){
        if(itemView != null) {
            itemView.setOnClickListener(this);
        }
    }

    public OnRecyclerItemClickListener getOnRecyclerItemClickListener() {
        return mClickListener;
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }
}
