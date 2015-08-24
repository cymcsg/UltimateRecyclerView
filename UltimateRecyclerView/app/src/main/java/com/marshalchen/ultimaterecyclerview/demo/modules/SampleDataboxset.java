package com.marshalchen.ultimaterecyclerview.demo.modules;

import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public static List<String> newListFromGen(int n) {
        final List<String> s = new ArrayList<>();
        genItems(n, s);
        return s;
    }

    public static List<String> newListFromGen() {
        final List<String> s = new ArrayList<>();
        genItems(38, s);
        return s;
    }

    private static void genItems(final int howmany, final List<String> list) {
        for (int i = 0; i < howmany; i++) {
            Random e = new Random();
            list.add(girl_name[e.nextInt(girl_name.length)]);
        }
    }

    public static void insertMore(SimpleAdapter sd, int howmany) {
        for (int i = 0; i < howmany; i++) {
            sd.insert("More " + i, sd.getAdapterItemCount());
        }
    }

    public static int getGirlImageRandom() {
        Random e = new Random();
        return res[e.nextInt(res.length)];
    }

    public static void insertMore(admobdfpadapter sd, int howmany) {
        for (int i = 0; i < howmany; i++) {
            sd.insert("More items " + i);
        }
    }

    public static final Integer[] res = new Integer[]{
            R.drawable.test_back2,
           // R.drawable.test_back1,
            R.drawable.test_back
    };
    public static final String[] girl_name = new String[]{
            "Anna",
            "Sindy",
            "Venus",
            "Pamela",
            "Chantel",
            "Lostus",
            "Sephia",
            "Sophy",
            "YahoLee",
            "Liza",
            "Angel",
            "Cristy",
            "Gobby",
            "Sophia",
            "Nicole",
            "Emily",
            "Tiffany",
            "Susan",
            "Vicki",
            "Eva",
            "Ruby",
            "Toby",
            "Gobia",
            "Victoria",
            "Annus",
            "Sammus",
            "Sindia",
            "Erica",
            "Vivian",
            "Septhanie",
            "Fiona",
            "Leonia",
            "Karon"
    };
}
