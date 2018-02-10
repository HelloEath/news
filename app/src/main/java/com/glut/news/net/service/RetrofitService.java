package com.glut.news.net.service;


import com.glut.news.entity.Comments;
import com.glut.news.entity.GuoKrDetail;
import com.glut.news.entity.GuoKrList;
import com.glut.news.entity.KaiYan;
import com.glut.news.entity.One;
import com.glut.news.entity.OneDateList;
import com.glut.news.entity.OneDetail;
import com.glut.news.entity.ZhiHuDetail;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yy on 2018/2/1.
 */

public interface RetrofitService {

    //知乎日报Api
    String BASE_ZHIHU_URL="http://news-at.zhihu.com/api/4/";

    //one Api
    String ONE_MOMENT_BASE = "http://v3.wufazhuce.com:8000/api/";

    //果壳精选Api
    String GUOKR_HANDPICK_BASE = "http://apis.guokr.com/minisite/";
    //开眼Api
    String KAIYAB_BASE_URL="http://baobab.kaiyanapp.com/api/";
    //"http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"
    	//"http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3
    interface ZhuhuService{
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

    interface OneService{
        //获得one列表数据
        @GET("onelist/idlist/?channel=wdj&version=4.0.2&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Observable<OneDateList> getDateList();
        //获得one最新或之前数据
        @GET("onelist/{date}/0?channel=wdj&version=4.0.2&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Observable<One> getOneLatestNews(@Path("date") String date);


        //获得one文章详情页数据
        @GET("essay/{id}?channel=wdj&source=summary&source_id=9261&version=4.0.2&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Observable<OneDetail> getDetailtNews(@Path("id") String id);

        //获得文章评论数据
        @GET("comment/praiseandtime/essay/{id}/0?channel=wdj&version=4.0.2&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Observable<Comments> getOneComment(@Path("id") String id);
    }

    interface GuoKrService{
        //获得果壳精选最新数据
        @GET("article.json?retrieve_type=by_minisite")
        Observable<GuoKrList> getLatestNews();

        //获得果壳文章详情页数据
        @GET("article/{id}.json")
        Observable<GuoKrDetail> getNewsDetail(@Path("id") int id);


    }

    public interface KaiYanService {
        //获得开眼热门数据
        @GET("v4/discovery/hot")
        Observable<KaiYan> getLateseNews3();

        //获得开眼下一页数据

        @GET("v4/discovery/hot?num=20")
        Observable<KaiYan> getNextPageNews(@Query("start") String page);
    }
}
