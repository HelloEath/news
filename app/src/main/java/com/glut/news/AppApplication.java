package com.glut.news;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yy on 2018/1/27.
 */

public class AppApplication extends Application {
    private static Application mapplication;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private static Context context;
    public static List<Object> activitys = new ArrayList<Object>();
    private static AppApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
     //  RxTool.init(this);//初始化工具箱
        context=this;





    }



    //获取单例模式中唯一的MyApplication实例

    public static AppApplication getInstance() {
        if (instance == null)
            instance = new AppApplication();
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (!activitys.contains(activity))
            activitys.add(activity);
    }

    // 遍历所有Activity并finish
    public void destory() {

        for (Object activity : activitys) {
            ((Activity) activity).finish();
        }
        System.exit(0);
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
