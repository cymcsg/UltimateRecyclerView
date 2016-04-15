package com.marshalchen.ultimaterecyclerview.demo.rvComponents;

import android.graphics.Color;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;

import java.security.SecureRandom;
import java.util.List;


public class sectionZeroAdapter extends easyRegularAdapter<String, itemCommonBinder> {
    // private List<String> stringList;

    public sectionZeroAdapter(List<String> stringList) {
        super(stringList);
        //  this.stringList = stringList;
    }

    /**
     * the layout id for the normal data
     *
     * @return the ID
     */
    @Override
    protected int getNormalLayoutResId() {
        return itemCommonBinder.layout;
    }

    @Override
    protected itemCommonBinder newViewHolder(View view) {
        return new itemCommonBinder(view, true);
    }



    @Override
    public itemCommonBinder newFooterHolder(View view) {
        return new itemCommonBinder(view, false);
    }

    @Override
    public itemCommonBinder newHeaderHolder(View view) {
        return new itemCommonBinder(view, false);
    }


  /*  @Override
    public void onBindViewHolder(final SHol holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= stringList.size() : position < stringList.size()) && (customHeaderView != null ? position > 0 : true)) {

            ((SHol) holder).textViewSample.setText(stringList.get(customHeaderView != null ? position - 1 : position));
            // ((ViewHolder) holder).itemView.setActivated(selectedItems.get(position, false));
            if (mDragStartListener != null) {
//                ((ViewHolder) holder).imageViewSample.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                            mDragStartListener.onStartDrag(holder);
//                        }
//                        return false;
//                    }
//                });

                ((SHol) holder).item_view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
            }
        }

    }
*/


    public final void insertOne(String e) {
        insertLast(e);
    }

    public final void removeLastOne() {
        removeLast();
    }

/*
    @Override
    public long generateHeaderId(int position) {
        // URLogs.d("position--" + position + "   " + getItem(position));
        if (getItem(position).length() > 0)
            return getItem(position).charAt(0);
        else return -1;
    }*/

    @Override
    protected void withBindHolder(itemCommonBinder holder, String data, int position) {
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

   /* public String getItem(int position) {
        if (customHeaderView != null)
            position--;
        if (position < stringList.size())
            return stringList.get(position);
        else
            return "";
    }*/
//
//    private int getRandomColor() {
//        SecureRandom rgen = new SecureRandom();
//        return Color.HSVToColor(150, new float[]{
//                rgen.nextInt(359), 1, 1
//        });
//    }

    public void setOnDragStartListener(OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;

    }


}