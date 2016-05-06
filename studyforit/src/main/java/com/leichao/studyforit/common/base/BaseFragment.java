package com.leichao.studyforit.common.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment基类
 * Created by leichao on 2016/3/14.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(inflater, container, savedInstanceState);
        if (view == null) {
            view = defaultView(inflater, container, savedInstanceState);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        defaultData();
        init();
        initData();
        initEvent();
    }

    protected View defaultView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    public abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void initData();

    public abstract void initEvent();

    // 让子Base类添加默认数据
    protected void defaultData() {

    }

    // Fragment的通用设置
    private void init() {

    }

}
