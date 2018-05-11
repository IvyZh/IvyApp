package com.ivy.ivyapp.app;

import android.app.Application;
import android.content.Context;

import com.ivy.ivyapp.utils.L;

/**
 * Created by Ivy on 2018/5/11.
 */

public class MyApplication extends Application {
    private static MyApplication mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
//        Logger.addLogAdapter(new AndroidLogAdapter());
        L.d("MyApplication onCreate");
    }

    public static Context getContext() {
        return mApp;
    }
}
