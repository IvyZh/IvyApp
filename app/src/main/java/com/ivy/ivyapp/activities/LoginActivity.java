package com.ivy.ivyapp.activities;

import android.graphics.Typeface;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ivy.commonlibrary.utils.L;
import com.ivy.ivyapp.AbstractLoginAndRegisterActivity;
import com.ivy.ivyapp.R;
import com.ivy.ivyapp.utils.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ivy on 2018/3/12.
 */

public class LoginActivity extends AbstractLoginAndRegisterActivity {

    @BindView(R.id.et_username)
    EditText mEtUserName;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.tv_forget_pwd)
    TextView mTvForgetPwd;
    @BindView(R.id.bt_login)
    TextView mBtLogin;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mBtLogin.setClickable(false);
        String loginStr = "<font color=\"#999999\">" + "没有账号？</font>" + "<font color=\"#2E8FFF\">" + "立即注册</font>";
        mTvRegister.setText(Html.fromHtml(loginStr.toString()));
        String protocolStr = "<font color=\"#C4C4C4\">" + "注册/登录即代表同意</font>" + "<font color=\"#000000\">" + "《ChatDemo用户协议》</font>";
        mTvProtocol.setText(Html.fromHtml(protocolStr.toString()));
        mEtUserName.addTextChangedListener(this);
        mEtPwd.addTextChangedListener(this);
        checkEditTextStatus();

//        Typeface typeface = Typeface.createFromAsset(getAssets(), "/fonts/FONT.TTF");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FONT.TTF");
        mTvProtocol.setTypeface(typeface);
    }


    @OnClick({R.id.tv_forget_pwd, R.id.bt_login, R.id.tv_register, R.id.tv_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_pwd:
                UIUtils.showToast("进入忘记密码界面");
                break;
            case R.id.bt_login:
                L.v("login");
                String username = mEtUserName.getText().toString().trim();
                String pwd = mEtPwd.getText().toString().trim();
                enterActivityFinishWithNoParams(MainActivity.class);
                //login(username, pwd);
                break;
            case R.id.tv_register:
                enterActivityFinishWithNoParams(RegisterActivity.class);
                break;
            case R.id.tv_protocol:
                UIUtils.showToast("进入协议用户内容界面");
                break;
        }
    }


    @Override
    protected void checkEditTextStatus() {
        String password = mEtUserName.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(pwd)) {
            mBtLogin.setTextColor(UIUtils.getColor(R.color.color_white));
            mBtLogin.setClickable(false);
        } else {
            mBtLogin.setClickable(true);
            mBtLogin.setTextColor(UIUtils.getColor(R.color.color_blue));
        }
    }
}
