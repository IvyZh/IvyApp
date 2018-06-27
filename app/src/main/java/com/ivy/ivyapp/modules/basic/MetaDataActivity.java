package com.ivy.ivyapp.modules.basic;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ivy.commonlibrary.utils.L;
import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;
import com.ivy.ivyapp.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ivy on 2018/6/27.
 */

public class MetaDataActivity extends BaseActivity {
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.button3)
    Button mButton3;

    @Override
    protected int getContentView() {
        return R.layout.activity_metadata;
    }

    @Override
    protected void initView() {
        mActionBar.setTitle("MetaData");
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }


    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                try {
                    ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
                    Bundle metaData = applicationInfo.metaData;
                    String meta_data_in_application = metaData.getString("meta_data_in_application");
                    L.v("meta-data:" + meta_data_in_application);

                    UIUtils.showToast(meta_data_in_application);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.button2:
                try {
                    ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
                    Bundle metaData = activityInfo.metaData;
                    String meta_data_in_mainactivity = metaData.getString("meta_data_in_mainactivity");
                    L.v("meta-data:" + meta_data_in_mainactivity);//null
                    UIUtils.showToast("meta-data:" + meta_data_in_mainactivity);
                } catch (Exception e) {
                    e.printStackTrace();
                    UIUtils.showToast(e.toString());
                    L.e(e.toString());
                }
                break;
            case R.id.button3:
                try {
                    ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
                    Bundle metaData = activityInfo.metaData;
                    String meta_data_in_permissionactivity = metaData.getString("meta_data_in_permissionactivity");
                    L.v("meta-data:" + meta_data_in_permissionactivity);//null

                    UIUtils.showToast("meta-data:" + meta_data_in_permissionactivity);
                } catch (Exception e) {
                    e.printStackTrace();
                    UIUtils.showToast(e.toString());
                    L.e(e.toString());

                }
                break;
            case R.id.button4:
                try {
                    ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
                    Bundle metaData = activityInfo.metaData;
                    String meta_data_in_metaactivity = metaData.getString("meta_data_in_metaactivity");
                    L.v("meta-data:" + meta_data_in_metaactivity);

                    UIUtils.showToast(meta_data_in_metaactivity);
                } catch (Exception e) {
                    e.printStackTrace();
                    //UIUtils.showToast(e.toString());
                    L.e(e.toString());
                }
                break;
        }
    }
}
