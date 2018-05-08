package com.glut.news.video.presenter.impl;

import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.video.model.entity.VideoModel;
import com.glut.news.video.presenter.IVideoTypeFragmentPresenter;
import com.glut.news.video.view.fragment.IVideoTypeFragmentView;

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
    public void loadVideoData(String u,String videoType, final String fp) {

        if ("fp".equals(fp)){
            NextPage=1;
        }
        if (videoType.equals("推荐")){

            if ("login".equals(u)){
                RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL,"VideoService").getVideo("isinterest",NextPage)
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
                                    iVideoTypeFragmentView.loadVideoDataFail();
                                }else{

                                    if (videoModel.getStus().equals("ok")){

                                        if (videoModel.isHaveNextPage()){

                                            if ("fp".equals(fp)){
                                                iVideoTypeFragmentView.loadVideoDataSuccess(videoModel);


                                            }else {
                                                iVideoTypeFragmentView.loadVideoMoreVideoDataSuccesss(videoModel);


                                            }
                                            NextPage= videoModel.getNextpage();


                                        }else {
                                            iVideoTypeFragmentView.loadVideoMoreVideoDataSuccesss(videoModel);
                                            iVideoTypeFragmentView.noMoreData();


                                        }

                                    }else {

                                        iVideoTypeFragmentView.loadVideoDataFail();
                                    }

                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {


                            }
                        });


            }else {

                RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL,"VideoService").getVideo("nointerest",NextPage)
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
                                    iVideoTypeFragmentView.loadVideoDataFail();
                                }else{

                                    if (videoModel.getStus().equals("ok")){

                                        if (videoModel.isHaveNextPage()){

                                            if ("fp".equals(fp)){
                                                iVideoTypeFragmentView.loadVideoDataSuccess(videoModel);


                                            }else {
                                                iVideoTypeFragmentView.loadVideoMoreVideoDataSuccesss(videoModel);


                                            }
                                            NextPage= videoModel.getNextpage();


                                        }else {
                                            iVideoTypeFragmentView.loadVideoMoreVideoDataSuccesss(videoModel);
                                            iVideoTypeFragmentView.noMoreData();


                                        }

                                    }else {

                                        iVideoTypeFragmentView.loadVideoDataFail();
                                    }

                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {


                            }
                        });


            }



        }else {
            RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL,"VideoService").getTypeVideo(videoType,NextPage)
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
                                iVideoTypeFragmentView.loadVideoDataFail();
                            }else{

                                if (videoModel.getStus().equals("ok")){

                                    if (videoModel.isHaveNextPage()){
                                        if ("fp".equals(fp)){
                                            iVideoTypeFragmentView.loadVideoDataSuccess(videoModel);

                                        }else {
                                            iVideoTypeFragmentView.loadVideoMoreVideoDataSuccesss(videoModel);

                                        }
                                        NextPage= videoModel.getNextpage();

                                    }else {
                                        iVideoTypeFragmentView.noMoreData();
                                        iVideoTypeFragmentView.loadVideoMoreVideoDataSuccesss(videoModel);


                                    }

                                }else {

                                    iVideoTypeFragmentView.loadVideoDataFail();

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


}
