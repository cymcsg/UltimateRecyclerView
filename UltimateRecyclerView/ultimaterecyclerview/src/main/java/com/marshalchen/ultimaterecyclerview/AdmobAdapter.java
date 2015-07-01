package com.marshalchen.ultimaterecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Enhanced Google Admob implementation
 * Created by hesk on 20/5/15.
 * Visit: https://github.com/jjhesk
 * JJHESK HKM. MIT LICENSE
 * {@link {https://github.com/jjhesk/MaterialTabsAdavanced/blob/master/LICENSE.md}}
 */
public abstract class AdmobAdapter<Adv extends ViewGroup, T, V extends UltimateRecyclerviewViewHolder> extends UltimateViewAdapter {
    public interface AdviewListener<Adv extends ViewGroup> {
        Adv onGenerateAdview();
    }

    protected class VIEW_TYPES extends UltimateViewAdapter.VIEW_TYPES {
        public static final int ADVIEW = 4;
    }

    protected Adv advertise_view = null;
    /**
     * There is an AD between the amount of the data items. adfrequency is known as the amount.
     */
    protected int adfrequency;
    /**
     * The ad is only insert once and no more.
     */
    protected boolean once;
    protected List<T> list;
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
     * @param adview      The AD mob view
     * @param insertOnce  only insert once and hold into the adapter object, that means the ad will only shown once in the list view
     * @param setInterval the order of item to show the ad. if @insertOnce=false the ad will show on interval bases.
     * @param L           the data source
     * @param listener    The listener for the admob cell to reveal when the cell is close to appear on the screen
     */
    public AdmobAdapter(Adv adview, boolean insertOnce, int setInterval, List<T> L, AdviewListener listener) {
        advertise_view = adview;
        /**
         * Disable focus for sub-views of the AdView to avoid problems with
         * trackpad navigation of the list.
         */
        for (int i = 0; i < advertise_view.getChildCount(); i++) {
            advertise_view.getChildAt(i).setFocusable(false);
        }
        advertise_view.setFocusable(false);
        once = insertOnce;
        adfrequency = setInterval;
        /*  registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                notifyDataSetChanged();
            }
        });*/
        list = L;
    }

    /**
     * the layout id for the normal data
     *
     * @return the ID
     */
    protected abstract int getNormalLayoutResId();

    /**
     * create a new view holder for data binding
     *
     * @param mview the view layout with resource initialized
     * @return the view type
     */
    protected abstract V newViewHolder(View mview);

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getNormalLayoutResId(), parent, false);
        return newViewHolder(v);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPES.ADVIEW) {
            RecyclerView.ViewHolder adview_holder;
            if (adviewlistener != null) {
                try {
                    adview_holder = new UltimateRecyclerviewViewHolder(adviewlistener.onGenerateAdview());
                } catch (NullPointerException e) {
                    adview_holder = new UltimateRecyclerviewViewHolder(advertise_view);
                } catch (Exception e) {
                    adview_holder = new UltimateRecyclerviewViewHolder(advertise_view);
                }

                return adview_holder;
            } else {
                adview_holder = new UltimateRecyclerviewViewHolder(advertise_view);
                return adview_holder;
            }
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = super.getItemViewType(position);
        if (type == VIEW_TYPES.NORMAL && position % adfrequency == 0 && !once && position > 0) {
            return VIEW_TYPES.ADVIEW;
        } else if (type == VIEW_TYPES.NORMAL && position == adfrequency && once) {
            return VIEW_TYPES.ADVIEW;
        } else {
            return type;
        }
    }

    @Override
    public int getItemCount() {
        final int base = super.getItemCount();
        if (once) {
            if (adfrequency > 0) {
                return base + 1;
            } else {
                return base;
            }
        } else {
            int check_sum = (adfrequency > 0 ? (int) Math.floor(getAdapterItemCount() / adfrequency) : 0) + base;
            return check_sum;
        }
    }

    @Override
    /**
     * Todo: need to resolve this problem when it crash. and we need more testing for this now.
     * Insert a item to the list of the adapter
     *
     * @param list
     * @param object
     * @param position
     * @param <T>
     */
    public <T> void insert(List<T> list, T object, int position) {
        list.add(position, object);
        final int offset = getReverseDataArrayPosition(position);
        notifyItemInserted(offset);
        if (isPosOnAdView(offset) && position > 0) {
            notifyItemInserted(offset + 1);
        }
    }

    @Override
    /**
     * Todo: need to resolve this problem when it crash
     * Remove a item of  the list of the adapter
     *
     * @param list the data source
     * @param position with the position on the list
     */
    public void remove(List<?> list, int position) {
        if (list.size() > 0) {
            list.remove(position);
            final int offset = getReverseDataArrayPosition(position);
            notifyItemRemoved(offset);
            if (isPosOnAdView(offset) && position > 0) {
                notifyItemRemoved(offset - 1);
            }
        }
    }

    /**
     * this is the mask to calculate whether the position of the item should be proceeded with data binding
     *
     * @param pos  machine loaded position in int
     * @param list the source data list
     * @return the decision to tell whether the data binding should be taken place
     */
    protected boolean onActionToBindData(final int pos, final List<T> list) {
        int getType = getItemViewType(pos);
        if (pos < getItemCount() && getType == VIEW_TYPES.NORMAL)
            if (customHeaderView != null) {
                return pos <= list.size() && pos > 0;
            } else {
                return pos < list.size();
            }
        else {
            return false;
        }
    }


    protected T getItem(int pos) {
        return list.get(getDataArrayPosition(pos));
    }

    /**
     * data binding related position shifting
     *
     * @param pos machine loaded position in int
     * @return the final confirmed position for data binding
     */
    protected int getDataArrayPosition(final int pos) {
        int shift = 0;
        if (customHeaderView != null) shift--;
        if (adfrequency > 0) {
            if (once) {
                if (pos >= adfrequency) shift--;
            } else {
                shift -= atAdPos(pos);
            }
        }
        return pos + shift;
    }

    /**
     * this is the {getDataArrayPosition} reverse
     *
     * @param dataPos position on the data list
     * @return offset
     */
    protected int getReverseDataArrayPosition(final int dataPos) {
        int shift = 0;
        if (customHeaderView != null) shift++;
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
     * indicate if the touch position is at the Adview
     *
     * @param pos in raw
     * @return yes or no
     */
    public boolean isPosOnAdView(final int pos) {
        final int zero_for_admob_selection = pos % adfrequency;
        return zero_for_admob_selection == 0;
    }

    /**
     * to display the accumulator for the Ad position
     *
     * @param pos raw touch position
     * @return the placement for the ad position
     */
    public int atAdPos(final int pos) {
        return (int) Math.floor(pos / adfrequency);
    }

    /**
     * for external shift number adjustment
     *
     * @param pos initial number
     * @return the number
     */
    public int getFinalShiftPosition(int pos) {
        return getDataArrayPosition(pos);
    }
}
