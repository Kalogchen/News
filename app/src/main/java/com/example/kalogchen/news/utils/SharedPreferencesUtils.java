package com.example.kalogchen.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * 封装SharePreferences
 * Created by kalogchen on 2016/12/15.
 */

public class SharedPreferencesUtils {

    public static String sharePreName = "config";

    public static void setBoolean(Context context, String key, boolean value ) {
        SharedPreferences sp = context.getSharedPreferences(sharePreName, MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(sharePreName, MODE_PRIVATE);
        boolean intoGuide = sp.getBoolean(key, defaultValue);
        return intoGuide;
    }
}
