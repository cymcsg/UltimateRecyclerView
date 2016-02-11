package com.marshalchen.ultimaterecyclerview.demo.modules;

import android.support.annotation.DrawableRes;

/**
 * Created by hesk on 3/2/16.
 */
public class JRitem {
    @DrawableRes
    public final int photo_id;
    public final String train_name;

    public JRitem(@DrawableRes final int photo_idjr, String name) {
        photo_id = photo_idjr;
        train_name = name;
    }
}
