package com.leichao.studyforit.common.widget.loading;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 *
 * 循环显示图片轮播控件，用于播放语音和加载loading等动画效果
 *
*/
public abstract class BaseLoading extends LinearLayout {

	private static final int DEFAULT_DURATION_TIME = 100; // 300ms

	private static final int MAX_DURATION_TIME = 1000; // 1000ms

	private int duration = DEFAULT_DURATION_TIME;

	private Context context;

	public BaseLoading(Context context) {
		this(context, null);
	}

	public BaseLoading(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void setDuration(int duration) {
		this.duration = duration;
		if (this.duration < 0 || this.duration > MAX_DURATION_TIME) {
			this.duration = DEFAULT_DURATION_TIME;
		}
	}

	public int getDuration() {
		return this.duration;
	}

	public Context getViewContext() {
		return this.context;
	}
	
	protected abstract void start();

	protected abstract void stop();

	protected abstract void handle(Message msg);

	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			handle(msg);
		}
	};
	
}
