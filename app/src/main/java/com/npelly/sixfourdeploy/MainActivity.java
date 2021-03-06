package com.npelly.sixfourdeploy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInstaller;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends android.app.Activity implements TextOutput.Callback, View.OnClickListener, Network.Callback {

    private TextView debugTextView;
    private TextView mainTextView;
    private ScrollView scrollView;
    private FloatingActionButton fab;
    private boolean fabGreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Base.logv("MainActivity onCreate()");

        setContentView(R.layout.activity_main);

        debugTextView = (TextView) findViewById(R.id.debug_text);
        mainTextView = (TextView) findViewById(R.id.main_text);
        scrollView = (ScrollView) findViewById(R.id.debug_scroll);
        fab =(FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Base.logv("MainActivity onResume()");
        Base.get().getTextOutput().setCallback(this);
        Base.get().getNetwork().setCallback1(this);
        debugTextView.setText(Base.get().getTextOutput().getText());
        fabGreen = Base.get().getNetwork().isListening();
        updateListening(fabGreen, Base.get().getNetwork().getStatus());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Base.logv("MainActivity onPause()");

        Base.get().getTextOutput().setCallback(null);
        Base.get().getNetwork().setCallback1(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Base.logv("MainActivity onDestroy()");
    }

    /** can be called on any thread */
    @Override
    public void updateText(final StringBuilder text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (debugTextView == null) return;

                debugTextView.setText(text);

                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
    }

    /** Callback for change in listening status.
     * Can be called on any thread */
    @Override
    public void updateListening(final boolean isListening, final String status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (fab == null) return;

                if (!isListening) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.holo_red_dark)));
                } else {
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.holo_green_dark)));
                }
                mainTextView.setText(status);
                fabGreen = isListening;
            }
        });
    }

    /** Callback for fab press */
    @Override
    public void onClick(View v) {
        if (fabGreen) {
            Base.get().getNetwork().stopListen();
        } else {
            Base.get().getNetwork().startListen();
        }
    }
}
