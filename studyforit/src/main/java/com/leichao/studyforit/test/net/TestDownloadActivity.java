package com.leichao.studyforit.test.net;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leichao.studyforit.R;
import com.leichao.studyforit.common.base.BaseActivity;
import com.leichao.studyforit.common.debug.Debug;
import com.leichao.studyforit.common.net.okhttp.ProgressListener;
import com.leichao.studyforit.common.net.retrofit.RetrofitManager;
import com.leichao.studyforit.common.util.FileUtil;
import com.leichao.studyforit.config.DownLoadConfig;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 测试下载,下载使用get和post均可
 * Created by leichao on 2016/4/27.
 */
public class TestDownloadActivity extends BaseActivity {

    private static final String TAG = "TestDownloadActivity";
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
                    testDownload("image03.jpg");
                }
            }
        });
    }

    private void testDownload(final String fileName) {
        result.setText("请求中");
        new RetrofitManager.Creator()
                .downListener(new ProgressListener() {
                    @Override
                    public void onProgress(final long progress, final long total, final boolean done) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                result.setText( (done?"下载完成:":"下载中:") + "--progress:" + progress + "--total:" + total);
                            }
                        });
                    }
                })
                .create(NetApi.class)
                .testGetDownload(fileName)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                        Debug.e(TAG, "开始下载");
                        result.setText("请求成功");
                        // 由于接口方法增加了注释@Streaming，所以流的读取和写入要在子线程中执行,否则会有NetworkOnMainThreadException
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FileUtil.inputStreamToFile(response.body().byteStream(),
                                        new File(DownLoadConfig.DOWN_LOAD_DIR, fileName));
                                Debug.e(TAG, "下载完成");
                                canClick = true;
                            }
                        }).start();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        result.setText("请求失败：" + t.getMessage());
                        canClick = true;
                    }
                });
    }
}
