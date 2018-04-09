package com.glut.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.glut.news.login.view.fragment.LoginActivity;

/**
 * Created by yy on 2018/4/6.
 */


public  class FirstFragment2  extends Fragment implements View.OnClickListener{

    private Button mButton_go_main;
    private Button mButton_go_login;
    private static int picId;
    private ImageView image;
    private TextView mTextView;

    private static String mTitle;
    public static FirstFragment2 newsInstance(String title, int pic, FragmentManager f){

        FirstFragment2 f2=new FirstFragment2();
        picId=pic;
        mTitle=title;
        return f2;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_first2,container,false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        mTextView=v.findViewById(R.id.title);
        mButton_go_main=v.findViewById(R.id.btn_go_main);
        mButton_go_login=v.findViewById(R.id.btn_go_login);
        image=v.findViewById(R.id.image);
        mButton_go_main.setOnClickListener(this);
        mButton_go_login.setOnClickListener(this);
        mTextView.setText(mTitle);
        Glide.with(AppApplication.getContext()).load(picId).into(image);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_go_main:
                startActivity(new Intent(getContext(),MainActivity.class));
                getActivity().finish();
                break;
            case R.id.btn_go_login:
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }
}

