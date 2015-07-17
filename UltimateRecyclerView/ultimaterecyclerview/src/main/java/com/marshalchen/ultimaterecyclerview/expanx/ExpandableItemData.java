package com.marshalchen.ultimaterecyclerview.expanx;

import java.util.List;

/**
 * Author Zheng Haibo
 * PersonalWebsite http://www.mobctrl.net
 * Description the bundle of the tree view style
 * enhanced by Hesk 2015
 * used with LinearExpandableURVAdapter
 */
public class ExpandableItemData<T extends ExpandableItemData> implements Comparable<T> {
    private String uuid;
    private int type;// 显示类型
    private String text;
    private String path;// 路径
    private int treeDepth = 0;// 路径的深度
    private List<T> children;
    private boolean expand;// 是否展开

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    public ExpandableItemData(int type, String text, String path, String uuid,
                              int treeDepth, List<T> children) {
        super();
        this.type = type;
        this.text = text;
        this.uuid = uuid;
        this.path = path;
        this.treeDepth = treeDepth;
        this.children = children;
    }

    public ExpandableItemData() {

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTreeDepth() {
        return treeDepth;
    }

    public void setTreeDepth(int treeDepth) {
        this.treeDepth = treeDepth;
    }

    @Override
    public int compareTo(ExpandableItemData another) {
        return this.getText().compareTo(another.getText());
    }

}