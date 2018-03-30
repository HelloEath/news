package com.glut.news.login.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.glut.news.AppApplication;
import com.glut.news.MainActivity;
import com.glut.news.R;
import com.glut.news.common.model.entity.UserInfo;
import com.glut.news.common.model.entity.UserModel;
import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.ToastUtil;
import com.glut.news.login.presenter.impl.RegisterActivityPresenterImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by yy on 2018/3/17.
 */

public class RegisterActivity extends AppCompatActivity implements IRegisterActivityView{

    private RegisterActivityPresenterImpl iRr=new RegisterActivityPresenterImpl(this);
  private   FloatingActionButton fab;
    private   CardView cvAdd;
    private EditText et_useremail;
    private EditText et_userName;
    private EditText et_userPwd;
    private EditText et_rePwd;
    private Button bt_go;
    private static final String PHONE_PATTERN = "[1][34578]\\d{9}";

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern;
    private Matcher matcher;
    private String    UserName;
    private String    UserPwd;
    private RelativeLayout logister_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_register);
        initView();
        AppApplication.getInstance().addActivity(this);

//转场动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });
    }

    private void initView() {
        logister_bg= (RelativeLayout) findViewById(R.id.register_bg);
        fab= (FloatingActionButton) findViewById(R.id.fab);
        cvAdd= (CardView) findViewById(R.id.cv_add);
        et_useremail= (EditText) findViewById(R.id.et_useremail);
        et_userName= (EditText) findViewById(R.id.et_username);
        et_userPwd= (EditText) findViewById(R.id.et_password);
        et_rePwd= (EditText) findViewById(R.id.et_repeatpassword);
        bt_go= (Button) findViewById(R.id.bt_go);
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                logister_bg.setBackground(resource);
            }
        };
        Glide.with(this).load(R.drawable.login_bg).into(simpleTarget);
        bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    UserName =et_userName.getText().toString();
                     UserPwd=et_rePwd.getText().toString();
                String UserRePwd=et_rePwd.getText().toString();

                String Test=et_useremail.getText().toString();
                UserInfo userInfo =new UserInfo();
              if ( vailUserInfo(userInfo,UserName,UserPwd,UserRePwd,Test)){
                  ToastUtil.showOnLoading("正在注册",RegisterActivity.this);

                  iRr.toRegister(userInfo);//发送请求

              }else{
                  ToastUtil.showError("注册失败",3000,RegisterActivity.this);


              }

            }
        });
    }

    private Boolean vailUserInfo(UserInfo userInfo, String UserName, String UserPwd, String UserRePwd, String Test) {
      boolean f=false;
        if (!"".equals(UserName)){
            userInfo.setUserName(UserName);
            f=true;
        }else{
            et_useremail.setError("用户名必须非空");
            f=false;
        }

        if (!"".equals(UserPwd)){

            if (!"".equals(UserRePwd)){

                if (UserRePwd.equals(UserPwd)){

                    userInfo.setUserPwd(UserPwd);
                    f=true;
                }

            }else{

                et_rePwd.setError("请输入确认密码");
                f=false;
            }
        }else{

            et_userPwd.setError("密码必须非空");
            f=false;
        }



        if (!"".equals(Test)){

            if (Test.contains("@")){
                pattern = Pattern.compile(EMAIL_PATTERN);
                matcher = pattern.matcher(Test);

                if ( matcher.matches()){
                    userInfo.setUserEmail(Test);
                    f=true;
                }else {
                    et_useremail.setError("邮箱格式有误");
                    f=false;

                }
            }else {
                pattern = Pattern.compile(PHONE_PATTERN);
                matcher = pattern.matcher(Test);
                if (matcher.matches()){
                    userInfo.setUserPhone(Test);
                    f=true;
                }else {

                    et_useremail.setError("手机号码格式有误");
                    f=false;
                }

            }

        }else{
            et_useremail.setError("邮箱/手机号不能为空");
            f=false;

        }
        return f;

    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    @Override
    public void onRegisterSuccess(UserModel userModel) {

        ToastUtil.showSuccess("注册成功,让我们乘风飞翔,和太阳肩并肩",3000,RegisterActivity.this);
        Intent i=new Intent(RegisterActivity.this, MainActivity.class);
        SpUtil.saveUserToSp("UserName",UserName);
        SpUtil.saveUserToSp("UserPwd",UserPwd);

        SpUtil.saveUserToSp("UserId",userModel.getUserInfo().getUserId()+"");
        SpUtil.saveUserToSp("UserLogo",userModel.getUserInfo().getUserLogo());

        i.putExtra("UserId",userModel.getUserInfo().getUserId());
        startActivity(i);
        finish();
    }

    @Override
    public void onRegisterFail() {
        ToastUtil.showError("注册失败，你早就存在于我的脑海里了",3000,RegisterActivity.this);

    }
}
