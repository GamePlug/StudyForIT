package com.leichao.studyforit.config;

import com.bumptech.glide.load.DecodeFormat;

import java.io.File;

import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 网络访问相关配置
 * Created by leichao on 2016/4/21.
 */
public class NetConfig {

    /**
     * Retrofit默认网络请求的地址
     * Retrofit默认网络请求的数据解析工厂
     */
    public static final String DEFAULT_BASEURL = "http://www.didi365.com/";
    public static final Converter.Factory DEFAULT_FACTORY = ScalarsConverterFactory.create();


    /**
     * Glide图片缓存的路径
     * Glide图片缓存的大小(100Mb)
     * Glide图片缓存的解码格式(PREFER_RGB_565、PREFER_ARGB_8888)
     */
    public static final String GLIDE_CACHE_DIR = CommonConfig.PUBLIC_DIR + File.separator + "glide_cache";
    public static final int GLIDE_CACHE_SIZE = 100*1024*1024;
    public static final DecodeFormat GLIDE_DECODE_FORMAT = DecodeFormat.PREFER_RGB_565;


    /**
     * OkHttp网络请求的超时时间(30秒)
     */
    public static final long TIMEOUT = 30;

}
