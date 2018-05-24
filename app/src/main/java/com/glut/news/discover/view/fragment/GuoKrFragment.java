package com.glut.news.discover.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.glut.news.R;
import com.glut.news.common.utils.NetUtil;
import com.glut.news.discover.model.adater.GuoKrAdater;
import com.glut.news.discover.model.entity.GuoKrListModel;
import com.glut.news.discover.presenter.impl.GuoKrPresenterImpl;
import com.glut.news.discover.view.fragment.activity.GuoKrDetailActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by yy on 2018/2/4.
 */
public class GuoKrFragment extends android.support.v4.app.Fragment implements IGuoKrFragmentView {

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private List<GuoKrListModel.GuokrHandpickNewsResult> guoKrList = new ArrayList<>();
    private GuoKrAdater guoKrAdater;
    TextView mTvLoadEmpty;
    TextView mTvLoadError;
    ContentLoadingProgressBar mPbLoading;



private GuoKrPresenterImpl guoKrPresenter=new GuoKrPresenterImpl(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my, container, false);
       /* //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);*/
        initView(v);
        loadData();

        return v;
    }



    private void initView(View v) {

        recyclerView = v.findViewById(R.id.dicover_recycler);
        refreshLayout = v.findViewById(R.id.refreshLayout);
        mTvLoadEmpty=v.findViewById(R.id.tv_load_empty);
        mTvLoadError=v.findViewById(R.id.tv_load_error);
        //mPbLoading=v.findViewById(R.id.pb_loading);
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        l.setOrientation(OrientationHelper.VERTICAL);

        recyclerView.setLayoutManager(l);
        guoKrAdater = new GuoKrAdater(getActivity(), guoKrList);
        //设置列表动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(guoKrAdater);
        //item点击事件
       guoKrAdater.setOnItemClickListener(new GuoKrAdater.OnItemClickListener() {
           @Override
           public void itemClick(View v, String id, String image, String image_from) {
              if (NetUtil.isNetworkConnected()){
                  Intent i=new Intent(getActivity(),GuoKrDetailActivity.class);
                  i.putExtra("url",id);
                  i.putExtra("image_url",image);
                  i.putExtra("image_from",image_from);
                  startActivity(i);
              }else {
                  Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
              }


           }
       });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
        refreshLayout.autoRefresh();
    }

    public void loadData() {
        guoKrPresenter.loadData();
        refreshLayout.setNoMoreData(false);

        if (!NetUtil.isNetworkConnected()){
            Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
        }

    }




    @Override
    public void setAdaterData(GuoKrListModel guoKrListModel) {
        guoKrAdater.changeData(guoKrListModel.getResult());
        refreshLayout.finishRefresh();
        refreshLayout.setNoMoreData(true);
    }




}


