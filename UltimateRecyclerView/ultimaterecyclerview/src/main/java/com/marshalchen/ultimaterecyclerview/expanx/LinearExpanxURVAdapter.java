package com.marshalchen.ultimaterecyclerview.expanx;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.expanx.Util.BaseViewHolder;
import com.marshalchen.ultimaterecyclerview.expanx.Util.ChildVH;
import com.marshalchen.ultimaterecyclerview.expanx.Util.ItemDataClickListener;
import com.marshalchen.ultimaterecyclerview.expanx.Util.OnScrollToListener;
import com.marshalchen.ultimaterecyclerview.expanx.Util.ParentVH;
import com.marshalchen.ultimaterecyclerview.expanx.Util.child;
import com.marshalchen.ultimaterecyclerview.expanx.Util.parent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 16/7/15.
 */
public abstract class LinearExpanxURVAdapter<T extends ExpandableItemData, G extends parent<T>, H extends child<T>> extends UltimateViewAdapter {

    public class ExpandableViewTypes extends VIEW_TYPES {

        public static final int ITEM_TYPE_PARENT = 1026;
        public static final int ITEM_TYPE_CHILD = 1135;

        protected ExpandableViewTypes() {
            super();
        }
    }

    private Context mContext;
    private List<T> mDataSet;
    private List<OnScrollToListener> monScrollToListenerList = new ArrayList<>();
    private OnScrollToListener onScrollToListener;
    public static final String TAG = "expAdapter";
    protected int expandableBehavior = 0;
    public static final int EXPANDABLE_ITEMS = 1;
    public static final int EXPANDABLE_SYSTEM = 0;
    private boolean customObject;

    protected Context getContext() {
        return mContext;
    }


    protected List<T> getSet() {
        return mDataSet;
    }

    public void addOnScrollToListener(OnScrollToListener onScrollToListener) {
        this.monScrollToListenerList.add(onScrollToListener);
    }

    @Deprecated
    public void setOnScrollToListener(OnScrollToListener onScrollToListener) {
        this.onScrollToListener = onScrollToListener;
    }

    public LinearExpanxURVAdapter(Context context, final int clickhandler, final boolean customholder) {
        this(context, clickhandler);
        this.customObject = customholder;
    }

    public LinearExpanxURVAdapter(Context context, final int clickhandler) {
        this(context);
        expandableBehavior = clickhandler;
    }


    public LinearExpanxURVAdapter(Context context) {
        mContext = context;
        mDataSet = new ArrayList<>();
        customObject = false;
    }


    /**
     * please do work on this id the custom object is true
     *
     * @param parentview the inflated view
     * @return the actual parent holder
     */
    protected abstract G iniCustomParentHolder(View parentview);

    /**
     * please do work on this if the custom object is true
     *
     * @param childview the inflated view
     * @return the actual child holder
     */
    protected abstract H iniCustomChildHolder(View childview);

    private View initiateview(ViewGroup parent, final @LayoutRes int layout) {
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }

    protected abstract int getLayoutResParent();

    protected abstract int getLayoutResChild();

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ExpandableViewTypes.ITEM_TYPE_PARENT:
                return iniCustomParentHolder(initiateview(parent, getLayoutResParent()));

            case ExpandableViewTypes.ITEM_TYPE_CHILD:
                return iniCustomChildHolder(initiateview(parent, getLayoutResChild()));

            default:
                return null;
        }
    }

    private ItemDataClickListener getBehavior() {
        switch (expandableBehavior) {
            case EXPANDABLE_ITEMS:
                return imageSetLoadItems;
            default:
                return imageClickListener;
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ExpandableViewTypes.ITEM_TYPE_PARENT:
                ParentVH imageViewHolder = (ParentVH) holder;
                imageViewHolder.bindView(mDataSet.get(position), position, getBehavior());
                break;
            case ExpandableViewTypes.ITEM_TYPE_CHILD:
                ChildVH textViewHolder = (ChildVH) holder;
                textViewHolder.bindView(mDataSet.get(position), position);
                break;
            default:
                break;
        }
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new BaseViewHolder(viewGroup);
    }

    private void triggerBoardCastEventScrollTo(final int n) {
        for (int i = 0; i < monScrollToListenerList.size(); i++) {
            OnScrollToListener m = monScrollToListenerList.get(i);
            m.scrollTo(n);
        }
    }

    private void triggerSingleEventScrollTo(final int n) {
        if (onScrollToListener != null) {
            onScrollToListener.scrollTo(n);
        }
    }

    protected abstract List<T> getChildrenByPath(String path, int depth, final int position);


    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    /**
     * this is the only number coming from the data size
     *
     * @return the integer from the list count
     */
    @Override
    public int getAdapterItemCount() {
        return getItemCount();
    }

    @Override
    public long generateHeaderId(int i) {
        return 0;
    }

    private int getChildrenCount(T item) {
        List<Object> list = new ArrayList<>();
        printChild(item, list);
        return list.size();
    }

    private void printChild(Object item, List<Object> list) {
        list.add(item);
        if (item instanceof ExpandableItemData) {
            ExpandableItemData it = (ExpandableItemData) item;
            if (it.getChildren() != null) {
                for (int i = 0; i < it.getChildren().size(); i++) {
                    printChild(it.getChildren().get(i), list);
                }
            }
        }

    }

    /**
     * 从position开始删除，删除
     *
     * @param position  the count position
     * @param itemCount 删除的数目
     */
    protected void removeAll(int position, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            mDataSet.remove(position);
        }
        notifyItemRangeRemoved(position, itemCount);
    }

    /**
     * the current position from the uuid
     *
     * @param uuid the string in uuid
     * @return the int as the position
     */
    protected int getCurrentPosition(String uuid) {
        for (int i = 0; i < mDataSet.size(); i++) {
            if (uuid.equalsIgnoreCase(mDataSet.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position).getType();
    }

    public void add(T text, int position) {
        mDataSet.add(position, text);
        notifyItemInserted(position);
    }

    public void addAll(List<T> list, int position) {
        mDataSet.addAll(position, list);
        notifyItemRangeInserted(position, list.size());
    }

    public void delete(int pos) {
        if (pos >= 0 && pos < mDataSet.size()) {
            if (mDataSet.get(pos).getType() == ExpandableViewTypes.ITEM_TYPE_PARENT
                    && mDataSet.get(pos).isExpand()) {
                for (int i = 0; i < mDataSet.get(pos).getChildren().size() + 1; i++) {
                    mDataSet.remove(pos);
                }
                notifyItemRangeRemoved(pos, mDataSet.get(pos).getChildren()
                        .size() + 1);
            } else {
                mDataSet.remove(pos);
                notifyItemRemoved(pos);
            }
        }
    }


    /**
     * the item click behavior and list item handler cases
     */

    private ItemDataClickListener imageSetLoadItems = new ItemDataClickListener<T>() {
        @Override
        public void onExpandChildren(T itemData) {
            int position = getCurrentPosition(itemData.getUuid());
            List<T> children = itemData.getChildren();
            if (children == null) {
                return;
            }
            addAll(children, position + 1);
            triggerSingleEventScrollTo(position + 1);
            triggerBoardCastEventScrollTo(position + 1);
        }

        @Override
        public void onHideChildren(T itemData) {
            int position = getCurrentPosition(itemData.getUuid());
            List<T> children = itemData.getChildren();
            if (children == null) {
                return;
            }
            removeAll(position + 1, getChildrenCount(itemData) - 1);
            triggerSingleEventScrollTo(position);
            triggerBoardCastEventScrollTo(position);
        }
    };
    private ItemDataClickListener imageClickListener = new ItemDataClickListener<T>() {

        @Override
        public void onExpandChildren(T itemData) {
            int position = getCurrentPosition(itemData.getUuid());
            List<T> children = getChildrenByPath(itemData.getPath(), itemData.getTreeDepth(), position);
            if (children == null) {
                return;
            }

            addAll(children, position + 1);
            itemData.setChildren(children);
            triggerSingleEventScrollTo(position + 1);
            triggerBoardCastEventScrollTo(position + 1);
        }

        @Override
        public void onHideChildren(T itemData) {
            int position = getCurrentPosition(itemData.getUuid());
            List<T> children = itemData.getChildren();
            if (children == null) {
                return;
            }
            removeAll(position + 1, getChildrenCount(itemData) - 1);
            triggerSingleEventScrollTo(position);
            triggerBoardCastEventScrollTo(position);
            itemData.setChildren(null);
        }
    };
}
