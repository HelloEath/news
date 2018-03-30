package com.glut.news.my.presenter;

/**
 * Created by yy on 2018/3/24.
 */

public interface IUserAlterActivityPresenter {
    void alterUserName(String newName);
    void alterUserDesc(String newDesc);
    void alterUserSex(String newSex);
    void alterUserBirthday(String newBir);
    void alterUserDistrc(String newDistrc);
    void logOut();

}
