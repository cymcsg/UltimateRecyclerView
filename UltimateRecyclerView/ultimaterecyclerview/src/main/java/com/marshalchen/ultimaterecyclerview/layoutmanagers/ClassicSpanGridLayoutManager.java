package com.marshalchen.ultimaterecyclerview.layoutmanagers;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;

import static com.marshalchen.ultimaterecyclerview.UltimateViewAdapter.VIEW_TYPES.FOOTER;
import static com.marshalchen.ultimaterecyclerview.UltimateViewAdapter.VIEW_TYPES.HEADER;
import static com.marshalchen.ultimaterecyclerview.UltimateViewAdapter.VIEW_TYPES.NORMAL;

/**
 * Created by hesk on 5/4/16.
 */
public class ClassicSpanGridLayoutManager extends GridLayoutManager {
    private final UltimateViewAdapter mAdapter;
    public static final int
            VIDEOPOST = 1, NEWSPAGE = 2, DEFAULT = -1;
    private int mode = DEFAULT;

    protected int onGetSpeanSize(final int position) {
        if (mode == NEWSPAGE) {
            if (mAdapter instanceof easyRegularAdapter) {
                int itemtype = mAdapter.getItemViewType(position);
                if (itemtype == FOOTER || itemtype == HEADER || position == 0) {
                    return getSpanCount();
                }
            }
        } else if (mode == DEFAULT) {
            if (mAdapter instanceof easyRegularAdapter) {
                easyRegularAdapter sw = (easyRegularAdapter) mAdapter;
                if (sw.getItemViewType(position) == FOOTER) {
                    return getSpanCount();
                } else if (sw.getItemViewType(position) == HEADER) {
                    return getSpanCount();
                } else if (sw.getItemViewType(position) == NORMAL) {
                    return 1;
                }
            }
        }

        return 1;
    }

    private GridLayoutManager.SpanSizeLookup m = new GridLayoutManager.SpanSizeLookup() {
        /**
         * Returns the index of the group this position belongs.
         * For example, if grid has 3 columns and each item occupies 1 span, span group index
         * for item 1 will be 0, item 5 will be 1.
         *
         * @param adapterPosition The position in adapter
         * @param spanCount       The total number of spans in the grid
         * @return The index of the span group including the item at the given adapter position
         */
        @Override
        public int getSpanGroupIndex(int adapterPosition, int spanCount) {
            return super.getSpanGroupIndex(adapterPosition, spanCount);
        }

        @Override
        public int getSpanSize(final int position) {
            return onGetSpeanSize(position);
        }
    };

    public ClassicSpanGridLayoutManager(Context context, int spanCount, easyRegularAdapter mAdapter) {
        super(context, spanCount);
        this.mAdapter = mAdapter;
        setSpanSizeLookup(m);
    }


    public ClassicSpanGridLayoutManager(Context context, int spanCount, int moded, int orientation,
                                        easyRegularAdapter mAdapter) {
        super(context, spanCount, orientation, false);
        this.mAdapter = mAdapter;
        setSpanSizeLookup(m);
        if (moded > 0) {
            mode = moded;
        }
    }

    public ClassicSpanGridLayoutManager(Context context, int spanCount, int moded,
                                        easyRegularAdapter mAdapter) {
        super(context, spanCount);
        this.mAdapter = mAdapter;
        setSpanSizeLookup(m);
        mode = moded;
    }


}
