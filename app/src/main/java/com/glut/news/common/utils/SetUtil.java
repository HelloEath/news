package com.glut.news.common.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import com.glut.news.AppApplication;

/**
 * Created by yy on 2018/3/29.
 */

public class SetUtil {
    private SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(AppApplication.getContext());

    public static SetUtil getInstance() {
        return SettingsUtilInstance.instance;
    }


    private static final class SettingsUtilInstance {
        private static final SetUtil instance = new SetUtil();
    }


    public void setStatusColor(int color,Window window){

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(color);
    }
    public boolean getIsFirstTime() {
        return setting.getBoolean("first_time", true);
    }

    public void setIsFirstTime(boolean flag) {
        setting.edit().putBoolean("first_time", flag).apply();
    }
}
