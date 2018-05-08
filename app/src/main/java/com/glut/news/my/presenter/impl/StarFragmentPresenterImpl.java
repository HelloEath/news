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
        if (SpUtil.getUserFromSp("UserId") == null) {

            UserId = Integer.parseInt(SpUtil.getUserFromSp("UserId"));

        }
    }

    @Override
    public void loadStarData(final String fp) {
        if ("fp".equals(fp)) {
            NextPage = 1;
        }
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "StarService").getStarList(UserId, NextPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
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
                        if (historyModel == null) {
                            starFragmentView.onLoadStarFail();
                        } else {

                            if (historyModel.getData() != null)
                                if (historyModel.isHaveNextPage()) {

                                    if ("fp".equals(fp)) {//上拉刷新
                                        starFragmentView.onLoadStarSuccess(historyModel);
                                    } else {//加载更多
                                        starFragmentView.onLoadSMoretarSuccess(historyModel);

                                    }
                                    NextPage = historyModel.getNextpage();
                                } else {
                                    starFragmentView.onLoadSMoretarSuccess(historyModel);
                                    starFragmentView.onNoMoreData();

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
