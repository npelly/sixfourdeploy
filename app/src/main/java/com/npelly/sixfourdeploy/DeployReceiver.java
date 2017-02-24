package com.npelly.sixfourdeploy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;

public class DeployReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Network.ACTION_INSTALL_UPDATE.equals(intent.getAction())) {
            return;
        }

        int result = intent.getIntExtra(PackageInstaller.EXTRA_STATUS,
                PackageInstaller.STATUS_FAILURE);
        // String packageName = intent.getStringExtra(PackageInstaller.EXTRA_PACKAGE_NAME);

        switch (result) {
            case PackageInstaller.STATUS_PENDING_USER_ACTION:
                Base.logd("need user confirmation...");
                Base.get().getContext().startActivity((Intent) intent.getParcelableExtra(Intent.EXTRA_INTENT));
                break;
            case PackageInstaller.STATUS_SUCCESS:
                Base.logd("Successful install");
                break;
            case PackageInstaller.STATUS_FAILURE_ABORTED:
                Base.logd("installation cancelled by user");
                break;
            default:
                Base.logd("installation failed (reason: %d)", result);
                break;
        }
    }
}
