package com.leichao.studyforit.test;

import android.database.Observable;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
}
