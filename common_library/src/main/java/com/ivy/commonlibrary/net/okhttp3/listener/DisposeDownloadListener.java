package com.ivy.commonlibrary.net.okhttp3.listener;

/**
 * @author vision
 * @function 监听下载进度
 */
public interface DisposeDownloadListener extends CommonResponseListener {
    public void onProgress(int progrss);
}
