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
        /*Debug.writeLog("leichao", "哈哈你这是哪里错了");
        Debug.delTimeoutLog();*/
    }

    private void request() {
        RetrofitManager
                /*.create(Api.class, "https://kyfw.12306.cn", ScalarsConverterFactory.create())
                .testHttps()*/
                /*.create(Api.class, "http://gank.io/", ScalarsConverterFactory.create())
                .ddd(2,1)*/
                .create(Api.class, "http://test.didi365.com/", ScalarsConverterFactory.create())
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

    @Override
    public void initEvent() {

    }
}
