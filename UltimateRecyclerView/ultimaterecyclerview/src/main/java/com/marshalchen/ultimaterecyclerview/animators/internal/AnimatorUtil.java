package com.marshalchen.ultimaterecyclerview.animators.internal;

import android.animation.Animator;
import android.support.annotation.NonNull;
/**
 * Created by hesk on 18/2/16.
 */
public class AnimatorUtil {

    private AnimatorUtil() {
    }

    /**
     * Merges given Animators into one array.
     */
    @NonNull
    public static Animator[] concatAnimators(@NonNull final Animator[] animators, @NonNull final Animator alphaAnimator) {
        Animator[] allAnimators = new Animator[animators.length + 1];
        int i = 0;

        for (Animator animator : animators) {
            allAnimators[i] = animator;
            ++i;
        }
        allAnimators[allAnimators.length - 1] = alphaAnimator;
        return allAnimators;
    }
}
