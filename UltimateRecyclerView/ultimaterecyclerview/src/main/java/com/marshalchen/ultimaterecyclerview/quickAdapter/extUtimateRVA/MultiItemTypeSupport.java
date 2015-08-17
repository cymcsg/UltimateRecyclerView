package com.marshalchen.ultimaterecyclerview.quickAdapter.extUtimateRVA;

/**
 * Created by hesk on 31/7/15.
 */
public interface MultiItemTypeSupport<T> {
    int getLayoutId(int position, T t);

    int getViewTypeCount();

    int getItemViewType(int postion, T t);
}
