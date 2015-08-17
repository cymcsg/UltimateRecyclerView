package com.marshalchen.ultimaterecyclerview.demo.modules;

import com.marshalchen.ultimaterecyclerview.demo.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 7/1/2015.
 */
public class SampleDataboxset {

    public static List<String> newList() {
        final List<String> stringList = new ArrayList<>();
        stringList.add("111");
        stringList.add("TYAT");
        stringList.add("BMW");
        stringList.add("3M");
        stringList.add("Apple");
        stringList.add("Organe");
        stringList.add("Nike");
        stringList.add("Addos");
        stringList.add("76 RE");
        return stringList;
    }

    public static List<String> newList(int longh) {
        final List<String> j = newList();
        genItems(longh, j);
        return j;
    }

    public static List<String> newListFromGen() {
        final List<String> s = new ArrayList<>();
        genItems(38, s);
        return s;
    }

    private static void genItems(final int howmany, final List<String> list) {
        for (int i = 0; i < howmany; i++) {
            list.add("foinefieifn");
        }
    }

    public static void insertMore(SimpleAdapter sd, int howmany) {
        for (int i = 0; i < howmany; i++) {
            sd.insert("More " + i, sd.getAdapterItemCount());
        }
    }

    public static void insertMore(admobdfpadapter sd, int howmany) {
        for (int i = 0; i < howmany; i++) {
            sd.insert("More items " + i);
        }
    }
}
