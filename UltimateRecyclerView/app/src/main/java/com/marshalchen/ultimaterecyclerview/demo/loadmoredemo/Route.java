package com.marshalchen.ultimaterecyclerview.demo.loadmoredemo;

import android.content.Context;
import android.content.Intent;

/**
 * Created by hesk on 24/2/16.
 */
public enum Route {
    CUSTOM_PULL_HEADER("Custom Pull", PullToRefreshActivity.class),
    LOADMORE_TEST("No Header", DebugNoHeaderLoadMoreActivity.class),
    LOADMORE_HEADER("Header Pallx", DebugLoadMoreActivity.class),;

    private String name;
    private Class<?> clazzna;

    Route(String name, Class<?> clazz) {
        this.name = name;
        this.clazzna = clazz;
    }

    public Route getAnimator() {

        return this;
    }


    public String getNameDisplay() {
        return name;
    }

    public void start(final Context ctx) {
        Intent intent = new Intent(ctx, clazzna);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }
}
