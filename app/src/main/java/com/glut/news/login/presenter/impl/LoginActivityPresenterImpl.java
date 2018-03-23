package com.glut.news.login.presenter.impl;

import com.glut.news.common.model.entity.UserInfo;
import com.glut.news.common.model.entity.UserModel;
import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.login.presenter.ILoginActivityPresenter;
import com.glut.news.login.view.fragment.ILoginActivityView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/17.
 */

public class LoginActivityPresenterImpl implements ILoginActivityPresenter {

    private ILoginActivityView loginActivityView;

    public LoginActivityPresenterImpl(ILoginActivityView loginActivityView) {
        this.loginActivityView = loginActivityView;
    }

    @Override
    public void toLogin(UserInfo userInfo) {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "UserService").login(userInfo)
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

                        if ("0".equals(userModel.getStus())){

                            loginActivityView.onUserPwdError();
                        }else if("1".equals(userModel.getStus())) {

                            SpUtil.saveUserToSp("UserId",userModel.getUserInfo().getUserId()+"");
                            SpUtil.saveUserToSp("UserLogo",userModel.getUserInfo().getUserLogo());
                            SpUtil.saveUserToSp("UserBirth",userModel.getUserInfo().getUserBirthder());
                            SpUtil.saveUserToSp("UserName",userModel.getUserInfo().getUserName());
                            SpUtil.saveUserToSp("UserDis",userModel.getUserInfo().getUserDistrc());
                            SpUtil.saveUserToSp("UserSex",userModel.getUserInfo().getUserSex());
                            SpUtil.saveUserToSp("UserSign",userModel.getUserInfo().getUserSign());

                            loginActivityView.onLoginSuccess();
                        }else {
                            loginActivityView.onUserUnExist();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });
    }
}
