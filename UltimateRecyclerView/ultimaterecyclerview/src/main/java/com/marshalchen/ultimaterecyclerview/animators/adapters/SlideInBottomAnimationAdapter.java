package com.marshalchen.ultimaterecyclerview.animators.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Copyright (C) 2015 Wasabeef
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class SlideInBottomAnimationAdapter<T extends RecyclerView.ViewHolder> extends AnimationAdapter<T> {
/*
    public SlideInBottomAnimationAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
    }
    @NonNull
    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)
        };
    }*/

    private static final String TRANSLATION_Y = "translationY";

    public SlideInBottomAnimationAdapter(RecyclerView.Adapter<T> adapter,
                                         RecyclerView recyclerView) {
        super(adapter, recyclerView);
    }

    @NonNull
    @Override
    public Animator[] getAnimators(@NonNull View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, TRANSLATION_Y, mRecyclerView.getMeasuredHeight() >> 1, 0)};
    }
}
