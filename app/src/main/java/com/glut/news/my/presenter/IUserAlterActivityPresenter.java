package com.glut.news.my.presenter;

import okhttp3.RequestBody;

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

    void alterUserLogo(String userId, RequestBody requestBody);
    void alterUserLogoByBase64(String newLogo);
}
