package com.leichao.studyforit.common.net.okhttp;

import com.leichao.studyforit.common.net.https.HttpsSSLSocketFactory;
import com.leichao.studyforit.config.NetConfig;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.internal.Util;

/**
 * 获取设置了默认参数的OkHttpClient.Builder
 * Created by leichao on 2016/4/23.
 */
public class ClientBuilder {

    private static OkHttpClient.Builder builder;

    /**
     * 设置okhttp的一些参数, 并返回一个OkHttpClient.Builder
     * 是否验证https的证书，需调用sslSocketFactory方法
     * 目前getSSLSocketFactory三个参数都为null,表示跳过所有HTTPS证书验证(详见HttpsSSLSocketFactory类)
     */
    public static OkHttpClient.Builder getBuilder() {
        if (builder == null) {
            builder = new OkHttpClient.Builder()
                    .sslSocketFactory(HttpsSSLSocketFactory.getSSLSocketFactory(null, null, null))
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .connectTimeout(NetConfig.TIMEOUT, TimeUnit.SECONDS)// 链接超时
                    .writeTimeout(NetConfig.TIMEOUT, TimeUnit.SECONDS)// 写入超时
                    .readTimeout(NetConfig.TIMEOUT, TimeUnit.SECONDS);// 读取超时
                    //.cache(new Cache(new File(""), 1024 * 1024 * 100))//缓存文件夹""，缓存大小100Mb
                    //.protocols(Util.immutableList(Protocol.HTTP_1_1))//设置支持的协议,默认有HTTP_2, SPDY_3, HTTP_1_1
        }
        return builder;
    }

    private ClientBuilder() {

    }

}
