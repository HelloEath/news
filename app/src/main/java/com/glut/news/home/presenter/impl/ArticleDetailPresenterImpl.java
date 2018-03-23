package com.glut.news.home.presenter.impl;

import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.home.presenter.IArticleDetailPresenter;
import com.glut.news.home.view.activity.ArticleDetailView;
import com.glut.news.my.model.entity.History;
import com.glut.news.my.model.entity.Star;
import com.glut.news.video.model.entity.VideoCommentsModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/13.
 */

public class ArticleDetailPresenterImpl implements IArticleDetailPresenter {

    private ArticleDetailView articleDetailView;
    private int ArticleId=0;

    private int NextPage;
    public ArticleDetailPresenterImpl(ArticleDetailView a) {
        articleDetailView=a;
    }

    @Override
    public void star( Star s) {

        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL,"StarService").putStar(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer i) {
                        return i;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer f) {
                        //mPbLoading.setVisibility(View.GONE);
                        if (f==1){
                            articleDetailView.onStarSuccess();
                        }else{

                            articleDetailView.onStarSuccess();

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });

    }

    @Override
    public void onHistory(History h) {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "HistoryService").insertHistory(h)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })

                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer comment) {
                        //mPbLoading.setVisibility(View.GONE);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    public void loadComment(int Id) {
        ArticleId=Id;
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL,"CommentService").getCOmment(ArticleId+"",1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .map(new Func1<VideoCommentsModel, VideoCommentsModel>() {
                    @Override
                    public VideoCommentsModel call(VideoCommentsModel guoKrList) {
                        return guoKrList;
                    }
                })
                .subscribe(new Action1<VideoCommentsModel>() {
                    @Override
                    public void call(VideoCommentsModel comment) {

                        if (comment==null){

                        }else{

                            if (comment.getData()!=null)
                                articleDetailView.changeAdater(comment);

                            NextPage=comment.getNextpage();
                            //mTvLoadEmpty.setVisibility(View.GONE);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                       /* mLoadLatestSnackbar.show();
                        refreshLayout.setRefreshing(false);
                        mLoadLatestSnackbar.show();
                        mTvLoadError.setVisibility(View.VISIBLE);
                        mTvLoadEmpty.setVisibility(View.GONE);*/

                    }
                });
    }

    @Override
    public void loadMoreComment() {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL,"CommentService").getCOmment(ArticleId+"",NextPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<VideoCommentsModel, VideoCommentsModel>() {
                    @Override
                    public VideoCommentsModel call(VideoCommentsModel guoKrList) {
                        return guoKrList;
                    }
                })
                .subscribe(new Action1<VideoCommentsModel>() {
                    @Override
                    public void call(VideoCommentsModel comment) {
                        //mPbLoading.setVisibility(View.GONE);
                        if (comment==null){
                            // mTvLoadEmpty.setVisibility(View.VISIBLE);
                        }else{

                            if (comment.getData()!=null)
                                articleDetailView.addAdater(comment);
                                NextPage=comment.getNextpage();
                            //mTvLoadEmpty.setVisibility(View.GONE);
                        }
                       /* mLoadLatestSnackbar.dismiss();
                        refreshLayout.setRefreshing(false);
                        mTvLoadError.setVisibility(View.GONE);*/
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                       /* mLoadLatestSnackbar.show();
                        refreshLayout.setRefreshing(false);
                        mLoadLatestSnackbar.show();
                        mTvLoadError.setVisibility(View.VISIBLE);
                        mTvLoadEmpty.setVisibility(View.GONE);*/

                    }
                });

    }
}
