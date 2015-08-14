package com.marshalchen.ultimaterecyclerview.demo.modules;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.marshalchen.ultimaterecyclerview.AdmobAdapter;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.demo.R;

import java.util.List;

/**
 * Created by hesk on 20/5/15.
 */
public class admobdfpadapter extends AdmobAdapter<AdView, String, admobdfpadapter.ViewHolder> {
    public admobdfpadapter(AdView v, int e, List<String> f) {
        super(v, false, e, f);
    }

    public admobdfpadapter(AdView v, int e, List<String> f, AdviewListener listener) {
        super(v, false, e, f, listener);
    }


    @Override
    protected int getNormalLayoutResId() {
        return R.layout.recycler_view_adapter;
    }

    @Override
    protected ViewHolder newViewHolder(View mview) {
        return new ViewHolder(mview);
    }



    @Override
    public long generateHeaderId(int position) {
        URLogs.d("position--" + position + "   " + getItem(position));
        if (getItem(position).length() > 0)
            return getItem(position).charAt(0);
        else return -1;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * should update the contents of the {@link ViewHolder#itemView} to reflect the item at
     * the given position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this
     * method again if the position of the item changes in the data set unless the item itself
     * is invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside this
     * method and should not keep a copy of it. If you need the position of an item later on
     * (e.g. in a click listener), use {@link ViewHolder#getPosition()} which will have the
     * updated position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(UltimateRecyclerviewViewHolder holder, int position) {
        if (onActionToBindData(position, list)) {
            ((ViewHolder) holder).textViewSample.setText((String) list.get(getDataArrayPosition(position)));
        }
    }


    static class ViewHolder extends UltimateRecyclerviewViewHolder {

        TextView textViewSample;
        ImageView imageViewSample;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewSample = (TextView) itemView.findViewById(R.id.textview);
            imageViewSample = (ImageView) itemView.findViewById(R.id.imageview);

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
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        TextView textView = (TextView) viewHolder.itemView;
        textView.setText(String.valueOf(getItem(i).charAt(0)));
        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AA70DB93"));
    }

    public String getItem(int position) {
        return (String) super.getItem(position);
    }


    @Override
    public long getHeaderId(int position) {
        if (position == 0) {
            return -1;
        } else {
            return getItem(position).charAt(0);
        }
    }

    public void remove(int position) {
        remove(list, position);
    }

    public void clear() {
        clear(list);
    }

    @Override
    public UltimateRecyclerviewViewHolder getViewHolder(View view) {
        return new UltimateRecyclerviewViewHolder(view);
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
        swapPositions(list, from, to);
    }
}
