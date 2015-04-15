package com.marshalchen.ultimaterecyclerview.demo;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.tonicartos.superslim.GridSLM;
import com.tonicartos.superslim.LayoutManager;
import com.tonicartos.superslim.LinearSLM;

import java.util.ArrayList;
import java.util.List;


public class SimpleAdapter extends UltimateViewAdapter {
    boolean mMarginsFixed = false;
    static final int LINEAR = 0;

    public SimpleAdapter(List<String> stringList) {
        mItems = new ArrayList<>();
        String lastHeader = "";
        int sectionManager = -1;
        int headerCount = 0;
        int sectionFirstPosition = 0;
        for (int i = 0; i < stringList.size(); i++) {
//            String header = stringList.get(i).substring(0, 1);
//            if (!TextUtils.equals(lastHeader, header)) {
//                // Insert new header view and update section data.
//                sectionManager = (sectionManager + 1) % 2;
//                sectionFirstPosition = i + headerCount;
//                lastHeader = header;
//                headerCount += 1;
//                mItems.add(new LineItem(header, true, sectionManager, sectionFirstPosition));
//            }
            mItems.add(new LineItem(stringList.get(i), false, sectionManager, sectionFirstPosition));
        }
        URLogs.d("mitem--" + mItems.size());
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= mItems.size() : position < mItems.size()) && (customHeaderView != null ? position > 0 : true)) {

            //   ((ViewHolder) holder).textViewSample.setText(stringList.get(customHeaderView != null ? position - 1 : position));
            // ((ViewHolder) holder).itemView.setActivated(selectedItems.get(position, false));
            if (mItems.size() > 0) {
                LineItem item = mItems.get(customHeaderView != null ? position - 1 : position);
                final View itemView = holder.itemView;
                if (!item.isHeader)
                    ((ViewHolder) holder).textViewSample.setText(item.text);
                final GridSLM.LayoutParams lp = new GridSLM.LayoutParams(
                        itemView.getLayoutParams());
                // Overrides xml attrs, could use different layouts too.
                if (item.isHeader) {
                    lp.headerDisplay = LayoutManager.LayoutParams.HEADER_OVERLAY;
                    if (lp.isHeaderInline() || (mMarginsFixed && !lp.isHeaderOverlay())) {
                        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    } else {
                        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    }

                    lp.headerEndMarginIsAuto = !mMarginsFixed;
                    lp.headerStartMarginIsAuto = !mMarginsFixed;
                }
                lp.setSlm(item.sectionManager == LINEAR ? LinearSLM.ID : GridSLM.ID);
                lp.setColumnWidth(40);
                lp.setFirstPosition(item.sectionFirstPosition);
                itemView.setLayoutParams(lp);
            }

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && customLoadMoreView != null) {
            if (isLoadMoreChanged) {
                return VIEW_TYPES.CHANGED_FOOTER;
            } else {
                return VIEW_TYPES.FOOTER;
            }


        } else if (position == 0 && customHeaderView != null) {
            return VIEW_TYPES.HEADER;
        } else if (mItems.get(customHeaderView != null ? position - 1 : position).isHeader) {
            return VIEW_TYPES.STICKY_HEADER;
        } else
            return VIEW_TYPES.NORMAL;
    }

    @Override
    public int getAdapterItemCount() {
        return mItems.size();
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public void insert(String string, int position) {
        insert(mItems, new LineItem(string, false, 0, 0), position);
    }

    public void remove(int position) {
        remove(mItems, position);
    }

    public void clear() {
        clear(mItems);
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
        swapPositions(mItems, from, to);
    }


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

    class StickyHeaderViewHolder extends UltimateRecyclerviewViewHolder {
        TextView textViewSample;

        public StickyHeaderViewHolder(View itemView) {
            super(itemView);
            textViewSample = (TextView) itemView.findViewById(
                    R.id.text);
        }
    }

    private ArrayList<LineItem> mItems;

    private static class LineItem {

        public int sectionManager;

        public int sectionFirstPosition;

        public boolean isHeader;

        public String text;

        public LineItem(String text, boolean isHeader, int sectionManager,
                        int sectionFirstPosition) {
            this.isHeader = isHeader;
            this.text = text;
            this.sectionManager = sectionManager;
            this.sectionFirstPosition = sectionFirstPosition;
        }
    }
}
