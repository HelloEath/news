package com.glut.news.common.utils.service;


import com.glut.news.common.model.entity.Comment;
import com.glut.news.common.model.entity.UserInfo;
import com.glut.news.common.model.entity.UserModel;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.discover.model.entity.GuoKrDetail;
import com.glut.news.discover.model.entity.GuoKrListModel;
import com.glut.news.discover.model.entity.KaiYanModel;
import com.glut.news.discover.model.entity.OneCommentsModel;
import com.glut.news.discover.model.entity.OneDateListModel;
import com.glut.news.discover.model.entity.OneDetailModel;
import com.glut.news.discover.model.entity.OneModel;
import com.glut.news.discover.model.entity.ZhiHuDetailModel;
import com.glut.news.discover.model.entity.ZhiHuList;
import com.glut.news.home.model.entity.ArticleModel;
import com.glut.news.my.model.entity.HistoryWithStarModel;
import com.glut.news.video.model.entity.VideoCommentsModel;
import com.glut.news.video.model.entity.VideoModel;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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

    //视频APi

    String VIDEO_BASE_URL="http://192.168.191.1:8085/News/";

    //"http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"
    	//"http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3
    interface ZhuhuService{
        //获得最新知乎每一一报数据
        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_LONG)
        @GET("stories/latest")
        Observable<ZhiHuList> getLatestNews();

        //根据id获取详情数据
        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_LONG)
        @GET("story/{id}")
        Observable<ZhiHuDetailModel> getNewsDetail(@Path("id") String id);
        //根据日期获取以前的数据
        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
        @GET("stories/before/{date}")
        Observable<ZhiHuList> getBeforeNews(@Path("date") String date);

    }

    interface OneService{
        //获得one列表数据
        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_LONG)

        @GET("onelist/idlist/?channel=wdj&version=4.0.2&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Observable<OneDateListModel> getDateList();
        //获得one最新或之前数据
        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)

        @GET("onelist/{date}/0?channel=wdj&version=4.0.2&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Observable<OneModel> getOneLatestNews(@Path("date") String date);


        //获得one文章详情页数据
        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)

        @GET("essay/{id}?channel=wdj&source=summary&source_id=9261&version=4.0.2&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Observable<OneDetailModel> getDetailtNews(@Path("id") String id);

        //获得文章评论数据
        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)

        @GET("comment/praiseandtime/essay/{id}/0?channel=wdj&version=4.0.2&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Observable<OneCommentsModel> getOneComment(@Path("id") String id);
    }

    interface GuoKrService{
        //获得果壳精选最新数据
        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_LONG)

        @GET("article.json?retrieve_type=by_minisite")
        Observable<GuoKrListModel> getLatestNews();

        //获得果壳文章详情页数据
        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)

        @GET("article/{id}.json")
        Observable<GuoKrDetail> getNewsDetail(@Path("id") int id);


    }

    public interface KaiYanService {
        //获得开眼热门数据
        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_LONG)

        @GET("v4/discovery/hot")
        Observable<KaiYanModel> getLateseNews3();

        //获得开眼下一页数据
        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)

        @GET("v4/discovery/hot?num=20")
        Observable<KaiYanModel> getNextPageNews(@Query("start") String page);
    }
    /*视频*/
    public  interface VideoService{
        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_LONG)
        @GET("video/getVideo")
        Observable<VideoModel> getVideo(@Query("pageNo")int pageno);

        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_LONG)
        @GET("video/getTypeVideo")
        Observable<VideoModel> getTypeVideo(@Query("Video_Type") String type, @Query("pageNo")int pageno);

        @FormUrlEncoded
        @POST("video/updateVideoPlays")
        Observable<Integer> updateVideoPlays(@Field("Video_Id") int ContentId);

    }
   /* 新闻*/
    public interface  ArticleService{
       /* 获取新闻列表*/
        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_LONG)
        @GET("article/typeArticle")
        Observable<ArticleModel> getTypeArticle(@Query("Article_Type") String type, @Query("pageNo")int pageno);



    }
   /* 评论*/
    public interface CommentService{
       /* 获取新闻评论列表*/
       @GET("comment/getComment")
       Observable<VideoCommentsModel> getComment(@Query("Comment_Article") String type, @Query("pageNo")int pageno);

       /*插入评论*/
       @FormUrlEncoded
      @POST("comment/insertComment")
       Observable<Comment> putComment(@Field("Comment_Content") String CommentContent,@Field("Comment_Article") String articleId,@Field("Comment_Author") String AuthorId,@Field("Comment_Time") String CommentTime);
      /*删除评论*/
      @FormUrlEncoded
       @POST("comment/deleteComment")
       Observable<Comment> deleteComment(@Field("Comment_Article") String articleId,@Field("Comment_Author") String authorId);
       /*更新评论数*/
       @FormUrlEncoded
       @POST("comment/updateComment")
       Observable<Integer> updateComment(@Field("ContentId") String ContentId,@Field("Type") int Type);


   }

    public interface HistoryService{
       /* 插入记录*/
        @FormUrlEncoded
        @POST("history/putHistory")
        Observable<Integer> insertHistory(@Field("History_Persion") int userId,@Field("History_Article") int ArticleId,@Field("History_Time") String time,@Field("History_Type") int type);

        /*获取历史记录列表*/
        @GET("history/historyList")
        Observable<HistoryWithStarModel> getHistoryList(@Query("History_Persion") String UserId, @Query("pageNo") int pageNo);


        /*获取历史记录数*/
        @GET("history/tatolHistory")
        Observable<Integer> getHistoryCount(@Query("UserId") String UserId);

    }

    public interface UserService{
        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_LONG)
        @POST("user/login")
        Observable<UserModel> login(@Body UserInfo userInfo);

        @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_LONG)
        @POST("user/register")
        Observable<UserModel> register(@Body UserInfo userInfo);

    }

    /*收藏*/
    public interface StarService{
        /* 插入收藏*/
        @FormUrlEncoded
        @POST("star/putStar")
        Observable<Integer> inserStar(@Field("Star_UserId") int userId,@Field("Star_ContentId") int ArticleId,@Field("Star_Time") String time,@Field("Star_Type") int type);

        /*获取收藏列表*/
        @GET("star/starList")
        Observable<HistoryWithStarModel> getStarList(@Query("Star_UserId") int UserId, @Query("pageNo") int pageNo);


        /*获取收藏记录数*/
        @GET("star/starCount")
        Observable<Integer> getStarCount(@Query("Star_UserId") int UserId);

    }

    /*搜索*/
    public interface SearchService{

        @GET("common/search")
        Observable<HistoryWithStarModel> doSearch(@Query("searchValue") String value,@Query("NextPage") int NextPage);
    }
}
