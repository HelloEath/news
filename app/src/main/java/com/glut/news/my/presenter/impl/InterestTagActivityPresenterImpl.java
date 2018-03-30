package com.glut.news.my.presenter.impl;

import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.my.model.entity.InterestTag;
import com.glut.news.my.presenter.IInterestTagActivityPresenter;
import com.glut.news.my.view.activity.IInterestTagActivityView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/27.
 */

public class InterestTagActivityPresenterImpl implements IInterestTagActivityPresenter {

    private IInterestTagActivityView i;

    public InterestTagActivityPresenterImpl(IInterestTagActivityView i) {
        this.i = i;
    }

    @Override
    public void getUserTagData(InterestTag interestTag) {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "InterestTagService").doInterestTag(interestTag)
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
                    public void call(Integer historyModel) {
                     if (historyModel==1){
                         i.interestTagsuccess();
                     }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });
    }
}
