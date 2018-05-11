package com.ivy.ivyapp.utils;

import android.content.Context;

import com.ivy.ivyapp.app.MyApplication;

/**
 * Created by Ivy on 2018/5/12.
 */

public class UIUtils {
    public static Context getContex() {
        return MyApplication.getContext();
    }

    public static String getString(int resId) {
        return getContex().getResources().getString(resId);
    }
}
