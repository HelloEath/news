package com.glut.news.common.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yy on 2018/3/17.
 */

public class UserModel {
    @SerializedName("stus")
    private String stus;
  @SerializedName("user")
    private UserInfo user;

    public String getStus() {
        return stus;
    }

    public void setStus(String stus) {
        this.stus = stus;
    }

    public UserInfo getUserInfo() {
        return user;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.user = userInfo;
    }
}
