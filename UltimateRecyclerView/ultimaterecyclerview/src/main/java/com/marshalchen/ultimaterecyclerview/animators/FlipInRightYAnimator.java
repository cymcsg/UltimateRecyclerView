package com.marshalchen.ultimaterecyclerview.animators;

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

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;

public class FlipInRightYAnimator extends BaseItemAnimator {
    public FlipInRightYAnimator(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    protected void prepareAnimateAdd(RecyclerView.ViewHolder holder) {
        ViewCompat.setRotationY(holder.itemView, -90);
    }

    @Override
    protected void animateAddImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.animate(holder.itemView)
                .rotationY(0)
                .setDuration(getAddDuration())
                .setListener(new DefaultAddVpaListener(holder)).start();
    }

    @Override
    protected void animateRemoveImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.animate(holder.itemView)
                .rotationY(-90)
                .setDuration(getRemoveDuration())
                .setListener(new DefaultRemoveVpaListener(holder))
                .start();
    }
/*
    @Override
    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        ViewCompat.animate(holder.itemView)
                .rotationY(-90)
                .setDuration(getRemoveDuration())
                .setListener(new DefaultRemoveVpaListener(holder))
                .start();
    }

    @Override
    protected void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.setRotationY(holder.itemView, -90);
    }

    @Override
    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
        ViewCompat.animate(holder.itemView)
                .rotationY(0)
                .setDuration(getAddDuration())
                .setListener(new DefaultAddVpaListener(holder)).start();
    }*/
}
