package com.glut.news.common.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.glut.news.AppApplication;
import com.glut.news.R;

/**
 * Created by yy on 2018/3/26.
 */

public  class FirstFragment extends Fragment{

   private static String titleString;
    private static String descString;
    private static int imageViewInt;

   private  TextView mTitle;
   private  ImageView mImageView;
   private  TextView mDesc;
    public FirstFragment(FragmentManager fragmentManager) {
    }
    public static FirstFragment newInstance(String title,String desc,int pic,FragmentManager f){


        FirstFragment   fragment=new FirstFragment(f);

        titleString=title;
        descString=desc;
        imageViewInt=pic;
      return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_intro,container,false);

        initView(view);
        return view;
    }

    private void initView(View v) {
        mTitle=v.findViewById(R.id.title);
        mImageView=v.findViewById(R.id.image);
        mDesc=v.findViewById(R.id.description);
        mTitle.setText(titleString);
        Glide.with(AppApplication.getContext()).load(imageViewInt).into(mImageView);
        mDesc.setText(descString);
    }
}
