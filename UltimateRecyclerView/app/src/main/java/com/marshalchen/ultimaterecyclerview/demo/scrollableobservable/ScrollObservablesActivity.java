package com.marshalchen.ultimaterecyclerview.demo.scrollableobservable;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.marshalchen.ultimaterecyclerview.demo.R;

/**
 * Created by hesk on 12/6/15.
 */
public class ScrollObservablesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_scroll_activity);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag(ViewPagerTabFragmentParentFragment.FRAGMENT_TAG) == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragment, new ViewPagerTabFragmentParentFragment(),
                    ViewPagerTabFragmentParentFragment.FRAGMENT_TAG);
            ft.commit();
            fm.executePendingTransactions();
        }
    }
}
