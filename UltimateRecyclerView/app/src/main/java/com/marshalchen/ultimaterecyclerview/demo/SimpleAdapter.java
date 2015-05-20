package com.marshalchen.ultimaterecyclerview.demo;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.security.SecureRandom;
import java.util.List;


public class SimpleAdapter extends UltimateViewAdapter {
    private List<String> stringList;

    public SimpleAdapter(List<String> stringList) {
        this.stringList = stringList;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int getType = getItemViewType(position);
        if (getType == VIEW_TYPES.NORMAL) {
            if (maskBindConditions(position, stringList)) {
                ((ViewHolder) holder).textViewSample.setText(stringList.get(positionAdjustments(position)));
            }
        }
    }

    @Override
    public int getAdapterItemCount() {
        return stringList.size();
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public void insert(String string, int position) {
        insert(stringList, string, position);
    }

    public void remove(int position) {
        remove(stringList, position);
    }

    public void clear() {
        clear(stringList);
    }

    @Override
    public void toggleSelection(int pos) {
        super.toggleSelection(pos);
    }

    @Override
    public void setSelected(int pos) {
        super.setSelected(pos);
    }

    @Override
    public void clearSelection(int pos) {
        super.clearSelection(pos);
    }


    public void swapPositions(int from, int to) {
        swapPositions(stringList, from, to);
    }

    @Override
    public long getHeaderId(int position) {
        if (position == 0) {
            return -1;
        } else {
            return getItem(position).charAt(0);
        }
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
        TextView textView = (TextView) viewHolder.itemView;
        textView.setText(String.valueOf(getItem(position).charAt(0)));
        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AA70DB93"));

    }
//
//    private int getRandomColor() {
//        SecureRandom rgen = new SecureRandom();
//        return Color.HSVToColor(150, new float[]{
//                rgen.nextInt(359), 1, 1
//        });
//    }


    class ViewHolder extends UltimateRecyclerviewViewHolder {

        TextView textViewSample;
        ImageView imageViewSample;
        ProgressBar progressBarSample;

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
        }
    }

    public String getItem(int position) {
        if (customHeaderView != null)
            position--;
        if (position < stringList.size())
            return stringList.get(position);
        else return "";
    }

}
