package com.marshalchen.ultimaterecyclerview.animators.internal;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
/**
 * Created by hesk on 18/2/16.
 */

/**
 * A class which decides whether given Views should be animated based on their position: each View should only be animated once.
 * It also calculates proper animation delays for the views.
 */
public class ViewAnimator {
    /* Saved instance state keys */
    private static final String SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION = "savedinstancestate_firstanimatedposition";
    private static final String SAVEDINSTANCESTATE_LASTANIMATEDPOSITION = "savedinstancestate_lastanimatedposition";
    private static final String SAVEDINSTANCESTATE_SHOULDANIMATE = "savedinstancestate_shouldanimate";

    /* Default values */

    /**
     * The default delay in millis before the first animation starts.
     */
    private static final int INITIAL_DELAY_MILLIS = 150;

    /**
     * The default delay in millis between view animations.
     */
    private static final int DEFAULT_ANIMATION_DELAY_MILLIS = 100;

    /**
     * The default duration in millis of the animations.
     */
    private static final int DEFAULT_ANIMATION_DURATION_MILLIS = 300;

    /* Fields */

    /**
     * The ListViewWrapper containing the ListView implementation.
     */
    @NonNull
    private final RecyclerView mRecyclerView;

    /**
     * The active Animators. Keys are hashcodes of the Views that are animated.
     */
    @NonNull
    private final SparseArray<Animator> mAnimators = new SparseArray<>();

    /**
     * The delay in millis before the first animation starts.
     */
    private int mInitialDelayMillis = INITIAL_DELAY_MILLIS;

    /**
     * The delay in millis between view animations.
     */
    private int mAnimationDelayMillis = DEFAULT_ANIMATION_DELAY_MILLIS;

    /**
     * The duration in millis of the animations.
     */
    private int mAnimationDurationMillis = DEFAULT_ANIMATION_DURATION_MILLIS;

    /**
     * The start timestamp of the first animation, as returned by {@link SystemClock#uptimeMillis()}.
     */
    private long mAnimationStartMillis;

    /**
     * The position of the item that is the first that was animated.
     */
    private int mFirstAnimatedPosition;

    /**
     * The position of the last item that was animated.
     */
    private int mLastAnimatedPosition;

    /**
     * Whether animation is enabled. When this is set to false, no animation is applied to the views.
     */
    private boolean mShouldAnimate = true;

    /**
     * Creates a new ViewAnimator, using the given {@link RecyclerView}.
     *
     * @param recyclerView the {@code ListViewWrapper} which wraps the implementation of the ListView used.
     */
    public ViewAnimator(@NonNull final RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mAnimationStartMillis = -1;
        mFirstAnimatedPosition = -1;
        mLastAnimatedPosition = -1;
    }

    /**
     * Call this method to reset animation status on all views.
     */
    public void reset() {
        for (int i = 0; i < mAnimators.size(); i++) {
            mAnimators.get(mAnimators.keyAt(i)).cancel();
        }
        mAnimators.clear();
        mFirstAnimatedPosition = -1;
        mLastAnimatedPosition = -1;
        mAnimationStartMillis = -1;
        mShouldAnimate = true;
    }

    /**
     * Set the starting position for which items should animate. Given position will animate as well.
     * Will also call {@link #enableAnimations()}.
     *
     * @param position the position.
     */
    public void setShouldAnimateFromPosition(final int position) {
        enableAnimations();
        mFirstAnimatedPosition = position - 1;
        mLastAnimatedPosition = position - 1;
    }

    /**
     * Set the starting position for which items should animate as the first position which isn't currently visible on screen. This call is also valid when the {@link View}s
     * haven't been drawn yet. Will also call {@link #enableAnimations()}.
     */
    public void setShouldAnimateNotVisible() {
        enableAnimations();
        mFirstAnimatedPosition = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        mLastAnimatedPosition = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
    }

    /**
     * Sets the value of the last animated position. Views with positions smaller than or equal to given value will not be animated.
     */
    void setLastAnimatedPosition(final int lastAnimatedPosition) {
        mLastAnimatedPosition = lastAnimatedPosition;
    }

    /**
     * Sets the delay in milliseconds before the first animation should start. Defaults to {@value #INITIAL_DELAY_MILLIS}.
     *
     * @param delayMillis the time in milliseconds.
     */
    public void setInitialDelayMillis(final int delayMillis) {
        mInitialDelayMillis = delayMillis;
    }

    /**
     * Sets the delay in milliseconds before an animation of a view should start. Defaults to {@value #DEFAULT_ANIMATION_DELAY_MILLIS}.
     *
     * @param delayMillis the time in milliseconds.
     */
    public void setAnimationDelayMillis(final int delayMillis) {
        mAnimationDelayMillis = delayMillis;
    }

    /**
     * Sets the duration of the animation in milliseconds. Defaults to {@value #DEFAULT_ANIMATION_DURATION_MILLIS}.
     *
     * @param durationMillis the time in milliseconds.
     */
    public void setAnimationDurationMillis(final int durationMillis) {
        mAnimationDurationMillis = durationMillis;
    }

    /**
     * Enables animating the Views. This is the default.
     */
    public void enableAnimations() {
        mShouldAnimate = true;
    }

    /**
     * Disables animating the Views. Enable them again using {@link #enableAnimations()}.
     */
    public void disableAnimations() {
        mShouldAnimate = false;
    }

    /**
     * Cancels any existing animations for given View.
     */
    public void cancelExistingAnimation(@NonNull final View view) {
        int hashCode = view.hashCode();
        Animator animator = mAnimators.get(hashCode);
        if (animator != null) {
            animator.end();
            mAnimators.remove(hashCode);
        }
    }

    /**
     * Animates given View if necessary.
     *
     * @param position the position of the item the View represents.
     * @param view     the View that should be animated.
     */
    public void animateViewIfNecessary(final int position, @NonNull final View view, @NonNull final Animator[] animators) {
        if (mShouldAnimate && position > mLastAnimatedPosition) {
            if (mFirstAnimatedPosition == -1) {
                mFirstAnimatedPosition = position;
            }

            animateView(position, view, animators);
            mLastAnimatedPosition = position;
        }
    }

    /**
     * Animates given View.
     *
     * @param view the View that should be animated.
     */
    private void animateView(final int position, @NonNull final View view, @NonNull final Animator[] animators) {
        if (mAnimationStartMillis == -1) {
            mAnimationStartMillis = SystemClock.uptimeMillis();
        }

        ViewCompat.setAlpha(view, 0);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        set.setStartDelay(calculateAnimationDelay(position));
        set.setDuration(mAnimationDurationMillis);
        set.start();

        mAnimators.put(view.hashCode(), set);
    }

    /**
     * Returns the delay in milliseconds after which animation for View with position mLastAnimatedPosition + 1 should start.
     */
    @SuppressLint("NewApi")
    private int calculateAnimationDelay(final int position) {
        int delay;

        int lastVisiblePosition = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
        int firstVisiblePosition = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

        if (mLastAnimatedPosition > lastVisiblePosition) lastVisiblePosition = mLastAnimatedPosition;

        int numberOfItemsOnScreen = lastVisiblePosition - firstVisiblePosition;
        int numberOfAnimatedItems = position - 1 - mFirstAnimatedPosition;

        if (numberOfItemsOnScreen + 1 < numberOfAnimatedItems) {
            delay = mAnimationDelayMillis;

            if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                int numColumns = ( (GridLayoutManager)mRecyclerView.getLayoutManager()).getSpanCount();
                delay += mAnimationDelayMillis * (position % numColumns);
                Log.d("GAB", "Delay[" + position + "]=*"+lastVisiblePosition+"|"+firstVisiblePosition+"|");
            }
        } else {
            int delaySinceStart = (position - mFirstAnimatedPosition) * mAnimationDelayMillis;
            delay = Math.max(0, (int) (-SystemClock.uptimeMillis() + mAnimationStartMillis + mInitialDelayMillis + delaySinceStart));
        }
        Log.d("GAB", "Delay[" + position + "]=" + delay+"|"+lastVisiblePosition+"|"+firstVisiblePosition+"|");
        return delay;
    }

    /**
     * Returns a Parcelable object containing the AnimationAdapter's current dynamic state.
     */
    @NonNull
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        bundle.putInt(SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION, mFirstAnimatedPosition);
        bundle.putInt(SAVEDINSTANCESTATE_LASTANIMATEDPOSITION, mLastAnimatedPosition);
        bundle.putBoolean(SAVEDINSTANCESTATE_SHOULDANIMATE, mShouldAnimate);

        return bundle;
    }

    /**
     * Restores this AnimationAdapter's state.
     *
     * @param parcelable the Parcelable object previously returned by {@link #onSaveInstanceState()}.
     */
    public void onRestoreInstanceState(@Nullable final Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            mFirstAnimatedPosition = bundle.getInt(SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION);
            mLastAnimatedPosition = bundle.getInt(SAVEDINSTANCESTATE_LASTANIMATEDPOSITION);
            mShouldAnimate = bundle.getBoolean(SAVEDINSTANCESTATE_SHOULDANIMATE);
        }
    }

}
