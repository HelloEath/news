package com.glut.news.my.view.activity;

import com.glut.news.common.model.entity.UserModel;

/**
 * Created by yy on 2018/3/24.
 */

public interface IUserAlterActivityView {
    void alterSuccess(UserModel u);
    void alterFail();
    void logOutSuccess();
    void logOutFail();
}
