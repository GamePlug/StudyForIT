package com.leichao.studyforit.appmode.tabhome;

import android.content.Intent;
import android.os.Handler;

import com.leichao.studyforit.R;
import com.leichao.studyforit.common.base.BaseActivity;

/**
 *
 * Created by leichao on 2016/4/19.
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    public void initView() {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainTabActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    @Override
    public void initEvent() {

    }
}
