package com.glut.news.discover.presenter.impl;

import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.discover.model.entity.GuoKrListModel;
import com.glut.news.discover.presenter.IGuoKrPresenter;
import com.glut.news.discover.view.fragment.IGuoKrFragmentView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/12.
 */

public class GuoKrPresenterImpl implements IGuoKrPresenter {



    private IGuoKrFragmentView iGuoKrFragmentView;


    public GuoKrPresenterImpl(IGuoKrFragmentView c){
        iGuoKrFragmentView =c;

    }
    @Override
    public void loadData() {

        RetrofitManager.builder(RetrofitService.GUOKR_HANDPICK_BASE,"GuoKrService").getLatestNews1()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        iGuoKrFragmentView.showLoading();
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
                        iGuoKrFragmentView.hideLoading();
                        if (guoKrListModel ==null){
                            iGuoKrFragmentView.showEmpty();
                        }else{

                            iGuoKrFragmentView.setAdaterData(guoKrListModel);
                            iGuoKrFragmentView.hideEnpty();
                        }
                       // mLoadLatestSnackbar.dismiss();
                        //refreshLayout.setRefreshing(false);
                        iGuoKrFragmentView.hideLoadError();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                       /* mLoadLatestSnackbar.show();
                        refreshLayout.setRefreshing(false);
                        mLoadLatestSnackbar.show();*/
                        iGuoKrFragmentView.hideEnpty();
                        iGuoKrFragmentView.showLoadError();


                    }
                });

    }

    @Override
    public void loadBeforeData() {

    }
}
