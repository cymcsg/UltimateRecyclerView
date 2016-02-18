package com.marshalchen.ultimaterecyclerview.animators.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hesk on 18/2/16.
 */
public class SwingBottomInAnimationAdapter extends AnimationAdapter {
    private static final String TRANSLATION_Y = "translationY";

    public SwingBottomInAnimationAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, RecyclerView recyclerView) {
        super(adapter, recyclerView);
    }

    @NonNull
    @Override
    public Animator[] getAnimators(@NonNull View view) {
        float mOriginalY = mRecyclerView.getLayoutManager().getDecoratedTop(view);
        float mDeltaY = mRecyclerView.getHeight() - mOriginalY;

        return new Animator[]{ObjectAnimator.ofFloat(view, TRANSLATION_Y, mDeltaY, 0)};
    }
}
