package com.ml93.captainmiaoUtil.listener;

import android.view.View;

/**
 * @author YanLu
 * @since 15/11/1
 */
public interface OnRecyclerItemClickListener {
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     * @param position position at
     */
    void onClick(View v, int position);

}
