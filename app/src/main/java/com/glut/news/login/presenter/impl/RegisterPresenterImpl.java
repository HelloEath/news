package com.glut.news.login.presenter.impl;

import com.glut.news.common.model.entity.UserInfo;
import com.glut.news.common.model.entity.UserModel;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.login.presenter.IRegisterActivityPresenter;
import com.glut.news.login.view.fragment.IRegisterActivityView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/17.
 */

public class RegisterPresenterImpl implements IRegisterActivityPresenter {

private IRegisterActivityView iRegisterActivityView;

    public RegisterPresenterImpl(IRegisterActivityView iRegisterActivityView) {
        this.iRegisterActivityView = iRegisterActivityView;
    }

    @Override
    public void toRegister(UserInfo userInfo) {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "UserService").register(userInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
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

                            iRegisterActivityView.onRegisterSuccess(userModel);
                        }else if(userModel.getStus().equals("0")){

                            iRegisterActivityView.onRegisterFail();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });
    }
}
