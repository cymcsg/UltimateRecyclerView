package com.marshalchen.ultimaterecyclerview.demo;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;

import java.util.List;

/**
 * Created by hesk on 24/8/15.
 */
public class GridAdapter extends UltimateViewAdapter {

    final private List<String> list;


    public GridAdapter(List<String> list) {
        this.list = list;

    }

    @Override
    public Gaholder getViewHolder(View view) {
        Gaholder g = new Gaholder(view, false);
        return g;
    }


    @Override
    public Gaholder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        return new Gaholder(view, true);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public int getAdapterItemCount() {
        return list.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Gaholder h = (Gaholder) holder;
        h.textViewSample.setText(list.get(position));
        h.imageViewSample.setImageResource(SampleDataboxset.getGirlImageRandom());
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class Gaholder extends UltimateRecyclerviewViewHolder {

        TextView textViewSample;
        ImageView imageViewSample;
        View item_view;

        public Gaholder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                textViewSample = (TextView) itemView.findViewById(R.id.example_row_tv_title);
                imageViewSample = (ImageView) itemView.findViewById(R.id.example_row_iv_image);
                item_view = itemView.findViewById(R.id.planview);

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

    public static class GridSpan extends GridLayoutManager.SpanSizeLookup {
        final private int columns;
        final private int intervalRow;

        public GridSpan(int col, int intervalRow) {
            this.columns = col;
            this.intervalRow = intervalRow;
        }

        /**
         * Returns the number of span occupied by the item at <code>position</code>.
         *
         * @param position The adapter position of the item
         * @return The number of spans occupied by the item at the provided position
         */
        @Override
        public int getSpanSize(int position) {
            int mIntervalHeader = columns * intervalRow;
            int h = position % mIntervalHeader == 0 ? columns : 1;
            return h;
        }
    }

}
