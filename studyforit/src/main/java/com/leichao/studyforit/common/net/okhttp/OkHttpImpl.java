package com.leichao.studyforit.common.net.okhttp;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 *
 * Created by leichao on 2016/4/23.
 */
public class OkHttpImpl {

    /**
     * RequestBody将转换成对应的字符串
     */
    public static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }

    /**
     * 设置okhttp的一些参数, 并返回一个不安全的OkHttpClient.Builder(跳过所有HTTPS证书验证)
     */
    public static OkHttpClient.Builder getUnsafeBuilder() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // OkHttpClient需采用Builder模式创建
            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory)
                    .protocols(Arrays.asList(Protocol.HTTP_1_1))
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });

        } catch (Exception e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
            // 若创建不安全的OkHttpClient.Builder失败，则返回正常的OkHttpClient.Builder
            return new OkHttpClient.Builder();
        }
    }

}
