package com.leichao.studyforit.test.net;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leichao.studyforit.R;
import com.leichao.studyforit.common.base.BaseActivity;
import com.leichao.studyforit.common.debug.Debug;
import com.leichao.studyforit.common.net.okhttp.ProgressListener;
import com.leichao.studyforit.common.net.retrofit.RetrofitManager;
import com.leichao.studyforit.common.net.retrofit.RetrofitUpload;
import com.leichao.studyforit.config.DownLoadConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 测试上传请求
 * Created by leichao on 2016/4/27.
 */
public class TestUploadActivity extends BaseActivity {

    private static final String TAG = "TestUploadActivity";
    private Button start;
    private TextView result;
    private boolean canClick = true;

    @Override
    public void initView() {
        setContentView(R.layout.test_activity_retrofit);
        start = (Button) findViewById(R.id.test_start);
        result = (TextView) findViewById(R.id.test_result);
    }

    @Override
    public void initData() {
        setTitle(getIntent().getStringExtra("des"));
        start.setText("点击开始"+getIntent().getStringExtra("des"));
    }

    @Override
    public void initEvent() {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canClick) {
                    canClick = false;
                    testGet();
                }
            }
        });
    }

    private void testGet() {
        result.setText("上传中");

        List<RetrofitUpload.UploadBean> list = new ArrayList<>();
        /*final File file1 = new File(DownLoadConfig.DOWN_LOAD_DIR, "image02.jpg");
        list.add(new RetrofitUpload.UploadBean(
                "image[]",
                file1,
                new ProgressListener() {
                    @Override
                    public void onProgress(long progress, long total, boolean done) {
                        Debug.e(TAG, file1.getName()+ "progress:" + progress +  "---total:" + total
                                + "---done" + done);
                    }
                }));*/
        final File file2 = new File(DownLoadConfig.DOWN_LOAD_DIR, "image03.jpg");
        list.add(new RetrofitUpload.UploadBean(
                "image[]",
                file2,
                new ProgressListener() {
                    @Override
                    public void onProgress(long progress, long total, boolean done) {
                        Debug.e(TAG, file2.getName() + "progress:" + progress + "---total:" + total
                                + "---done" + done);
                    }
                }));
        Map<String, RequestBody> params = RetrofitUpload.getRequestBodyMap(list);

        new RetrofitManager.Creator()
                .create(NetApi.class)
                .testUpload("didi", params)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Debug.e(TAG, "onResponse:" + response.body());
                        result.setText("上传成功：" + response.body());
                        canClick = true;
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Debug.e(TAG, "onFailure:" + t.getMessage());
                        result.setText("上传失败：" + t.getMessage());
                        canClick = true;
                    }
                });
    }
}
