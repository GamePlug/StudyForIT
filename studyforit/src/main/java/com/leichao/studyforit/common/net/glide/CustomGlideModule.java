package com.leichao.studyforit.common.net.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.leichao.studyforit.common.debug.Debug;
import com.leichao.studyforit.config.NetConfig;

import java.io.InputStream;

/**
 * 自定义GlideModule
 * 参考资料http://mrfu.me/2016/02/27/Glide_Getting_Started/
 * 需要在AndroidManifest.xml中application标签下增加
 *  <meta-data
 *      android:name="com.leichao.studyforit.common.net.glide.CustomGlideModule"
 *      android:value="GlideModule" />
 * Created by leichao on 2016/4/20.
 */
public class CustomGlideModule implements GlideModule {

    private static final String TAG = "CustomGlideModule";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        Debug.e(TAG, "applyOptions");
        //builder.setMemoryCache(MemoryCache memoryCache);
        //builder.setBitmapPool(BitmapPool bitmapPool);
        builder.setDiskCache(new DiskLruCacheFactory(NetConfig.GLIDE_CACHE_DIR, NetConfig.GLIDE_CACHE_SIZE));
        //builder.setDiskCacheService(ExecutorService service);
        //builder.setResizeService(ExecutorService service);
        builder.setDecodeFormat(NetConfig.GLIDE_DECODE_FORMAT);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        Debug.e(TAG, "registerComponents");
        /**
         * 使用OkHttp加载图片,需要导入包：
         * compile 'com.github.bumptech.glide:okhttp3-integration:1.4.0@jar'
         * 以及okhttp包
         */
        //glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
        glide.register(GlideUrl.class, InputStream.class, new OkHttpModeLoader.Factory());

        /**
         * 使用Volley加载图片,需要导入包：
         * compile 'com.github.bumptech.glide:volley-integration:1.4.0@jar'
         * 以及volley包
         */
        //glide.register(GlideUrl.class, InputStream.class, new VolleyUrlLoader.Factory(context));
    }
}
