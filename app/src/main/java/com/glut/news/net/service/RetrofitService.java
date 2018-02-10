package com.glut.news.net.service;


import com.glut.news.entity.ZhiHuDetail;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by yy on 2018/2/1.
 */

public interface RetrofitService {


    String DOUBAN_MOMENT_BASE = "https://moment.douban.com/api/";

    String GUOKR_HANDPICK_BASE = "http://apis.guokr.com/minisite/";
    //获得最新知乎每一一报数据
    @GET("stories/latest")
    Observable<ZhiHuList> getLatestNews();
   //根据id获取详情数据
    @GET("story/{id}")
    Observable<ZhiHuDetail> getNewsDetail(@Path("id") String id);
    //根据日期获取以前的数据
    @GET("stories/before/{date}")
    Observable<ZhiHuList> getBeforeNews(@Path("date") String date);
}
