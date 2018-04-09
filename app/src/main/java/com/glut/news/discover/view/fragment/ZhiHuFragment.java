package com.glut.news.discover.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by yy on 2018/2/4.
 */
public class ZhiHuFragment extends android.support.v4.app.Fragment implements PullToRefreshView.OnRefreshListener,IZhiHuFragmentView
{

    private RecyclerView recyclerView;
    private PullToRefreshView refreshLayout;
    private List<ZhiHuNewsModel> zhiHuList = new ArrayList<>();
    private ZhiHuAdater zhiHuAdater;
    private boolean isloading=false;
    private String currentDate;
    TextView mTvLoadEmpty;
    TextView mTvLoadError;
    ContentLoadingProgressBar mPbLoading;
    private Snackbar mLoadLatestSnackbar;
    private Snackbar mLoadBeforeSnackbar;

private ZhiHufragmentPresenterImpl z=new ZhiHufragmentPresenterImpl(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my, container, false);

        initView(v);
        loadDta();
        return v;
    }



    private void initView(View v) {
        recyclerView = v.findViewById(R.id.dicover_recycler);
        refreshLayout = v.findViewById(R.id.dicover_refresh);
        mTvLoadEmpty=v.findViewById(R.id.tv_load_empty);
        mTvLoadError=v.findViewById(R.id.tv_load_error);
        mPbLoading=v.findViewById(R.id.pb_loading);
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        l.setOrientation(OrientationHelper.VERTICAL);
        refreshLayout.setOnRefreshListener(this);

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

        //上拉加载更多
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                //获取所有item数目
                int totalItemCount = layoutManager.getItemCount();

                //获取最后一个item位置
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!isloading && totalItemCount < (lastVisibleItem + 2)) {
                    isloading=true;
                    loadDataBefore();


                }

            }
        });


        mLoadLatestSnackbar = Snackbar.make(recyclerView, R.string.load_fail, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.refresh, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadDta();
                    }
                });


        mLoadBeforeSnackbar = Snackbar.make(recyclerView, R.string.load_more_fail, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.refresh, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadDataBefore();
                    }
                });
    }

    public void loadDta() {
        if (NetUtil.isNetworkConnected()){
            z.loadData();

        }else {
            Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
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
    public void onRefresh() {
        if (NetUtil.isNetworkConnected()){
            loadDta();

        }else {
            Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLoadDataSuccess(ZhiHuList zhiHuList) {
        zhiHuAdater.changeData(zhiHuList.getStories());
        mPbLoading.setVisibility(View.GONE);
        mTvLoadEmpty.setVisibility(View.GONE);
   mLoadLatestSnackbar.dismiss();
                        refreshLayout.setRefreshing(false);
                        mTvLoadError.setVisibility(View.GONE);
    }

    @Override
    public void onLoadMoreDataSuccess(ZhiHuList zhiHuList) {
        zhiHuAdater.addData(zhiHuList.getStories());
        mTvLoadEmpty.setVisibility(View.GONE);
        mLoadBeforeSnackbar.dismiss();
        mTvLoadError.setVisibility(View.GONE);
        isloading=false;
    }

    @Override
    public void onLoadDataFail() {
        mLoadBeforeSnackbar.show();
        mPbLoading.setVisibility(View.GONE);
        mTvLoadError.setVisibility(View.VISIBLE);
        mTvLoadEmpty.setVisibility(View.GONE);
    }

    @Override
    public void onLoadMoreDataFail() {
        mLoadBeforeSnackbar.show();
        mPbLoading.setVisibility(View.GONE);
        mTvLoadError.setVisibility(View.VISIBLE);
        mTvLoadEmpty.setVisibility(View.GONE);
    }
}
