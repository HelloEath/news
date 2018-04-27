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
    /*从sp获取用户设置值*/
    public static boolean getSetingFromSp(String key){
        SharedPreferences sp= AppApplication.getContext().getSharedPreferences("setting", Context.MODE_PRIVATE);

        return sp.getBoolean(key,false);
    }
    /*设置设置值*/
    public static void setSetingToSp(String key,boolean b){
        SharedPreferences sp= AppApplication.getContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
        //获取sp对象
        SharedPreferences.Editor se=sp.edit();
        se.putBoolean(key,b);
        se.commit();

    }
}
