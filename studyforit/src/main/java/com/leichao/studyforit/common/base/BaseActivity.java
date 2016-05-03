package com.leichao.studyforit.common.base;

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
        init();
        initData();
        initEvent();
    }

    public abstract void initView();

    public abstract void initData();

    public abstract void initEvent();

    private void init() {
        // TODO Activity的通用设置

        defaultData();
    }

    // 让子Base类添加默认数据
    protected void defaultData() {

    }

}
