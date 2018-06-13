package com.ivy.commonlibrary.net.okhttp3;

import com.ivy.commonlibrary.net.okhttp3.cookie.SimpleCookieJar;
import com.ivy.commonlibrary.net.okhttp3.https.HttpsUtils;
import com.ivy.commonlibrary.net.okhttp3.response.CommonFileCallback;
import com.ivy.commonlibrary.net.okhttp3.response.CommonHandleResonseData;
import com.ivy.commonlibrary.net.okhttp3.response.CommonJsonCallback;
import com.ivy.commonlibrary.utils.L;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ivy on 2018/5/14.
 */

public class CommonOkHttpClient {
    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient;

    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        /**
         *  为所有请求添加请求头，看个人需求
         */
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
//                        .addHeader("User-Agent", "Imooc-Mobile") // 标明发送本次请求的客户端
                        .build();
                return chain.proceed(request);
            }
        });

        okHttpClientBuilder.addInterceptor(new LogInterceptor());

        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.followRedirects(true);
        /**
         * trust all the https point
         */
        okHttpClientBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager());
        mOkHttpClient = okHttpClientBuilder.build();
    }

    private static class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            L.v("request:" + request.toString());
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            L.v(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            L.v("response body:" + content);
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }


    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static Call get(Request request, CommonHandleResonseData handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    public static Call post(Request request, CommonHandleResonseData handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

//    public static Call downloadFile(Request request, DisposeDataHandle handle) {
//        Call call = mOkHttpClient.newCall(request);
//        call.enqueue(new CommonFileCallback(handle));
//        return call;
//    }

    public static Call downloadFile(Request request, CommonHandleResonseData handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonFileCallback(handle));
        return call;
    }
}
