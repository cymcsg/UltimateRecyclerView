package com.marshalchen.ultimaterecyclerview.expanx.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;
import com.marshalchen.ultimaterecyclerview.expanx.LinearExpanxURVAdapter;
import com.marshalchen.ultimaterecyclerview.expanx.SmartItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by hesk on 16/7/15.
 */
public class DataUtil {
    public final static String TAG = "TreeList";

    public static List<SmartItem> getSmallList(final String[] list) {
        final List<SmartItem> items = new ArrayList<>();
        for (int i = 0; i < list.length - 2; i = i + 2) {
            items.add(SmartItem.child(list[i], list[i + 1]));
        }
        return items;
    }

    /**
     *
     *
     * @param path      path
     * @param treeDepth treedepth
     * @return the list object
     */
    public static List<ExpandableItemData> getChildrenByPath(String path, int treeDepth) {
        treeDepth++;
        try {
            List<ExpandableItemData> list = new ArrayList<>();
            File file = new File(path);
            File[] children = file.listFiles();
            List<ExpandableItemData> fileList = new ArrayList<>();
            for (File child : children) {
                if (child.isDirectory()) {
                    list.add(new ExpandableItemData(LinearExpanxURVAdapter.ExpandableViewTypes.ITEM_TYPE_PARENT, child
                            .getName(), child.getAbsolutePath(), UUID
                            .randomUUID().toString(), treeDepth, null));
                } else {
                    fileList.add(new ExpandableItemData(LinearExpanxURVAdapter.ExpandableViewTypes.ITEM_TYPE_CHILD, child
                            .getName(), child.getAbsolutePath(), UUID
                            .randomUUID().toString(), treeDepth, null));
                }
            }
            Collections.sort(list);
            Collections.sort(fileList);
            list.addAll(fileList);
            return list;
        } catch (Exception e) {
            Log.d(TAG, "get child error:" + e.getMessage());
        }
        return null;
    }

    public static void openFileInSystem(String path, Context context) {
        try {
            MimeTypeMap myMime = MimeTypeMap.getSingleton();
            Intent newIntent = new Intent(Intent.ACTION_VIEW);
            String mimeType = myMime.getMimeTypeFromExtension(fileExt(path).substring(1));
            newIntent.setDataAndType(Uri.fromFile(new File(path)), mimeType);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newIntent);
        } catch (Exception e) {
            Toast.makeText(context, "No handler for this type of file.", Toast.LENGTH_LONG).show();
        }
    }


    @SuppressLint("DefaultLocale")
    private static String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf("."));
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();
        }
    }
}
