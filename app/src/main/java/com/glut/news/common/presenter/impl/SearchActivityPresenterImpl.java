package com.glut.news.common.presenter.impl;

import com.glut.news.common.presenter.ISearchActivityPresenter;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.common.view.ISearchActivityView;
import com.glut.news.my.model.entity.HistoryWithStarModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/19.
 */

public class SearchActivityPresenterImpl implements ISearchActivityPresenter {
   private ISearchActivityView iSearchActivityView;
    private int NextPage;

    public SearchActivityPresenterImpl(ISearchActivityView iSearchActivityView) {
        this.iSearchActivityView = iSearchActivityView;
    }

    @Override
    public void search(String searchValue) {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "SearchService").doSearch(searchValue,1)
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
                    public void call(HistoryWithStarModel sum) {
                        if (sum.getStus().equals("1")){
                            NextPage= sum.getNextpage();
                            iSearchActivityView.onSearchSuccess(sum);

                        }else {
                            iSearchActivityView.onSearchFail();
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });
    }



    @Override
    public void loadMoreSearch(String v) {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "SearchService").doSearch(v,NextPage)
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
                    public void call(HistoryWithStarModel sum) {
                        if (sum.getStus().equals("1")){
                           NextPage= sum.getNextpage();
                            iSearchActivityView.onSearchSuccess(sum);

                        }else {
                            iSearchActivityView.onSearchFail();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });
    }
}
