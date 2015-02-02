package com.marshalchen.ultimaterecyclerview.demo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.Logs;
import com.marshalchen.ultimaterecyclerview.SwipeDismissTouchListener;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

/**
 * Created by cym on 15-1-26.
 */
public class SimpleAdapter extends UltimateViewAdapter {
    private List<String> stringList;

    public SimpleAdapter(List<String> stringList) {
        this.stringList = stringList;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < stringList.size())
            ((ViewHolder) holder).textViewSample.setText(stringList.get(position));
    }

    @Override
    public int getAdapterItemCount() {
        return stringList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public void insert(String string, int position) {
        stringList.add(position, string);
        System.out.print("  insert:   ");
        for (String s : stringList)
            System.out.print(s + "   ");
        notifyItemInserted(position);
        notifyItemChanged(position + 1);
        notifyItemChanged(position);
    }

    public void remove(int position) {
        stringList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = stringList.size();
        stringList.clear();
        notifyItemRangeRemoved(0, size);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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
}
