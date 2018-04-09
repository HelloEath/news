package com.glut.news.discover.presenter.impl;

import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.discover.model.entity.KaiYanModel;
import com.glut.news.discover.presenter.IKaiYanPresenter;
import com.glut.news.discover.view.fragment.IKaiYanFragmentView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/12.
 */

public class KaiYanPresenterImpl implements IKaiYanPresenter {

    private IKaiYanFragmentView iKaiYanFragmentView;
    private String nextPage;
    public KaiYanPresenterImpl(IKaiYanFragmentView v) {
        iKaiYanFragmentView =v;
    }

    @Override
    public void loadData() {

        RetrofitManager.builder(RetrofitService.KAIYAB_BASE_URL,"KaiYanService").getLatestNews3()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        iKaiYanFragmentView.showLoading();

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
                        iKaiYanFragmentView.hideLoading();
                        if (kaiYanModel ==null){
                            iKaiYanFragmentView.showEmpty();
                        }else{

                            iKaiYanFragmentView.changeAdaterData(kaiYanModel);
                            iKaiYanFragmentView.hideEnpty();
                        }
                        nextPage= kaiYanModel.getNextPageUrl().substring(55, kaiYanModel.getNextPageUrl().length()-7);


                        iKaiYanFragmentView.changeAdaterData(kaiYanModel);
                        iKaiYanFragmentView.hideEnpty();

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iKaiYanFragmentView.hideEnpty();
                        iKaiYanFragmentView.showLoadError();

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
                        iKaiYanFragmentView.hideLoading();
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
                        iKaiYanFragmentView.hideLoading();
                        if (kaiYanModel ==null){
                            iKaiYanFragmentView.showEmpty();
                        }else{

                            iKaiYanFragmentView.addAdaterData(kaiYanModel);
                            iKaiYanFragmentView.hideEnpty();
                        }
                            nextPage= kaiYanModel.getNextPageUrl().substring(55, kaiYanModel.getNextPageUrl().length()-7);


                            iKaiYanFragmentView.addAdaterData(kaiYanModel);
                            iKaiYanFragmentView.hideEnpty();
                        }
                        // mLoadLatestSnackbar.dismiss();
                        //refreshLayout.setRefreshing(false);
                    //iKaiYanFragmentView.hideLoadError();

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                       /* mLoadLatestSnackbar.show();
                        refreshLayout.setRefreshing(false);
                        mLoadLatestSnackbar.show();*/
                        iKaiYanFragmentView.hideEnpty();
                        iKaiYanFragmentView.showLoadError();
                    }
                });
    }
}
