package com.leichao.studyforit.test;

import com.leichao.studyforit.R;
import com.leichao.studyforit.base.BaseActivity;
import com.leichao.studyforit.common.debug.Debug;
import com.leichao.studyforit.common.net.retrofit.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * Created by leichao on 2016/4/21.
 */
public class TestOkhttpActivity extends BaseActivity {
    @Override
    public void initView() {
        setContentView(R.layout.activity_base_loading);
    }

    @Override
    public void initData() {
        request();
        request2();
        request3();
        /*Debug.writeLog("leichao", "哈哈你这是哪里错了");
        Debug.delTimeoutLog();*/
    }

    private void request() {
        new RetrofitManager
                /*.create(Api.class, "https://kyfw.12306.cn", ScalarsConverterFactory.create())
                .testHttps()*/
                /*.create(Api.class, "http://gank.io/", ScalarsConverterFactory.create())
                .ddd(2,1)*/
                .Creator()
                .baseUrl("http://test.didi365.com/")
                .factory(ScalarsConverterFactory.create())
                .create(Api.class)
                .didi()
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Debug.e("leilei", "onResponse"+response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Debug.e("leilei", "onFailure"+t.getMessage());
                    }
                });
    }

    private void request2() {
        new RetrofitManager
                .Creator()
                .baseUrl("https://kyfw.12306.cn")
                .factory(ScalarsConverterFactory.create())
                .create(Api.class)
                .testHttps()
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Debug.e("leilei", "onResponse"+response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Debug.e("leilei", "onFailure"+t.getMessage());
                    }
                });
    }

    private void request3() {
        new RetrofitManager.Creator()
                .baseUrl("http://gank.io/")
                .factory(ScalarsConverterFactory.create())
                .create(Api.class)
                .ddd(2,1)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Debug.e("leilei", "onResponse"+response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Debug.e("leilei", "onFailure"+t.getMessage());
                    }
                });
    }

    @Override
    public void initEvent() {

    }
}
