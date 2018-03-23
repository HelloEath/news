package com.glut.news.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.glut.news.AppApplication;

/**
 * Created by yy on 2018/3/4.
 */

public class SpUtil {

   /* 保存用户数据到sp*/
    public static void  saveUserToSp(String key,String value){
        SharedPreferences sp= AppApplication.getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        //获取sp对象
        SharedPreferences.Editor se=sp.edit();
            se.putString(key,value);
        se.commit();


    }

    /*从sp获取用户数据*/
    public static String getUserFromSp(String key){
        SharedPreferences sp= AppApplication.getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        return sp.getString(key,"null");
    }

}
