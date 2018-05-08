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
    private int UserId;
    private int NextPage;
    public HistoryFragmentPresenterImpl(HistoryFragmentView historyFragmentView) {
        this.historyFragmentView = historyFragmentView;
        UserId=Integer.parseInt(SpUtil.getUserFromSp("UserId"));
    }



    @Override
    public void loadHistory(final String fp) {
        if ("fp".equals(fp)){
            NextPage=1;
        }
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "HistoryService").getHistoryList(UserId+"", NextPage)
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
                            historyFragmentView.noHistoryData();
                        } else {

                            if (historyModel.getData() != null) {

                                if (historyModel.isHaveNextPage()) {

                                    if ("fp".equals(fp)) {//刷新

                                        historyFragmentView.onLoadHistorySuccess(historyModel);
                                    } else {//加载更多
                                        historyFragmentView.onLoadMoreHistorySuccess(historyModel);
                                    }
                                    NextPage = historyModel.getNextpage();

                                } else {
                                    historyFragmentView.onLoadMoreHistorySuccess(historyModel);

                                    historyFragmentView.onNoMoreHistoryData();
                                }
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
