package com.leichao.studyforit.test;

import android.view.View;

import com.leichao.studyforit.R;
import com.leichao.studyforit.base.BaseLoadingActivity;
import com.leichao.studyforit.common.widget.loading.DialogLoading;
import com.leichao.studyforit.common.widget.loading.RequestLoading;

/**
 *
 * Created by leichao on 2016/4/18.
 */
public class TestLoadingActivity extends BaseLoadingActivity {

    private RequestLoading loading;
    private DialogLoading dialogLoading;

    @Override
    public void initView() {
        setContentView(R.layout.test_activity_loading);
        loading = (RequestLoading) findViewById(R.id.test_loading);
    }

    @Override
    public void initData() {
        loading.start();
        //loading.stop();

        dialogLoading = new DialogLoading(this, "加载咯");
        dialogLoading.show();
        dialogLoading.dismiss();

        startLoading();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //stopLoading();
                        showReconnect();
                    }
                });
            }
        }).start();
    }

    @Override
    public void initEvent() {
        setReconnectListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stopLoading();
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
