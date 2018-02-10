package com.glut.news.net.manager;

import com.glut.news.base.AppApplication;
import com.glut.news.entity.Comments;
import com.glut.news.entity.GuoKrDetail;
import com.glut.news.entity.GuoKrList;
import com.glut.news.entity.KaiYan;
import com.glut.news.entity.One;
import com.glut.news.entity.OneDateList;
import com.glut.news.entity.OneDetail;
import com.glut.news.entity.ZhiHuDetail;
import com.glut.news.net.service.RetrofitService;
import com.glut.news.net.service.ZhiHuList;
import com.glut.news.utils.NetUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;


    private static OkHttpClient mOkHttpClient;
    private RetrofitService.ZhuhuService mZhihuService=null;
    private  RetrofitService.OneService mOneService=null;
    private RetrofitService.GuoKrService mGuokrService=null;
    private RetrofitService.KaiYanService mKaiYanSevice=null;

    public static RetrofitManager builder(String url,String type){
        return new RetrofitManager(url,type);
    }
    private RetrofitManager(String url,String type){
        initOkHttpClient();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        if ("OneService".equals(type)){
            mOneService=retrofit.create(RetrofitService.OneService.class);

        }else if ("ZhuHuService".equals(type)){
            mZhihuService=retrofit.create(RetrofitService.ZhuhuService.class);

        }else  if ("GuoKrService".equals(type)){
            mGuokrService=retrofit.create(RetrofitService.GuoKrService.class);


        }else {
            mKaiYanSevice=retrofit.create(RetrofitService.KaiYanService.class);
        }
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient==null){
            synchronized (RetrofitManager.class){
               if (mOkHttpClient==null){
                   //指定缓存，缓存大小100Mb
                  Cache cache=new Cache(new File(AppApplication.getContext().getCacheDir(),"HttpCache"),1024*1024*100);
                   mOkHttpClient=new OkHttpClient.Builder()
                           .cache(cache)
                           .addInterceptor(mRewriteCacheControlInterceptor)
                           .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                           .addInterceptor(interceptor)
                           .retryOnConnectionFailure(true)
                           .connectTimeout(15, TimeUnit.SECONDS)
                           .build();

               }

            }
        }

    }

    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkConnected()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtil.isNetworkConnected()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
                        .removeHeader("Pragma").build();
            }
        }
    };
    public Observable<ZhiHuList> getLatestNews(){
        return mZhihuService.getLatestNews();
    }

    public Observable<ZhiHuDetail> getZhiHuDetail(String id){
        return mZhihuService.getNewsDetail(id);
    }
    public Observable<ZhiHuList> getBeforeData(String date){
        return mZhihuService.getBeforeNews(date);
    }

    public Observable<GuoKrList> getLatestNews1(){
        return mGuokrService.getLatestNews();
    }

    public Observable<GuoKrDetail> getZhiHuDetail2(int id){
        return mGuokrService.getNewsDetail(id);
    }

    public Observable<OneDateList> getOneDateList(){
        return mOneService.getDateList();
    }
    public Observable<One> getOneLasteNews(String date){
        return mOneService.getOneLatestNews(date);
    }

    public Observable<Comments> getOneComment(String id){
        return mOneService.getOneComment(id);
    }
    public Observable<OneDetail> getOneDetail(String id){
        return mOneService.getDetailtNews(id);
    }


    public Observable<KaiYan> getLatestNews3(){
        return mKaiYanSevice.getLateseNews3();
    }
    public Observable<KaiYan> getNextPageNews(String page){
        return mKaiYanSevice.getNextPageNews(page);
    }
}
