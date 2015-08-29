package com.marshalchen.ultimaterecyclerview.demo.modules;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;

import com.marshalchen.ultimaterecyclerview.demo.CustomSwipeToRefreshRefreshActivity;
import com.marshalchen.ultimaterecyclerview.demo.GridLayoutRVTest;
import com.marshalchen.ultimaterecyclerview.demo.MultiViewTypesActivity;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.TestAdMob;
import com.marshalchen.ultimaterecyclerview.demo.DebugLoadMoreActivity;
import com.marshalchen.ultimaterecyclerview.demo.TestAdvancedAdmobActivity;
import com.marshalchen.ultimaterecyclerview.demo.scrollableobservable.ScrollObservablesActivity;
import com.marshalchen.ultimaterecyclerview.demo.swipelist.SwipeListViewExampleActivity;
import com.marshalchen.ultimaterecyclerview.demo.TestExpandableRV;

/**
 * Created by hesk on 7/1/2015.
 */
public enum FastBinding {
    action_bottom(R.id.action_bottom, MultiViewTypesActivity.class),
    action_custom(R.id.action_custom, CustomSwipeToRefreshRefreshActivity.class),
    admob(R.id.admob, TestAdMob.class),
    scrollactivity(R.id.scrollactivity, ScrollObservablesActivity.class),
    swipe_and_drag(R.id.swipe_and_drag, SwipeListViewExampleActivity.class),
    debug_load_more(R.id.debug_load_more, DebugLoadMoreActivity.class),
    advancedAdmob(R.id.adv_admob, TestAdvancedAdmobActivity.class),
    gridlayouttesting(R.id.gridlayoutperformance, GridLayoutRVTest.class),
    expandmenu(R.id.expandmenu, TestExpandableRV.class);

    private int id;
    private Class<?> clazz;

    FastBinding(final @IdRes int id, Class<?> clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    public Class<?> getClassName() {
        return clazz;
    }

    public int getId() {
        return id;
    }

    public static void startactivity(final Context ctx, final @IdRes int id) {
        final int g = FastBinding.values().length;
        for (int i = 0; i < g; i++) {
            FastBinding bind = FastBinding.values()[i];
            if (bind.getId() == id) {
                Intent intent = new Intent(ctx, bind.getClassName());
                ctx.startActivity(intent);
                return;
            }
        }
    }
}
