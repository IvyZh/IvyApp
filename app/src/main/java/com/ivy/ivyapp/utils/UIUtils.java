package com.ivy.ivyapp.utils;

import android.content.Context;
import android.widget.Toast;

import com.ivy.ivyapp.app.MyApplication;

import static com.ivy.ivyapp.app.MyApplication.getContext;

/**
 * Created by Ivy on 2018/5/12.
 */

public class UIUtils {
    private static Toast mToast;

    public static Context getContex() {
        return getContext();
    }

    public static String getString(int resId) {
        return getContex().getResources().getString(resId);
    }

    public static void showToast(final String msg) {
        if (mToast == null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
                }
            });
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mToast.setText(msg);
                mToast.show();
            }
        });
    }

    public static void runOnUiThread(Runnable runnable) {
        runOnUiThread(runnable, 0);
    }

    public static void runOnUiThread(Runnable runnable, long delayMillis) {
        if (android.os.Process.myTid() == MyApplication.getMainTid()) {
            if (delayMillis != 0) {
                MyApplication.getHandler().postDelayed(runnable, delayMillis);
            } else {
                runnable.run();
            }
        } else {
            MyApplication.getHandler().postDelayed(runnable, delayMillis);
        }
    }

    public static int getColor(int colorId) {
        return getContex().getResources().getColor(colorId);
    }
}
