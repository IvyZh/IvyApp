package com.ivy.ivyapp.modules.customui;

import android.support.v7.app.ActionBar;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;

/**
 * Created by Ivy on 2018/5/17.
 */

public class YouKuMenuActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_youku_menu;
    }

    @Override
    protected void initView() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("优酷菜单");


    }
}
