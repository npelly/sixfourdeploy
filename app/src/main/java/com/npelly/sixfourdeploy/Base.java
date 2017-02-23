package com.npelly.sixfourdeploy;

import android.content.Context;
import android.util.Log;

/**
 * God singleton.
 */
public class Base {
    public static final String TAG = "Base";

    private static Base singleton;

    public static void logv(String fmt, Object... args) {
        if (true) {  //TODO: move into build config
            Log.d(TAG, String.format(fmt, args));
        }
        if (false) {
            get().getTextOutput().append(fmt + "\n", args);
        }
    }

    public static void logd(String fmt, Object... args) {
        if (true) {  //TODO: move into build config
            Log.d(TAG, String.format(fmt, args));
        }
        if (true) {
            get().getTextOutput().append(fmt + "\n", args);
        }
    }

    public static void createSingleton(Context context) {
        if (singleton == null) {
            singleton = new Base(context);
        }
    }

    public static Base get() {
        if (singleton == null) {
            throw new RuntimeException("Singleton requested before creation.");
        }
        return singleton;
    }

    private final Context context;  // Application context
    private final TextOutput textOutput;
    private final Network network;

    private Base(Context context) {
        this.context = context.getApplicationContext();
        this.textOutput = new TextOutput();
        this.network = new Network();
    }

    public Context getContext() {
        return context;
    }

    public TextOutput getTextOutput() {
        return textOutput;
    }

    public Network getNetwork() {
        return network;
    }
}
