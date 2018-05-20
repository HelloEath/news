package com.glut.news.my.presenter.impl;

import com.glut.news.common.model.entity.UserInfo;
import com.glut.news.common.model.entity.UserModel;
import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.my.presenter.IUserAlterActivityPresenter;
import com.glut.news.my.view.activity.IUserAlterActivityView;

import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/24.
 */

public class UserAlterActivityPresenterImpl implements IUserAlterActivityPresenter {
   private IUserAlterActivityView iUserAlterActivityView;

    public UserAlterActivityPresenterImpl(IUserAlterActivityView iUserAlterActivityView) {
        this.iUserAlterActivityView = iUserAlterActivityView;
    }

    @Override
    public void alterUserName(String newName) {
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId(Integer.parseInt(SpUtil.getUserFromSp("UserId")));
        userInfo.setUserName(newName);
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "UserService").alterUserName(userInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<UserModel , UserModel>() {
                    @Override
                    public UserModel call(UserModel h) {
                        return h;
                    }
                })
                .subscribe(new Action1<UserModel>() {
                    @Override
                    public void call(UserModel userModel) {
                        //mPbLoading.setVisibility(View.GONE);
                        if (userModel == null) {
                            // mTvLoadEmpty.setVisibility(View.VISIBLE);
                            iUserAlterActivityView.alterFail();
                        } else {

                            if (userModel.getStus().equals("1")){
                                iUserAlterActivityView.alterSuccess(userModel);

                            }else {

                                iUserAlterActivityView.alterFail();
                            }

                            //isloading = false;
                            //mTvLoadEmpty.setVisibility(View.GONE);
                        }
                       /* mLoadLatestSnackbar.dismiss();
                        refreshLayout.setRefreshing(false);
                        mTvLoadError.setVisibility(View.GONE);*/
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

    @Override
    public void alterUserDesc(String newDesc) {
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId(Integer.parseInt(SpUtil.getUserFromSp("UserId")));
        userInfo.setUserSign(newDesc);
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "UserService").alterUserDesc(userInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<UserModel , UserModel>() {
                    @Override
                    public UserModel call(UserModel h) {
                        return h;
                    }
                })
                .subscribe(new Action1<UserModel>() {
                    @Override
                    public void call(UserModel userModel) {
                        //mPbLoading.setVisibility(View.GONE);
                        if (userModel == null) {
                            // mTvLoadEmpty.setVisibility(View.VISIBLE);
                            iUserAlterActivityView.alterFail();
                        } else {

                            if (userModel.getStus().equals("1")){
                                iUserAlterActivityView.alterSuccess(userModel);

                            }else {

                                iUserAlterActivityView.alterFail();
                            }

                            //isloading = false;
                            //mTvLoadEmpty.setVisibility(View.GONE);
                        }
                       /* mLoadLatestSnackbar.dismiss();
                        refreshLayout.setRefreshing(false);
                        mTvLoadError.setVisibility(View.GONE);*/
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

    @Override
    public void alterUserSex(String newSex) {
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId(Integer.parseInt(SpUtil.getUserFromSp("UserId")));
        userInfo.setUserSex(newSex);
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "UserService").alterUserSex(userInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<UserModel , UserModel>() {
                    @Override
                    public UserModel call(UserModel h) {
                        return h;
                    }
                })
                .subscribe(new Action1<UserModel>() {
                    @Override
                    public void call(UserModel userModel) {
                        //mPbLoading.setVisibility(View.GONE);
                        if (userModel == null) {
                            // mTvLoadEmpty.setVisibility(View.VISIBLE);
                            iUserAlterActivityView.alterFail();
                        } else {

                            if (userModel.getStus().equals("1")){
                                iUserAlterActivityView.alterSuccess(userModel);

                            }else {

                                iUserAlterActivityView.alterFail();
                            }

                            //isloading = false;
                            //mTvLoadEmpty.setVisibility(View.GONE);
                        }
                       /* mLoadLatestSnackbar.dismiss();
                        refreshLayout.setRefreshing(false);
                        mTvLoadError.setVisibility(View.GONE);*/
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

    @Override
    public void alterUserBirthday(String newBir) {
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId(Integer.parseInt(SpUtil.getUserFromSp("UserId")));
        userInfo.setUserBirthder(newBir);
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "UserService").alterUserBitrth(userInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .map(new Func1<UserModel , UserModel>() {
                    @Override
                    public UserModel call(UserModel h) {
                        return h;
                    }
                })
                .subscribe(new Action1<UserModel>() {
                    @Override
                    public void call(UserModel userModel) {
                        if (userModel == null) {
                            iUserAlterActivityView.alterFail();
                        } else {

                            if (userModel.getStus().equals("1")){
                                iUserAlterActivityView.alterSuccess(userModel);

                            }else {

                                iUserAlterActivityView.alterFail();
                            }


                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });
    }

    @Override
    public void alterUserDistrc(String newDistrc) {
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId(Integer.parseInt(SpUtil.getUserFromSp("UserId")));
        userInfo.setUserDistrc(newDistrc);
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "UserService").alterUserDistrc(userInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .map(new Func1<UserModel , UserModel>() {
                    @Override
                    public UserModel call(UserModel h) {
                        return h;
                    }
                })
                .subscribe(new Action1<UserModel>() {
                    @Override
                    public void call(UserModel userModel) {
                        if (userModel == null) {
                            iUserAlterActivityView.alterFail();
                        } else {

                            if (userModel.getStus().equals("1")){
                                iUserAlterActivityView.alterSuccess(userModel);

                            }else {

                                iUserAlterActivityView.alterFail();
                            }

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });
    }

    @Override
    public void logOut() {
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId(Integer.parseInt(SpUtil.getUserFromSp("UserId")));

        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "UserService").logOut(userInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<UserModel , UserModel>() {
                    @Override
                    public UserModel call(UserModel h) {
                        return h;
                    }
                })
                .subscribe(new Action1<UserModel>() {
                    @Override
                    public void call(UserModel userModel) {

                        if (userModel == null) {

                            iUserAlterActivityView.logOutFail();
                        } else {

                            if (userModel.getStus().equals("1")){
                                iUserAlterActivityView.logOutSuccess();

                            }else {

                                iUserAlterActivityView.logOutFail();
                            }


                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });
    }

    @Override
    public void alterUserLogo(String userId, RequestBody requestBody) {
       /* RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "UserService").alterUserLogo(userId,requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .map(new Func1<UserModel , UserModel>() {
                    @Override
                    public UserModel call(UserModel h) {
                        return h;
                    }
                })
                .subscribe(new Action1<UserModel>() {
                    @Override
                    public void call(UserModel userModel) {
                        //mPbLoading.setVisibility(View.GONE);
                        if (userModel == null) {
                            // mTvLoadEmpty.setVisibility(View.VISIBLE);
                            iUserAlterActivityView.logOutFail();
                        } else {

                            if (userModel.getStus().equals("1")){
                                iUserAlterActivityView.alterSuccess(userModel);

                            }else {

                                iUserAlterActivityView.alterFail();
                            }


                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });*/
    }

    @Override
    public void alterUserLogoByBase64(String newLogo) {
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId(Integer.parseInt(SpUtil.getUserFromSp("UserId")));
        userInfo.setUserLogo(newLogo);
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "UserService").alterUserLogoByBase64(userInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .map(new Func1<UserModel , UserModel>() {
                    @Override
                    public UserModel call(UserModel h) {
                        return h;
                    }
                })
                .subscribe(new Action1<UserModel>() {
                    @Override
                    public void call(UserModel userModel) {
                        if (userModel == null) {
                            iUserAlterActivityView.alterFail();
                        } else {

                            if (userModel.getStus().equals("1")){
                                iUserAlterActivityView.alterSuccess(userModel);

                            }else {

                                iUserAlterActivityView.alterFail();
                            }


                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });
    }
}
