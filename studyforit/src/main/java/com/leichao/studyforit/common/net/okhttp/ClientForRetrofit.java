package com.leichao.studyforit.common.net.okhttp;

import com.leichao.studyforit.common.debug.Debug;
import com.leichao.studyforit.config.NetConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 创建Retrofit的OkHttpClient
 * Created by leichao on 2016/4/21.
 */
public class ClientForRetrofit {

    private static OkHttpClient client;

    public static OkHttpClient getClient() {
        if (client == null) {
            //client = new ClientForRetrofit().build(new OkHttpClient.Builder());
            client = new ClientForRetrofit().build(OkHttpImpl.getUnsafeBuilder());
        }
        return client;
    }

    /**
     * 设置Retrofit所用OkHttp的一些参数
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
                        return interceptForRetrofit(chain);
                    }
                })
                .build();
    }

    /**
     * 控制Retrofit的拦截操作
     */
    private Response interceptForRetrofit(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        switch (request.method()) {
            case "GET":
                HttpUrl url = request.url().newBuilder()
                        .addQueryParameter("username", "leichao")
                        .addQueryParameter("password", "getme")
                        .build();
                request = request.newBuilder()
                        .url(url)
                        .build();
                Debug.e("retrofit", "url:" + request.url());
                break;

            case "POST":
                RequestBody formBody = new FormBody.Builder()
                        .add("username", "leichao")
                        .add("password", "postme")
                        .build();
                String postBodyString = OkHttpImpl.bodyToString(request.body());
                postBodyString += ((postBodyString.length() > 0) ? "&" : "") + OkHttpImpl.bodyToString(formBody);
                request = request.newBuilder()
                        .post(RequestBody.create(
                                MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"),
                                postBodyString))
                        .build();
                Debug.e("retrofit", "url:" + request.url() + "?" + postBodyString);
                break;

            default:
                /*request = request.newBuilder()
                        .header("Accept", "application/json")
                        .header("Authorization", "auth-token")
                        .method(request.method(), request.body())
                        .build();*/
                break;
        }
        return chain.proceed(request);
    }

}
