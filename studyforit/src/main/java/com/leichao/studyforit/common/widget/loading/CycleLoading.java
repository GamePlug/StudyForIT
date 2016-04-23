package com.leichao.studyforit.common.widget.loading;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 *
 *循环显示图片轮播控件，用于播放语音和加载loading等动画效果
 *
*/
public class CycleLoading extends BaseLoading{

	TaskThread thread;
	ImageView switcher;
	Drawable startBg;
	Drawable endBg;
	Drawable[] resources=null;
	boolean isStoped = false;
	boolean isStarted=false;
	private int index=-1;
	
	public CycleLoading(Context context) {
		this(context, null);
	}
	
	public CycleLoading(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init(){
		switcher=new ImageView(getViewContext());
		switcher.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		switcher.setPadding(0, 0, 0, 0);
		switcher.setImageDrawable(startBg);
		addView(switcher);
	}

	public void setstartDrawable(int resId){
		Drawable start=getResources().getDrawable(resId);
		if(start==null){
			throw new RuntimeException("not found resource for this resId");
		}
		this.startBg=start;
		switcher.setImageDrawable(startBg);
	}
	
	public void setstartDrawable(Drawable drawable){
		if(drawable==null){
			throw new RuntimeException("not found resource for this resId");
		}
		this.startBg=drawable;
		switcher.setImageDrawable(startBg);
	}
	
	public Drawable getstartDrawable(){
		return this.startBg;
	}
	
	public void setendDrawable(int resId){
		Drawable end=getResources().getDrawable(resId);
		if(end==null){
			throw new RuntimeException("not found resource for this resId");
		}
		this.endBg=end;
	}
	
	public void setendDrawable(Drawable drawable){
		if(drawable==null){
			throw new RuntimeException("not found resource for this resId");
		}
		this.endBg=drawable;
	}
	
	public Drawable getendDrawable(){
		return this.endBg;
	}

	public void setDrawables(Drawable[] resource){
		this.resources=resource;
	}

	public Drawable[] getDrawables(){
		return this.resources;
	}

	@Override
	public void start() {
		if(isStarted){
			return ;
		}
		isStoped =false;
		switcher.setImageDrawable(startBg);
		if(this.resources==null){
			throw new NullPointerException("resource array is null");
		}
		thread=new TaskThread();
		thread.start();
		isStarted=true;
	}

	@Override
	public void stop() {
		isStoped =true;
		if(thread!=null){
			thread.interrupt();
			thread=null;
		}
		
	}

	@Override
	protected void handle(Message msg) {
		if(!isStoped){
			if(msg.what==1){
				switcher.setImageDrawable(endBg);
				return ;
			}
			if(index==this.resources.length-1){
				index=-1;
			}
			index++;
			switcher.setImageDrawable(resources[index]);
		}
		else{
			switcher.setImageDrawable(endBg);
		}
	}
	
	class TaskThread extends Thread{
		@Override
		public void run() {
			super.run();
			while(!isStoped){
				try {
					Thread.sleep(getDuration());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0);
			}
			isStarted=false;
			handler.sendEmptyMessage(1);
		}
	}

}
