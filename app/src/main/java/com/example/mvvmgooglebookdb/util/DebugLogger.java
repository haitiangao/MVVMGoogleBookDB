package com.example.mvvmgooglebookdb.util;

import android.util.Log;

import static com.example.mvvmgooglebookdb.util.Constants.*;
import static com.example.mvvmgooglebookdb.util.Constants.ERROR_PREFIX;
import static com.example.mvvmgooglebookdb.util.Constants.TAG;

public class DebugLogger {

    public static void logError(Throwable throwable) {
        Log.d(TAG, ERROR_PREFIX + throwable.getLocalizedMessage());
    }

    public static void logDebug(String message) {
        Log.d(TAG, message);
    }
}
