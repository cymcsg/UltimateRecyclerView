package com.marshalchen.ultimaterecyclerview.ui;

import android.app.Activity;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by hesk on 5/8/15.
 */
public class AdGoogleDisplaySupport {
    public static <ad extends ViewGroup> void panelAdjust(ad view, final int height_pixel) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, height_pixel);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_BOTTOM, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        view.setLayoutParams(params);
    }

    public static double ratioMatching(DisplayMetrics Dm) {
        switch (Dm.densityDpi) {
            case 320:
                return 1.2d;
            default:
                return 1.2d;
        }
    }

    //320x50	標準橫幅廣告	手機和平板電腦	BANNER
    //320x100	大型橫幅廣告	手機和平板電腦	LARGE_BANNER
    //300x250	IAB 中矩形廣告	手機和平板電腦	MEDIUM_RECTANGLE
    //468x60	IAB 完整橫幅廣告	平板電腦	FULL_BANNER
    //728x90	IAB 超級橫幅廣告	平板電腦	LEADERBOARD
    //螢幕寬度 x 32|50|90	智慧型橫幅廣告	手機和平板電腦	SMART_BANNER
    public static int defaultHeight(int screenwidth) {
        switch (screenwidth) {
            case 1080:
                return 600;
            case 720:
                return 400;
            default:
                return 500;
        }
    }

    public static <T extends ViewGroup> void scale(T view, double scaleRatio) {
        ViewCompat.setScaleX(view, (float) scaleRatio);
        ViewCompat.setScaleY(view, (float) scaleRatio);
    }

    public static RelativeLayout initialSupport(Activity activity, DisplayMetrics dm) {
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        final RelativeLayout layout = new RelativeLayout(activity);
        return layout;
    }
}
