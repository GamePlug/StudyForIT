package com.leichao.studyforit.common.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.leichao.studyforit.R;
import com.leichao.studyforit.common.widget.loading.RequestLoadingView;

/**
 * Fragment基类
 * Created by leichao on 2016/3/14.
 */
public abstract class BaseLoadingFragment extends BaseFragment {

    protected FrameLayout layout;
    private RequestLoadingView loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = (FrameLayout) inflater.inflate(R.layout.fragment_base_loading, container, false);
            loading = (RequestLoadingView) layout.findViewById(R.id.base_loading_requestloadingview);
            View view = initView(inflater, layout, savedInstanceState);
            layout.addView(view, 0);
        } else {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
        return layout;
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
