package com.glut.news.my.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.glut.news.AppApplication;
import com.glut.news.MainActivity;
import com.glut.news.R;
import com.glut.news.common.model.entity.UserInfo;
import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.ToastUtil;
import com.glut.news.my.presenter.impl.InterestTagActivityPresenterImpl;
import com.google.gson.Gson;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yy on 2018/3/27.
 */

public class InterestTagActivity extends AppCompatActivity implements IInterestTagActivityView{
    private TagFlowLayout tagFlowLayout;
    private Button btn_go;
    private Map<String,Integer> tagList;
    private String[] mVals = new String[]
            {"科技", "互联网", "热点", "时尚", "游戏", "军事", "社会", "旅游",
                    "国内", "国际", "电影", "搞笑", "音乐", "生活","开眼","科学",
                    "数码","语录","情感","健康","财经","教育","宠物","故事"};

    private InterestTagActivityPresenterImpl i=new InterestTagActivityPresenterImpl(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intersettag);
        initView();
        initData();
        AppApplication.getInstance().addActivity(this);
    }

    private void initData() {
        tagList=new HashMap<>();

    }

    private void initView() {
       // final String UserId=   getIntent().getStringExtra("UserId");
      // final int UserId=getIntent().getIntExtra("UserId",0);
        final LayoutInflater mInflater = LayoutInflater.from(this);
        btn_go=findViewById(R.id.btn_IntersetGo);
        tagFlowLayout=findViewById(R.id.id_flowlayout);
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer stringBuffer=new StringBuffer();
                Gson gson=new Gson();
                gson.toJson(tagList);
                UserInfo userInfo=new UserInfo();
                userInfo.setUserId(Integer.parseInt(SpUtil.getUserFromSp("UserId")));
                userInfo.setUserInterest(gson.toJson(tagList).toString());
                i.getUserTagData(userInfo);//发送数据
                Toast.makeText(InterestTagActivity.this,userInfo.toString(),Toast.LENGTH_LONG).show();
                startActivity(new Intent(InterestTagActivity.this, MainActivity.class));
                finish();
            }
        });
        //初始化标签
        tagFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        tagFlowLayout, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                tagList.put(mVals[position],0);

               // Toast.makeText(InterestTagActivity.this, mVals[position], Toast.LENGTH_SHORT).show();

            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                tagList.remove(mVals[position]);
            }
        });


    }

    @Override
    public void interestTagsuccess() {
        ToastUtil.showSuccess("兴趣点设置成功",3000,InterestTagActivity.this);
    }
}
