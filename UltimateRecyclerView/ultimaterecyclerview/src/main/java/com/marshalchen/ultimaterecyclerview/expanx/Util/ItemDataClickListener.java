package com.marshalchen.ultimaterecyclerview.expanx.Util;


import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;

/**
 * Author Zheng Haibo
 * PersonalWebsite http://www.mobctrl.net
 * Description
 */
public interface ItemDataClickListener<T extends ExpandableItemData> {

    void onExpandChildren(T itemData);

    void onHideChildren(T itemData);

}
