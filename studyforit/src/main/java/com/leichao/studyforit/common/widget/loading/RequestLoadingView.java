package com.leichao.studyforit.common.widget.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.leichao.studyforit.R;
import com.leichao.studyforit.common.widget.loading.RequestLoading;

/**
 *
 * Created by leichao on 2016/4/18.
 */
public class RequestLoadingView extends LinearLayout {

    private LinearLayout processLl, reconnectLl;
    private RequestLoading loading;

    private View.OnClickListener reconnectListener;

    public RequestLoadingView(Context context) {
        this(context, null);
    }

    public RequestLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.setVisibility(View.GONE);
        View view = LayoutInflater.from(context).inflate(R.layout.view_request_loading, this);
        processLl = (LinearLayout) view.findViewById(R.id.request_loading_process_ll);
        reconnectLl = (LinearLayout) view.findViewById(R.id.request_loading_reconnect_ll);
        loading = (RequestLoading) view.findViewById(R.id.request_loading_requestloading);

        reconnectLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reconnectListener != null) {
                    reconnectListener.onClick(v);
                    processLl.setVisibility(View.VISIBLE);
                    reconnectLl.setVisibility(View.GONE);
                }
            }
        });
    }

    public void startLoading() {
        this.setVisibility(View.VISIBLE);
        processLl.setVisibility(View.VISIBLE);
        reconnectLl.setVisibility(View.GONE);
        loading.start();
    }

    public void stopLoading() {
        this.setVisibility(View.GONE);
        loading.stop();
    }

    public void showReconnect(){
        processLl.setVisibility(View.GONE);
        reconnectLl.setVisibility(View.VISIBLE);
    }

    public void setReconnectListener(View.OnClickListener reconnectListener) {
        this.reconnectListener = reconnectListener;
    }
}
