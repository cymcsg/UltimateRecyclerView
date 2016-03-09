package com.marshalchen.ultimaterecyclerview.ui;

import android.app.Activity;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * The final solution hack for quick adaption on Google Admob display defects
 * Created by hesk on 5/8/15.
 * Self developed by Heskeyo Kam 2015
 */
public class AdGoogleDisplaySupport {
    public static final int BANNER = 101;
    public static final int FULL_BANNER = 102;
    public static final int LARGE_BANNER = 103;
    public static final int LEADERBOARD = 104;
    public static final int MEDIUM_RECTANGLE = 105;
    public static final int WIDE_SKYSCRAPER = 106;

    public static RelativeLayout initialSupport(Activity activity, DisplayMetrics dm) {
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        final RelativeLayout layout = new RelativeLayout(activity);
        return layout;
    }

    public static <ad extends ViewGroup> void panelAdjust(ad view, final int height_pixel, int AdType) {
        panelAdjustApply(view, height_pixel);
    }

    public static <ad extends ViewGroup> void panelAdjust(ad view, final int height_pixel) {
        panelAdjustApply(view, height_pixel);
    }

    private static <ad extends ViewGroup> void panelAdjustApply(ad view, final int height_pixel) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height_pixel);
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

    public static int defaultHeight(DisplayMetrics Dm) {
        return defaultHeightApply(Dm, MEDIUM_RECTANGLE);
    }

    public static int defaultHeight(DisplayMetrics Dm, int type) {
        return defaultHeightApply(Dm, type);
    }

    private static int defaultHeightApply(DisplayMetrics Dm, int type) {
        if (type == MEDIUM_RECTANGLE) {
            switch (Dm.widthPixels) {
                case 1080:
                    return 600;
                case 720:
                    return 400;
                default:
                    return 500;
            }
        }
        if (type == BANNER) {
            switch (Dm.widthPixels) {
                case 1080:
                    return 100;
                case 720:
                    return 80;
                default:
                    return 80;
            }
        }


        return 100;
    }

    public static <T extends ViewGroup> void scale(T view, double scaleRatio) {
        ViewCompat.setScaleX(view, (float) scaleRatio);
        ViewCompat.setScaleY(view, (float) scaleRatio);
    }


}
