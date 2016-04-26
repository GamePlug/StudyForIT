package com.leichao.studyforit.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.leichao.studyforit.R;
import com.leichao.studyforit.base.BaseListActivity;
import com.leichao.studyforit.common.net.glide.TransformCircle;
import com.leichao.studyforit.common.net.glide.TransformRotate;
import com.leichao.studyforit.common.net.retrofit.RetrofitManager;
import com.leichao.studyforit.common.debug.Debug;
import com.leichao.studyforit.common.widget.pullrecycler.BaseViewHolder;
import com.leichao.studyforit.common.widget.pullrecycler.PullRecycler;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Created by leichao on 2016/4/19.
 */
public class TestListActivity extends BaseListActivity<Benefit> {

    private int page = 1;

    @Override
    public void initData() {
        super.initData();

        /*CrashHandler.delAllLog();
        int a = 5/0;*/

        //recycler.setRefreshing();
        startLoading();
        onRefresh(PullRecycler.ACTION_IDLE);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        setReconnectListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh(PullRecycler.ACTION_IDLE);
            }
        });
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_activity_list_item, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    public void onRefresh(final int action) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }

        if (action == PullRecycler.ACTION_PULL_TO_REFRESH
                || action == PullRecycler.ACTION_IDLE) {
            page = 1;
        }

        new RetrofitManager.Creator()
            .create(Api.class)
            .defaultBenefits(20, page++)
            //.postBenefits(20, page++, "wgghw")
            //.postBenefits(20, page++)
            .enqueue(new Callback<BaseModel<ArrayList<Benefit>>>() {
                 @Override
                 public void onResponse(Call<BaseModel<ArrayList<Benefit>>> call, Response<BaseModel<ArrayList<Benefit>>> response) {
                     if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
                         mDataList.clear();
                     } else if(action == PullRecycler.ACTION_IDLE) {
                         stopLoading();
                     }
                     if (response.body() == null
                             || response.body().results == null
                             || response.body().results.size() == 0) {
                         recycler.enableLoadMore(false);
                         Debug.e("leilei", "---" + response.headers().toString());
                     } else {
                         recycler.enableLoadMore(true);
                         mDataList.addAll(response.body().results);
                         adapter.notifyDataSetChanged();
                     }
                     recycler.onRefreshCompleted();
                 }

                 @Override
                 public void onFailure(Call<BaseModel<ArrayList<Benefit>>> call, Throwable t) {
                     Debug.e("leilei", "---" + t.getMessage());
                     if(action == PullRecycler.ACTION_IDLE) {
                         showReconnect();
                     }
                     recycler.onRefreshCompleted();
                 }
            }
        );
    }

    class SampleViewHolder extends BaseViewHolder {

        ImageView mSampleListItemImg;

        public SampleViewHolder(View itemView) {
            super(itemView);
            mSampleListItemImg = (ImageView) itemView.findViewById(R.id.mSampleListItemImg);
        }

        @Override
        public void onBindViewHolder(int position) {
            Glide.with(mSampleListItemImg.getContext())
                    .load(mDataList.get(position).url)
                    //.load("http://ww1.sinaimg.cn/large/85cccab3gw1etdi67ue4eg208q064n50.jpg")
                    //.load("https://pixabay.com/static/uploads/photo/2016/04/13/21/58/auto-1327801__180.jpg")
                    //.centerCrop()
                    //.fitCenter()
                    .transform(new TransformRotate(TestListActivity.this, 90f))
                    //.transform(new TransformCircle(TestListActivity.this))
                    //.transform(new TransformRound(TestListActivity.this, 18))
                    //.transform(new CenterCrop(TestListActivity.this), new TransformRound(TestListActivity.this, 18))
                    //.transform(new FitCenter(TestListActivity.this), new TransformRound(TestListActivity.this, 18))
                    .placeholder(R.color.white)
                    .crossFade()
                    .into(mSampleListItemImg);
                    /*.asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            mSampleListItemImg.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            Debug.e("leilei", e.getMessage());
                        }
                    });*/
        }

        @Override
        public void onItemClick(View view, int position) {
            Glide.get(TestListActivity.this).clearDiskCache();
        }

    }
}
