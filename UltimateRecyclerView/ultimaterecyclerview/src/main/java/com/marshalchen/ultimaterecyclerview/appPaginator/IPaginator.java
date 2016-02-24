package com.marshalchen.ultimaterecyclerview.appPaginator;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

@Deprecated
/**
 * Created by zJJ on 2/24/2016.
 */
public interface IPaginator {
    @IdRes
    int getUltimate_recycler_viewResId();

    @LayoutRes
    int getFragmentResId();

    void onClickItem(final String route);

    void onClickItem(final long route_id);

    void makeBasicRequest();

    void makeRefreshRequest();
}
