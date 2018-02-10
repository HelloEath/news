package com.glut.news.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yy on 2018/1/27.
 */

public class AppApplication extends Application {
    private static Application mapplication;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;

    }
    public static Context getContext(){
        return context;
    }
   /* 获取application*/
    public Application getApplication(){
        return mapplication;
    }
   /* 获取数据库*/

    public SQLiteOpenHelper getSQL(){


        return null;
    }
}
