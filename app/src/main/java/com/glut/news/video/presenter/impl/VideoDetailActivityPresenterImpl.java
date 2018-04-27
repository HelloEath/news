package com.glut.news.video.presenter.impl;

import com.glut.news.common.model.entity.Comment;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.my.model.entity.Star;
import com.glut.news.video.model.entity.VideoCommentsModel;
import com.glut.news.video.model.entity.VideoModel;
import com.glut.news.video.presenter.IVideoDetailActivityPresenter;
import com.glut.news.video.view.activity.IVideoDetailActivityView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/13.
 */

public class VideoDetailActivityPresenterImpl implements IVideoDetailActivityPresenter {
    private IVideoDetailActivityView mIVideoDetailActivityView;
    private boolean isLastPage;
    private int NextPage;
    private int ArticleId;

    public VideoDetailActivityPresenterImpl(IVideoDetailActivityView iVideoDetailActivityView) {
        this.mIVideoDetailActivityView = iVideoDetailActivityView;
    }



    @Override
    public void loadComment(final String fp) {

        if ("fp".equals(fp)){
            NextPage=1;
        }
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
                            mIVideoDetailActivityView.onloadVideoCommentFail();
                        }else{

                            if (comment.getData()!=null)
                                if (comment.isHaveNextPage()){

                                if ("fp".equals(fp)){

                                mIVideoDetailActivityView.changeCommentAdater(comment);

                                }else {
                                    mIVideoDetailActivityView.addCommentAdater(comment);
                                }
                            NextPage=comment.getNextpage();
                                }else {
                                    mIVideoDetailActivityView.addCommentAdater(comment);
                                    mIVideoDetailActivityView.noMoreVideoComment();
                                }

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });

    }

    @Override
    public void sendComment(final  Comment c) {


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

                                mIVideoDetailActivityView.onSendCommentSuccess();

                            }else {
                                mIVideoDetailActivityView.onSendCommentFail();
                            }
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });
    }

    @Override
    public void recordStar(Star s) {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "StarService").putStar(s)
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
                       if (comment==1){
                           mIVideoDetailActivityView.onStarSuccess();
                       }else {
                           mIVideoDetailActivityView.onStarFali();
                       }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    public void updateComments() {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "CommentService").updateComment(ArticleId+"",2)
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


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    public void getVideoDetailInfo(int id) {
        ArticleId=id;
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "VideoService").getVideoDetailInfo(ArticleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<VideoModel, VideoModel>() {
                    @Override
                    public VideoModel call(VideoModel videoModel) {
                        return videoModel;
                    }
                })
                .subscribe(new Action1<VideoModel>() {
                    @Override
                    public void call(VideoModel videoModel) {

                        if (videoModel.getStus().equals("ok")){

                            mIVideoDetailActivityView.onloadVideoDetailInfoSuccess(videoModel);
                        }else{

                            mIVideoDetailActivityView.onloadVideoDetailInfoFail();

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    public void loadRecommenData(String videoType) {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "VideoService").getDetailVideo(videoType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })

                .subscribe(new Action1<VideoModel>() {
                    @Override
                    public void call(VideoModel videoModel) {

                        if ("ok".equals(videoModel.getStus()))
                            mIVideoDetailActivityView.addRecommentData(videoModel.getData());
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
                              mIVideoDetailActivityView.onDeleteCommentSuccess();

                            }else {

                                mIVideoDetailActivityView.onDeleteCommentFail();
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
