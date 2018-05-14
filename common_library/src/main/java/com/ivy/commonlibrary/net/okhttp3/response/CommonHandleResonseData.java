package com.ivy.commonlibrary.net.okhttp3.response;

import com.ivy.commonlibrary.net.okhttp3.listener.CommonResponseListener;

/**
 * Created by Ivy on 2018/5/14.
 */

public class CommonHandleResonseData {
    public CommonResponseListener mListener = null;
    public Class<?> mClass = null;
    public String mSource = null;

    public CommonHandleResonseData(CommonResponseListener listener) {
        this.mListener = listener;
    }

    public CommonHandleResonseData(CommonResponseListener listener, Class<?> clazz) {
        this.mListener = listener;
        this.mClass = clazz;
    }

    public CommonHandleResonseData(CommonResponseListener listener, String source) {
        this.mListener = listener;
        this.mSource = source;
    }

}
