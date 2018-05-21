package com.ivy.ivyapp.modules.basic;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.ivy.commonlibrary.utils.L;
import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ivy on 2018/5/21.
 */

public class PermissionActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_permission;
    }

    @Override
    protected void initView() {
        mActionBar.setTitle("权限管理");
        mActionBar.setDisplayHomeAsUpEnabled(true);

    }


    @OnClick({R.id.btn_sdcard_permission, R.id.btn_photo_permission, R.id.btn_camera_permission})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sdcard_permission:
                if (hasPermission()) {
                    L.v("有权限,doSomething");
                } else {
                    L.v("没有权限，请求权限");
                    requestPermisson();
                }

                break;
            case R.id.btn_photo_permission:

                if (hasPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    L.v("打开EXTERNAL_STORAGE_CAMERA_PERMISSION doSomething....");
                } else {
                    L.v("请求EXTERNAL_STORAGE_CAMERA_PERMISSION 权限....");
                    requestPermissions(EXTERNAL_STORAGE_CAMERA_PERMISSION, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                break;
            case R.id.btn_camera_permission:
                if (hasPermissions(Manifest.permission.CAMERA)) {
                    L.v("打开相机doSomething....");
                } else {
                    L.v("请求相机权限....");
                    requestPermissions(CAMERA_PERMISSION, Manifest.permission.CAMERA);
                }
                break;
        }
    }

    // 2. 申请权限
    private void requestPermisson() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x01);
    }

    // 1. 判断是否有权限
    private boolean hasPermission() {
        boolean isGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return isGranted;
    }

    // 3. 判断用户是否授予了权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x01) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                L.v("用户授予了权限，doSomething...");
            } else {
                L.v("用户拒绝了权限");
            }
        }
    }


    @Override
    protected void doCamera(boolean isGranted) {
        super.doCamera(isGranted);
        if (isGranted) {
            L.v("打开相机doSomething....");
        } else {
            L.v("用户拒绝授权");
        }
    }
}
