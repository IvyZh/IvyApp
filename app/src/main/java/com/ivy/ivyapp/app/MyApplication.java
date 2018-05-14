package com.ivy.ivyapp.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.ivy.commonlibrary.utils.L;
import com.ivy.ivyapp.constant.Constant;

import cn.bmob.v3.Bmob;

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
        L.d("MyApplication onCreate");
        MultiDex.install(this);
        mApp = this;
        handler = new Handler();
        mainTid = android.os.Process.myTid();

        //init
        Bmob.initialize(this, Constant.BmobAppKey);
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
