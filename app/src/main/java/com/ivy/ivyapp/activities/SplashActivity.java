package com.ivy.ivyapp.activities;

import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                enterActivityFinishWithNoParams(LoginActivity.class);
            }
        }
    };

    @Override
    protected void preContentView() {
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {
        createShortCut();//TODO 以后再实现
        mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    /**
     * 创建桌面快捷图标
     */
    private void createShortCut() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }
}
