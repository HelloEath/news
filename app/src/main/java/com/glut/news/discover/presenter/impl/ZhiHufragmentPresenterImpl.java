package com.glut.news.discover.presenter.impl;

import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.discover.model.entity.ZhiHuList;
import com.glut.news.discover.model.entity.ZhiHuNewsModel;
import com.glut.news.discover.presenter.IZhiHuFragmentPresenter;
import com.glut.news.discover.view.fragment.IZhiHuFragmentView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/4/3.
 */

public class ZhiHufragmentPresenterImpl implements IZhiHuFragmentPresenter {
    private String currentDate;

private IZhiHuFragmentView iZhiHuFragmentView;

    public ZhiHufragmentPresenterImpl(IZhiHuFragmentView iZhiHuFragmentView) {
        this.iZhiHuFragmentView = iZhiHuFragmentView;
    }

    @Override
    public void loadData() {
        RetrofitManager.builder(RetrofitService.BASE_ZHIHU_URL,"ZhuHuService").getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .map(new Func1<ZhiHuList, ZhiHuList>() {
                    @Override
                    public ZhiHuList call(ZhiHuList zhiHuList2) {
                        return zhiHuList2;
                    }
                })
                .subscribe(new Action1<ZhiHuList>() {
                    @Override
                    public void call(ZhiHuList zhiHuList2) {
                        if (zhiHuList2==null){
                        }else{
                            currentDate=zhiHuList2.getDate();
                            for (ZhiHuNewsModel z:zhiHuList2.getStories()){

                                z.setDate(currentDate);
                            }
                            iZhiHuFragmentView.onLoadDataSuccess(zhiHuList2);

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iZhiHuFragmentView.onLoadDataFail();

                    }
                });

    }

    @Override
    public void loadMoreData() {
        RetrofitManager.builder(RetrofitService.BASE_ZHIHU_URL,"ZhuHuService").getBeforeData(currentDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .map(new Func1<ZhiHuList, ZhiHuList>() {
                    @Override
                    public ZhiHuList call(ZhiHuList zhiHuList2) {
                        return zhiHuList2;
                    }
                })
                .subscribe(new Action1<ZhiHuList>() {
                    @Override
                    public void call(ZhiHuList zhiHuList) {
                        if (zhiHuList==null){
                        }else{
                            currentDate = zhiHuList.getDate();
                            for (ZhiHuNewsModel z:zhiHuList.getStories()){

                                z.setDate(currentDate);
                            }
                            iZhiHuFragmentView.onLoadMoreDataSuccess(zhiHuList);

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iZhiHuFragmentView.onLoadMoreDataFail();
                    }
                });
    }
}
