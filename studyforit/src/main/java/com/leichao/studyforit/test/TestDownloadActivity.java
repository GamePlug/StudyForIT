package com.leichao.studyforit.test;

import android.util.Log;

import com.leichao.studyforit.R;
import com.leichao.studyforit.base.BaseActivity;
import com.leichao.studyforit.common.debug.Debug;
import com.leichao.studyforit.common.net.okhttp.ProgressListener;
import com.leichao.studyforit.common.net.okhttp.ProgressResponseBody;
import com.leichao.studyforit.common.net.retrofit.RetrofitManager;
import com.leichao.studyforit.common.util.FileUtil;
import com.leichao.studyforit.config.DownLoadConfig;

import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Created by leichao on 2016/4/26.
 */
public class TestDownloadActivity extends BaseActivity {
    @Override
    public void initView() {
        setContentView(R.layout.test_activity_upload);
    }

    @Override
    public void initData() {
        //getImageFile("image03.jpg");
        //getImageFileIng("image03.jpg");
        //responseImageFile("image03.jpg");
        responseImageFile("ShenMaDiDiClient2.0.91.0.apk");
    }

    @Override
    public void initEvent() {

    }

    private void getImageFile(final String fileName) {
        Api api = new RetrofitManager.Creator().create(Api.class);
        Call<ResponseBody> call = api.getImageFile(fileName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                FileUtil.inputStreamToFile(response.body().byteStream(),
                        new File(DownLoadConfig.DOWN_LOAD_DIR, fileName));
                Log.e("leilei","success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("leilei","failure");
            }
        });
    }


    private void getImageFileIng(final String fileName) {
        new RetrofitManager.Creator()
                .client(
                        new OkHttpClient.Builder()
                        .addNetworkInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                okhttp3.Response orginalResponse = chain.proceed(chain.request());

                                return orginalResponse.newBuilder()
                                        .body(new ProgressResponseBody(orginalResponse.body(), new ProgressListener() {
                                            @Override
                                            public void onProgress(long progress, long total, boolean done) {
                                                Log.e("leilei", "onProgress: "+ "progress:" + progress +  "---total:" + total
                                                        + "---done" + done);
                                            }
                                        }))
                                        .build();
                            }
                        })
                        .build()
                )
                .create(Api.class)
                .getImageFile(fileName)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        FileUtil.inputStreamToFile(response.body().byteStream(),
                                new File(DownLoadConfig.DOWN_LOAD_DIR, fileName));
                        Log.e("leilei","success");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("leilei","failure");
                    }
                });
    }

    private void responseImageFile(final String fileName) {
        new RetrofitManager.Creator()
                .client(
                        new OkHttpClient.Builder()
                                .addNetworkInterceptor(new Interceptor() {
                                    @Override
                                    public okhttp3.Response intercept(Chain chain) throws IOException {
                                        okhttp3.Response orginalResponse = chain.proceed(chain.request());

                                        return orginalResponse.newBuilder()
                                                .body(new ProgressResponseBody(orginalResponse.body(), new ProgressListener() {
                                                    @Override
                                                    public void onProgress(long progress, long total, boolean done) {
                                                        Debug.e("leilei", "onProgress: "+ "progress:" + progress +  "---total:" + total
                                                                + "---done" + done);
                                                    }
                                                }))
                                                .build();
                                    }
                                })
                                .build()
                )
                .create(Api.class)
                //.getImageFile(fileName)
                .getApk(fileName)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                        Log.e("leilei","开始写入");
                        // 由于接口方法增加了注释@Streaming，所有要在子线程中执行,否则会有NetworkOnMainThreadException
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FileUtil.inputStreamToFile(response.body().byteStream(),
                                new File(DownLoadConfig.DOWN_LOAD_DIR, fileName));
                                Log.e("leilei","写入完成");
                            }
                        }).start();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("leilei","failure");
                    }
                });
    }

}
