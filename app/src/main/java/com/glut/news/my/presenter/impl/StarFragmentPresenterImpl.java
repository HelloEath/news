package com.glut.news.my.presenter.impl;

import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.my.model.entity.HistoryWithStarModel;
import com.glut.news.my.presenter.IStarFragmentPresenter;
import com.glut.news.my.view.fragment.IStarFragmentView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/18.
 */

public class StarFragmentPresenterImpl implements IStarFragmentPresenter {

    private IStarFragmentView starFragmentView;
    private boolean IsLastPage;

    private int UserId;
    private int NextPage;
    public StarFragmentPresenterImpl(IStarFragmentView starFragmentView) {
        this.starFragmentView = starFragmentView;
        UserId=Integer.parseInt(SpUtil.getUserFromSp("UserId"));
    }

    @Override
    public void loadStarData() {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "StarService").getStarList(UserId,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<HistoryWithStarModel, HistoryWithStarModel>() {
                    @Override
                    public HistoryWithStarModel call(HistoryWithStarModel h) {
                        return h;
                    }
                })
                .subscribe(new Action1<HistoryWithStarModel>() {
                    @Override
                    public void call(HistoryWithStarModel historyModel) {
                        //mPbLoading.setVisibility(View.GONE);
                        if (historyModel == null) {
                            // mTvLoadEmpty.setVisibility(View.VISIBLE);
                        } else {

                            if (historyModel.getData() != null)
                                if (NextPage == historyModel.getNextpage()) {

                                    IsLastPage = true;
                                }


                            starFragmentView.onLoadStarSuccess(historyModel);
                            NextPage = historyModel.getNextpage();


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

    @Override
    public void loadMoreStarData() {
        if (!IsLastPage) {
            RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "StarService").getStarList(UserId,NextPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            //mPbLoading.setVisibility(View.VISIBLE);
                        }
                    })
                    .map(new Func1<HistoryWithStarModel, HistoryWithStarModel>() {
                        @Override
                        public HistoryWithStarModel call(HistoryWithStarModel historyModel) {
                            return historyModel;
                        }
                    })
                    .subscribe(new Action1<HistoryWithStarModel>() {
                        @Override
                        public void call(HistoryWithStarModel historyWithStarModel) {
                            //mPbLoading.setVisibility(View.GONE);
                            if (historyWithStarModel == null) {
                                // mTvLoadEmpty.setVisibility(View.VISIBLE);
                            } else {

                                if (historyWithStarModel.getData() != null)
                                    if (NextPage == historyWithStarModel.getNextpage()) {

                                        IsLastPage = true;
                                    }


                                starFragmentView.onLoadSMoretarSuccess(historyWithStarModel);
                                NextPage = historyWithStarModel.getNextpage();


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
}
