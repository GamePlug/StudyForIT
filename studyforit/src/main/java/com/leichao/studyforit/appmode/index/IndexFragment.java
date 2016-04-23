package com.leichao.studyforit.appmode.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leichao.studyforit.R;
import com.leichao.studyforit.base.BaseLoadingFragment;

/**
 *
 * Created by leichao on 2016/4/18.
 */
public class IndexFragment extends BaseLoadingFragment {

    private View view;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_index, container, false);
        return view;
    }

    @Override
    public void initData() {
        startLoading();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
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
                        getActivity().runOnUiThread(new Runnable() {
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
