package com.leichao.studyforit.test;

import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.leichao.studyforit.R;
import com.leichao.studyforit.base.BaseActivity;
import com.leichao.studyforit.common.debug.Debug;
import com.leichao.studyforit.common.net.retrofit.RetrofitManager;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * Created by leichao on 2016/4/25.
 */
public class TestUploadActivity extends BaseActivity {

    ImageView imageView;

    @Override
    public void initView() {
        setContentView(R.layout.test_activity_upload);
        imageView = (ImageView) findViewById(R.id.test_image);
    }

    @Override
    public void initData() {
        //upload();
        //uploadOneImage();
        uploadMore();
    }

    @Override
    public void initEvent() {

    }

    private void upload() {
        File file = new File(Environment.getExternalStorageDirectory()
                + "/picture/lol/image02.jpg");
        //Debug.e("leilei", "exists:" + file.exists());
        final RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"),file);
        /*Map<String, RequestBody> params = new HashMap<>();
        params.put("file[]\"; filename=\""+file.getName()+"", requestBody);*/
        RetrofitManager.create(Api.class, ScalarsConverterFactory.create())
                .uploadImage("hello",requestBody)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Debug.e("leilei", "onResponse:" + response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Debug.e("leilei", "onFailure:" + t.getMessage());
                    }
                });
    }

    private void uploadOneImage() {
        File file = new File(Environment.getExternalStorageDirectory()
                + "/picture/lol/image02.jpg");
        //Debug.e("leilei", "exists:" + file.exists());
        final RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"),file);
        Map<String, RequestBody> params = new HashMap<>();
        //params.put("file[]\"; filename=\""+file.getName()+"", requestBody);
        params.put("image\"; filename=\""+file.getName()+"", requestBody);
        RetrofitManager.create(Api.class, ScalarsConverterFactory.create())
                .uploadOneImage("hello",params)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Debug.e("leilei", "onResponse:" + response.body());
                        Glide.with(imageView.getContext())
                                .load(response.body().split("_")[0])
                                .into(imageView);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Debug.e("leilei", "onFailure:" + t.getMessage());
                    }
                });
    }


    private void uploadMore() {
        // HashMap是无序的，LinkedHashMap是有序的
        Map<String, RequestBody> params = new LinkedHashMap<>();

        File file2 = new File(Environment.getExternalStorageDirectory()
                + "/picture/lol/image02.jpg");
        final RequestBody requestBody2 =
                RequestBody.create(MediaType.parse("multipart/form-data"),file2);
        params.put("image[]\"; filename=\""+file2.getName()+"", requestBody2);

        File file3 = new File(Environment.getExternalStorageDirectory()
                + "/picture/lol/image03.jpg");
        final RequestBody requestBody3 =
                RequestBody.create(MediaType.parse("multipart/form-data"),file3);
        params.put("image[]\"; filename=\""+file3.getName()+"", requestBody3);

        RetrofitManager.create(Api.class, ScalarsConverterFactory.create())
                .UploadMoreImage("hello",params)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Debug.e("leilei", "onResponse:" + response.body());
                        Glide.with(imageView.getContext())
                                .load(response.body().split("_")[0])
                                .into(imageView);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Debug.e("leilei", "onFailure:" + t.getMessage());
                    }
                });
    }
}
