package com.glut.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;

/**
 * Created by yy on 2018/3/31.
 */

public class BaseFragment extends Fragment {
    public LocationClient locationClient;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//在使用SDK各组件之前初始化context信息，传入ApplicationContext（初始化百度地图API）
        SDKInitializer.initialize(getContext());

        locationClient = new LocationClient(getContext());

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
