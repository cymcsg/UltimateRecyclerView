package com.marshalchen.ultimaterecyclerview.quickAdapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.List;

/**
 * Enhanced Google Admob implementation
 * Created by hesk on 20/5/15.
 */
public abstract class AdmobAdapter<Adv extends ViewGroup, T, BINDHOLDER extends AdItemHolder> extends easyRegularAdapter<T, BINDHOLDER> {
    public interface AdviewListener<Adv extends ViewGroup> {
        Adv onGenerateAdview();
    }

    protected final Adv advertise_view;
    /**
     * There is an AD between the amount of the data items. adfrequency is known as the amount.
     */
    protected int adfrequency;
    /**
     * The ad is only insert once and no more.
     */
    protected boolean once;
    protected AdviewListener adviewlistener;
    public static final int POSITION_ON_AD = -1;

    /**
     * This is the enhanced listview injection model that will be able to work with Googlge Admob, DoubleClick, and
     * related custom cell injection on the fly. It provides two specific options for the view to be taken place.
     * While insertOnce=true will increase the performance and false will slow down a little bit on the performance but it is insignificant.
     *
     * @param adview      The AD mob view
     * @param insertOnce  only insert once and hold into the adapter object, that means the ad will only shown once in the list view
     * @param setInterval the order of item to show the ad. if @insertOnce=false the ad will show on interval bases.
     * @param L           the data source
     */
    public AdmobAdapter(Adv adview, boolean insertOnce, int setInterval, List<T> L) {
        this(adview, insertOnce, setInterval, L, null);
    }


    /**
     * This is same to the above method
     *
     * @param advertisement_view The AD mob view
     * @param insertOnce         only insert once and hold into the adapter object, that means the ad will only shown once in the list view
     * @param setInterval        the order of item to show the ad. if @insertOnce=false the ad will show on interval bases.
     * @param L                  the data source
     * @param listener           The listener for the admob cell to reveal when the cell is close to appear on the screen
     */
    public AdmobAdapter(
            final Adv advertisement_view,
            boolean insertOnce,
            int setInterval,
            List<T> L,
            @Nullable AdviewListener listener) {
        super(L);

        // setHasStableIds(true);
        /**
         * Disable focus for sub-views of the AdView to avoid problems with
         * trackpad navigation of the list.
         */
        for (int i = 0; i < advertisement_view.getChildCount(); i++) {
            advertisement_view.getChildAt(i).setFocusable(false);
        }
        advertisement_view.setFocusable(false);
        once = insertOnce;
        adfrequency = setInterval + 1;
        advertise_view = advertisement_view;
        if (listener == null) {
            adviewlistener = new AdviewListener() {
                @Override
                public ViewGroup onGenerateAdview() {
                    return advertisement_view;
                }
            };
        } else
            adviewlistener = listener;
    }

    /**
     * requirement: ADVIEW
     *
     * @param view v
     * @return holder for this ADVIEW
     */
    @Override
    public RecyclerView.ViewHolder getAdViewHolder(View view) {
        return new AdItemHolder(adviewlistener.onGenerateAdview(), VIEW_TYPES.ADVIEW) {
            @Override
            protected void bindNormal(View view) {

            }

            @Override
            protected void bindAd(View view) {

            }
        };
    }




    @Override
    public int totalAdditionalItems() {
        final int base = super.totalAdditionalItems();
        if (once) {
            if (adfrequency > 0) {
                return base + 1;
            } else {
                return base;
            }
        } else {
            final int check_sum = (adfrequency > 0 ? atAdPos(base) : 0) + base;
            Log.d("getItemCountE2", check_sum + "");
            return check_sum;
        }
    }

    /**
     * indicate if the touch position is at the Adview
     *
     * @param pos in raw
     * @return in raw
     */
    @Override
    protected boolean isOnAdView(int pos) {
        final int zero_for_admob_selection = (pos + 1) % adfrequency;
        return zero_for_admob_selection == 0;
    }

    @Override
    protected void notifyAfterRemoveAllData(int data_size_before_remove, int display_size_before_remove) {
        try {

            final int n_start = hasHeaderView() ? 1 : 0;

            final int n_end = hasHeaderView() ? display_size_before_remove - 1 : display_size_before_remove;

            if (detectDispatchLoadMoreDisplay(data_size_before_remove, display_size_before_remove))
                return;

            if (data_size_before_remove == 0) return;

            if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_KEEP_HEADER_AND_LOARMORE) {
                notifyItemRangeRemoved(n_start, n_end);
            } else if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_KEEP_HEADER) {
                notifyItemRangeRemoved(n_start, n_end);
                removeDispatchLoadMoreView();
            } else if (mEmptyViewPolicy == UltimateRecyclerView.EMPTY_CLEAR_ALL) {
                notifyItemRangeRemoved(0, display_size_before_remove);
                removeDispatchLoadMoreView();
            } else {
                notifyItemRangeRemoved(0, display_size_before_remove);
            }

        } catch (Exception e) {
            String o = e.fillInStackTrace().getCause().getMessage().toString();
            Log.d("fillInStackTrace", o + " : ");
        }
    }

    /**
     * data binding related position shifting
     *
     * @param touch_position pos machine loaded position in int
     * @return the final confirmed position for data binding
     */
    @Override
    protected int getItemDataPosFromInternalPos(int touch_position) {
        int shift = 0;
        if (hasHeaderView()) shift--;
        if (adfrequency > 0) {
            if (once) {
                if (touch_position >= adfrequency) shift--;
            } else {
                shift -= atAdPos(touch_position);
            }
        }
        return touch_position + shift;
    }

    /**
     * this is the {getDataArrayPosition} reverse from data position to layout position
     *
     * @param dataPos position on the data list
     * @return offset
     */
    protected int getReverseDataArrayPosition(final int dataPos) {
        int shift = 0;
        if (hasHeaderView()) shift++;
        if (adfrequency > 0) {
            if (once) {
                if (dataPos >= adfrequency) shift++;
            } else {
                shift += atAdPos(dataPos);
            }
        }
        return dataPos + shift;
    }


    /**
     * the API access for adview indication
     *
     * @param pos position to be final
     * @return bool
     */
    public final boolean isPosOnAdView(final int pos) {
        return isOnAdView(pos);
    }

    /**
     * to display the accumulator for the Ad position
     *
     * @param pos raw touch position
     * @return the placement for the ad position
     */
    public final int atAdPos(final int pos) {
        final int take_int = (int) Math.floor((pos + 1) / adfrequency);
        Log.d("atAdPosE2", take_int + "");
        return take_int;
    }

    /**
     * for external shift number adjustment
     *
     * @param pos initial number
     * @return the number
     */
    public final int getFinalShiftPosition(int pos) {
        return getItemDataPosFromInternalPos(pos);
    }
}
