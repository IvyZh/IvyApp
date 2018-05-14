package com.ivy.ivyapp;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.ivy.commonlibrary.utils.L;
import com.ivy.commonlibrary.utils.Md5Utils;
import com.ivy.ivyapp.activities.MainActivity;
import com.ivy.ivyapp.activities.base.BaseActivity;
import com.ivy.ivyapp.domain.User;
import com.ivy.ivyapp.utils.UIUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Ivy on 2017/12/4.
 */

public abstract class AbstractLoginAndRegisterActivity extends BaseActivity implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        checkEditTextStatus();
    }

    protected abstract void checkEditTextStatus();


    protected void login(String username, String pwd) {
//        String ruler = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
//        Pattern regex = Pattern.compile(ruler);
//        Matcher matcher = regex.matcher(email);
//        if (!matcher.matches()) {
//            UIUtils.showToast("邮箱格式不正确");
//            return;
//        }
        if (TextUtils.isEmpty(username)) {
            UIUtils.showToast("用户名不能为空");
            return;
        }

        if (pwd.length() < 6 || pwd.length() > 12) {
            UIUtils.showToast("密码长度请限制在6-12位");
            return;
        }

        //TODO
        L.v("login...");
        User user = new User();
        user.setUsername(username);
        user.setPassword(Md5Utils.encode(pwd));
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    L.v("登录成功:" + user);
                    enterActivityFinishWithNoParams(MainActivity.class);
                } else {
                    L.v("登录失败:" + e.toString());
                    UIUtils.showToast(e.toString());
                }
            }
        });
    }
}
