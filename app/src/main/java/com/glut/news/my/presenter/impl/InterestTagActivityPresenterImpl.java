package com.glut.news.my.presenter.impl;

import com.glut.news.common.model.entity.UserInfo;
import com.glut.news.common.model.entity.UserModel;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
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
    public void getUserTagData(UserInfo userInfo) {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "UserService").alterUserInterest(userInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .map(new Func1<UserModel, UserModel>() {
                    @Override
                    public UserModel call(UserModel h) {
                        return h;
                    }
                })
                .subscribe(new Action1<UserModel>() {
                    @Override
                    public void call(UserModel userModel) {
                     if (userModel.getStus().equals("1")){
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
