package com.npelly.sixfourdeploy;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Obtain application context before any other Android lifecycle events.
 */
public class MainApplication extends Application implements Network.Callback {
    @Override
    public void onCreate(){
        super.onCreate();
        Base.createSingleton(this);
        Base.logv("MainApplication onCreate()");
        Base.logd(getPackageName() + " " + getVersionString());

        Base.get().getNetwork().setCallback2(this);

        Base.get().getNetwork().startListen();
    }

    private String getVersionString() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return "v" + packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "<error>";
        }
    }

    @Override
    public void updateListening(boolean isListening, String status) {
        if (isListening) {
            startService(new Intent(this, DeployService.class));
        } else {
            stopService(new Intent(this, DeployService.class));
        }
    }
}
