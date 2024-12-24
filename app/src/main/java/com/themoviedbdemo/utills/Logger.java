package com.themoviedbdemo.utills;


import android.util.Log;

import com.themoviedbdemo.BuildConfig;

public class Logger {

    public static void errorLog(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void debugLog(String tag, String message) {
//        if (BuildConfig.DEBUG) {
            largeLog(tag, message);
        System.out.println("TAG => "+message);
//        }
    }
    private static void largeLog(String tag, String content) {
        if (content.length() > 4000) {
            Log.d(tag, content.substring(0, 4000));
            largeLog(tag, content.substring(4000));

        } else {
            Log.d(tag, content);
        }
    }
}
