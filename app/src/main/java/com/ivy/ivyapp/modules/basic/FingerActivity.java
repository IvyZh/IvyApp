package com.ivy.ivyapp.modules.basic;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ivy.commonlibrary.utils.L;
import com.ivy.ivyapp.R;
import com.ivy.ivyapp.activities.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ivy on 2018/7/9.
 */

public class FingerActivity extends BaseActivity {
    @BindView(R.id.tv_intro)
    TextView mTvIntro;
    @BindView(R.id.btn_finish)
    Button mBtnFinish;

    private FingerprintManager mFingerprintManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_finger;
    }

    @Override
    protected void initView() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mFingerprintManager = getSystemService(FingerprintManager.class);
            L.v("isHardwareDetected：" + mFingerprintManager.isHardwareDetected());
            L.v("isHardwareDetected：" + mFingerprintManager.hasEnrolledFingerprints());

            if (mFingerprintManager.isHardwareDetected() && mFingerprintManager.hasEnrolledFingerprints()) {
                if (mFingerprintManager != null) {
                    //mFingerprintManager.authenticate(cryptoObject, mCancellationSignal, 0, mAuthCallback, null);
                    mFingerprintManager.authenticate(null, null, 0, mAuthCallback, null);
                }
            } else {
                mTvIntro.setText("当前手机硬件不支持手机指纹功能");
            }
        } else {
            mTvIntro.setText("当前手机版本小于M,不支持手机指纹功能");
        }
    }

    private FingerprintManager.AuthenticationCallback mAuthCallback = new FingerprintManager.AuthenticationCallback() {
        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            //指纹验证成功
            Toast.makeText(FingerActivity.this, "onAuthenticationSucceeded()", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            //指纹验证失败，不可再验
            Toast.makeText(FingerActivity.this, "onAuthenticationError()" + errString, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            //指纹验证失败，可再验，可能手指过脏，或者移动过快等原因。
            Toast.makeText(FingerActivity.this, "onAuthenticationHelp()" + helpString, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationFailed() {
            //指纹验证失败，指纹识别失败，可再验，该指纹不是系统录入的指纹。
            Toast.makeText(FingerActivity.this, "onAuthenticationFailed():不能识别", Toast.LENGTH_SHORT).show();
        }
    };


    @OnClick({R.id.btn_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_finish://正面
                finish();
                break;

        }
    }


}
