package com.marshalchen.ultimaterecyclerview.demo.FragmentTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.marshalchen.ultimaterecyclerview.demo.R;

/**
 * Created by hesk on 2016/9/16.
 */

public class FragmentGridHeader extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.framelo);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new FragmentURV())
                .addToBackStack(null)
                .commit();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
