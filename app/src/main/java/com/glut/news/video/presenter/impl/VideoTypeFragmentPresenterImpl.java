package com.glut.news.video.presenter.impl;

import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.video.model.entity.VideoModel;
import com.glut.news.video.presenter.IVideoTypeFragmentPresenter;
import com.glut.news.video.view.fragment.IVideoTypeFragmentView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/28.
 */

public class VideoTypeFragmentPresenterImpl implements IVideoTypeFragmentPresenter {

    private int NextPage;
    private int nextPage_tuijian;

    private IVideoTypeFragmentView iVideoTypeFragmentView;

    public VideoTypeFragmentPresenterImpl(IVideoTypeFragmentView iVideoTypeFragmentView) {
        this.iVideoTypeFragmentView = iVideoTypeFragmentView;
    }

    @Override
    public void loadVideoData(String videoType,final RefreshLayout r) {

        if (videoType.equals("推荐")){

            RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL,"VideoService").getVideo(1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                        }
                    })
                    .map(new Func1<VideoModel,VideoModel>() {
                        @Override
                        public VideoModel call(VideoModel videoModel) {
                            return videoModel;
                        }
                    })
                    .subscribe(new Action1<VideoModel>() {
                        @Override
                        public void call(VideoModel videoModel) {

                            if (videoModel ==null){
                                iVideoTypeFragmentView.loadVideoDataFail(r);
                            }else{

                                NextPage= videoModel.getNextpage();
                                iVideoTypeFragmentView.loadVideoDataSuccess(videoModel,r);

                            }

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {


                        }
                    });

        }else {
            RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL,"VideoService").getTypeVideo(videoType,1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                        }
                    })
                    .map(new Func1<VideoModel,VideoModel>() {
                        @Override
                        public VideoModel call(VideoModel videoModel) {
                            return videoModel;
                        }
                    })
                    .subscribe(new Action1<VideoModel>() {
                        @Override
                        public void call(VideoModel videoModel) {
                            if (videoModel ==null){
                                iVideoTypeFragmentView.loadVideoDataFail(r);
                            }else{

                                NextPage= videoModel.getNextpage();
                                iVideoTypeFragmentView.loadVideoDataSuccess(videoModel,r);                              //mTvLoadEmpty.setVisibility(View.GONE);
                            }

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {


                        }
                    });
        }


    }

    @Override
    public void loadMoreData(String videoType,final RefreshLayout r) {

        if (videoType.equals("推荐")) {
            RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "VideoService").getVideo(NextPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
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
                            if (videoModel == null) {
                                iVideoTypeFragmentView.loadVideoMoreVideoDataFail(r);
                            } else {

                                //判断是否使用缓存数据
                                if (NextPage== videoModel.getNextpage()){


                                }else {
                                    NextPage= videoModel.getNextpage();
                                    iVideoTypeFragmentView.loadVideoMoreVideoDataSuccesss(videoModel,r);                                }

                            }


                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {


                        }
                    });

        } else {
            RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "VideoService").getTypeVideo(videoType, NextPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
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
                            if (videoModel == null) {
                                iVideoTypeFragmentView.loadVideoMoreVideoDataFail(r);

                            }else{
                                //判断是否使用缓存数据
                                if (NextPage == videoModel.getNextpage()) {


                                } else {
                                    NextPage = videoModel.getNextpage();
                                }
                                iVideoTypeFragmentView.loadVideoMoreVideoDataSuccesss(videoModel, r);

                            }


                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {


                        }
                    });
        }

    }
}
