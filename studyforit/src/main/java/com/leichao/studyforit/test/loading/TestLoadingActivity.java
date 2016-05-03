package com.leichao.studyforit.test.loading;

import android.view.View;

import com.leichao.studyforit.R;
import com.leichao.studyforit.common.base.BaseLoadingActivity;
import com.leichao.studyforit.common.widget.loading.DialogLoading;
import com.leichao.studyforit.common.widget.loading.RequestLoading;
import com.leichao.studyforit.common.widget.titlebar.ToolbarManager;

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
        /*ToolbarManager.setToolbar(this, R.drawable.toolbar_back, R.string.app_name, -1, new ToolbarManager.OnToolbarListener() {
            @Override
            public void onLeftIconClicked() {
                finish();
            }

            @Override
            public boolean onMenuItemClick() {
                return false;
            }
        });*/
        ToolbarManager.setToolbar(this, "加载转圈");
        //ToolbarManager.setBackgroudColor(this, R.color.black);
        //ToolbarManager.setTitleColor(this, R.color.white);
        /*ToolbarManager.setToolbar(this, R.string.app_name, new OnLeftIconListener() {
            @Override
            public void onLeftIconClicked() {
                Toast.makeText(TestLoadingActivity.this, "你好啊", Toast.LENGTH_SHORT).show();
            }
        });*/
        loading = (RequestLoading) findViewById(R.id.test_loading);
    }

    @Override
    public void initData() {
        loading.start();
        //loading.stop();

        dialogLoading = new DialogLoading(this, "加载咯");
        dialogLoading.show();
        //dialogLoading.dismiss();

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
