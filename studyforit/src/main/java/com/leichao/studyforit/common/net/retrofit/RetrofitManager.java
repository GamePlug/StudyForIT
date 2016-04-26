package com.leichao.studyforit.common.net.retrofit;

import com.leichao.studyforit.common.net.okhttp.ClientForRetrofit;
import com.leichao.studyforit.config.NetConfig;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Retrofit的工具类
 * Created by leichao on 2016/4/20.
 */
public class RetrofitManager {

    public static final class Creator {
        private OkHttpClient client;
        private String baseUrl;
        private Converter.Factory factory;


        public Creator client(OkHttpClient client) {
            this.client = client;
            return this;
        }

        public Creator baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Creator factory(Converter.Factory factory) {
            this.factory = factory;
            return this;
        }

        public  <T> T  create(final Class<T> service) {
            if (client == null) {
                client = ClientForRetrofit.getClient();
            }
            if (baseUrl == null) {
                baseUrl = NetConfig.DEFAULT_BASEURL;
            }
            if (factory == null) {
                factory = NetConfig.DEFAULT_FACTORY;
            }
            return new Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addConverterFactory(factory)
                    .build()
                    .create(service);
        }
    }

}
