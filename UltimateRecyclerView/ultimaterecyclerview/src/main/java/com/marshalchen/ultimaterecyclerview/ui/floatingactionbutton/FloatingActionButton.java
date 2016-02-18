package com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;

import com.marshalchen.ultimaterecyclerview.R;



public class FloatingActionButton extends ImageButton {

    public static final int SIZE_NORMAL = 0;
    public static final int SIZE_NOSHADOW = 2;
    public static final int SIZE_MINI = 1;

    private static final int HALF_TRANSPARENT_WHITE = Color.argb(128, 255, 255, 255);
    private static final int HALF_TRANSPARENT_BLACK = Color.argb(128, 0, 0, 0);
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    protected int mColorNormal;
    protected int mColorPressed;
    @DrawableRes
    protected int mIcon;
    protected int mSize;
    protected float mCircleSize;
    protected float mShadowRadius;
    protected float mShadowOffset;
    protected int mDrawableSize;
    private boolean mHidden = false;
    /**
     * The FAB button's Y position when it is displayed.
     */
    private float mYDisplayed = -1;
    /**
     * The FAB button's Y position when it is hidden.
     */
    private float mYHidden = -1;

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attributeSet) {
        mColorNormal = getColor(android.R.color.holo_blue_dark);
        mColorPressed = getColor(android.R.color.holo_blue_light);
        mIcon = 0;
        mSize = SIZE_NORMAL;
        if (attributeSet != null) {
            initAttributes(context, attributeSet);
        }

        mCircleSize = getCircleSize(mSize);
        mShadowRadius = getDimension(R.dimen.fab_shadow_radius);
        mShadowOffset = getDimension(R.dimen.fab_shadow_offset);
        mDrawableSize = (int) (mCircleSize + 2 * mShadowRadius);

        //point size overhead
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = mWindowManager.getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(size);
            mYHidden = size.y;
        } else mYHidden = display.getHeight();

        updateBackground();
    }

    protected void updateBackground() {
        float circleLeft = mShadowRadius;
        float circleTop = mShadowRadius - mShadowOffset;
        final RectF circleRect = new RectF(circleLeft, circleTop, circleLeft + mCircleSize, circleTop + mCircleSize);
        final LayerDrawable layerDrawable = generateFinalDrawables(circleRect);
        float iconOffset = (mCircleSize - getDimension(R.dimen.fab_icon_size)) / 2f;
        int iconInsetHorizontal = (int) (mShadowRadius + iconOffset);
        int iconInsetTop = (int) (circleTop + iconOffset);
        int iconInsetBottom = (int) (mShadowRadius + mShadowOffset + iconOffset);
        layerDrawable.setLayerInset(layerDrawable.getNumberOfLayers() - 1, iconInsetHorizontal, iconInsetTop, iconInsetHorizontal, iconInsetBottom);
        setBackgroundCompat(layerDrawable);
    }

    protected int getColor(@ColorRes int id) {
        return getResources().getColor(id);
    }

    protected float getDimension(@DimenRes int id) {
        return getResources().getDimension(id);
    }

    protected void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.FloatActionButton, 0, 0);
        if (attr != null) {
            try {
                mColorNormal = attr.getColor(R.styleable.FloatActionButton_urv_fab_colorNormal, getColor(android.R.color.holo_blue_dark));
                mColorPressed = attr.getColor(R.styleable.FloatActionButton_urv_fab_colorPressed, getColor(android.R.color.holo_blue_light));
                mSize = attr.getInt(R.styleable.FloatActionButton_urv_fab_size, SIZE_NORMAL);
                mIcon = attr.getResourceId(R.styleable.FloatActionButton_icon, 0);
                load_extended_attributes(attr);
            } finally {
                attr.recycle();
            }
        }
    }

    protected void load_extended_attributes(TypedArray attr) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mDrawableSize, mDrawableSize);
    }

    protected float getCircleSize(int mSize) {
        switch (mSize) {
            case SIZE_NORMAL:
                return getDimension(R.dimen.fab_size_normal);
            case SIZE_MINI:
                return getDimension(R.dimen.fab_size_mini);
            case SIZE_NOSHADOW:
                return getDimension(R.dimen.fab_size_normal);
            default:
                return getDimension(R.dimen.fab_size_normal);
        }
    }

    @DrawableRes
    protected int getDrawableBySize(int mSize) {
        switch (mSize) {
            case SIZE_NORMAL:
                return R.drawable.urv_floating_action_button_fab_bg_normal;
            case SIZE_MINI:
                return R.drawable.urv_floating_action_button_fab_bg_mini;
            case SIZE_NOSHADOW:
                return -1;
        }
        return -1;
    }

    protected LayerDrawable generateFinalDrawables(RectF circleRect) {
        if (mSize == SIZE_NOSHADOW) {
            return new LayerDrawable(
                    new Drawable[]{
                            createFillDrawable(circleRect),
                            createStrokesDrawable(circleRect),
                            getIconDrawable()
                    });
        } else {
            return new LayerDrawable(
                    new Drawable[]{
                            getResources().getDrawable(getDrawableBySize(mSize)),
                            createFillDrawable(circleRect),
                            createStrokesDrawable(circleRect),
                            getIconDrawable()
                    });
        }
    }

    protected Drawable getIconDrawable() {
        if (mIcon != 0) {
            return getResources().getDrawable(mIcon);
        } else {
            return new ColorDrawable(Color.TRANSPARENT);
        }
    }

    /**
     * @param circleRect the defined rectangle
     * @param alpha      between  0 - 1
     * @return StateListDrawable
     */
    protected StateListDrawable createFillDrawable(RectF circleRect, float alpha) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, createAlphaDrawble(circleRect, mColorPressed, alpha));
        drawable.addState(new int[]{}, createAlphaDrawble(circleRect, mColorNormal, alpha));
        return drawable;
    }

    /**
     * @param circleRect the defined rectangle
     * @return StateListDrawable
     */
    protected StateListDrawable createFillDrawable(RectF circleRect) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, createCircleDrawable(circleRect, mColorPressed));
        drawable.addState(new int[]{}, createCircleDrawable(circleRect, mColorNormal));
        return drawable;
    }

    protected Drawable createCircleDrawable(RectF circleRect, int color) {
        final Bitmap bitmap = Bitmap.createBitmap(mDrawableSize, mDrawableSize, Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawOval(circleRect, paint);
        return new BitmapDrawable(getResources(), bitmap);
    }

    protected Drawable createAlphaDrawble(RectF circleRect, int color, float alpha) {
        final Bitmap bitmap = Bitmap.createBitmap(mDrawableSize, mDrawableSize, Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setAlpha(opacityToAlpha(alpha));
        canvas.drawOval(circleRect, paint);
        return new BitmapDrawable(getResources(), bitmap);
    }

    protected int opacityToAlpha(float opacity) {
        return (int) (255f * opacity);
    }

    protected Drawable createStrokesDrawable(RectF circleRect) {
        final Bitmap bitmap = Bitmap.createBitmap(mDrawableSize, mDrawableSize, Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);

        final float strokeWidth = getDimension(R.dimen.fab_stroke_width);
        final float halfStrokeWidth = strokeWidth / 2f;

        RectF outerStrokeRect = new RectF(
                circleRect.left - halfStrokeWidth,
                circleRect.top - halfStrokeWidth,
                circleRect.right + halfStrokeWidth,
                circleRect.bottom + halfStrokeWidth
        );

        RectF innerStrokeRect = new RectF(
                circleRect.left + halfStrokeWidth,
                circleRect.top + halfStrokeWidth,
                circleRect.right - halfStrokeWidth,
                circleRect.bottom - halfStrokeWidth
        );

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Style.STROKE);

        // outer
        paint.setColor(Color.BLACK);
        paint.setAlpha(opacityToAlpha(0.02f));
        canvas.drawOval(outerStrokeRect, paint);

        // inner bottom
        paint.setShader(new LinearGradient(innerStrokeRect.centerX(), innerStrokeRect.top, innerStrokeRect.centerX(), innerStrokeRect.bottom,
                new int[]{Color.TRANSPARENT, HALF_TRANSPARENT_BLACK, Color.BLACK},
                new float[]{0f, 0.8f, 1f},
                TileMode.CLAMP
        ));
        paint.setAlpha(opacityToAlpha(0.04f));
        canvas.drawOval(innerStrokeRect, paint);

        // inner top
        paint.setShader(new LinearGradient(innerStrokeRect.centerX(), innerStrokeRect.top, innerStrokeRect.centerX(), innerStrokeRect.bottom,
                new int[]{Color.WHITE, HALF_TRANSPARENT_WHITE, Color.TRANSPARENT},
                new float[]{0f, 0.2f, 1f},
                TileMode.CLAMP
        ));
        paint.setAlpha(opacityToAlpha(0.8f));
        canvas.drawOval(innerStrokeRect, paint);

        return new BitmapDrawable(getResources(), bitmap);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void setBackgroundCompat(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    public void hide(boolean hide) {
        // If the hidden state is being updated
        if (mHidden != hide) {

            // Store the new hidden state
            mHidden = hide;

            // Animate the FAB to it's new Y position
            ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationY", mHidden ? mYHidden - mYDisplayed : 0).setDuration(500);

            animator.setInterpolator(mInterpolator);
            animator.start();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mYDisplayed == -1) {
            mYDisplayed =    ViewCompat.getY(this);
        }
    }
}
