package com.leichao.studyforit.common.base;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.leichao.studyforit.R;
import com.leichao.studyforit.common.widget.loading.RequestLoadingView;


/**
 * Activity基类
 * Created by leichao on 2016/4/18.
 */
public abstract class BaseLoadingActivity extends BaseActivity {

    protected FrameLayout layout;
    private RequestLoadingView loading;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base_loading);
        layout = (FrameLayout) findViewById(R.id.base_framelayout);
        layout.addView(LayoutInflater.from(this).inflate(layoutResID, null), 0);
        loading = (RequestLoadingView) findViewById(R.id.base_loading_requestloadingview);
    }

    public void startLoading() {
        loading.startLoading();
    }

    public void stopLoading() {
        loading.stopLoading();
    }

    public void showReconnect(){
        loading.showReconnect();
    }

    public void setReconnectListener(View.OnClickListener reconnectListener) {
        loading.setReconnectListener(reconnectListener);
    }
}
