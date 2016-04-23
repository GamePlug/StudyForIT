package com.leichao.studyforit.common.net.retrofit;

import com.leichao.studyforit.common.net.okhttp.ClientForRetrofit;
import com.leichao.studyforit.config.NetConfig;

import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Retrofit的工具类
 * Created by leichao on 2016/4/20.
 */
public class RetrofitManager {

    /**
     * 获得网络请求接口
     * @param service Retrofit网络请求需要的interface
     */
    public static  <T> T create(final Class<T> service) {
        return RetrofitManager
                .getRetrofitInstance(NetConfig.DEFAULT_BASEURL, NetConfig.DEFAULT_FACTORY)
                .create(service);
    }

    /**
     * 获得网络请求接口
     * @param service Retrofit网络请求需要的interface
     * @param baseUrl 域名
     */
    public static  <T> T create(final Class<T> service, String baseUrl) {
        return RetrofitManager
                .getRetrofitInstance(baseUrl, NetConfig.DEFAULT_FACTORY)
                .create(service);
    }

    /**
     * 获得网络请求接口
     * @param service Retrofit网络请求需要的interface
     * @param factory 解析返回数据的工厂类
     */
    public static  <T> T create(final Class<T> service, Converter.Factory factory) {
        return RetrofitManager
                .getRetrofitInstance(NetConfig.DEFAULT_BASEURL, factory)
                .create(service);
    }

    /**
     * 获得网络请求接口
     * @param service Retrofit网络请求需要的interface
     * @param baseUrl 域名
     * @param factory 解析返回数据的工厂类
     */
    public static  <T> T create(final Class<T> service, String baseUrl, Converter.Factory factory) {
        return RetrofitManager
                .getRetrofitInstance(baseUrl, factory)
                .create(service);
    }

    /**
     * 设置retrofit的一些参数
     * @param baseUrl 域名
     * @param factory 解析返回数据的工厂类
     */
    private static Retrofit getRetrofitInstance(String baseUrl, Converter.Factory factory) {
        return new Retrofit.Builder()
                .client(ClientForRetrofit.getClient())
                .baseUrl(baseUrl)
                .addConverterFactory(factory)
                .build();
    }

}
