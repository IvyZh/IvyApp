package com.ivy.ivyapp.activities.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.ivy.commonlibrary.utils.L;

import java.util.Arrays;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected static final int WRITE_EXTERNAL_STORAGE_PERMISSION = 40001;
    protected static final int READ_PHOTO_PERMISSION = 40002;
    protected static final int CAMERA_PERMISSION = 40003;
    protected static final int EXTERNAL_STORAGE_CAMERA_PERMISSION = 40004;
    protected ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preContentView();
        setContentView(getContentView());
        ButterKnife.bind(this);
        mActionBar = getSupportActionBar();
        initView();
        loadData();
    }

    protected void loadData() {
    }

    protected void preContentView() {
    }

    protected abstract int getContentView();

    protected abstract void initView();

    protected void enterActivityFinishWithNoParams(Class clz) {
        startActivity(new Intent(this, clz));
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    // 1. 权限判断的封装
    protected boolean hasPermissions(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // 2. 权限申请的封装
    protected void requestPermissions(int requestCode, String... permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        }
    }

    // 3. 申请权限回调


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_PERMISSION:
                doSDCard(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
                break;
            case READ_PHOTO_PERMISSION:
                doPhoto(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
                break;
            case CAMERA_PERMISSION:
                doCamera(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
                break;
            case EXTERNAL_STORAGE_CAMERA_PERMISSION:

                L.v("grant:" + Arrays.asList(grantResults).toArray());
                for (int i = 0; i < grantResults.length; i++) {
                    L.v("p:" + grantResults[i]);
                }

                break;
        }
    }

    protected void doPhoto(boolean isGranted) {
    }

    protected void doCamera(boolean isGranted) {
    }

    protected void doSDCard(boolean isGranted) {
    }
}
