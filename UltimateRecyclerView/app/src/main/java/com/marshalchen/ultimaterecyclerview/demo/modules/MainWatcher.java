package com.marshalchen.ultimaterecyclerview.demo.modules;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import me.drakeet.library.CrashWoodpecker;

/**
 * Created by hesk on 2/10/15.
 */
public class MainWatcher extends Application {
    @Override public void onCreate() {
        super.onCreate();
        CrashWoodpecker.fly().to(this);
    }
}
