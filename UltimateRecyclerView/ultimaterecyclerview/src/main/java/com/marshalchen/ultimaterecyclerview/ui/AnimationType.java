package com.marshalchen.ultimaterecyclerview.ui;


import android.view.animation.OvershootInterpolator;

import jp.wasabeef.recyclerview.animators.BaseItemAnimator;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.FlipInLeftYAnimator;
import jp.wasabeef.recyclerview.animators.FlipInRightYAnimator;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import jp.wasabeef.recyclerview.animators.OvershootInRightAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInLeftAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInRightAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * https://github.com/wasabeef/recyclerview-animators/blob/master/example/src/main/java/jp/wasabeef/example/recyclerview/AnimatorSampleActivity.java
 * Created by zJJ on 2/19/2016.
 */
public enum AnimationType {

    FadeIn(new FadeInAnimator(new OvershootInterpolator(1f))),
    FadeInDown(new FadeInDownAnimator(new OvershootInterpolator(1f))),
    FadeInUp(new FadeInUpAnimator(new OvershootInterpolator(1f))),
    FadeInLeft(new FadeInLeftAnimator(new OvershootInterpolator(1f))),
    FadeInRight(new FadeInRightAnimator(new OvershootInterpolator(1f))),
    Landing(new LandingAnimator(new OvershootInterpolator(1f))),
    ScaleIn(new ScaleInAnimator(new OvershootInterpolator(1f))),
    ScaleInTop(new ScaleInTopAnimator(new OvershootInterpolator(1f))),
    ScaleInBottom(new ScaleInBottomAnimator(new OvershootInterpolator(1f))),
    ScaleInLeft(new ScaleInLeftAnimator(new OvershootInterpolator(1f))),
    ScaleInRight(new ScaleInRightAnimator(new OvershootInterpolator(1f))),
    FlipInTopX(new FlipInTopXAnimator(new OvershootInterpolator(1f))),
    FlipInBottomX(new FlipInBottomXAnimator(new OvershootInterpolator(1f))),
    FlipInLeftY(new FlipInLeftYAnimator(new OvershootInterpolator(1f))),
    FlipInRightY(new FlipInRightYAnimator(new OvershootInterpolator(1f))),
    SlideInLeft(new SlideInLeftAnimator(new OvershootInterpolator(1f))),
    SlideInRight(new SlideInRightAnimator(new OvershootInterpolator(1f))),
    SlideInDown(new SlideInDownAnimator(new OvershootInterpolator(1f))),
    SlideInUp(new SlideInUpAnimator(new OvershootInterpolator(1f))),
    OvershootInRight(new OvershootInRightAnimator(1.0f)),
    OvershootInLeft(new OvershootInLeftAnimator(1.0f));

    private BaseItemAnimator mAnimator;

    AnimationType(BaseItemAnimator animator) {
        mAnimator = animator;
    }

    public BaseItemAnimator getAnimator() {
        return mAnimator;

    }
}
