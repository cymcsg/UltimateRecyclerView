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
        stringList.add("aaa");
        stringList.add("222");
        stringList.add("33");
        stringList.add("44");
        stringList.add("55");
        stringList.add("66");
        stringList.add("11771");
        return stringList;
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
