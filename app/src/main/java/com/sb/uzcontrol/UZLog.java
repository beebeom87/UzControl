package com.sb.uzcontrol;

import android.util.Log;

/**
 * Created by SB on 2016-08-22.
 */
public class UZLog {
    public static String LOG_TAG = "UZControl";

    public static void d(String tag, String msg) {
        Log.d(LOG_TAG, tag + ": " + msg);
    }

    public static void i(String tag, String msg) {
        Log.i(LOG_TAG, tag + ": " + msg);
    }

    public static void e(String tag, String msg, Exception e) {
        Log.e(LOG_TAG, tag + ": " + msg, e);
    }
}
