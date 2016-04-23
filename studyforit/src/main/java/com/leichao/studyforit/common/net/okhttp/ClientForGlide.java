package com.leichao.studyforit.common.net.okhttp;

import com.leichao.studyforit.common.debug.Debug;
import com.leichao.studyforit.config.NetConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

    public static OkHttpClient getGlideClient() {
        if (client == null) {
            //client = new ClientForGlide().build(new OkHttpClient.Builder());
            client = new ClientForGlide().build(OkHttpImpl.getUnsafeBuilder());
        }
        return client;
    }

    /**
     * 设置Glide所用的OkHttp的一些参数
     */
    private OkHttpClient build(OkHttpClient.Builder builder) {

        return builder
                .connectTimeout(NetConfig.TIMEOUT, TimeUnit.SECONDS)// 链接超时
                .writeTimeout(NetConfig.TIMEOUT, TimeUnit.SECONDS)// 写入超时
                .readTimeout(NetConfig.TIMEOUT, TimeUnit.SECONDS)// 读取超时
                //.cache(new Cache(new File(""), 1024 * 1024 * 100))//缓存文件夹""，缓存大小100Mb
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
