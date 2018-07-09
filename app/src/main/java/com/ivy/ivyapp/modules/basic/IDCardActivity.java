package com.ivy.ivyapp.modules.basic;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;
import com.wildma.idcardcamera.camera.CameraActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ivy on 2018/7/9.
 */

public class IDCardActivity extends BaseActivity {
    @BindView(R.id.iv_idcard)
    ImageView mIvIdcard;
    @BindView(R.id.btn_idcard_front)
    Button mBtnIdcardFront;
    @BindView(R.id.btn_idcard_opposite)
    Button mBtnIdcardOpposite;

    @Override
    protected int getContentView() {
        return R.layout.activity_idcard;
    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.btn_idcard_front, R.id.btn_idcard_opposite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_idcard_front://正面
                CameraActivity.toCameraActivity(this, CameraActivity.TYPE_IDCARD_FRONT);
                break;
            case R.id.btn_idcard_opposite://反面
                CameraActivity.toCameraActivity(this, CameraActivity.TYPE_IDCARD_BACK);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CameraActivity.REQUEST_CODE && resultCode == CameraActivity.RESULT_CODE) {
            //获取图片路径，显示图片
            final String path = CameraActivity.getImagePath(data);
            if (!TextUtils.isEmpty(path)) {
                mIvIdcard.setImageBitmap(BitmapFactory.decodeFile(path));
            }
        }
    }
}
