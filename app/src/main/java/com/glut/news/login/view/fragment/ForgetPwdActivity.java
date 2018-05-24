package com.glut.news.login.view.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.glut.news.AppApplication;
import com.glut.news.R;
import com.glut.news.common.model.entity.UserInfo;
import com.glut.news.common.model.entity.UserModel;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by yy on 2018/3/18.
 */

public class ForgetPwdActivity  extends AppCompatActivity implements OnClickListener{


    private EditText mEditText_phone;
    private EditText mEditText_veriCode;
    private EditText mEditText_newPwd;
    private EditText mEditText_reNewPwd;
    private Button mButton_sendCode;
    private Button mButton_confirm;
    private Button mButton_goBack;
    private String mPwdString;
    private Button btn_gotoLogin;
    UserInfo userInfo;
    private boolean isCodeEnable=false;


    //private Rx2Timer timer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //切换需要使用动画定义
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //要做的动画
        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
//退出时使用
        getWindow().setExitTransition(explode);
//第一次进入时使用
        getWindow().setEnterTransition(explode);
//再次进入时使用
        getWindow().setReenterTransition(explode);
        setContentView(R.layout.activity_forgetpwd);
        initView();
        initData();
        AppApplication.getInstance().addActivity(this);
    }

    private void initData() {

    }

    private void initView() {
        mEditText_phone=findViewById(R.id.et_phone);
        mEditText_veriCode=findViewById(R.id.et_vefiCode);
        mButton_sendCode=findViewById(R.id.btn_sendCode);
       mEditText_newPwd=findViewById(R.id.et_newPwd);
       mEditText_reNewPwd=findViewById(R.id.et_reNewPwd);
       mButton_confirm=findViewById(R.id.btn_confirm);
       mButton_goBack=findViewById(R.id.btn_goBack);
        btn_gotoLogin=findViewById(R.id.btn_gotoLogin);
        mButton_sendCode.setOnClickListener(this);
        mButton_goBack.setOnClickListener(this);
        mButton_confirm.setOnClickListener(this);
        btn_gotoLogin.setOnClickListener(this);
        //输入框验证码触发事件
        mEditText_veriCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    if (mEditText_phone.getText().toString().trim().equals("")){

                    }else {
                        submitCode("86",mEditText_phone.getText().toString().trim(),mEditText_veriCode.getText().toString().trim());

                    }

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_sendCode:
                  String PHONE_PATTERN = "[1][34578]\\d{9}";
                if (!mEditText_phone.getText().toString().trim().equals("")){
                    Pattern pattern = Pattern.compile(PHONE_PATTERN);
                    Matcher matcher = pattern.matcher(mEditText_phone.getText().toString().trim());
                    if (matcher.matches()){
                        sendCode("86",mEditText_phone.getText().toString().trim());
                    }else {
                        mEditText_phone.setError("手机号码格式有误");
                    }
                }else {
                    mEditText_phone.setError("手机号码不能为空");
                }

                break;
            case R.id.btn_confirm:
                String newPwd=mEditText_newPwd.getText().toString().trim();
                String reNewPwd=mEditText_reNewPwd.getText().toString().trim();
                String phoneNum=mEditText_phone.getText().toString().trim();
                if (!"".equals(mEditText_veriCode.getText().toString().trim())){

                    if ("".equals(reNewPwd)){
                        mEditText_newPwd.setError("密码不能为空");

                    }else {


                    if (newPwd.equals(reNewPwd)){

                        if(isCodeEnable){
                            mPwdString=reNewPwd;
                            userInfo=new UserInfo();
                            userInfo.setUserPhone(phoneNum);
                            userInfo.setUserPwd(reNewPwd);
                            alterUserPwd(userInfo);
                            //submitCode("86",mEditText_phone.getText().toString().trim(),mEditText_veriCode.getText().toString().trim());


                        }else{
                            Toast.makeText(ForgetPwdActivity.this,"验证码无效",Toast.LENGTH_SHORT).show();

                        }

                    }else {
                        Toast.makeText(ForgetPwdActivity.this,"两次输入的密码不一致,请重新输入",Toast.LENGTH_SHORT).show();

                    }
                    }

                }else {
                    mEditText_veriCode.setError("验证码不能为空");
                }

                break;
            case R.id.btn_goBack:
                startActivity(new Intent(ForgetPwdActivity.this,LoginActivity.class));
                finish();
                break;
            case R.id.btn_gotoLogin:
                startActivity(new Intent(ForgetPwdActivity.this,LoginActivity.class));
                finish();
                break;
                default:
                    break;
        }
    }

    public void alterUserPwd(UserInfo userInfo){
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "UserService").alterUserPwd(userInfo)
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
                        if (userModel == null) {
                          alterFail();
                        } else {

                            if (userModel.getStus().equals("1")){
                              alterSuccess(userModel);

                            }else {

                                alterFail();
                            }

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                });

    }

    public void alterFail(){
        Toast.makeText(ForgetPwdActivity.this,"修改失败,没有该用户",Toast.LENGTH_SHORT).show();



    }

    public void alterSuccess(UserModel userModel){
        mButton_confirm.setVisibility(View.GONE);
        mButton_goBack.setVisibility(View.GONE);
        btn_gotoLogin.setVisibility(View.VISIBLE);
        Toast.makeText(ForgetPwdActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
    }
    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    new Thread() {
                        public void run() {
                            Log.i("log", "run");
                            Looper.prepare();
                            if (!isCodeEnable){
                                Toast.makeText(ForgetPwdActivity.this,"发送成功",Toast.LENGTH_SHORT).show();

                            }
                            Looper.loop();
                        }
                        // 进入loop中的循环，查看消息队列

                    }.start();

                } else{
                    // TODO 处理错误的结果
                    new Thread() {
                        public void run() {
                            Log.i("log", "run");
                            Looper.prepare();
                            Toast.makeText(ForgetPwdActivity.this,"发送失败",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        // 进入loop中的循环，查看消息队列

                    }.start();
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    isCodeEnable=true;

                } else{
                    new Thread() {
                        public void run() {
                            Log.i("log", "run");
                            Looper.prepare();
                            Toast.makeText(ForgetPwdActivity.this,"验证码有误",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        // 进入loop中的循环，查看消息队列

                    }.start();
                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    }

}
