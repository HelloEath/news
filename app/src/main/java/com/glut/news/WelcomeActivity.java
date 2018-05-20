package com.glut.news;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.glut.news.common.model.entity.UserInfo;
import com.glut.news.common.model.entity.UserModel;
import com.glut.news.common.utils.ApiConstants;
import com.glut.news.common.utils.DateUtil;
import com.glut.news.common.utils.SetUtil;
import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.UserUtil;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.common.view.IntroActivity;
import com.glut.news.login.view.fragment.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by yy on 2018/1/27.
 */

/*@RuntimePermissions*/
public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

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
        AppApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        //view = View.inflate(this, R.layout.activity_welcome, null);
        //去掉标题栏
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        String updateTime = String.valueOf(System.currentTimeMillis()); // 在需要重新获取更新的图片时调用
        ImageView imageView = (ImageView) findViewById(R.id.welcomeimage);
       Glide.with(this).load(ApiConstants.welcomeImageAPi)
               .transition(new DrawableTransitionOptions().crossFade(2000))
                .apply(new RequestOptions().signature(new ObjectKey(updateTime)).placeholder(R.drawable.welcome1))
                .into(imageView);

        /*Glide.with(this).load(ApiConstants.welcomeImageAPi)
                .into(imageView);*/
        initPermission();
        //WelcomeActivityPermissionsDispatcher.initLocationPermissionWithCheck(WelcomeActivity.this);


    }

    private void initView() {
        timer.schedule(task, 1000, 1000);//等待时间一秒，停顿时间一秒


        tv = (TextView) findViewById(R.id.btn_tiaoguo);
        tv.setOnClickListener(this);
        TextView t = (TextView) findViewById(R.id.welTime);
        t.setText("今天是" + DateUtil.formatDate_getCurrentDateByF("MM月dd日"));




        /*  正常情况下不点击跳过*/

        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {

                if (SetUtil.getInstance().getIsFirstTime()){
                    Intent intent = new Intent(WelcomeActivity.this, IntroActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    if (UserUtil.isUserLogin()){
                        goLogin();

                    }else {
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }


            }
        }, 5000);//延迟5S后发送handler信息

    }

    private void goLogin() {
        String pnohe=SpUtil.getUserFromSp("UserPhone");
if ("null".equals("pnohe")){
    Intent i2 = new Intent(WelcomeActivity.this, LoginActivity.class);
    startActivity(i2);
    finish();

}else {
   UserInfo userInfo=new UserInfo();
    userInfo.setUserPhone(SpUtil.getUserFromSp("UserPhone"));
    userInfo.setUserPwd(SpUtil.getUserFromSp("UserPwd"));
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "UserService").login(userInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .map(new Func1<UserModel, UserModel>() {
                    @Override
                    public UserModel call(UserModel h) {
                        return h;
                    }
                })
                .subscribe(new Action1<UserModel>() {
                    @Override
                    public void call(UserModel userModel) {

                        if ("0".equals(userModel.getStus())){
                        }else if("1".equals(userModel.getStus())) {
                            SpUtil.saveUserToSp("UserId",userModel.getUserInfo().getUserId()+"");
                            SpUtil.saveUserToSp("UserLogo",userModel.getUserInfo().getUserLogo());
                            SpUtil.saveUserToSp("UserBirth",userModel.getUserInfo().getUserBirthder());
                            SpUtil.saveUserToSp("UserName",userModel.getUserInfo().getUserName());
                            SpUtil.saveUserToSp("UserDis",userModel.getUserInfo().getUserDistrc());
                            SpUtil.saveUserToSp("UserSex",userModel.getUserInfo().getUserSex());
                            SpUtil.saveUserToSp("UserSign",userModel.getUserInfo().getUserSign());
                            Intent i2 = new Intent(WelcomeActivity.this, MainActivity.class);
                            //i2.putExtra("UserId", SpUtil.getUserFromSp("UserId"));
                            startActivity(i2);
                        }else {
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });

}
    }



/*

     *//*
    注解在需要调用运行时权限的方法上，当用户给予权限时会执行该方法
*//*
//,Manifest.permission.WRITE_EXTERNAL_STORAGE
    @NeedsPermission( {Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    public void initLocationPermission() {

     *//*   //从闪屏界面跳转到首界面
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();*//*
    }





*//*    注解在用于向用户解释为什么需要调用该权限的方法上，
只有当第一次请求权限被用户拒绝，下次请求权限之前会调用*//*

    *//*注解在当用户拒绝了权限请求时调用*//*
    @OnPermissionDenied({Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void onLocationDenied() {
        initView();


    }
*//*
    解在当用户选中了授权窗口中的不再询问复选框后并拒绝了权限请求时需要调用的方法，
    一般可以向用户解释为何申请此权限，并根据实际需求决定是否再次弹出权限请求对话框
*//*
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNeverAskAgain() {
        Toast.makeText(this, R.string.permission_camera_never_askagain, Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(PermissionRequest request) {
        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
        showRationaleDialog(R.string.permission_camera_rationale, request);
    }*/

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

                if (SetUtil.getInstance().getIsFirstTime()){
                    Intent intent = new Intent(WelcomeActivity.this, IntroActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    if (UserUtil.isUserLogin()) {
                        goLogin();

                    } else {
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                break;
            default:
                break;
        }

    }


    public void initPermission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
       /* if (ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }*/

        if (ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.RECORD_AUDIO);
        }
        if (!permissionList.isEmpty()) {
            String[] permission = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(WelcomeActivity.this, permission, 1);
        } else {
            initView();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(WelcomeActivity.this, "必须同意所有权限才能使用APP", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }

                    }
                   initView();
                } else {
                    Toast.makeText(WelcomeActivity.this, "发生未知错误！", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }





}
