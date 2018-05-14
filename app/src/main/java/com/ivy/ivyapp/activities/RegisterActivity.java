package com.ivy.ivyapp.activities;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ivy.commonlibrary.utils.L;
import com.ivy.commonlibrary.utils.Md5Utils;
import com.ivy.ivyapp.AbstractLoginAndRegisterActivity;
import com.ivy.ivyapp.R;
import com.ivy.ivyapp.domain.User;
import com.ivy.ivyapp.utils.UIUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Ivy on 2018/3/12.
 */

public class RegisterActivity extends AbstractLoginAndRegisterActivity {
    @BindView(R.id.et_username)
    EditText mEtUserName;
    @BindView(R.id.et_mail)
    EditText mEtMail;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.et_pwd2)
    EditText mEtPwd2;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;
    @BindView(R.id.bt_register)
    TextView mTvRegister;


    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mEtMail.addTextChangedListener(this);
        mEtPwd.addTextChangedListener(this);
        mEtPwd2.addTextChangedListener(this);
        mTvRegister.setClickable(false);
        String loginStr = "<font color=\"#999999\">" + "已有账号，</font>" + "<font color=\"#2E8FFF\">" + "登录</font>";
        mTvLogin.setText(Html.fromHtml(loginStr.toString()));
        String protocolStr = "<font color=\"#C4C4C4\">" + "注册/登录即代表同意</font>" + "<font color=\"#000000\">" + "《ChatDemo用户协议》</font>";
        mTvProtocol.setText(Html.fromHtml(protocolStr.toString()));
        checkEditTextStatus();
    }


    @OnClick({R.id.bt_register, R.id.tv_login, R.id.tv_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_register:
                L.v("register");
                register();
                break;
            case R.id.tv_login:
                enterActivityFinishWithNoParams(LoginActivity.class);
                break;
            case R.id.tv_protocol:
                UIUtils.showToast("进入协议用户内容界面");
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        String userName = mEtUserName.getText().toString().trim();
        String email = mEtMail.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        String pwd2 = mEtPwd2.getText().toString().trim();
        String ruler = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        Pattern regex = Pattern.compile(ruler);
        Matcher matcher = regex.matcher(email);

        if (TextUtils.isEmpty(userName)) {
            UIUtils.showToast("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            UIUtils.showToast("邮箱不能为空");
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            UIUtils.showToast("密码不能为空");
            return;
        }

        if (TextUtils.isEmpty(pwd2)) {
            UIUtils.showToast("重复密码不能为空");
            return;
        }


        if (!matcher.matches()) {
            UIUtils.showToast("邮箱格式不正确");
            return;
        }
        if (!TextUtils.equals(pwd, pwd2)) {
            UIUtils.showToast("两次密码不一致");
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 12) {
            UIUtils.showToast("密码长度请限制在6-12位");
            return;
        }
        //TODO
        L.v("regeister");
        User user = new User();
        user.setUsername(userName);
        user.setEmail(email);
        user.setPassword(Md5Utils.encode(pwd));
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    L.v("注册成功:" + user);
                } else {
                    L.v("注册失败:" + e.toString());
                    UIUtils.showToast(e.toString());
                }
            }
        });


    }


    @Override
    protected void checkEditTextStatus() {
        String userName = mEtUserName.getText().toString().trim();
        String email = mEtMail.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        String pwd2 = mEtPwd2.getText().toString().trim();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwd2)) {
            mTvRegister.setTextColor(UIUtils.getColor(R.color.color_white));
            mTvRegister.setClickable(false);
        } else {
            mTvRegister.setClickable(true);
            mTvRegister.setTextColor(UIUtils.getColor(R.color.color_blue));
        }
    }
}
