package com.marshalchen.ultimaterecyclerview.demo.swipelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.SwipeableUltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout;

import java.util.List;

public class SwipeAdapter extends SwipeableUltimateViewAdapter {

    private List<String> mData;

    public SwipeAdapter(List<String> mData) {
        this.mData = mData;
    }


    @Override
    public UltimateRecyclerviewViewHolder getViewHolder(View view) {
        return new UltimateRecyclerviewViewHolder(view);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        final Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.item_swipeable, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URLogs.d("click");
            }
        });
        final ViewHolder viewHolder = new ViewHolder(view);
        SwipeLayout swipeLayout = viewHolder.swipeLayout;
        swipeLayout.setDragEdge(SwipeLayout.DragEdge.Right);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(viewHolder.getPosition());
                Toast.makeText(v.getContext(), "Deleted " + viewHolder.getPosition(), Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final UltimateRecyclerviewViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);

        ((ViewHolder)holder).textView.setText(mData.get(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getAdapterItemCount() {
        return 0;
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(String string) {
        insert(string, mData.size());
    }

    public void insert(String string, int position) {
        closeAllExcept(null);

        mData.add(position, string);

        notifyItemInserted(position);
    }

    public void remove(int position) {
        mData.remove(position);

        closeItem(position);

        notifyItemRemoved(position);
    }

    public static class ViewHolder extends UltimateRecyclerviewViewHolder {
        public TextView textView;
        public Button   deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.position);
            deleteButton = (Button) itemView.findViewById(R.id.delete);
        }
    }
}
