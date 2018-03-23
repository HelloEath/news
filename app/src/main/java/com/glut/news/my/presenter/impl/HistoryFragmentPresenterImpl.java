package com.glut.news.my.presenter.impl;

import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.my.model.entity.HistoryWithStarModel;
import com.glut.news.my.presenter.IHistoryFragmentPresenter;
import com.glut.news.my.view.fragment.HistoryFragmentView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/14.
 */

public class HistoryFragmentPresenterImpl implements IHistoryFragmentPresenter {
private HistoryFragmentView historyFragmentView;
private boolean IsLastPage;
    private boolean IsLastPage2;
    private int UserId;
    private int NextPage;
    public HistoryFragmentPresenterImpl(HistoryFragmentView historyFragmentView) {
        this.historyFragmentView = historyFragmentView;
        UserId=Integer.parseInt(SpUtil.getUserFromSp("UserId"));
    }



    @Override
    public void loadHistory() {
        String ts=SpUtil.getUserFromSp("UserId");
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "HistoryService").getHistoryList(SpUtil.getUserFromSp("UserId"), 1)
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

                                    IsLastPage2 = true;
                                }


                            historyFragmentView.onLoadHistorySuccess(historyModel);
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
    public void loadMoreHistory() {
        if (!IsLastPage) {
            RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "HistoryService").getHistoryList(SpUtil.getUserFromSp("UserId"), NextPage)
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
                        public void call(HistoryWithStarModel historyModel) {
                            //mPbLoading.setVisibility(View.GONE);
                            if (historyModel == null) {
                                // mTvLoadEmpty.setVisibility(View.VISIBLE);
                            } else {

                                if (historyModel.getData() != null)
                                    if (NextPage == historyModel.getNextpage()) {

                                        IsLastPage = true;
                                    }


                                historyFragmentView.onLoadMoreHistorySuccess(historyModel);
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
    }
}
