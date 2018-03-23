package com.glut.news.home.presenter.impl;

import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.home.presenter.IHomeFragment;
import com.glut.news.home.view.fragment.IHomeFragmentView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/15.
 */

public class HomeFragmentImpl implements IHomeFragment {
    private IHomeFragmentView iHomeFragmentView;

    public HomeFragmentImpl(IHomeFragmentView iHomeFragmentView) {
        this.iHomeFragmentView = iHomeFragmentView;
    }

    @Override
    public void loadStarCount() {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "StarService").getStarCOunt(Integer.parseInt(SpUtil.getUserFromSp("UserId")))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer h) {
                        return h;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer sum) {
                        iHomeFragmentView.onLoadStarCountSuccess(sum);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });
    }

    @Override
    public void loadHistoryCount() {
        String d=SpUtil.getUserFromSp("UserId");
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "HistoryService").getHistoryCount(SpUtil.getUserFromSp("UserId"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer h) {
                        return h;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer num) {

                        iHomeFragmentView.onLoadHistoryCountSuccess(num);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });
    }


}
