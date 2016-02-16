package com.marshalchen.ultimaterecyclerview.demo.basicdemo;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;

import java.security.SecureRandom;
import java.util.List;

/**
 * Created by hesk on 16/2/16.
 */
public class sectionCommonAdapter extends easyRegularAdapter<String, binderCommon> {

    /**
     * dynamic object to start
     *
     * @param list the list source
     */
    public sectionCommonAdapter(List<String> list) {
        super(list);
    }

    /**
     * the layout id for the normal data
     *
     * @return the ID
     */
    @Override
    protected int getNormalLayoutResId() {
        return R.layout.recycler_view_adapter;
    }

    @Override
    protected binderCommon newViewHolder(View view) {
        return new binderCommon(view, true);
    }

    /**
     * this is for HEADER
     *
     * @param view view
     * @return view
     */
    @Override
    public binderCommon getViewHolder(View view) {
        return new binderCommon(view, false);
    }

    private void setRandomImage(ImageView image) {
        SecureRandom imgGen = new SecureRandom();
        switch (imgGen.nextInt(3)) {
            case 0:
                image.setImageResource(R.drawable.scn1);
                break;
            case 1:
                image.setImageResource(R.drawable.jr13);
                break;
            case 2:
                image.setImageResource(R.drawable.jr16);
                break;
        }
    }

    @Override
    protected void withBindHolder(binderCommon holder, String data, int position) {
        char Firstletter = data.charAt(0);
        holder.textViewSample.setText(data);
        holder.item_view.setBackgroundColor(Color.parseColor("#AAffffff"));
        setRandomImage(holder.imageViewSample);
       /* if (position < getItemCount() && (hasHeaderView() ? position <= getAdapterItemCount() : position < getAdapterItemCount()) && (hasHeaderView() ? position > 0 : true)) {
            holder.textViewSample.setText(getItem(hasHeaderView() ? position - 1 : position));
           if (mDragStartListener != null) {
                holder.item_view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
            }
        }*/
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

    /**
     * this is the way to enable section header
     *
     * @param i position in the display items
     * @return long position
     */
    @Override
    public long generateHeaderId(int i) {
        if (getItem(i).length() > 0) {
            return getItem(i).charAt(0);
        } else return -1;
    }

    /**
     * this is the way to enable section header
     *
     * @param viewGroup enable the view group
     * @return view holder
     */

    @Override
    public UltimateRecyclerviewViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stick_header_item, viewGroup, false);
        return new UltimateRecyclerviewViewHolder(view) {

        };
    }

    /**
     * this is the section header binding
     *
     * @param viewHolder section holder
     * @param position   position
     */
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.stick_text);
        textView.setText(String.valueOf(getItem(hasHeaderView() ? position - 1 : position).charAt(0)));
//        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AA70DB93"));
        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AAffffff"));
        ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.stick_img);

        SecureRandom imgGen = new SecureRandom();
        switch (imgGen.nextInt(3)) {
            case 0:
                imageView.setImageResource(R.drawable.scn1);
                break;
            case 1:
                imageView.setImageResource(R.drawable.jr13);
                break;
            case 2:
                imageView.setImageResource(R.drawable.jr16);
                break;
        }


    }
}