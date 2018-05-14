package com.ivy.ivyapp.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.ivy.commonlibrary.utils.L;

/**
 * Created by Ivy on 2018/5/11.
 */

public class MyApplication extends Application {
    private static MyApplication mApp;
    private static Handler handler;
    private static int mainTid;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        handler = new Handler();
        mainTid = android.os.Process.myTid();
//        Logger.addLogAdapter(new AndroidLogAdapter());
        L.d("MyApplication onCreate");
    }

    public static Context getContext() {
        return mApp;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainTid() {
        return mainTid;
    }
}
