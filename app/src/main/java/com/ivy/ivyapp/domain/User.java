package com.ivy.ivyapp.domain;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by Ivy on 2018/5/14.
 */

public class User extends BmobUser {

    @Override
    public String toString() {
        return this.getUsername();
    }
}
