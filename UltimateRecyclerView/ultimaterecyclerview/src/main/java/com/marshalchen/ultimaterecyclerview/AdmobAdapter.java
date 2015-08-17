package com.marshalchen.ultimaterecyclerview;

import android.util.Log;
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
public abstract class AdmobAdapter<Adv extends ViewGroup, T, V extends UltimateRecyclerviewViewHolder> extends UltimateViewAdapter<UltimateRecyclerviewViewHolder> {
    public interface AdviewListener<Adv extends ViewGroup> {
        Adv onGenerateAdview();
    }

    protected class VIEW_TYPES extends UltimateViewAdapter.VIEW_TYPES {
        public static final int ADVIEW = 4;
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
        // setHasStableIds(true);

        /**
         * Disable focus for sub-views of the AdView to avoid problems with
         * trackpad navigation of the list.
         */
        for (int i = 0; i < advertise_view.getChildCount(); i++) {
            advertise_view.getChildAt(i).setFocusable(false);
        }
        advertise_view.setFocusable(false);

        once = insertOnce;
        adfrequency = setInterval + 1;
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
    // @Deprecated
    protected abstract V newViewHolder(View mview);

    /*
        @Override
        public V getViewHolder(View view) { return  }
    */
    public static class AdHolder extends UltimateRecyclerviewViewHolder {
        public AdHolder(AdviewListener adviewlistener) {
            super(adviewlistener.onGenerateAdview());
        }
    }

    /**
     * only on the first creation
     *
     * @param parent parent resource
     * @return the UtimateView
     */
    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getNormalLayoutResId(), parent, false);
        return newViewHolder(v);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //   if (parent == null)
        //       Log.d("getItemCountE2", "parent is null on viewType: " + viewType);
        if (viewType == VIEW_TYPES.ADVIEW) {
            UltimateRecyclerviewViewHolder adview_holder;
            if (adviewlistener != null) {
                try {
                    adview_holder = new AdHolder(adviewlistener);
                } catch (NullPointerException e) {
                    adview_holder = new UltimateRecyclerviewViewHolder(advertise_view);
                } catch (Exception e) {
                    adview_holder = new UltimateRecyclerviewViewHolder(advertise_view);
                }
            } else {
                adview_holder = new UltimateRecyclerviewViewHolder(advertise_view);
            }
            return adview_holder;
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = super.getItemViewType(position);
        if (type == VIEW_TYPES.NORMAL) {
            if (!once) {
                if (position > 0 && isPosOnAdView(position)) {
                    return VIEW_TYPES.ADVIEW;
                } else return type;
            } else {
                if (isPosOnAdView(position) && adfrequency == position + 1) {
                    return VIEW_TYPES.ADVIEW;
                } else return type;
            }
        } else {
            return type;
        }
    }

    /**
     * Returns the number of items in the adapter bound to the parent RecyclerView.
     *
     * @return The number of items in the bound adapter
     */
    @Override
    public int getAdapterItemCount() {
        return list.size();
    }

    /**
     * get the display item count
     *
     * @return the final items for display
     */
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
            final int check_sum = (adfrequency > 0 ? atAdPos(base) : 0) + base;
            Log.d("getItemCountE2", check_sum + "");
            return check_sum;
        }
    }

    @Override
    /**
     * Insert a item to the list of the adapter
     *
     * @param list the data list
     * @param object the object
     * @param first_insert_data_pos the object position at the end of the list
     * @param <T> the generic type
     */
    public <T> void insert(final List<T> list, final T object, final int first_insert_data_pos) {
        try {
            list.add(first_insert_data_pos, object);
            final int offset = getReverseDataArrayPosition(first_insert_data_pos);
            if (isPosOnAdView(offset) && first_insert_data_pos > 0) {
                notifyItemRangeChanged(offset, offset + 1);
            } else {
                notifyItemInserted(offset);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("admobErrorMr3", e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            Log.d("admobErrorMr3", e.getMessage());
        }
    }

    /**
     * to insert data with a new list
     *
     * @param original_list    the original list
     * @param new_list         the list that items are in it
     * @param first_insert_pos the first item
     * @param <T>              the list type holder
     */
    public <T> void insert(final List<T> original_list, final List<T> new_list, final int first_insert_pos) {
        try {
            original_list.addAll(first_insert_pos, new_list);
            final int view_pos_1 = getReverseDataArrayPosition(first_insert_pos);
            final int view_pos_2 = getReverseDataArrayPosition(first_insert_pos + new_list.size());
            notifyItemRangeChanged(view_pos_1, view_pos_2);
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("admobErrorMr3", e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            Log.d("admobErrorMr3", e.getMessage());
        }
    }

    /**
     * default insert that will append the object at the end
     *
     * @param object data object
     */
    public void insert(final T object) {
        insert(list, object, list.size());
    }

    public void insert(final List<T> newlist) {
        insert(list, newlist, list.size());
    }

    public void removeAll() {
        list.clear();
        notifyDataSetChanged();
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
        try {
            if (list.size() > 0 && position < list.size()) {
                list.remove(position);
                final int offset = getReverseDataArrayPosition(position);
                notifyItemRemoved(offset);
                if (offset > 1 && isPosOnAdView(offset) && position > 0) {
                    notifyItemRemoved(offset - 1);
                }
                Log.d("normaladmob", "offset final: " + offset);
            } else {
                throw new ArrayIndexOutOfBoundsException("no data or the remove position is not exist p:" + position + ", list size:" + list.size());
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("admobError r1", e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            Log.d("admobError r2", e.getMessage());
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
     * this is the {getDataArrayPosition} reverse from data position to layout position
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
        final int zero_for_admob_selection = (pos + 1) % adfrequency;
        return zero_for_admob_selection == 0;
    }

    /**
     * to display the accumulator for the Ad position
     *
     * @param pos raw touch position
     * @return the placement for the ad position
     */
    public int atAdPos(final int pos) {
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
    public int getFinalShiftPosition(int pos) {
        return getDataArrayPosition(pos);
    }
}
