package com.marshalchen.ultimaterecyclerview.demo;


import android.app.Fragment;

import java.lang.reflect.Field;

/**
 * Created by hesk on 2016/9/16.
 */

public class FragmentArgs {

    private Fragment fragment;
    private Class fragmentClass;

    public FragmentArgs(Class clazz) {
        fragmentClass = clazz;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Class getFragmentClass() {
        return fragmentClass;
    }

    public FragmentArgs setInt(String field, int value) {
        try {
            Field f = fragmentClass.getDeclaredField(field);
            f.setAccessible(true);
            f.setInt(fragment, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public FragmentArgs setLong(String field, long value) {
        try {
            Field f = fragmentClass.getDeclaredField(field);
            f.setAccessible(true);
            f.setLong(fragment, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public FragmentArgs setDouble(String field, double value) {
        try {
            Field f = fragmentClass.getDeclaredField(field);
            f.setAccessible(true);
            f.setDouble(fragment, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public FragmentArgs setFloat(String field, float value) {
        try {
            Field f = fragmentClass.getDeclaredField(field);
            f.setAccessible(true);
            f.setFloat(fragment, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public FragmentArgs setByte(String field, byte value) {
        try {
            Field f = fragmentClass.getDeclaredField(field);
            f.setAccessible(true);
            f.setByte(fragment, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public FragmentArgs setBoolean(String field, boolean value) {
        try {
            Field f = fragmentClass.getDeclaredField(field);
            f.setAccessible(true);
            f.setBoolean(fragment, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public FragmentArgs setString(String field, String value) {
        try {
            Field f = fragmentClass.getDeclaredField(field);
            f.setAccessible(true);
            f.set(fragment, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public FragmentArgs setObject(String field, Object value) {
        try {
            Field f = fragmentClass.getDeclaredField(field);
            f.setAccessible(true);
            f.set(fragment, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Fragment create() {
        return fragment;
    }
}
