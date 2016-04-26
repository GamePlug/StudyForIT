package com.leichao.studyforit.common.net.okhttp;

import com.leichao.studyforit.common.debug.Debug;
import com.leichao.studyforit.config.NetConfig;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 创建Retrofit的OkHttpClient
 * Created by leichao on 2016/4/21.
 */
public class ClientForRetrofit {

    public static final String APPLICATION_FORM_URL = "application/x-www-form-urlencoded;charset=UTF-8";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static OkHttpClient client;
    private ProgressListener listener;

    public static OkHttpClient getClient() {
        if (client == null) {
            client = new ClientForRetrofit(null).get();
        }
        return client;
    }

    public static OkHttpClient newClient(ProgressListener listener) {
        return new ClientForRetrofit(listener).get();
    }

    private ClientForRetrofit(ProgressListener listener) {
        this.listener = listener;
    }

    /**
     * 设置Retrofit所用OkHttp的一些参数
     */
    private OkHttpClient get() {

        return OkHttpImpl.getUnsafeBuilder()
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
                request = normalGet(request);
                break;
            case "POST":
                MediaType contentType = request.body().contentType();
                if (contentType != null && contentType.toString().contains(MULTIPART_FORM_DATA)) {
                    request = uploadPost(request);
                } else {
                    request = normalPost(request);
                }
                break;
        }
        Response response = chain.proceed(request);
        if (listener != null) {
            response = response.newBuilder()
                    .body(new ProgressResponseBody(response.body(), new ProgressListener() {
                        @Override
                        public void onProgress(long progress, long total, boolean done) {
                            listener.onProgress(progress, total, done);
                        }
                    }))
                    .build();
        }
        return response;
    }

    /**
     * 普通get请求统一处理的操作
     */
    private Request normalGet(Request request) {
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("username", "leichao")
                .addQueryParameter("password", "normalGet")
                .build();
        request = request.newBuilder()
                //.header("Accept", "application/json")
                //.header("Authorization", "auth-token")
                .url(url)
                .build();
        Debug.e("retrofit", "get_url:" + request.url());
        return request;
    }

    /**
     * 普通post请求统一处理的操作
     */
    private Request normalPost(Request request) {
        RequestBody formBody = new FormBody.Builder()
                .add("username", "leichao")
                .add("password", "normalPost")
                .build();
        String postBodyString = OkHttpImpl.bodyToString(request.body());
        postBodyString += ((postBodyString.length() > 0) ? "&" : "") + OkHttpImpl.bodyToString(formBody);
        request = request.newBuilder()
                .post(RequestBody.create(
                        MediaType.parse(APPLICATION_FORM_URL),
                        postBodyString))
                .build();
        Debug.e("retrofit", "post_url:" + request.url() + "?" + postBodyString);
        return request;
    }

    /**
     * 文件上传统一处理的操作
     */
    private Request uploadPost(Request request) {
        if ( !(request.body() instanceof MultipartBody) ) {
            return request;
        }
        List<MultipartBody.Part> parts = ((MultipartBody) request.body()).parts();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", "leichao")
                .addFormDataPart("password", "uploadPost");
        for(MultipartBody.Part part : parts) {
            builder.addPart(part);
        }
        MultipartBody multiBody = builder.build();
        request = request.newBuilder()
                .post(multiBody)
                .build();

        String bodystring = "";
        for(MultipartBody.Part part : multiBody.parts()) {
            try {
                String key, value;
                Field field = MultipartBody.Part.class.getDeclaredField("headers");
                field.setAccessible(true);//访问私有必须调用
                Headers headers = (Headers) field.get(part);
                String cd = headers.get("Content-Disposition");
                key = cd.split("\"").length >= 2 ? cd.split("\"")[1] : "";
                if (headers.get("Content-Disposition").contains("filename")) {
                    value = cd.split("\"").length >= 4 ? cd.split("\"")[3] : "";
                } else {
                    Field field2 = MultipartBody.Part.class.getDeclaredField("body");
                    field2.setAccessible(true);
                    RequestBody body = (RequestBody)field2.get(part);
                    value = OkHttpImpl.bodyToString(body);
                }
                bodystring += (bodystring.equals("") ? "" : "&") + key + "=" + value;
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        Debug.e("retrofit", "upload_post_url:" + request.url() + "?" + bodystring);
        return request;
    }

}
