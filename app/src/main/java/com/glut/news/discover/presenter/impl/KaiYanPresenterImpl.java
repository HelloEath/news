package com.glut.news.discover.presenter.impl;

import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.discover.model.entity.KaiYanModel;
import com.glut.news.discover.presenter.IKaiYanPresenter;
import com.glut.news.discover.view.fragment.activity.IKaiYanView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/12.
 */

public class KaiYanPresenterImpl implements IKaiYanPresenter {

    private IKaiYanView iKaiYanView;
    private String nextPage;
    public KaiYanPresenterImpl(IKaiYanView v) {
        iKaiYanView=v;
    }

    @Override
    public void loadData() {

        RetrofitManager.builder(RetrofitService.KAIYAB_BASE_URL,"KaiYanService").getLatestNews3()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        iKaiYanView.showLoading();

                    }
                })
                .map(new Func1<KaiYanModel, KaiYanModel>() {
                    @Override
                    public KaiYanModel call(KaiYanModel kaiYanModel) {
                        return kaiYanModel;
                    }
                })
                .subscribe(new Action1<KaiYanModel>() {
                    @Override
                    public void call(KaiYanModel kaiYanModel) {
                        iKaiYanView.hideLoading();
                        if (kaiYanModel ==null){
                            iKaiYanView.showEmpty();
                        }else{

                            iKaiYanView.changeAdaterData(kaiYanModel);
                            iKaiYanView.hideEnpty();
                        }
                        nextPage= kaiYanModel.getNextPageUrl().substring(55, kaiYanModel.getNextPageUrl().length()-7);


                        iKaiYanView.changeAdaterData(kaiYanModel);
                        iKaiYanView.hideEnpty();

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iKaiYanView.hideEnpty();
                        iKaiYanView.showLoadError();

                    }
                });
    }

    @Override
    public void loadBeforeData() {

        RetrofitManager.builder(RetrofitService.KAIYAB_BASE_URL,"KaiYanService").getNextPageNews(nextPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        iKaiYanView.hideLoading();
                    }
                })
                .map(new Func1<KaiYanModel, KaiYanModel>() {
                    @Override
                    public KaiYanModel call(KaiYanModel kaiYanModel) {
                        return kaiYanModel;
                    }
                })
                .subscribe(new Action1<KaiYanModel>() {
                    @Override
                    public void call(KaiYanModel kaiYanModel) {
                        iKaiYanView.hideLoading();
                        if (kaiYanModel ==null){
                            iKaiYanView.showEmpty();
                        }else{

                            iKaiYanView.addAdaterData(kaiYanModel);
                            iKaiYanView.hideEnpty();
                        }
                            nextPage= kaiYanModel.getNextPageUrl().substring(55, kaiYanModel.getNextPageUrl().length()-7);


                            iKaiYanView.addAdaterData(kaiYanModel);
                            iKaiYanView.hideEnpty();
                        }
                        // mLoadLatestSnackbar.dismiss();
                        //refreshLayout.setRefreshing(false);
                    //iKaiYanView.hideLoadError();

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                       /* mLoadLatestSnackbar.show();
                        refreshLayout.setRefreshing(false);
                        mLoadLatestSnackbar.show();*/
                        iKaiYanView.hideEnpty();
                        iKaiYanView.showLoadError();
                    }
                });
    }
}
