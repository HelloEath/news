package com.glut.news.login.view.fragment;

import com.glut.news.common.model.entity.UserModel;

/**
 * Created by yy on 2018/3/17.
 */

public interface IRegisterActivityView {
  void   onRegisterSuccess(UserModel model);
    void onRegisterFail();
}
