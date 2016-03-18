package com.marshalchen.ultimaterecyclerview.demo.basicdemo;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.demo.R;

/**
 * Created by hesk on 16/2/16.
 * this is the example holder for the simple adapter
 */
public class binderCommon extends UltimateRecyclerviewViewHolder {

    public TextView textViewSample;
    public ImageView imageViewSample;
    public ProgressBar progressBarSample;
    public RelativeLayout item_view;

    /**
     * give more control over NORMAL or HEADER view binding
     *
     * @param itemView view binding
     * @param isItem   bool
     */
    public binderCommon(View itemView, boolean isItem) {
        super(itemView);
//            itemView.setOnTouchListener(new SwipeDismissTouchListener(itemView, null, new SwipeDismissTouchListener.DismissCallbacks() {
//                @Override
//                public boolean canDismiss(Object token) {
//                    Logs.d("can dismiss");
//                    return true;
//                }
//
//                @Override
//                public void onDismiss(View view, Object token) {
//                   // Logs.d("dismiss");
//                    remove(getPosition());
//
//                }
//            }));
        if (isItem) {
            textViewSample = (TextView) itemView.findViewById(R.id.str_textview_holder);
            imageViewSample = (ImageView) itemView.findViewById(R.id.str_image_holder);
            progressBarSample = (ProgressBar) itemView.findViewById(R.id.str_progress_holder);
            item_view = (RelativeLayout) itemView.findViewById(R.id.str_item_view);
        }

    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }
}
