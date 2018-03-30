package com.glut.news.common.utils;

import android.content.Context;
import android.os.Handler;

import com.glut.news.R;

import net.steamcrafted.loadtoast.LoadToast;

/**
 * Created by yy on 2018/3/25.
 */

public class ToastUtil {

    static LoadToast  lt;

    public ToastUtil(){


    }
    public static void showOnLoading(String msg, Context c){
        if (lt==null){
            lt= new LoadToast(c);
        }
        lt.setTextColor(c.getResources().getColor(R.color.side_1));
        lt.setProgressColor(c.getResources().getColor(R.color.side_1));
        lt.setTranslationY(100);
        lt.setText(msg);
        lt.show();

    }

    public static void showSuccess(String msg,final int delayTime, Context c){
        if (lt==null){
            lt= new LoadToast(c);
        }
        lt.setTextColor(c.getResources().getColor(R.color.side_1));
        lt.setProgressColor(c.getResources().getColor(R.color.side_1));
        lt.setTranslationY(200);
        lt.setText(msg);
        lt.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lt.hide();
            }
        }, delayTime);
    }





    public static void showError(String msg,final int delayTime, Context c){
       if (lt==null){
           lt= new LoadToast(c);
       }

lt.setTextColor(c.getResources().getColor(R.color.side_4));
       lt.setProgressColor(c.getResources().getColor(R.color.side_4));
        lt.setTranslationY(100);
        lt.setText(msg);
        lt.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lt.hide();
            }
        }, delayTime);
    }
}
