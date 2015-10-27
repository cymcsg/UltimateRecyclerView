package com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import com.marshalchen.ultimaterecyclerview.R;

/**
 * it is just another static button to show as the alpha enhanced appearance
 * Created by hesk on 13/10/2015.
 * LICENSE. MIT
 */
public class JellyBeanFloatingActionButton extends FloatingActionButton {
    protected float mAlpha_press, mAlpha_normal;

    public JellyBeanFloatingActionButton(Context context) {
        super(context);
    }

    public JellyBeanFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JellyBeanFloatingActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void load_extended_attributes(TypedArray attr) {
        mAlpha_press = attr.getFloat(R.styleable.FloatActionButton_urv_fab_alphaPressed, 0.5f);
        mAlpha_normal = attr.getFloat(R.styleable.FloatActionButton_urv_fab_alphaNormal, 0.5f);
        //override important settings
        mSize = SIZE_NOSHADOW;
    }

    /**
     * more advanced usage for fillable in alpha
     *
     * @param circleRect the defined rectangle
     * @return StateListDrawable item
     */
    protected StateListDrawable createFillDrawable(RectF circleRect) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, createAlphaDrawble(circleRect, mColorPressed, mAlpha_press));
        drawable.addState(new int[]{}, createAlphaDrawble(circleRect, mColorNormal, mAlpha_normal));
        return drawable;
    }

    protected LayerDrawable generateFinalDrawables(RectF circleRect) {
        LayerDrawable layerDrawable = new LayerDrawable(
                new Drawable[]{
                        createFillDrawable(circleRect),
                        getIconDrawable()
                });
        return layerDrawable;
    }

}
