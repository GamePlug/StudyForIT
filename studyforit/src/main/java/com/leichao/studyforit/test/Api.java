package com.leichao.studyforit.test;

import android.database.Observable;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 *
 * Created by leichao on 2016/4/19.
 */
public interface Api {
    // @FormUrlEncoded 只能和 @POST一起用
    // @FormUrlEncoded 注解的方法中，必须至少有一个@Field的参数
    // 方法有@Field参数的时候，必须要加@FormUrlEncoded
    /*即在@POST方法中，@FormUrlEncoded和@Field要么都出现，要么都不出现*/

    @GET("api/data/福利/{pageCount}/{pageIndex}")
    Call<BaseModel<ArrayList<Benefit>>> defaultBenefits(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    );

    @GET("api/data/福利/{pageCount}/{pageIndex}")
    Observable<BaseModel<ArrayList<Benefit>>> rxBenefits(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    );

    //@FormUrlEncoded
    @POST("api/data/福利/{pageCount}/{pageIndex}")
    Call<BaseModel<ArrayList<Benefit>>> postBenefits(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
            //@Field("name") String name
            //@Query("sort") String sort
    );

    //https://kyfw.12306.cn/otn/
    @GET("otn")
    Call<String> testHttps();

    @GET("api/data/福利/{pageCount}/{pageIndex}")
    Call<String> ddd(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    );

    //http://test.didi365.com/Gold/note/getLists
    @POST("Gold/note/getLists")
    Call<String> didi();


    @Multipart
    @POST("http://119.29.62.241/leichao/study/UploadImage.php")
    Call<String> uploadImage(
            @Part("fileName") String des,
            @Part("file\"; filename=\"image01.jpg") RequestBody param
    );

    @Multipart
    @POST("http://119.29.62.241/leichao/study/uploadOneImage.php")
    Call<String> uploadOneImage(
            @Part("hahahaha") String des,
            @PartMap Map<String, RequestBody> params
    );

    @Multipart
    @POST("http://119.29.62.241/leichao/study/UploadMoreImage.php")
    Call<String> UploadMoreImage(
            @Part("hahahaha") String des,
            @PartMap Map<String, RequestBody> params
    );

}
