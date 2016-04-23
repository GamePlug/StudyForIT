package com.leichao.studyforit.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity基类
 * Created by leichao on 2016/4/18.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    public abstract void initView();

    public abstract void initData();

    public abstract void initEvent();

}
