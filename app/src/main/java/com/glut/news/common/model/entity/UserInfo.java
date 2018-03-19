package com.glut.news.common.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yy on 2018/3/17.
 */
public class UserInfo {
    @SerializedName("User_Id")
    private int UserId;
    @SerializedName("User_NickName")
    private String UserName;
    @SerializedName("User_Password")
    private String UserPwd;
    @SerializedName("User_Email")
    private String UserEmail;
    @SerializedName("User_PhoneNum")
    private String UserPhone;
    @SerializedName("User_Birthday")
    private String UserBirthder;
    @SerializedName("User_District")
    private String UserDistrc;
    @SerializedName("User_Sex")
    private String UserSex;
    @SerializedName("User_Picture")
    private String UserLogo;
    @SerializedName("User_Describe")
    private String UserSign;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPwd() {
        return UserPwd;
    }

    public void setUserPwd(String userPwd) {
        UserPwd = userPwd;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserBirthder() {
        return UserBirthder;
    }

    public void setUserBirthder(String userBirthder) {
        UserBirthder = userBirthder;
    }

    public String getUserDistrc() {
        return UserDistrc;
    }

    public void setUserDistrc(String userDistrc) {
        UserDistrc = userDistrc;
    }

    public String getUserSex() {
        return UserSex;
    }

    public void setUserSex(String userSex) {
        UserSex = userSex;
    }

    public String getUserLogo() {
        return UserLogo;
    }

    public void setUserLogo(String userLogo) {
        UserLogo = userLogo;
    }

    public String getUserSign() {
        return UserSign;
    }

    public void setUserSign(String userSign) {
        UserSign = userSign;
    }
}
