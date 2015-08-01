package com.marshalchen.ultimaterecyclerview.quickAdapter.extUtimateRVA;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.quickAdapter.MultiItemTypeSupport;
import com.marshalchen.ultimaterecyclerview.quickAdapter.extBaseAdapter.BaseAdapterHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Started by hesk and it is work in progress
 * Created by hesk on 31/7/15.
 */
public abstract class BaseQuickAdapter<VH extends RecyclerView.ViewHolder, T, H extends BaseAdapterHelper> extends UltimateViewAdapter<VH> {
    protected static final String TAG = BaseQuickAdapter.class.getSimpleName();
    protected final Context context;
    protected int layoutResId;
    protected final List<T> data;
    protected boolean displayIndeterminateProgress = false;
    protected MultiItemTypeSupport<T> mMultiItemSupport;

    protected class QUICK_TYPE extends VIEW_TYPES {
        public static final int PROGRESS_VIEW = 201;
    }

    /**
     * Create a QuickAdapter.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     */
    public BaseQuickAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with some
     * initialization data.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public BaseQuickAdapter(Context context, int layoutResId, List<T> data) {
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.context = context;
        this.layoutResId = layoutResId;
    }

    public BaseQuickAdapter(Context context, ArrayList<T> data, MultiItemTypeSupport<T> multiItemSupport) {
        this.mMultiItemSupport = multiItemSupport;
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.context = context;
    }

    @Override
    public int getItemCount() {
        int extra = displayIndeterminateProgress ? 1 : 0;
        return data.size() + extra;
    }

    @Override
    public int getItemViewType(int position) {
        final int type = super.getItemViewType(position);
        final boolean isNormal = type == VIEW_TYPES.NORMAL;
        if (isNormal) {
            if (displayIndeterminateProgress) {
                if (mMultiItemSupport != null)
                    return position >= data.size() ? 0 : mMultiItemSupport
                            .getItemViewType(position, data.get(position));
            } else {
                if (mMultiItemSupport != null)
                    return mMultiItemSupport.getItemViewType(position,
                            data.get(position));
            }
            return position >= data.size() ? 0 : 1;
        } else {
            return type;
        }
    }

 /*  @Override
    public T getItem(int position) {
        if (position >= data.size())
            return null;
        return data.get(position);
    }
   */
   /* @Override
    public int getViewTypeCount() {
        if (mMultiItemSupport != null)
            return mMultiItemSupport.getViewTypeCount() + 1;
        return 2;
    }*/
}
