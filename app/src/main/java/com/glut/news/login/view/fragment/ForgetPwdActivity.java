package com.glut.news.login.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.glut.news.AppApplication;
import com.glut.news.R;


/**
 * Created by yy on 2018/3/18.
 */

public class ForgetPwdActivity  extends AppCompatActivity implements OnClickListener{
    private TextView textView;
    private Button button;

    //private Rx2Timer timer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
     /*   timer = Rx2Timer.builder()
                .initialDelay(0) //default is 0
                .period(1) //default is 1
                .take(30) //default is 60
                .unit(TimeUnit.SECONDS) // default is TimeUnit.SECONDS
                .onCount(new Rx2Timer.OnCount() {
                    @Override
                    public void onCount(Long count) {

                            if (count < 10) {
                                textView.setText("0" + count + " s");
                            } else {
                               textView.setText(count + " s");
                            }

                    }
                })
                .onError(new Rx2Timer.OnError() {
                    @Override
                    public void onError(Throwable throwable) {

                    }
                })
                .onComplete(new Rx2Timer.OnComplete() {
                    @Override
                    public void onComplete() {
                        textView.setText(0+ " s");
                    }
                })
                .build();
*/

      /*  timer.stop();
        timer.restart();
        timer.pause();
        timer.resume();*/
    }

    private void initView() {
        textView= (TextView) findViewById(R.id.showtimer);
        button= (Button) findViewById(R.id.btn_timer);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_timer:
               // timer.start();
                break;
        }
    }
}
