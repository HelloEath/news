package com.glut.news.login.view.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.glut.news.AppApplication;
import com.glut.news.MainActivity;
import com.glut.news.R;
import com.glut.news.common.model.entity.UserInfo;
import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.ToastUtil;
import com.glut.news.login.presenter.impl.LoginActivityPresenterImpl;
import com.glut.news.my.view.activity.InterestTagActivity;


/**
 * Created by yy on 2018/3/17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,ILoginActivityView {

    private EditText etUsername;

    private EditText etPassword;

    private Button btGo;

    private CardView cv;

    private ImageView btn_WeChat;
    private ImageView btn_QQ;
    private TextView btn_forGetPwd;
    private FloatingActionButton fab2;
    private RelativeLayout login_bg;
    private FloatingActionButton fab;
private LoginActivityPresenterImpl l=new LoginActivityPresenterImpl(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏半透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_login);
        initView();
        initData();
        AppApplication.getInstance().addActivity(this);
    }

    private void initData() {
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                login_bg.setBackground(resource);
            }
        };
        Glide.with(this).load(R.drawable.login_bg).into(simpleTarget);
        Glide.with(this).load(R.drawable.qq_icon).into(btn_QQ);
        Glide.with(this).load(R.drawable.wechat_icon).into(btn_WeChat);

    }

    private void initView() {
        login_bg= (RelativeLayout) findViewById(R.id.login_bg);
        btn_forGetPwd= (TextView) findViewById(R.id.btn_forgetPwd);
        btn_QQ= (ImageView) findViewById(R.id.btn_QQ);
        btn_WeChat= (ImageView) findViewById(R.id.btn_WeChat);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btGo = (Button) findViewById(R.id.bt_go);
        cv = (CardView) findViewById(R.id.cv);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab2= (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(this);
        //cv.setOnClickListener(this);
        btGo.setOnClickListener(this);
        btn_WeChat.setOnClickListener(this);
        btn_forGetPwd.setOnClickListener(this);
        btn_QQ.setOnClickListener(this);
        fab2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fab:

                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
                break;
           case R.id.bt_go:

                UserInfo u=new UserInfo();
               String t=etUsername.getText().toString();
               String UserPwd=etPassword.getText().toString();
               startActivity(new Intent(LoginActivity.this, InterestTagActivity.class));
             if (valiLogin(u,t,UserPwd)){
                 ToastUtil.showOnLoading("登陆是需要时间的...",LoginActivity.this);

                 l.toLogin(u);

             }else {
                 //lt.error();
             }


                break;
            case R.id.btn_forgetPwd:
                /*//要做的动画
                Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
//退出时使用
                getWindow().setExitTransition(explode);
//第一次进入时使用
                getWindow().setEnterTransition(explode);
//再次进入时使用
                getWindow().setReenterTransition(explode);

                startActivity(new Intent(this, ForgetPwdActivity.class),ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                *//*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }*/

                break;
            case R.id.btn_QQ:
                //lt.error();

                break;
            case R.id.btn_WeChat:
              //  lt.success();
               // lt.hide();

                break;
            case R.id.fab2:
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                break;
        }

    }

    private boolean valiLogin(UserInfo u,String t,String UserPwd) {
       boolean f=false;
        if (!"".equals(t)){

            if (t.contains("@")){
                u.setUserEmail(t);


            }else {

                u.setUserPhone(t);
            }
            f=true;
        }else{
            etUsername.setError("账户不能为空");
            f=false;
        }


        if (!"".equals(UserPwd)){
            f=true;
            u.setUserPwd(UserPwd);
        }else{
            etUsername.setError("密码不能为空");
            f=false;
        }

        return f;
    }


    @Override
    public void onLoginSuccess() {
        Explode explode = new Explode();
        explode.setDuration(500);

        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        Intent i2 = new Intent(this, InterestTagActivity.class);
        i2.putExtra("UserId", SpUtil.getUserFromSp("UserId"));
        startActivity(i2, oc2.toBundle());

        finish();
    }

    @Override
    public void onUserUnExist() {
        ToastUtil.showError("我的数据库没有你的信息...",3000,LoginActivity.this);
    }

    @Override
    public void onUserPwdError() {
        ToastUtil.showError("你的密码有点问题...",3000,LoginActivity.this);

    }
}
