package com.marshalchen.ultimaterecyclerview.multiViewTypes;

import java.util.HashMap;
import java.util.Map;

/**
 * Adapter class for managing data binders by mapping enum view type and data binder
 *
 * Created by yqritc on 2015/03/25.
 */
public abstract class EnumMapBindAdapter<E extends Enum<E>> extends DataBindAdapter {

    private Map<E, DataBinder> mBinderMap = new HashMap<>();

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
        return getEnumFromPosition(position).ordinal();
    }

    @Override
    public <T extends DataBinder> T getDataBinder(int viewType) {
        return getDataBinder(getEnumFromOrdinal(viewType));
    }

    @Override
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

    @Override
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

    @Override
    public void notifyBinderItemRangeChanged(DataBinder binder, int positionStart, int itemCount) {
        for (int i = positionStart; i <= itemCount; i++) {
            notifyItemChanged(getPosition(binder, i));
        }
    }

    @Override
    public void notifyBinderItemRangeInserted(DataBinder binder, int positionStart, int itemCount) {
        for (int i = positionStart; i <= itemCount; i++) {
            notifyItemInserted(getPosition(binder, i));
        }
    }

    @Override
    public void notifyBinderItemRangeRemoved(DataBinder binder, int positionStart, int itemCount) {
        for (int i = positionStart; i <= itemCount; i++) {
            notifyItemRemoved(getPosition(binder, i));
        }
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
