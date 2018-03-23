package com.glut.news.discover.presenter.impl;

import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.discover.model.entity.GuoKrListModel;
import com.glut.news.discover.presenter.IGuoKrPresenter;
import com.glut.news.discover.view.fragment.activity.IGuoKrView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/12.
 */

public class GuoKrPresenterImpl implements IGuoKrPresenter {



    private IGuoKrView iGuoKrView;


    public GuoKrPresenterImpl(IGuoKrView  c){
        iGuoKrView=c;

    }
    @Override
    public void loadData() {

        RetrofitManager.builder(RetrofitService.GUOKR_HANDPICK_BASE,"GuoKrService").getLatestNews1()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        iGuoKrView.showLoading();
                    }
                })
                .map(new Func1<GuoKrListModel, GuoKrListModel>() {
                    @Override
                    public GuoKrListModel call(GuoKrListModel guoKrListModel) {
                        return guoKrListModel;
                    }
                })
                .subscribe(new Action1<GuoKrListModel>() {
                    @Override
                    public void call(GuoKrListModel guoKrListModel) {
                        iGuoKrView.hideLoading();
                        if (guoKrListModel ==null){
                            iGuoKrView.showEmpty();
                        }else{

                            iGuoKrView.setAdaterData(guoKrListModel);
                            iGuoKrView.hideEnpty();
                        }
                       // mLoadLatestSnackbar.dismiss();
                        //refreshLayout.setRefreshing(false);
                        iGuoKrView.hideLoadError();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                       /* mLoadLatestSnackbar.show();
                        refreshLayout.setRefreshing(false);
                        mLoadLatestSnackbar.show();*/
                        iGuoKrView.hideEnpty();
                        iGuoKrView.showLoadError();


                    }
                });

    }

    @Override
    public void loadBeforeData() {

    }
}
