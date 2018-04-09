package com.glut.news.common.utils.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.glut.news.AppApplication;
import com.glut.news.common.model.entity.Comment;
import com.glut.news.common.model.entity.UserInfo;
import com.glut.news.common.model.entity.UserModel;
import com.glut.news.common.utils.NetUtil;
import com.glut.news.common.utils.service.RetrofitService;
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
import com.glut.news.my.model.entity.History;
import com.glut.news.my.model.entity.HistoryWithStarModel;
import com.glut.news.my.model.entity.InterestTag;
import com.glut.news.my.model.entity.Star;
import com.glut.news.video.model.entity.VideoCommentsModel;
import com.glut.news.video.model.entity.VideoModel;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by yy on 2018/2/1.
 */

public class RetrofitManager {

    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 60;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";

    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private static OkHttpClient mOkHttpClient;
    private RetrofitService.ZhuhuService mZhihuService = null;
    private RetrofitService.OneService mOneService = null;
    private RetrofitService.GuoKrService mGuokrService = null;
    private RetrofitService.KaiYanService mKaiYanSevice = null;
    private RetrofitService.VideoService mVideoService = null;
    private RetrofitService.ArticleService mArticleService = null;
    private RetrofitService.CommentService mCommentService=null;
    private RetrofitService.HistoryService mHistoryService=null;
    private RetrofitService.UserService mUserService=null;
    private RetrofitService.StarService mStarService=null;
    private RetrofitService.SearchService mSearchService=null;
    private RetrofitService.InterestTag mInterestTagService=null;

    public static RetrofitManager builder(String url, String type) {
        return new RetrofitManager(url, type);
    }

    private RetrofitManager(String url, String type) {
        initOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        if ("OneService".equals(type)) {
            mOneService = retrofit.create(RetrofitService.OneService.class);

        } else if ("ZhuHuService".equals(type)) {
            mZhihuService = retrofit.create(RetrofitService.ZhuhuService.class);

        } else if ("GuoKrService".equals(type)) {
            mGuokrService = retrofit.create(RetrofitService.GuoKrService.class);


        } else if ("KaiYanService".equals(type)) {
            mKaiYanSevice = retrofit.create(RetrofitService.KaiYanService.class);
        } else if ("VideoService".equals(type)) {
            mVideoService = retrofit.create(RetrofitService.VideoService.class);
        }else if ("ArticleService".equals(type)){
            mArticleService=retrofit.create(RetrofitService.ArticleService.class);
        }else if ("CommentService".equals(type)){
            mCommentService=retrofit.create(RetrofitService.CommentService.class);
        }else if ("HistoryService".equals(type)){
            mHistoryService=retrofit.create(RetrofitService.HistoryService.class);
        }else if ("UserService".equals(type)){
            mUserService=retrofit.create(RetrofitService.UserService.class);
        }else if ("StarService".equals(type)){
            mStarService=retrofit.create(RetrofitService.StarService.class);
        }else if ("SearchService".equals(type)){
            mSearchService=retrofit.create(RetrofitService.SearchService.class);
        }else if ("InterestTagService".equals(type)){
            mInterestTagService=retrofit.create(RetrofitService.InterestTag.class);
        }
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {
                    //指定缓存，缓存大小100Mb
                    Cache cache = new Cache(new File(AppApplication.getContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)



                            .addInterceptor(interceptor)
                            .addInterceptor(new AddCookiesInterceptor(AppApplication.getContext()))

                            .addInterceptor(new SaveCookiesInterceptor(AppApplication.getContext()))

                            .addInterceptor(mRewriteCacheControlInterceptor)

                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .build();

                }

            }
        }

    }

    // init cookie manager
    //CookieHandler cookieHandler = new CookiesManager();

    public class SaveCookiesInterceptor implements Interceptor {
        private static final String COOKIE_PREF = "cookies_prefs";
        private Context mContext;

        public SaveCookiesInterceptor(Context context) {
            mContext = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //保存cookie
            Response response = chain.proceed(request); //set-cookie可能为多个
            if (!response.headers("set-cookie").isEmpty()) {
                List<String> cookies = response.headers("set-cookie");


            String cookie = encodeCookie(cookies);
            saveCookie(request.url().toString(), request.url().host(), cookie);
            }

        return response;
    }


    //整合cookie为唯一字符串

    private String encodeCookie(List<String> cookies) {
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();
        for (String cookie : cookies) {
            String[] arr = cookie.split(";");
            for (String s : arr) {
                if (set.contains(s)) continue;
                set.add(s);
            }
        }
        Iterator<String> ite = set.iterator();
        while (ite.hasNext()) {
            String cookie = ite.next();
            sb.append(cookie).append(";");
        }
        int last = sb.lastIndexOf(";");
        if (sb.length() - 1 == last) {
            sb.deleteCharAt(last);
        }
        return sb.toString();
    }

        //保存cookie到本地，这里我们分别为该url和host设置相同的cookie，其中host可选 //这样能使得该cookie的应用范围更广

    private void saveCookie(String url, String domain, String cookies) {
        SharedPreferences sp = AppApplication.getContext().getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("url is null.");
        } else {
            editor.putString(url, cookies);
        }
        if (!TextUtils.isEmpty(domain)) {
            editor.putString(domain, cookies);
        }
        editor.apply();
    }
}





            public class AddCookiesInterceptor implements Interceptor {
                private static final String COOKIE_PREF = "cookies_prefs";
                private Context mContext;

                public AddCookiesInterceptor(Context context) {
                    mContext = context;
                }

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request.Builder builder = request.newBuilder();
                    //取出cookie
                    String cookie = getCookie(request.url().toString(), request.url().host());
                    if (!TextUtils.isEmpty(cookie)) {
                        builder.addHeader("Cookie", cookie);
                    }
                    return chain.proceed(builder.build());
                }

                private String getCookie(String url, String domain) {
                    SharedPreferences sp = mContext.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
                    if (!TextUtils.isEmpty(url) && sp.contains(url) && !TextUtils.isEmpty(sp.getString(url, ""))) {
                        return sp.getString(url, "");
                    }
                    if (!TextUtils.isEmpty(domain) && sp.contains(domain) && !TextUtils.isEmpty(sp.getString(domain, ""))) {
                        return sp.getString(domain, "");
                    }
                    return null;
                }

            }



            // 云端响应头拦截器，用来配置缓存策略
            private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!NetUtil.isNetworkConnected()) {
                        //没网时拦截请求，从缓存获取数据
                        request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                    }
                    Response originalResponse = chain.proceed(request);
                    if (NetUtil.isNetworkConnected()) {
                        //有网的时候读接口上的@Headers里的配置，拦截返回的response数据并添加头数据，设置缓存策略
                        String cacheControl = request.cacheControl().toString();

                        return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                                .removeHeader("Pragma")
                                .build();
                    } else {
                        return originalResponse.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
                                .removeHeader("Pragma").build();
                    }
                }
            };

    /*知乎日报*/
            public Observable<ZhiHuList> getLatestNews () {
                return mZhihuService.getLatestNews();
            }

            public Observable<ZhiHuDetailModel> getZhiHuDetail (String id){
                return mZhihuService.getNewsDetail(id);
            }

            public Observable<ZhiHuList> getBeforeData (String date){
                return mZhihuService.getBeforeNews(date);
            }

    /*果壳精选*/
            public Observable<GuoKrListModel> getLatestNews1 () {
                return mGuokrService.getLatestNews();
            }

            public Observable<GuoKrDetail> getZhiHuDetail2 (int id){
                return mGuokrService.getNewsDetail(id);
            }

    /*OneModel*/
            public Observable<OneDateListModel> getOneDateList () {
                return mOneService.getDateList();
            }

            public Observable<OneModel> getOneLasteNews (String date){
                return mOneService.getOneLatestNews(date);
            }

            public Observable<OneCommentsModel> getOneComment (String id){
                return mOneService.getOneComment(id);
            }

            public Observable<OneDetailModel> getOneDetail (String id){
                return mOneService.getDetailtNews(id);
            }

    /* 开眼视频*/
            public Observable<KaiYanModel> getLatestNews3 () {
                return mKaiYanSevice.getLateseNews3();
            }

            public Observable<KaiYanModel> getNextPageNews (String page){
                return mKaiYanSevice.getNextPageNews(page);
            }

    /*News视频*/

    //推荐
            public Observable<VideoModel> getVideo (int pageno) {

                return mVideoService.getVideo(pageno);
            }

    //获得分类视频列表
            public Observable<VideoModel> getTypeVideo (String type, int pageno){
                return mVideoService.getTypeVideo(type, pageno);
            }
    //更新视频播放量
    public Observable<Integer> updateVideoPlays (int ContentId){
        return mVideoService.updateVideoPlays(ContentId);
    }

    /*News文章*/

    //获得文章列表
    public Observable<ArticleModel> getTypeArticle(String type, int pageno){
        return  mArticleService.getTypeArticle(type,pageno);
    }
    //获得推荐列表
    public Observable<ArticleModel> getTuiJianArticle(String type, int pageno){
        return  mArticleService.getTuiJianArticle(type,pageno);
    }
/*评论*/

    //获得对应文章/视频的评论数据
    public Observable<VideoCommentsModel> getCOmment(String id, int pageno){
        return  mCommentService.getComment(id,pageno);
    }
    //用户发表评论
    public Observable<Comment> putComment(String ComentContent,String ArticleId, String AuthorId, String CommentTime){
        return  mCommentService.putComment(ComentContent,ArticleId,AuthorId,CommentTime);
    }

    //用户删除评论
    public Observable<Comment> deleteComment(String ArticleId, String AuthorId){
        return  mCommentService.deleteComment(ArticleId,AuthorId);
    }

    //更新文章/视频评论数
    public Observable<Integer> updateComment(String ContentId,int Type){
        return  mCommentService.updateComment(ContentId,Type);
    }
    /* 厉害记录*/
    //获得记录列表
    public Observable<HistoryWithStarModel> getHistoryList(String UserId, int PageNo){
        return mHistoryService.getHistoryList(UserId,PageNo);
    }

    //获得记录总数
    public Observable<Integer> getHistoryCount(){
        return mHistoryService.getHistoryCount();
    }

    //插入记录
    public Observable<Integer> insertHistory(History history){
        return mHistoryService.insertHistory(history.getHistory_Persion(),history.getHistory_Article(),history.getHistory_Time(),history.getHistory_Type(),history.getContent_type());
    }

    /*用户*/
    //登录请求
    public Observable<UserModel> login(UserInfo userInfo){
        return mUserService.login(userInfo);
    }

    //注册请求
    public Observable<UserModel> register(UserInfo userInfo){
        return mUserService.register(userInfo);
    }

    //修改用户名请求
    public Observable<UserModel> alterUserName(UserInfo userInfo){
        return mUserService.alterUserName(userInfo);
    }

    //修改用户性别
    public Observable<UserModel> alterUserSex(UserInfo userInfo){
        return mUserService.alterUserSex(userInfo);
    }

    //修改用户desc
    public Observable<UserModel> alterUserDesc(UserInfo userInfo){
        return mUserService.alterUserDesc(userInfo);
    }

    //修改用户地区
    public Observable<UserModel> alterUserDistrc(UserInfo userInfo){
        return mUserService.alterUserDistrc(userInfo);
    }
    //修改用户密码
    public Observable<UserModel> alterUserPwd(UserInfo userInfo){
        return mUserService.alterUserPwd(userInfo);
    }
    //修改用户生日
    public Observable<UserModel> alterUserBitrth(UserInfo userInfo){
        return mUserService.alterUserBirth(userInfo);
    }

    //修改用户头像
    public Observable<UserModel> alterUserLogo(String UserId, RequestBody requestBody){
        return mUserService.alterUserLogo(UserId,requestBody);
    }
    //修改用户头像
    public Observable<UserModel> alterUserLogoByBase64(UserInfo userInfo){
        return mUserService.alterUserLogoByBase64(userInfo);
    }
    //修改用户兴趣点
    public Observable<Integer> alterUserInterest(UserInfo userInfo){
        return mUserService.alterUserInterest(userInfo);
    }
    //登出
    public Observable<UserModel> logOut(UserInfo userInfo){
        return mUserService.logOut(userInfo);
    }


    /*收藏*/

   //查询收藏列表
    public Observable<HistoryWithStarModel> getStarList(int UserId,int NextPage ){
        return mStarService.getStarList(UserId,NextPage);

    }
   //插入收藏
    public Observable<Integer> putStar(Star s){

        return mStarService.inserStar(s.getStar_UserId(),s.getStar_ContentId(),s.getStar_Time(),s.getStar_Type());

    }

    //获得收藏数
    public Observable<Integer> getStarCount(){

        return mStarService.getStarCount();

    }
    /*搜索*/

    public Observable<HistoryWithStarModel> doSearch(String v,int NextPage){
        return mSearchService.doSearch(v,NextPage);
    }

    /*兴趣标签*/
    public Observable<Integer> doInterestTag(InterestTag interestTag){
        return mInterestTagService.setUserInterestTag(interestTag);
    }
}


