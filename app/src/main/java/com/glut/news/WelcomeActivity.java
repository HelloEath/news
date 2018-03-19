package com.glut.news;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.glut.news.login.view.activity.LoginActivity;
import com.glut.news.common.utils.ApiConstants;
import com.glut.news.common.utils.DateUtil;
import com.glut.news.view.WelcomeActivityPermissionsDispatcher;

import java.util.Timer;
import java.util.TimerTask;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by yy on 2018/1/27.
 */

@RuntimePermissions
public class WelcomeActivity extends Activity implements View.OnClickListener {

    private int recLen = 5;//跳过倒计时提示5秒
    private TextView tv;
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;
    Context c;
    private boolean isGrantPerssion;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //view = View.inflate(this, R.layout.activity_welcome, null);
        //去掉标题栏
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ImageView imageView=findViewById(R.id.welcomeimage);
        Glide.with(this).load(ApiConstants.welcomeImageAPi)
                .into(imageView);
        WelcomeActivityPermissionsDispatcher.initLocationPermissionWithCheck(WelcomeActivity.this);



    }

    private void initView() {
        timer.schedule(task, 1000, 1000);//等待时间一秒，停顿时间一秒


        tv=findViewById(R.id.btn_tiaoguo);
        tv.setOnClickListener(this);
        TextView t=findViewById(R.id.welTime);
        t.setText("今天是"+ DateUtil.formatDate_getCurrentDateByF("MM月dd日"));




        /*  正常情况下不点击跳过*/

        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {

                //从闪屏界面跳转到首界面
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();


            }
        }, 5000);//延迟5S后发送handler信息

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WelcomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /*
    注解在需要调用运行时权限的方法上，当用户给予权限时会执行该方法
*/
//,Manifest.permission.WRITE_EXTERNAL_STORAGE
    @NeedsPermission( {Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    public void initLocationPermission() {

     /*   //从闪屏界面跳转到首界面
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();*/
    }





/*    注解在用于向用户解释为什么需要调用该权限的方法上，
只有当第一次请求权限被用户拒绝，下次请求权限之前会调用*/

    /*注解在当用户拒绝了权限请求时调用*/
    @OnPermissionDenied({Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void onLocationDenied() {
        initView();


    }
/*
    解在当用户选中了授权窗口中的不再询问复选框后并拒绝了权限请求时需要调用的方法，
    一般可以向用户解释为何申请此权限，并根据实际需求决定是否再次弹出权限请求对话框
*/
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNeverAskAgain() {
        Toast.makeText(this, R.string.permission_camera_never_askagain, Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(PermissionRequest request) {
        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
        showRationaleDialog(R.string.permission_camera_rationale, request);
    }
    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    tv.setText("跳过 " + recLen);
                    if (recLen < 0) {
                        timer.cancel();
                        tv.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                }
            });
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tiaoguo:
                //从闪屏界面跳转到首界面
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                break;
            default:
                break;
        }

    }
}
