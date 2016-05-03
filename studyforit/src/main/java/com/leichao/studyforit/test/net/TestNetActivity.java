package com.leichao.studyforit.test.net;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.leichao.studyforit.R;
import com.leichao.studyforit.common.base.BaseActivity;
import com.leichao.studyforit.common.widget.titlebar.ToolbarManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 主Activity界面
 * Created by leichao on 2016/4/27.
 */
public class TestNetActivity extends BaseActivity {

    private ListView listView;
    private List<MyBean> listBean;

    @Override
    public void initView() {
        setContentView(R.layout.test_activity_net);
        ToolbarManager.setToolbar(this, "网络框架测试");
        listView = (ListView) findViewById(R.id.main_listview);
    }

    @Override
    public void initData() {
        listBean = new ArrayList<>();

        listBean.add(new MyBean(TestGetActivity.class, "GET请求"));
        listBean.add(new MyBean(TestPostActivity.class, "POST请求"));
        listBean.add(new MyBean(TestDownloadActivity.class, "下载请求"));
        listBean.add(new MyBean(TestUploadActivity.class, "上传请求"));
        listBean.add(new MyBean(TestGsonActivity.class, "自动解析"));
        listBean.add(new MyBean(TestHttpsActivity.class, "Https请求"));

        listView.setAdapter(new MyAdapter());
    }

    @Override
    public void initEvent() {

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listBean == null ? 0 : listBean.size();
        }

        @Override
        public Object getItem(int position) {
            return listBean == null ? null : listBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final MyBean bean = listBean.get(position);
            MyHolder holder = null;
            if (convertView == null) {
                holder = new MyHolder();
                convertView = LayoutInflater.from(TestNetActivity.this).inflate(R.layout.test_item_net, parent, false);
                holder.textView = (TextView) convertView.findViewById(R.id.main_item_text);
                convertView.setTag(holder);
            } else {
                holder = (MyHolder) convertView.getTag();
            }

            holder.textView.setText(bean.des);

            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TestNetActivity.this, bean.clazz);
                    intent.putExtra("des", bean.des);
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    class MyHolder {
        TextView textView;
    }

    class MyBean {
        Class clazz;
        String des;
        public MyBean(Class clazz, String des) {
            this.clazz = clazz;
            this.des = des;
        }
    }
}
