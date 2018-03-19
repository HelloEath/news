package com.glut.news.common.utils;

import java.text.SimpleDateFormat;

/**
 * Created by yy on 2018/2/3.
 */

public class DateUtil {
    public static String formatDate(String date) {
        String dateFormat = null;
        try {
            dateFormat = date.substring(4, 6) + "月" + date.substring(6, 8) + "日";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateFormat;
    }

    public static String formatDate_GuoKr(String date) {
        String dateFormat = null;
        try {
            dateFormat = date.substring(5, 7) + "月" + date.substring(8, 10) + "日"+date.substring(11, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateFormat;
    }

    //获取当前系统时间：格式：2017-02-04 12:20
    public static String formatDate_getCurrentDate(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    //获取当前系统时间：格式：按照传入的格式定
    public static String formatDate_getCurrentDateByF(String f){
        SimpleDateFormat sDateFormat = new SimpleDateFormat(f);
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }
}
