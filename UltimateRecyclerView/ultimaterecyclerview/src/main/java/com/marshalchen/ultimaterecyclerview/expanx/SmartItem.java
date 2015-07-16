package com.marshalchen.ultimaterecyclerview.expanx;

import android.content.Context;
import android.support.annotation.StringRes;

import java.util.List;
import java.util.UUID;

/**
 * Created by hesk on 8/7/15.
 * JJHESK MIT LICNESE
 * based on library https://github.com/jjhesk/BringItBackAdvanceSlidingMenu
 */
public class SmartItem extends ExpandableItemData {
    /**
     * construct the basic view type
     *
     * @param type     LinearExpandableURVAdapter.ExpandableViewTypes.VIEW_TYPES
     * @param text     the normal string
     * @param path     the path of the text or link
     * @param depth    the deep
     * @param children the children list
     */
    public SmartItem(int type, String text, String path, int depth, List<SmartItem> children) {
        super(type, text, path, UUID.randomUUID().toString(), depth, children);
    }

    public static SmartItem parent(final String title, final String path, final List<SmartItem> carrying_list) {
        return new SmartItem(LinearExpanxURVAdapter.ExpandableViewTypes.ITEM_TYPE_PARENT, title, path, 0, carrying_list);
    }

    public static SmartItem parent(final Context ctx, final @StringRes int title, final String path, final List<SmartItem> carrying_list) {
        return new SmartItem(LinearExpanxURVAdapter.ExpandableViewTypes.ITEM_TYPE_PARENT,
                ctx.getResources().getString(title), path, 0, carrying_list);
    }

    public static SmartItem child(final String title, final String path) {
        return new SmartItem(LinearExpanxURVAdapter.ExpandableViewTypes.ITEM_TYPE_CHILD, title, path, 1, null);
    }

    public static SmartItem child(final Context ctx, final @StringRes int title, final String path) {
        return new SmartItem(LinearExpanxURVAdapter.ExpandableViewTypes.ITEM_TYPE_CHILD, ctx.getResources().getString(title), path, 1, null);
    }


}
