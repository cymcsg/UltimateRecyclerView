package com.marshalchen.ultimaterecyclerview.demo;

/**
 * Created by Marshal Chen on 3/8/2016.
 */

import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.demo.rvComponents.itemCommonBinder;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperViewHolder;

import java.security.SecureRandom;
import java.util.List;


public class SimpleAdapter extends UltimateViewAdapter {
    private List<String> stringList;

    public SimpleAdapter(List<String> stringList) {
        this.stringList = stringList;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= stringList.size() : position < stringList.size()) && (customHeaderView != null ? position > 0 : true)) {

            ((ViewHolder) holder).textViewSample.setText(stringList.get(customHeaderView != null ? position - 1 : position));
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

                ((ViewHolder) holder).item_view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
            }
        }

    }

    @Override
    public int getAdapterItemCount() {
        return stringList.size();
    }

    @Override
    public RecyclerView.ViewHolder newFooterHolder(View view) {
        // return new itemCommonBinder(view, false);
        return new UltimateRecyclerviewViewHolder<>(view);
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return new UltimateRecyclerviewViewHolder<>(view);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public void insert(String string, int position) {
        insertInternal(stringList, string, position);
    }

    public void remove(int position) {
        removeInternal(stringList, position);
    }

    public void clear() {
        clearInternal(stringList);
    }


    public void swapPositions(int from, int to) {
        swapPositions(stringList, from, to);
    }


    @Override
    public long generateHeaderId(int position) {
        // URLogs.d("position--" + position + "   " + getItem(position));
        if (getItem(position).length() > 0)
            return getItem(position).charAt(0);
        else return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stick_header_item, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.stick_text);
        textView.setText(String.valueOf(getItem(position).charAt(0)));
//        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AA70DB93"));
        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AAffffff"));
        ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.stick_img);

        SecureRandom imgGen = new SecureRandom();
        switch (imgGen.nextInt(3)) {
            case 0:
                imageView.setImageResource(R.drawable.jr1);
                break;
            case 1:
                imageView.setImageResource(R.drawable.jr2);
                break;
            case 2:
                imageView.setImageResource(R.drawable.jr3);
                break;
        }

    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition > 0 && toPosition > 0) {
            swapPositions(fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
            super.onItemMove(fromPosition, toPosition);
        }

    }

    @Override
    public void onItemDismiss(int position) {
        if (position > 0) {
            remove(position);
            // notifyItemRemoved(position);
//        notifyDataSetChanged();
            super.onItemDismiss(position);
        }

    }
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

    class ViewHolder extends UltimateRecyclerviewViewHolder {

        TextView textViewSample;
        ImageView imageViewSample;
        ProgressBar progressBarSample;
        View item_view;

        public ViewHolder(View itemView) {
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
            textViewSample = (TextView) itemView.findViewById(
                    R.id.textview);
            imageViewSample = (ImageView) itemView.findViewById(R.id.imageview);
            progressBarSample = (ProgressBar) itemView.findViewById(R.id.progressbar);
            progressBarSample.setVisibility(View.GONE);
            item_view = itemView.findViewById(R.id.itemview);
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

    public String getItem(int position) {
        if (customHeaderView != null)
            position--;
        // URLogs.d("position----"+position);
        if (position >= 0 && position < stringList.size())
            return stringList.get(position);
        else return "";
    }

}