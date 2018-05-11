package com.ivy.ivyapp.app;

import android.app.Application;

import com.ivy.ivyapp.utils.L;

/**
 * Created by Ivy on 2018/5/11.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        Logger.addLogAdapter(new AndroidLogAdapter());
        L.d("MyApplication onCreate");
    }
}
