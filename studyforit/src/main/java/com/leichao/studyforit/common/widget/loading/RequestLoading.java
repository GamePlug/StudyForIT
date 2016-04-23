package com.leichao.studyforit.common.widget.loading;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.leichao.studyforit.R;

/**
 *
 * 加载loading动画效果
 *
 */
public class RequestLoading extends CycleLoading{

	Context context;

	public RequestLoading(Context context) {
		this(context, null);
	}

	public RequestLoading(Context context, AttributeSet attrs) {
		super(context,attrs);
		this.context = context;
		initBg();
	}
	
	private void initBg() {
		switcher.setImageResource(R.drawable.loading_m1);
		startBg = context.getResources().getDrawable(R.drawable.loading_m1);
		endBg = context.getResources().getDrawable(R.drawable.loading_m1);
        initResources();
	}

    private void initResources() {
		resources = new Drawable[11];
		resources[0] = context.getResources().getDrawable(R.drawable.loading_m1);
		resources[1] = context.getResources().getDrawable(R.drawable.loading_m2);
		resources[2] = context.getResources().getDrawable(R.drawable.loading_m3);
		resources[3] = context.getResources().getDrawable(R.drawable.loading_m4);
		resources[4] = context.getResources().getDrawable(R.drawable.loading_m5);
		resources[5] = context.getResources().getDrawable(R.drawable.loading_m6);
		resources[6] = context.getResources().getDrawable(R.drawable.loading_m7);
		resources[7] = context.getResources().getDrawable(R.drawable.loading_m8);
		resources[8] = context.getResources().getDrawable(R.drawable.loading_m9);
		resources[9] = context.getResources().getDrawable(R.drawable.loading_m10);
		resources[10] = context.getResources().getDrawable(R.drawable.loading_m11);
    }
}
