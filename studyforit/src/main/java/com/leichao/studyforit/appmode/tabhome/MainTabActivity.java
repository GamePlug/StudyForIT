package com.leichao.studyforit.appmode.tabhome;

import android.widget.TextView;

import com.leichao.studyforit.R;
import com.leichao.studyforit.common.base.BaseActivity;
import com.leichao.studyforit.appmode.discover.DiscoverFragment;
import com.leichao.studyforit.appmode.index.IndexFragment;
import com.leichao.studyforit.appmode.me.MeFragment;
import com.leichao.studyforit.appmode.message.MessageFragment;
import com.leichao.studyforit.common.widget.titlebar.ToolbarManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用主界面
 * Created by leichao on 2016/3/14.
 */
public class MainTabActivity extends BaseActivity {

    private MainTabView tabView;
    private TextView title;

    private static MainTabActivity instance;

    @Override
    public void initView() {
        setContentView(R.layout.activity_main_tab);
        ToolbarManager.setToolbar(this, -1, getString(R.string.tab_index), null);
        title = ToolbarManager.getTitleTv(this);
        tabView = (MainTabView) findViewById(R.id.main_tab_bottom);
    }

    @Override
    public void initData() {
        instance = this;
        initTab();
    }

    private void initTab() {
        List<MainTabBean> listBean = new ArrayList<>();
        listBean.add(new MainTabBean(new IndexFragment(), R.drawable.tab_index, R.string.tab_index));
        listBean.add(new MainTabBean(new DiscoverFragment(), R.drawable.tab_discover, R.string.tab_discover));
        listBean.add(new MainTabBean(new MessageFragment(), R.drawable.tab_message, R.string.tab_message));
        listBean.add(new MainTabBean(new MeFragment(), R.drawable.tab_me, R.string.tab_me));
        tabView.init(getSupportFragmentManager(), listBean);
    }

    @Override
    public void initEvent() {
        tabView.setOnTabListener(new MainTabView.OnTabListener() {
            @Override
            public void onTabSelected(int position, int lastPosition) {
                switch (position) {
                    case 0:
                        title.setText(getString(R.string.tab_index));
                        break;
                    case 1:
                        title.setText(getString(R.string.tab_discover));
                        break;
                    case 2:
                        title.setText(getString(R.string.tab_message));
                        break;
                    case 3:
                        title.setText(getString(R.string.tab_me));
                        break;
                }
            }
        });
    }

    public static MainTabActivity getInstance() {
        return instance;
    }

    public void setCurrentTab(int position) {
        tabView.setCurrentTab(position);
    }

    public void setUnreadNum(int position, int count) {
        tabView.setUnreadNum(position, count);
    }
}
