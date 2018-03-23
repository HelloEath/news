package com.glut.news.video.presenter;

import com.glut.news.common.model.entity.Comment;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.my.model.entity.History;
import com.glut.news.my.model.entity.Star;
import com.glut.news.video.model.entity.VideoCommentsModel;
import com.glut.news.video.view.activity.IVideoDetailView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.R.attr.id;

/**
 * Created by yy on 2018/3/13.
 */

public class VideoPresenterImpl implements IVideoPresenter{
    private IVideoDetailView iVideoDetailView;
    private boolean isLastPage;
    private int NextPage;
    private int ArticleId;

    public VideoPresenterImpl(IVideoDetailView iVideoDetailView) {
        this.iVideoDetailView = iVideoDetailView;
    }

    @Override
    public void recordHistory(History h) {
        ArticleId=h.getHistory_Article();
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
    public void loadMoreComment() {


        if (!isLastPage) {
            RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "CommentService").getCOmment(id+"", NextPage)
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
                            if (comment == null) {
                                // mTvLoadEmpty.setVisibility(View.VISIBLE);
                            } else {

                                if (comment.getData() != null)
                                    if (NextPage == comment.getNextpage()) {

                                        isLastPage = true;
                                    }


                                iVideoDetailView.addCommentAdater(comment);
                                NextPage = comment.getNextpage();


                                //isloading = false;
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

    @Override
    public void loadComment(int id) {

        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL,"CommentService").getCOmment(ArticleId+"",1)
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
                                iVideoDetailView.changeCommentAdater(comment);
                            NextPage=comment.getNextpage();
                            iVideoDetailView.initListenter();
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

    @Override
    public void sendComment( final  Comment c) {


        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "CommentService").putComment(c.getComment_Content(),c.getComment_Article()+"",c.getComment_Author()+"",c.getComment_Time())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
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
                        //mPbLoading.setVisibility(View.GONE);
                        if (comment == null) {
                            // mTvLoadEmpty.setVisibility(View.VISIBLE);
                        } else {

                            if ("ok".equals(comment.getStus()) ){

                                iVideoDetailView.onSendCommentSuccess(c);

                            }else {
                                iVideoDetailView.onSendCommentFail();
                            }
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
                           iVideoDetailView.onStarSuccess();
                       }else {
                           iVideoDetailView.onStarFali();
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
    public void updatePlays() {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "VideoService").updateVideoPlays(ArticleId)
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


}
