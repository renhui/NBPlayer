package com.renhui.nbplayer.utils;

import com.orhanobut.logger.Logger;
import com.renhui.nbplayer.BuildConfig;

public class LoggerUtil {
    /**
     * 手动开关
     */
    public final static boolean DEBUG = BuildConfig.DEBUG;

    public static void d(String message) {
        if (DEBUG) {
            Logger.d(message);
        }
    }

    public static void e(String message) {
        if (DEBUG) {
            Logger.e(message);
        }
    }

    public static void e(String message, Object args) {
        if (DEBUG) {
            Logger.e(message, args);
        }
    }

    public static void w(String message) {
        if (DEBUG) {
            Logger.w(message);
        }
    }

    public static void v(String message) {
        if (DEBUG) {
            Logger.v(message);
        }
    }

    public static void i(String message) {
        if (DEBUG) {
            Logger.i(message);
        }
    }

    public static void wtf(String message) {
        if (DEBUG) {
            Logger.wtf(message);
        }
    }

    public static void xml(String message) {
        if (DEBUG) {
            Logger.xml(message);
        }
    }

    public static void json(String message) {
        if (DEBUG) {
            Logger.json(message);
        }
    }
}