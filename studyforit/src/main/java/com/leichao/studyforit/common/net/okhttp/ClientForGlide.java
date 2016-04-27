package com.leichao.studyforit.common.net.okhttp;

import com.leichao.studyforit.common.debug.Debug;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建Glide的OkHttpClient
 * Created by leichao on 2016/4/23.
 */
public class ClientForGlide {

    private static OkHttpClient client;

    public static OkHttpClient getClient() {
        if (client == null) {
            client = new ClientForGlide().get();
        }
        return client;
    }

    /**
     * 设置Glide所用的OkHttp的一些参数
     */
    private OkHttpClient get() {

        return ClientBuilder.getBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return interceptForGlide(chain);
                    }
                })
                .build();
    }

    private Response interceptForGlide(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();
        Debug.e("glide","url:"+original.url());
        return chain.proceed(original);
    }

}
