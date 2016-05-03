package com.leichao.studyforit.test.net;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;

/**
 * 网络请求接口
 * Created by leichao on 2016/4/27.
 */
public interface NetApi {
    //------------- get请求 ----------------//

    @GET("Gold/note/getLists")
    Call<String> testGet(
            @Query("type") String type,
            @Query("page") String page
    );

    @GET("Gold/{what}/getLists")
    Call<String> testGet(
            @Path("what") String what,
            @QueryMap Map<String, String> whos
    );


    //------------- post请求 ----------------//

    @POST("Gold/note/getLists")
    Call<String> testPost(

    );

    @FormUrlEncoded
    @POST("Gold/note/getLists")
    Call<String> testPost(
            @Field("type") String type,
            @Field("page") String page
    );

    @FormUrlEncoded
    @POST("Gold/note/getLists")
    Call<String> testPost(
            @FieldMap Map<String, String> whos
    );


    //------------- 下载 ----------------//

    @GET("http://119.29.62.241/leichao/data/upload/image/{fileName}")
    @Streaming// 如果不加此注释，则会把整个文件写入内存，那么大文件会内存溢出。
    Call<ResponseBody> testGetDownload(
            @Path("fileName") String fileName
    );

    @POST("http://srctest.didi365.com/didi365/Upload/app/ShenMaDiDiClient2.0.91.0.apk")
    @Streaming
    Call<ResponseBody> testPostDownload(
    );


    //------------- 上传 ----------------//

    @Multipart
    @POST("http://119.29.62.241/leichao/study/UploadMoreImage.php")
    Call<String> testUpload(
            @Part("who") String who,
            @PartMap Map<String, RequestBody> params
    );


    //------------- 直接解析为对象 ----------------//

    @FormUrlEncoded
    @POST("http://www.didi365.com/Gold/note/getLists")
    Call<NoteBean> testGson(
            @Field("type") String type,
            @Field("page") String page
    );

    @FormUrlEncoded
    @POST("http://www.didi365.com/Gold/note/getLists")
    Call<NoteBean> testGson(
            @FieldMap Map<String, String> params
    );


    //------------- https证书 ----------------//
    @GET("https://kyfw.12306.cn/otn/")
    Call<String> testHttps();
}
