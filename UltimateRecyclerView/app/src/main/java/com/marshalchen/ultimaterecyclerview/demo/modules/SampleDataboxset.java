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

    public static void insertMore(SimpleAdapter sd, int howmany){

        for (int i = 0; i < howmany; i++) {
            sd.insert("More " + i, sd.getAdapterItemCount());

        }

    }
}
