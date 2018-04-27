package com.glut.news.home.presenter.impl;

import com.glut.news.common.model.entity.Comment;
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
                    }
                })

                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer comment) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    public void loadComment(int Id, final String fp) {
        if ("fp".equals(fp)){

            NextPage=1;
        }
        ArticleId=Id;
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL,"CommentService").getCOmment(ArticleId+"",NextPage)
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

                            if (comment.getStus().equals("ok")){

                                if (comment.isHaveNextPage()){

                                    if ("fp".equals(fp)){
                                        articleDetailView.changeAdater(comment);


                                    }else {

                                        articleDetailView.addAdater(comment);
                                    }
                                    NextPage=comment.getNextpage();

                                }else {
                                    articleDetailView.addAdater(comment);

                                    articleDetailView.noMoreData();

                                }
                            }else {


                            }

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });
    }


    public void sendComment(Comment c) {

        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "CommentService").putComment(c.getComment_Content(),c.getComment_Article()+"",c.getComment_Author()+"",c.getComment_Time(),c.getAuthor_logo(),c.getAuthor_name())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .map(new Func1<Comment, Comment>() {
                    @Override
                    public Comment call(Comment guoKrList) {
                        return guoKrList;
                    }
                })
                .subscribe(new Action1<Comment>() {
                    @Override
                    public void call(Comment comment) {
                        if (comment == null) {
                        } else {

                            if ("ok".equals(comment.getStus()) ){

                                articleDetailView.onSendCommentSuccess();

                            }else {
                                articleDetailView.onSendCommentFail();
                            }
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });
    }

    public void updateComments() {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "CommentService").updateComment(ArticleId+"",1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })

                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer comment) {


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    public void deleteComment(String userId) {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "CommentService").deleteComment(ArticleId+"",userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .map(new Func1<Comment, Comment>() {
                    @Override
                    public Comment call(Comment guoKrList) {
                        return guoKrList;
                    }
                })
                .subscribe(new Action1<Comment>() {
                    @Override
                    public void call(Comment comment) {
                        if (comment == null) {
                        } else {

                            if ("ok".equals(comment.getStus()) ){
                               articleDetailView.onDeleteCommentSuccess();

                            }else {

                                articleDetailView.onDeleteCommentFail();
                            }
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });



    }
}
