package com.glut.news.common.utils;

/**
 * Created by yy on 2018/3/30.
 */

public class UserUtil {
    public static boolean isUserLogin(){

        String d=SpUtil.getUserFromSp("UserId");
        if (!"null".equals(SpUtil.getUserFromSp("UserId"))){
            return true;
        }
        return false;
    }
}
