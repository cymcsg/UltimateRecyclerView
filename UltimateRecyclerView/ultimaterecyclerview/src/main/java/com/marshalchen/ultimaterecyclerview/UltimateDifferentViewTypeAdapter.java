package com.marshalchen.ultimaterecyclerview;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.multiViewTypes.DataBinder;

import java.util.HashMap;
import java.util.Map;

/**
 * An adapter which support different layout
 */
public abstract class UltimateDifferentViewTypeAdapter<E extends Enum<E>> extends UltimateViewAdapter<UltimateRecyclerviewViewHolder> {
    private Map<E, DataBinder> mBinderMap = new HashMap<>();

    protected class VIEW_TYPES extends UltimateViewAdapter.VIEW_TYPES {
        public static final int MULTI_VIEWS = 5;
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == VIEW_TYPES.HEADER||viewType ==VIEW_TYPES.FOOTER
//                ||viewType==VIEW_TYPES.CHANGED_FOOTER) {
//            return super.onCreateViewHolder(parent, viewType);
//        } else
            return getDataBinder(viewType).newViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(UltimateRecyclerviewViewHolder viewHolder, int position) {
        //int binderPosition = getBinderPosition(position);
        getDataBinder(viewHolder.getItemViewType()).bindViewHolder(viewHolder, position);

    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        for (DataBinder binder : mBinderMap.values()) {
            itemCount += binder.getItemCount();
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
//        int type = super.getItemViewType(position);
//        if (type == VIEW_TYPES.NORMAL) {
//            return getEnumFromPosition(position).ordinal();
//        } else {
//            return type;
//        }
        return getEnumFromPosition(position).ordinal();

    }

    public <T extends DataBinder> T getDataBinder(int viewType) {
        return getDataBinder(getEnumFromOrdinal(viewType));
    }

    public int getPosition(DataBinder binder, int binderPosition) {
        E targetViewType = getEnumFromBinder(binder);
        for (int i = 0; i < getItemCount(); i++) {
            if (targetViewType == getEnumFromPosition(i)) {
                binderPosition--;
                if (binderPosition <= 0) {
                    return i;
                }
            }
        }
        return getItemCount();
    }

    public int getBinderPosition(int position) {
        E targetViewType = getEnumFromPosition(position);
        int binderPosition = -1;
        for (int i = 0; i <= position; i++) {
            if (targetViewType == getEnumFromPosition(i)) {
                binderPosition++;
            }
        }

        if (binderPosition == -1) {
            throw new IllegalArgumentException("Invalid Argument");
        }
        return binderPosition;
    }

    public void notifyBinderItemRangeChanged(DataBinder binder, int positionStart, int itemCount) {
        for (int i = positionStart; i <= itemCount; i++) {
            notifyItemChanged(getPosition(binder, i));
        }
    }

    public void notifyBinderItemRangeInserted(DataBinder binder, int positionStart, int itemCount) {
        for (int i = positionStart; i <= itemCount; i++) {
            notifyItemInserted(getPosition(binder, i));
        }
    }

    public void notifyBinderItemRangeRemoved(DataBinder binder, int positionStart, int itemCount) {
        for (int i = positionStart; i <= itemCount; i++) {
            notifyItemRemoved(getPosition(binder, i));
        }
    }

    public void notifyBinderItemChanged(DataBinder binder, int binderPosition) {
        notifyItemChanged(getPosition(binder, binderPosition));
    }


    public void notifyBinderItemInserted(DataBinder binder, int binderPosition) {
        notifyItemInserted(getPosition(binder, binderPosition));
    }

    public void notifyBinderItemMoved(DataBinder binder, int fromPosition, int toPosition) {
        notifyItemMoved(getPosition(binder, fromPosition), getPosition(binder, toPosition));
    }


    public void notifyBinderItemRemoved(DataBinder binder, int binderPosition) {
        notifyItemRemoved(getPosition(binder, binderPosition));
    }


    public abstract E getEnumFromPosition(int position);

    public abstract E getEnumFromOrdinal(int ordinal);

    public E getEnumFromBinder(DataBinder binder) {
        for (Map.Entry<E, DataBinder> entry : mBinderMap.entrySet()) {
            if (entry.getValue().equals(binder)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Invalid Data Binder");
    }

    public <T extends DataBinder> T getDataBinder(E e) {
        return (T) mBinderMap.get(e);
    }

    public Map<E, DataBinder> getBinderMap() {
        return mBinderMap;
    }

    public void putBinder(E e, DataBinder binder) {
        mBinderMap.put(e, binder);
    }

    public void removeBinder(E e) {
        mBinderMap.remove(e);
    }

    public void clearBinderMap() {
        mBinderMap.clear();
    }
}
