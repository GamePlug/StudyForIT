package com.leichao.studyforit.test.net;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.leichao.studyforit.R;
import com.leichao.studyforit.common.base.BaseActivity;
import com.leichao.studyforit.common.debug.Debug;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 将数据直接解析为java对象
 * Created by leichao on 2016/4/28.
 */
public class TestGsonActivity extends BaseActivity {

    private static final String TAG = "TestGsonActivity";
    private ListView listView;
    private MyAdapter adapter;
    private List<NoteBean.DataBean> listBean;

    @Override
    public void initView() {
        setContentView(R.layout.test_activity_gson);
        listView = (ListView) findViewById(R.id.gson_listview);
    }

    @Override
    public void initData() {
        setTitle(getIntent().getStringExtra("des"));
        listBean = new ArrayList<>();
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        request();
    }

    @Override
    public void initEvent() {

    }

    private void request() {

        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://test.didi365.com/")
                .build()
                .create(NetApi.class)
                .testGson("1", "1")
                .enqueue(new Callback<NoteBean>() {
                    @Override
                    public void onResponse(Call<NoteBean> call, Response<NoteBean> response) {
                        NoteBean noteBean = response.body();
                        listBean = noteBean.getData();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<NoteBean> call, Throwable t) {
                        Debug.e(TAG, "onFailure:" + t.getMessage());
                    }
                });

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
            final NoteBean.DataBean bean = listBean.get(position);
            MyHolder holder = null;
            if (convertView == null) {
                holder = new MyHolder();
                convertView = LayoutInflater.from(TestGsonActivity.this).inflate(R.layout.test_item_gson, parent, false);
                holder.imageView = (ImageView) convertView.findViewById(R.id.gson_imageview);
                convertView.setTag(holder);
            } else {
                holder = (MyHolder) convertView.getTag();
            }

            Glide.with(TestGsonActivity.this)
                    .load(bean.getImage())
                    .placeholder(R.color.white)
                    .into(holder.imageView);

            return convertView;
        }
    }

    class MyHolder {
        ImageView imageView;
    }
}
