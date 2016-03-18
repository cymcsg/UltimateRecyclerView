package com.marshalchen.ultimaterecyclerview.demo.modules;

import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.admobdemo.ZeroStickyAdvertistmentAdapter;
import com.marshalchen.ultimaterecyclerview.demo.basicdemo.sectionZeroAdapter;

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

    public static void genItems(final int howmany, final List<String> list) {
        for (int i = 0; i < howmany; i++) {
            Random e = new Random();
            list.add("No." + i + " " + girl_name[e.nextInt(girl_name.length)]);
        }
    }

    public static void insertMore(sectionZeroAdapter sd, int howmany) {
        for (int i = 0; i < howmany; i++) {
            sd.insertLast("More ** " + i);
        }
    }

    public static void insertMoreWhole(sectionZeroAdapter sd, int howmany) {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < howmany; i++) {
            items.add("More ** " + i);
        }
        sd.insert(items);
    }


    public static List<JRitem> genJRList(int counts) {
        List<JRitem> items = new ArrayList<>();
        for (int i = 0; i < counts; i++) {
            items.add(genJRSingle());
        }
        return items;
    }

    public static JRitem genJRSingle() {
        Random e = new Random();
        JRitem bodu = new JRitem(SampleDataboxset.getGirlImageRandom(), girl_name[e.nextInt(girl_name.length)]);
        return bodu;
    }

    public static int getGirlImageRandom() {
        Random e = new Random();
        return res[e.nextInt(res.length)];
    }

    public static void insertMore(ZeroStickyAdvertistmentAdapter sd, int howmany) {
        for (int i = 0; i < howmany; i++) {
            sd.insertFirst("More items " + i);
        }
    }

    public static final Integer[] res = new Integer[]{
            R.drawable.jr13,
            R.drawable.jr16,
            R.drawable.jr14,
            R.drawable.jr15,
            R.drawable.jr17,
            R.drawable.jr1,
            R.drawable.jr2,
            R.drawable.jr3,
            R.drawable.jr4,
            R.drawable.jr5
    };

    public static final Integer[] res_scn = new Integer[]{
            R.drawable.scn1,
            R.drawable.scn2
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
