package com.glut.news.discover.presenter.impl;

import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.discover.model.entity.OneDateListModel;
import com.glut.news.discover.model.entity.OneModel;
import com.glut.news.discover.presenter.IOneFragmentPresenter;
import com.glut.news.discover.view.fragment.IOneFragmentView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/4/3.
 */

public class OneFragmentPresenterImpl implements IOneFragmentPresenter {

    IOneFragmentView iOneFragmentView;
    private int currentDate;

    public OneFragmentPresenterImpl(IOneFragmentView iOneFragmentView) {
        this.iOneFragmentView = iOneFragmentView;
    }

    @Override
    public void loadData() {
        RetrofitManager.builder(RetrofitService.ONE_MOMENT_BASE,"OneService").getOneDateList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<OneDateListModel, OneDateListModel>() {
                    @Override
                    public OneDateListModel call(OneDateListModel douBanList) {
                        return douBanList;
                    }
                })
                .subscribe(new Action1<OneDateListModel>() {
                    @Override
                    public void call(OneDateListModel oneDateListModel) {
                       // mPbLoading.setVisibility(View.GONE);
                        if (oneDateListModel ==null){
                            //mTvLoadEmpty.setVisibility(View.VISIBLE);
                        }else{

                            currentDate=Integer.parseInt(oneDateListModel.getData().get(0))-1;
                            loadData2(oneDateListModel.getData().get(0));
                            //oneAdater.changeData(douBanList.getPosts());
                            //mTvLoadEmpty.setVisibility(View.GONE);
                        }
                        //mLoadLatestSnackbar.dismiss();
                        //refreshLayout.setRefreshing(false);
                       // mTvLoadError.setVisibility(View.GONE);
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

    private void loadData2(String s){

        RetrofitManager.builder(RetrofitService.ONE_MOMENT_BASE,"OneService").getOneLasteNews(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                       // mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<OneModel, OneModel>() {
                    @Override
                    public OneModel call(OneModel oneModel) {
                        return oneModel;
                    }
                })
                .subscribe(new Action1<OneModel>() {
                    @Override
                    public void call(OneModel oneModel) {
                        //mPbLoading.setVisibility(View.GONE);
                        if (oneModel ==null){
                            //mTvLoadEmpty.setVisibility(View.VISIBLE);
                        }else{
                            iOneFragmentView.loadDataSuccess(oneModel);
                           // oneAdater.changeData(oneModel.getData().getContent_list());
                            //mTvLoadEmpty.setVisibility(View.GONE);
                        }
                        //mLoadLatestSnackbar.dismiss();
                       // refreshLayout.setRefreshing(false);
                       // mTvLoadError.setVisibility(View.GONE);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                      /*  mLoadLatestSnackbar.show();
                        refreshLayout.setRefreshing(false);
                        mLoadLatestSnackbar.show();
                        mTvLoadError.setVisibility(View.VISIBLE);
                        mTvLoadEmpty.setVisibility(View.GONE);*/

                    }
                });

    }

    @Override
    public void loadMoreData() {
        RetrofitManager.builder(RetrofitService.ONE_MOMENT_BASE,"OneService").getOneLasteNews(currentDate+"")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<OneModel, OneModel>() {
                    @Override
                    public OneModel call(OneModel oneModel) {
                        return oneModel;
                    }
                })
                .subscribe(new Action1<OneModel>() {
                    @Override
                    public void call(OneModel oneModel) {
                       // mPbLoading.setVisibility(View.GONE);
                        if (oneModel ==null){
                           // mTvLoadEmpty.setVisibility(View.VISIBLE);
                        }else{
                            currentDate = currentDate-1;
                            iOneFragmentView.loadMoreDataSuccess(oneModel);
                            //oneAdater.addData(oneModel.getData().getContent_list());
                           // mTvLoadEmpty.setVisibility(View.GONE);
                        }
                        //mLoadBeforeSnackbar.dismiss();
                       // mTvLoadError.setVisibility(View.GONE);
                        //isloading=false;
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                       // mLoadBeforeSnackbar.show();
                       // mPbLoading.setVisibility(View.GONE);
                       // mTvLoadError.setVisibility(View.VISIBLE);
                       // mTvLoadEmpty.setVisibility(View.GONE);
                    }
                });
    }
}
