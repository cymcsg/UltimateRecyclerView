package com.marshalchen.ultimaterecyclerview.demo;

import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.dragsortadapter.DragSortAdapter;
import com.marshalchen.ultimaterecyclerview.dragsortadapter.NoForegroundShadowBuilder;

import java.security.SecureRandom;
import java.util.List;


public class DragAdatper extends DragSortAdapter<DragAdatper.MainViewHolder> {
    private List<Integer> stringList;

    public DragAdatper(RecyclerView recyclerView, List<Integer> data) {
        super(recyclerView);
        this.stringList = data;
    }

    @Override public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_adapter, parent, false);
        MainViewHolder holder = new MainViewHolder(this, view);
        view.setOnClickListener(holder);
        view.setOnLongClickListener(holder);
        return holder;
    }

    @Override public void onBindViewHolder(final MainViewHolder holder, final int position) {
        Integer itemId = stringList.get(position);
        holder.textViewSample.setText(itemId + "  ");
        // NOTE: check for getDraggingId() match to set an "invisible space" while dragging
//        holder.container.setVisibility(getDraggingId() == itemId ? View.INVISIBLE : View.VISIBLE);
//        holder.container.postInvalidate();
    }

    @Override public long getItemId(int position) {
       // URLogs.d("hashcode---"+stringList.get(position).hashCode()+"    "+position);
        return stringList.get(position).hashCode();
    }
    protected static int convertToOriginalPosition(int position, int dragInitial, int dragCurrent) {
        if (dragInitial < 0 || dragCurrent < 0) {
            // not dragging
            return position;
        } else {
            if ((dragInitial == dragCurrent) ||
                    ((position < dragInitial) && (position < dragCurrent)) ||
                    (position > dragInitial) && (position > dragCurrent)) {
                return position;
            } else if (dragCurrent < dragInitial) {
                if (position == dragCurrent) {
                    return dragInitial;
                } else {
                    return position - 1;
                }
            } else { // if (dragCurrent > dragInitial)
                if (position == dragCurrent) {
                    return dragInitial;
                } else {
                    return position + 1;
                }
            }
        }
    }
    @Override public int getItemCount() {
        return stringList.size();
    }

    @Override public int getPositionForId(long id) {
        return stringList.indexOf((int) id);
    }

    @Override public boolean move(int fromPosition, int toPosition) {
        stringList.add(toPosition, stringList.remove(fromPosition));
        return true;
    }

    static class MainViewHolder extends DragSortAdapter.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {


        TextView textViewSample;
        ImageView imageViewSample;
        ProgressBar progressBarSample;

        public MainViewHolder(DragSortAdapter adapter, View itemView) {
            super(adapter, itemView);
            textViewSample = (TextView) itemView.findViewById(
                    R.id.textview);
            imageViewSample = (ImageView) itemView.findViewById(R.id.imageview);
            progressBarSample = (ProgressBar) itemView.findViewById(R.id.progressbar);
            progressBarSample.setVisibility(View.GONE);
        }

        @Override public void onClick(@NonNull View v) {
            URLogs.d(textViewSample.getText() + " clicked!");
        }

        @Override public boolean onLongClick(@NonNull View v) {
            startDrag();
            return true;
        }

        @Override public View.DragShadowBuilder getShadowBuilder(View itemView, Point touchPoint) {
            return new NoForegroundShadowBuilder(itemView, touchPoint);
        }
    }
}
