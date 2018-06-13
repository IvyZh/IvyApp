package com.ivy.ivyapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ivy.commonlibrary.net.okhttp3.listener.DisposeDownloadListener;
import com.ivy.commonlibrary.utils.L;
import com.ivy.ivyapp.R;
import com.ivy.ivyapp.fragments.base.BaseFragment;
import com.ivy.ivyapp.modules.basic.PermissionActivity;
import com.ivy.ivyapp.modules.customui.CustomUiActivity;
import com.ivy.ivyapp.modules.dn.ui.DnUiActivity;
import com.ivy.ivyapp.net.RequestCenter;
import com.ivy.ivyapp.utils.UIUtils;

import java.io.File;
import java.util.Arrays;

import butterknife.BindView;

import static cn.bmob.v3.Bmob.getFilesDir;

/**
 * Created by Ivy on 2018/5/11.
 */

public class O3Fragment extends BaseFragment {
    @BindView(R.id.lv_datas)
    ListView mLvDatas;

    private String[] datas = {"权限", "算法", "下载演示", "D"};


    @Override
    protected int getContentView() {
        return R.layout.fragment_o3;
    }


    @Override
    protected void initView() {
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, Arrays.asList(datas));
        mLvDatas.setAdapter(adapter);
        mLvDatas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = datas[position];
                if (text.contains("权限")) {
                    startActivity(new Intent(getContext(), PermissionActivity.class));
                } else if (text.contains("算法")) {
                    startActivity(new Intent(getContext(), DnUiActivity.class));
                } else if (text.contains("下载演示")) {
                    download();
                }
            }
        });
    }

    private void download() {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置样式格式
        dialog.show();

        String apkUrl = "http://imtt.dd.qq.com/16891/83753F323D3AFCF545A5D286F0D3F857.apk?fsname=cn.net.sinodata.ddj_1.9.4_14.apk&csr=1bbd";
        String filePath = getFilesDir().getAbsolutePath();//-- data/data/packageName/files
        filePath = Environment.getExternalStorageDirectory() + File.separator + "/coolapp_down";
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            file.mkdirs();
        }
        String apkPath = file.getAbsolutePath() + File.separator + "cool_app.apk";

        RequestCenter.downloadFile(apkUrl, apkPath, new DisposeDownloadListener() {
            @Override
            public void onProgress(int progrss) {
                L.v("onProgress:" + progrss);
                dialog.setProgress(progrss);
            }

            @Override
            public void onSuccess(Object responseObj) {
                L.v("onSuccess:" + responseObj);
                dialog.dismiss();
                UIUtils.showToast("下载完成" + responseObj);
            }

            @Override
            public void onFailure(Object reasonObj) {
                L.v("onFailure:" + reasonObj);
                UIUtils.showToast("下载失败" + reasonObj);
                dialog.dismiss();
            }
        });
    }


    @Override
    protected void loadData() {

    }
}
