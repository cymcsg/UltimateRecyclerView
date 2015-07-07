package com.marshalchen.ultimaterecyclerview.demo.scrollableobservable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.uiUtils.ScrollUtils;
import com.marshalchen.ultimaterecyclerview.Scrollable;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.scrollableobservable.widget.SlidingTabLayout;
import com.marshalchen.ultimaterecyclerview.uiUtils.CacheFragmentStatePagerAdapter;
import com.marshalchen.ultimaterecyclerview.uiUtils.TouchInterceptionLayout;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by hesk on 12/6/15.
 */
public class ViewPagerTabFragmentParentFragment extends BaseFragment implements ObservableScrollViewCallbacks {

    public static final String FRAGMENT_TAG = "fragmentViewPager";

    private TouchInterceptionLayout mInterceptionLayout;
    private ViewPager mPager;
    private NavigationAdapter mPagerAdapter;
    private int mSlop;
    private boolean mScrolled = false;
    private ObservableScrollState mLastScrollState;
    private View mHeaderContainer;
    private ImageView headerBanner;
    private int slidingTabLayout_height, mBaseTranslationY;
    private SlidingTabLayout slidingTabLayout;
    private FrameLayout pager_wrapper;
    private int totalfullheight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simplefragment_viewpaper_fragment_parent, container, false);
        totalfullheight = view.getHeight();
        AppCompatActivity parentActivity = (AppCompatActivity) getActivity();
        mPagerAdapter = new NavigationAdapter(getChildFragmentManager());
        mPager = (ViewPager) view.findViewById(R.id.pager);
        pager_wrapper = (FrameLayout) view.findViewById(R.id.pager_wrapper);
        mPager.setAdapter(mPagerAdapter);
        mHeaderContainer = (View) view.findViewById(R.id.header);
        headerBanner = (ImageView) view.findViewById(R.id.header_background);
        slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        slidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
        slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.accent));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(mPager);
        ViewConfiguration vc = ViewConfiguration.get(parentActivity);
        mSlop = vc.getScaledTouchSlop();
        mInterceptionLayout = (TouchInterceptionLayout) view.findViewById(R.id.container);
        mInterceptionLayout.setScrollInterceptionListener(mInterceptionListener);
        mPager.setCurrentItem(0);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setpagertoppadding(headerBanner.getHeight() + slidingTabLayout.getHeight());
    }

    protected void setpagertoppadding(float m) {
        final int mheight = (int) Math.abs(m);
        pager_wrapper.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mheight));
        pager_wrapper.requestLayout();
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (dragging) {
            int headerBannerHeight = headerBanner.getHeight();
            float currentHeaderTranslationY = ViewCompat.getTranslationY(mHeaderContainer);
            if (firstScroll) {
                if (-headerBannerHeight < currentHeaderTranslationY) {
                    mBaseTranslationY = scrollY;
                }
            }
            float headerTranslationY = ScrollUtils.getFloat(mBaseTranslationY - scrollY, -headerBannerHeight, 0);
            ViewPropertyAnimator.animate(mHeaderContainer).cancel();
            ViewCompat.setTranslationY(mHeaderContainer, headerTranslationY);
            //todo: need some more works on this
            setpagertoppadding(totalfullheight - headerTranslationY);
        }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ObservableScrollState scrollState) {
        if (!mScrolled) {
            // This event can be used only when TouchInterceptionFrameLayout
            // doesn't handle the consecutive events.
            // toolbarAdjustment(scrollState);
            mBaseTranslationY = 0;

            Fragment fragment = getCurrentFragment();
            if (fragment == null) {
                return;
            }
            View view = fragment.getView();
            if (view == null) {
                return;
            }
            //    toolbarAdjustment(mLastScrollState, view);
        }
    }

    private TouchInterceptionLayout.TouchInterceptionListener mInterceptionListener = new TouchInterceptionLayout.TouchInterceptionListener() {
        @Override
        public boolean shouldInterceptTouchEvent(MotionEvent ev, boolean moving, float diffX, float diffY) {
            if (!mScrolled && mSlop < Math.abs(diffX) && Math.abs(diffY) < Math.abs(diffX)) {
                // Horizontal scroll is maybe handled by ViewPager
                return false;
            }

            Scrollable scrollable = getCurrentScrollable();
            if (scrollable == null) {
                mScrolled = false;
                return false;
            }

            // If interceptionLayout can move, it should intercept.
            // And once it begins to move, horizontal scroll shouldn't work any longer.
            // View toolbarView = getActivity().findViewById(R.id.toolbar);
            int headerBannerHeight = headerBanner.getHeight();
            int translationY = (int) ViewCompat.getTranslationY(mInterceptionLayout);
            boolean scrollingUp = 0 < diffY;
            boolean scrollingDown = diffY < 0;
            if (scrollingUp) {
                if (translationY < 0) {
                    mScrolled = true;
                    mLastScrollState = ObservableScrollState.UP;
                    return true;
                }
            } else if (scrollingDown) {
                if (-headerBannerHeight < translationY) {
                    mScrolled = true;
                    mLastScrollState = ObservableScrollState.DOWN;
                    return true;
                }
            }
            mScrolled = false;
            return false;
        }

        @Override
        public void onDownMotionEvent(MotionEvent ev) {
        }

        @Override
        public void onMoveMotionEvent(MotionEvent ev, float diffX, float diffY) {
         /*   View tView = adjustmentToolBarView();
            float translationY = ScrollUtils.getFloat(ViewCompat.getTranslationY(mInterceptionLayout) + diffY, -tView.getHeight(), 0);
            ViewCompat.setTranslationY(mInterceptionLayout, translationY);
            ViewCompat.setTranslationY(tView, translationY);
            if (translationY < 0) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mInterceptionLayout.getLayoutParams();
                lp.height = (int) (-translationY + getScreenHeight());
                mInterceptionLayout.requestLayout();
            }*/
            float translationY = ScrollUtils.getFloat(ViewCompat.getTranslationY(mInterceptionLayout) + diffY, -headerBanner.getHeight(), 0);
            ViewCompat.setTranslationY(mInterceptionLayout, translationY);
            if (translationY < 0) {
                // start getting smaller
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mInterceptionLayout.getLayoutParams();
                lp.height = (int) (-translationY + getScreenHeight());
                mInterceptionLayout.requestLayout();
            }
        }

        @Override
        public void onUpOrCancelMotionEvent(MotionEvent ev) {
            mScrolled = false;
            //  toolbarAdjustment(mLastScrollState);
        }
    };

    private Scrollable getCurrentScrollable() {
        Fragment fragment = getCurrentFragment();
        if (fragment == null) {
            return null;
        }
        View view = fragment.getView();
        if (view == null) {
            return null;
        }
        return viewscrollable(view);
    }

    private Scrollable viewscrollable(View fromFragmentView) {
        return (Scrollable) fromFragmentView.findViewById(R.id.scroll);

    }

    private View adjustmentToolBarView() {
        return getActivity().findViewById(R.id.toolbar);
        //  return getCurrentFragment().getView().findViewById(R.id.header);
    }

    private void toolbarAdjustment(ObservableScrollState scrollState) {
        View tView = adjustmentToolBarView();
        int toolbarHeight = tView.getHeight();
        final Scrollable scrollable = getCurrentScrollable();
        if (scrollable == null) {
            return;
        }
        int scrollY = scrollable.getCurrentScrollY();
        if (scrollState == ObservableScrollState.DOWN) {
            showToolbar();
        } else if (scrollState == ObservableScrollState.UP) {
            if (toolbarHeight <= scrollY) {
                hideToolbar();
            } else {
                showToolbar();
            }
        } else if (!toolbarIsShown() && !toolbarIsHidden()) {
            // Toolbar is moving but doesn't know which to move:
            // you can change this to hideToolbar()
            showToolbar();
        }
    }


    private void toolbarAdjustment(ObservableScrollState scrollState, View view) {
        int toolbarHeight = headerBanner.getHeight();
        final Scrollable scrollView = viewscrollable(view);
        if (scrollView == null) {
            return;
        }
        int scrollY = scrollView.getCurrentScrollY();
        if (scrollState == ObservableScrollState.DOWN) {
            showToolbar();
        } else if (scrollState == ObservableScrollState.UP) {
            if (toolbarHeight <= scrollY) {
                hideToolbar();
            } else {
                showToolbar();
            }
        } else {
            // Even if onScrollChanged occurs without scrollY changing, toolbar should be adjusted
            if (toolbarIsShown() || toolbarIsHidden()) {
                // Toolbar is completely moved, so just keep its state
                // and propagate it to other pages
                propagateToolbarState(toolbarIsShown());
            } else {
                // Toolbar is moving but doesn't know which to move:
                // you can change this to hideToolbar()
                showToolbar();
            }
        }
    }

    private void propagateToolbarState(boolean isShown) {
        final int toolbarHeight = headerBanner.getHeight();
        // Set scrollY for the fragments that are not created yet
        mPagerAdapter.setScrollY(isShown ? 0 : toolbarHeight);

        // Set scrollY for the active fragments
        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            // Skip current item
            if (i == mPager.getCurrentItem()) {
                continue;
            }
            // Skip destroyed or not created item
            Fragment f = mPagerAdapter.getItemAt(i);
            if (f == null) {
                continue;
            }
            View view = f.getView();
            if (view == null) {
                continue;
            }

            if (view.findViewById(R.id.scroll) instanceof UltimateRecyclerView) {
                UltimateRecyclerView listView = (UltimateRecyclerView) viewscrollable(view);
                if (isShown) {
                    // Scroll up
                    if (0 < listView.getCurrentScrollY()) {
                        // listView.setSelection(0);
                        Log.d(FRAGMENT_TAG, "up");
                    }
                } else {
                    // Scroll down (to hide padding)
                    if (listView.getCurrentScrollY() < toolbarHeight) {
                        //listView.setSelection(1);
                        Log.d(FRAGMENT_TAG, "down");
                    }
                }
            }

        }
    }

    private Fragment getCurrentFragment() {
        return mPagerAdapter.getItemAt(mPager.getCurrentItem());
    }

    private boolean toolbarIsShown() {
        return ViewCompat.getTranslationY(mInterceptionLayout) == 0;
    }

    private boolean toolbarIsHidden() {
        View view = getView();
        if (view == null) {
            return false;
        }
        View tView = adjustmentToolBarView();
        return ViewCompat.getTranslationY(mInterceptionLayout) == -tView.getHeight();
    }

    private void showToolbar() {
        animateToolbar(0);
    }

    private void hideToolbar() {
        View tView = adjustmentToolBarView();
        animateToolbar(-tView.getHeight());
    }

    private void animateToolbar(final float toY) {
        float layoutTranslationY = ViewCompat.getTranslationY(mInterceptionLayout);
        if (layoutTranslationY != toY) {
            ValueAnimator animator = ValueAnimator.ofFloat(ViewCompat.getTranslationY(mInterceptionLayout), toY).setDuration(200);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float translationY = (float) animation.getAnimatedValue();
                    View tView = adjustmentToolBarView();
                    ViewCompat.setTranslationY(mInterceptionLayout, translationY);
                    ViewCompat.setTranslationY(tView, translationY);
                    if (translationY < 0) {
                        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mInterceptionLayout.getLayoutParams();
                        lp.height = (int) (-translationY + getScreenHeight());
                        mInterceptionLayout.requestLayout();
                    }
                }
            });
            animator.start();
        }
    }

    /**
     * This adapter provides two types of fragments as an example.
     * {@linkplain #createItem(int)} should be modified if you use this example for your app.
     */
    private static class NavigationAdapter extends CacheFragmentStatePagerAdapter {

        private static final String[] TITLES = new String[]{"Applepie", "Butter Cookie", "Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop"};
        //  private static final String[] TITLES = new String[]{"fff", "nd eee"};

        public NavigationAdapter(FragmentManager fm) {
            super(fm);
        }

        private int mScrollY;

        public void setScrollY(int scrollY) {
            mScrollY = scrollY;
        }

        @Override
        protected Fragment createItem(int position) {

            Fragment f = new ViewPagerFragmentListSingle();
            if (0 < mScrollY) {
                Bundle args = new Bundle();
                // args.putInt(ViewPagerTab2RecyclerViewFragment.ARG_INITIAL_POSITION, 1);
                f.setArguments(args);
            }

            return f;
            /*
            Fragment f;
            final int pattern = position % 5;
            switch (pattern) {
                case 0:
                    f = new ViewPagerTab2RecyclerViewFragment();
                    break;
                case 1:
                    f = new ViewPagerTab2RecyclerViewFragment();
                    break;
                case 2:
                    f = new ViewPagerTab2RecyclerViewFragment();
                    break;
                case 3:
                    f = new ViewPagerTab2RecyclerViewFragment();
                    break;
                case 4:
                default:
                    f = new ViewPagerTab2RecyclerViewFragment();
                    break;
            }
            return f;*/
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
    }
}
