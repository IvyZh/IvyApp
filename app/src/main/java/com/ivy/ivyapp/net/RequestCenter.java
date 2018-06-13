package com.ivy.ivyapp.net;


import android.net.Uri;

import com.ivy.commonlibrary.net.okhttp3.CommonOkHttpClient;
import com.ivy.commonlibrary.net.okhttp3.listener.CommonResponseListener;
import com.ivy.commonlibrary.net.okhttp3.listener.DisposeDownloadListener;
import com.ivy.commonlibrary.net.okhttp3.request.CommonRequest;
import com.ivy.commonlibrary.net.okhttp3.request.CommonRequestParams;
import com.ivy.commonlibrary.net.okhttp3.response.CommonHandleResonseData;
import com.ivy.ivyapp.modules.doubanmovies.domain.MoviesListData;

import java.net.URLEncoder;

/**
 * Created by Ivy on 2018/5/14.
 */


public class RequestCenter {
    private static String DOUBAN_MOVIE_HOST = "https://api.douban.com";

    private static void get(String url, CommonRequestParams request, CommonResponseListener listener, Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, request), new CommonHandleResonseData(listener, clazz));
    }

    public static void getBook(int id, CommonResponseListener listener) {
        String url = "https://api.douban.com/v2/book/" + id;
        get(url, null, listener, String.class);
    }

    public static void getInTheaters(String city, CommonResponseListener listener) {
        String url = DOUBAN_MOVIE_HOST + "/v2/movie/in_theaters?city=" + Uri.encode(city);
        get(url, null, listener, MoviesListData.class);
    }

    public static void downloadFile(String url, String path, DisposeDownloadListener listener) {
        CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(url, null),
                new CommonHandleResonseData(listener, path));
    }


}
