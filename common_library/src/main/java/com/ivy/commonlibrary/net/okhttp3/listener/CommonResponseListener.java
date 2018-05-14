package com.ivy.commonlibrary.net.okhttp3.listener;

/**
 * Created by Ivy on 2018/5/14.
 */

public interface CommonResponseListener {

    /**
     * 请求成功回调事件处理
     */
    public void onSuccess(Object responseObj);

    /**
     * 请求失败回调事件处理
     */
    public void onFailure(Object reasonObj);
}
