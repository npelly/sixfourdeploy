package com.npelly.sixfourdeploy;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Obtain application context before any other Android lifecycle events.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Base.createSingleton(this);
        Base.logv("MainApplication onCreate()");
        Base.logd(getPackageName() + " " + getVersionString());
    }

    private String getVersionString() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return "v" + packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "<error>";
        }
    }
}
