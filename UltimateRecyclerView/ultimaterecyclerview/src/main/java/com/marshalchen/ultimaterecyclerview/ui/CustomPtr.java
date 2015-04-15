package com.marshalchen.ultimaterecyclerview.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by cym on 15/3/21.
 */
public class CustomPtr extends PtrFrameLayout {
    public CustomPtr(Context context) {
        super(context);
    }

    public CustomPtr(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPtr(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        return dispatchTouchEventSupper(e);
    }
}
