package com.npelly.sixfourdeploy;

import android.app.Activity;
import android.os.Looper;

public class TextOutput {

    public interface Callback {
        void updateText(StringBuilder text);
    }

    private final StringBuilder text;
    private Callback callback;

    public TextOutput() {
        text = new StringBuilder();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public StringBuilder getText() {
        return text;
    }

    public void append(String fmt, Object... args) {
        text.append(String.format(fmt, args));


        if (callback != null) {
            callback.updateText(text);
        }
    }
}
