package com.marshalchen.ultimaterecyclerview.demo.rvComponents;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;

import java.security.SecureRandom;
import java.util.List;

/**
 * Created by hesk on 5/4/16.
 */
public class staggerAdapter extends easyRegularAdapter<String, itemStaggerCommonBinder> {


    public staggerAdapter(List<String> stringList) {
        super(stringList);
        //  this.stringList = stringList;
    }

    @Override
    protected int getNormalLayoutResId() {
        return itemStaggerCommonBinder.layout;
    }

    @Override
    protected itemStaggerCommonBinder newViewHolder(View view) {
        return new itemStaggerCommonBinder(view, VIEW_TYPES.NORMAL);
    }

    @Override
    public itemStaggerCommonBinder newFooterHolder(View view) {
        return new itemStaggerCommonBinder(view, VIEW_TYPES.FOOTER);
    }

    @Override
    public itemStaggerCommonBinder newHeaderHolder(View view) {
        return new itemStaggerCommonBinder(view, VIEW_TYPES.HEADER);
    }

    @Override
    protected void withBindHolder(itemStaggerCommonBinder holder, String data, int position) {
        holder.textViewSample.setText(data + "just the sample data");
        holder.item_view.setBackgroundColor(Color.parseColor("#AAffffff"));
        SecureRandom imgGen = new SecureRandom();
        switch (imgGen.nextInt(3)) {
            case 0:
                holder.imageViewSample.setImageResource(R.drawable.scn1);
                break;
            case 1:
                holder.imageViewSample.setImageResource(R.drawable.jr13);
                break;
            case 2:
                holder.imageViewSample.setImageResource(R.drawable.jr16);
                break;
        }
    }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        swapPositions(fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
        super.onItemMove(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        if (position > 0)
            removeAt(position);
        // notifyItemRemoved(position);
        //        notifyDataSetChanged();
        super.onItemDismiss(position);
    }

    public void setOnDragStartListener(OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;

    }

}
