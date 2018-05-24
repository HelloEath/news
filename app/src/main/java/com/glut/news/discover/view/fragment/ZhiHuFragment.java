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
import com.glut.news.discover.model.adater.ZhiHuAdater;
import com.glut.news.discover.model.entity.ZhiHuList;
import com.glut.news.discover.model.entity.ZhiHuNewsModel;
import com.glut.news.discover.presenter.impl.ZhiHufragmentPresenterImpl;
import com.glut.news.discover.view.fragment.activity.ZhiHuDetailActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by yy on 2018/2/4.
 */
public class ZhiHuFragment extends android.support.v4.app.Fragment implements IZhiHuFragmentView
{

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private List<ZhiHuNewsModel> zhiHuList = new ArrayList<>();
    private ZhiHuAdater zhiHuAdater;
    TextView mTvLoadEmpty;
    TextView mTvLoadError;
    ContentLoadingProgressBar mPbLoading;

private ZhiHufragmentPresenterImpl z=new ZhiHufragmentPresenterImpl(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my, container, false);
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
        zhiHuAdater = new ZhiHuAdater(getActivity(), zhiHuList);
        //设置列表动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(zhiHuAdater);
        //item点击事件
        zhiHuAdater.setOnItemClickListener(new ZhiHuAdater.OnItemClickListener() {
            @Override
            public void itemClick(View v, String id) {
                if (NetUtil.isNetworkConnected()){
                    Intent i = new Intent(getActivity(), ZhiHuDetailActivity.class);
                    i.putExtra("id", id);
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

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadDataBefore();
            }
        });
        refreshLayout.autoRefresh();

    }

    public void loadData() {
        z.loadData();
        if (!NetUtil.isNetworkConnected()) {
            Toast.makeText(getContext(), "网络走失了", Toast.LENGTH_SHORT).show();
        }




    }
    //加载以前的数据
    private void loadDataBefore() {
        if (NetUtil.isNetworkConnected()){
            z.loadMoreData();

        }else {
            Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onLoadDataSuccess(ZhiHuList zhiHuList) {
        zhiHuAdater.changeData(zhiHuList.getStories());
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();

    }

    @Override
    public void onLoadMoreDataSuccess(ZhiHuList zhiHuList) {
        zhiHuAdater.addData(zhiHuList.getStories());
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadDataFail() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        Toast.makeText(getContext(),"加载数据失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMoreDataFail() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }
}
