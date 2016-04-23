package com.leichao.studyforit.appmode.tabhome;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leichao.studyforit.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by leichao on 2016/4/19.
 */
public class MainTabView extends LinearLayout {

    private Context context;
    private FragmentManager fragmentManager;
    private List<MainTabBean> listBean;
    private List<ImageView> images;
    private List<TextView> texts;
    private List<TextView> unreads;
    private OnTabListener onTabListener;
    private int currentPosition;// 当前tab的position

    // 是否预加载所有fragment
    private static final boolean isPreLoadFragment = false;

    public MainTabView(Context context) {
        this(context, null);
    }

    public MainTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(FragmentManager fragmentManager, List<MainTabBean> listBean) {
        this.fragmentManager = fragmentManager;
        this.listBean = listBean;
        images = new ArrayList<>();
        texts = new ArrayList<>();
        unreads = new ArrayList<>();
        initTab();
    }

    private void initTab() {
        if(listBean == null || listBean.size() == 0) {
            return;
        }
        for(int i = 0; i < listBean.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_main_tab, this, false);
            addView(view);
            RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.item_tab_re);
            ImageView imageView = (ImageView) view.findViewById(R.id.item_tab_iv);
            TextView textView = (TextView) view.findViewById(R.id.item_tab_tv);
            TextView unreadNum = (TextView) view.findViewById(R.id.item_tab_unread_number);

            imageView.setImageResource(listBean.get(i).getIcoResource());
            textView.setText(listBean.get(i).getTextResource());
            images.add(imageView);
            texts.add(textView);
            unreads.add(unreadNum);

            final int position = i;
            relativeLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrentTab(position);
                }
            });
        }

        images.get(currentPosition).setSelected(true);
        texts.get(currentPosition).setSelected(true);
        if(isPreLoadFragment) {
            // 添加显示第一个fragment,预加载所有fragment
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            for(MainTabBean bean : listBean) {
                transaction.add(R.id.fragment_container, bean.getFragment())
                        .hide(bean.getFragment());
            }
            transaction.show(listBean.get(currentPosition).getFragment()).commit();
        } else {
            // 添加显示第一个fragment,不预加载其他fragment
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, listBean.get(currentPosition).getFragment())
                    .commit();
        }
    }

    public void setCurrentTab(int position) {
        if(position < 0 || position >= listBean.size()) {
            position = 0;
        }
        if (position != currentPosition) {
            images.get(currentPosition).setSelected(false);
            images.get(position).setSelected(true);
            texts.get(currentPosition).setSelected(false);
            texts.get(position).setSelected(true);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(listBean.get(currentPosition).getFragment());
            Fragment fragment = listBean.get(position).getFragment();
            if (!fragment.isAdded()) {
                transaction.add(R.id.fragment_container, fragment);
            }
            transaction.show(fragment).commit();

            if(onTabListener != null) {
                onTabListener.onTabSelected(position, currentPosition);
            }
            currentPosition = position;
        }
    }

    public void setUnreadNum(int position, int count) {
        TextView unreadNum = unreads.get(position);
        if(count <= 0) {
            unreadNum.setVisibility(View.GONE);
        } else  {
            unreadNum.setVisibility(View.VISIBLE);
            unreadNum.setText(String.valueOf(count));
        }
    }

    public void setOnTabListener(OnTabListener onTabListener) {
        this.onTabListener = onTabListener;
    }

    public interface OnTabListener {
        void onTabSelected(int position, int lastPosition);
    }
}
