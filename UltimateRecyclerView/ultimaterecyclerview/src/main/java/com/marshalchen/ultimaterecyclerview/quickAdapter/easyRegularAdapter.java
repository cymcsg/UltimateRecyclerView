package com.marshalchen.ultimaterecyclerview.quickAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.dragsortadapter.DragSortAdapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * This is the normal adapter for implementation on the regular basis
 * Created by hesk on 4/8/15.
 * integrated with efficient adapter
 */
public abstract class easyRegularAdapter<T, BINDHOLDER extends UltimateRecyclerviewViewHolder> extends UltimateViewAdapter {
    protected List<T> source;


    /**
     * dynamic object to start
     *
     * @param list the list source
     */
    public easyRegularAdapter(List<T> list) {
        source = list;
    }


    /**
     * Constructor
     *
     * @param objects The objects to represent in the RecyclerView.
     */
    public easyRegularAdapter(T... objects) {
        this(new ArrayList<T>(Arrays.asList(objects)));
    }


    /**
     * the layout id for the normal data
     *
     * @return the ID
     */
    protected abstract int getNormalLayoutResId();

    /**
     * this is the Normal View Holder initiation
     *
     * @param view view
     * @return holder
     */
    protected abstract BINDHOLDER newViewHolder(View view);

    @Override
    public BINDHOLDER newFooterHolder(View view) {
        return null;
    }

    @Override
    public BINDHOLDER newHeaderHolder(View view) {
        return null;
    }

    /**
     * this MUST BE USING THE NORMAL VIEW
     *
     * @param parent view group parent
     * @return THE HOLDER
     */
    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getNormalLayoutResId(), parent, false);
        return newViewHolder(v);
    }

    @Override
    public int getAdapterItemCount() {
        return source.size();
    }

    protected T getItem(final int pos) {
        synchronized (mLock) {
            return source.get(pos);
        }
    }

    /**
     * Determine if the object provide is in this adapter
     *
     * @param object the data object
     * @return true if the object is in this adapter
     */
    public boolean hasItem(T object) {
        synchronized (mLock) {
            return source.contains(object);
        }
    }

    /**
     * Returns whether this {@code List} contains no elements.
     *
     * @return {@code true} if this {@code List} has no elements, {@code false}
     * otherwise.
     * @see #source
     */
    public boolean isEmpty() {
        return source.size() == 0;
    }


    /**
     * @return a copy of the {@code List} of elements.
     */
    public List<T> getObjects() {
        synchronized (mLock) {
            return new ArrayList<T>(source);
        }
    }


    @Override
    public long generateHeaderId(int i) {
        //    if (position < stringList.size())
        // return stringList.get(position);
        return -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //    if (position >= getAdapterItemCount()) return;
        if (getItemViewType(position) == VIEW_TYPES.ADVIEW) {
            onBindAdViewHolder(holder, position);
        } else if (getItemViewType(position) == VIEW_TYPES.CUSTOMVIEW) {
            onBindCustomViewHolder(holder, position);
        } else if (getItemViewType(position) == VIEW_TYPES.HEADER) {
            onBindHeaderViewHolder(holder, position);
        } else if (getItemViewType(position) == VIEW_TYPES.FOOTER) {
            onBindFooterViewHolder(holder, position);
        } else if (getItemViewType(position) == VIEW_TYPES.NORMAL) {
            // if (position >= getAdapterItemCount()) return;
            T object;
            synchronized (mLock) {
                object = source.get(getItemDataPosFromInternalPos(position));
            }
            withBindHolder((BINDHOLDER) holder, object, position);
        }
    }


    protected int getItemDataPosFromInternalPos(final int touch_position) {
        int shift = 0;
        if (hasHeaderView()) shift--;
        int prefinal = touch_position + shift;
        if (prefinal >= getAdapterItemCount()) {
            return 0;
        } else if (prefinal < 0) {
            return 0;
        }
        return prefinal;
    }


    /**
     * binding normal view holder
     *
     * @param holder   holder class
     * @param data     data
     * @param position position
     */
    protected abstract void withBindHolder(final BINDHOLDER holder, final T data, final int position);

    /**
     * this is the implementation from sticky viewholder interface
     *
     * @param viewHolder ViewHolder
     * @param pos        position
     */
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {

    }

    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, final int pos) {

    }

    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, final int pos) {

    }

    protected void onBindAdViewHolder(RecyclerView.ViewHolder holder, final int pos) {

    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new UltimateRecyclerviewViewHolder(parent);
    }


    public void insert(List<T> new_data) {
        insertInternal(new_data, source);
    }

    public void removeAll() {
        clearInternal(source);
    }

    public void insertFirst(T item) {
        insertFirstInternal(source, item);
    }

    public void insertLast(T item) {
        insertLastInternal(source, item);
    }


    public final void removeAt(int pos) {
        removeInternal(source, pos);
    }

    public void removeLast() {
        removeLastInternal(source);
    }

    public void removeFirst() {
        removeFirstInternal(source);
    }

    public final void swapPositions(int from, int to) {
        swapPositions(source, from, to);
    }

    public void setStableId(boolean b) {
        if (!hasObservers()) {
            setHasStableIds(b);
        }
    }


    /**
     * Adds the specified Collection at the end of the array.
     *
     * @param collection The Collection to add at the end of the array.
     */
/*    public void addAll(Collection<? extends T> collection) {
        int positionOfInsert;
        synchronized (mLock) {
         //   positionOfInsert = mObjects.size();
          //  mObjects.addAll(collection);
        }
        notifyItemInserted(positionOfInsert);
    }*/

    /**
     * Get the view holder to instantiate for the object for this position
     *
     * @param viewType viewType return by getItemViewType()
     * @return the class of the view holder to instantiate
     */
    //protected abstract Class<? extends BINDHOLDER> getViewHolderClass(int viewType);

    /**
     * Generate a view holder for this view for this viewType
     */
  /*  private BINDHOLDER generateViewHolder(View v, int viewType) {
        Class<? extends BINDHOLDER> viewHolderClass = getViewHolderClass(viewType);
        if (viewHolderClass == null) {
            throw new NullPointerException(
                    "You must supply a view holder class for the element for view type "
                            + viewType);
        }
        Constructor<?> constructorWithView = getConstructorWithView(viewHolderClass);
        try {
            Object viewHolder = constructorWithView.newInstance(v);
            return (BINDHOLDER) viewHolder;
        } catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Impossible to instantiate "
                            + viewHolderClass.getSimpleName(), e);
        } catch (InstantiationException e) {
            throw new RuntimeException(
                    "Impossible to instantiate "
                            + viewHolderClass.getSimpleName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                    "Impossible to instantiate "
                            + viewHolderClass.getSimpleName(), e);
        }
    }*/

    /**
     * Get the constructor with a view for this class
     */
    private Constructor<?> getConstructorWithView(Class<? extends BINDHOLDER> viewHolderClass) {
        Constructor<?>[] constructors = viewHolderClass.getDeclaredConstructors();
        if (constructors != null) {
            for (Constructor<?> constructor : constructors) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                if (parameterTypes != null
                        && parameterTypes.length == 1
                        && parameterTypes[0].isAssignableFrom(View.class)) {
                    return constructor;
                }
            }
        }
        throw new RuntimeException(
                "Impossible to found a constructor with a view for "
                        + viewHolderClass.getSimpleName());
    }

/*

    @Override
    public void onViewRecycled(BINDHOLDER holder) {
        super.onViewRecycled(holder);
        holder.onViewRecycled();
    }

    @Override
    public void onViewAttachedToWindow(BINDHOLDER holder) {
        super.onViewAttachedToWindow(holder);
        holder.onViewAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(BINDHOLDER holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onViewDetachedFromWindow();
    }
*/


}
