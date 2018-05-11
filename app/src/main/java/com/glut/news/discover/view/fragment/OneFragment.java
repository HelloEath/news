package com.glut.news.discover.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.glut.news.discover.model.adater.OneAdater;
import com.glut.news.discover.model.entity.OneDataModel;
import com.glut.news.discover.model.entity.OneModel;
import com.glut.news.discover.presenter.impl.OneFragmentPresenterImpl;
import com.glut.news.discover.view.fragment.activity.OneDetailActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2018/2/4.
 */
public class OneFragment extends android.support.v4.app.Fragment implements IOneFragmentView {

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private List<OneDataModel.OneList> oneList = new ArrayList<>();
    private OneAdater oneAdater;
    private boolean isloading=false;
    private int currentDate;
    TextView mTvLoadEmpty;
    TextView mTvLoadError;

    ContentLoadingProgressBar mPbLoading;
    private Snackbar mLoadLatestSnackbar;
    private Snackbar mLoadBeforeSnackbar;


private OneFragmentPresenterImpl o=new OneFragmentPresenterImpl(this);
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
        mPbLoading=v.findViewById(R.id.pb_loading);
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        l.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(l);
        oneAdater = new OneAdater(getActivity(), oneList);
        //设置列表动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(oneAdater);
        //item点击事件
        oneAdater.setOnItemClickListener(new OneAdater.OnItemClickListener() {
            @Override
            public void OnItemclick(View v, String id, String author,String img) {

                if (NetUtil.isNetworkConnected()){
                    Intent i=new Intent(getActivity(),OneDetailActivity.class);
                    i.putExtra("author",author);
                    i.putExtra("id",id);
                    i.putExtra("img",img);
                    startActivity(i);
                }else {
                    Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
                }

            }
        });
   /*     mLoadLatestSnackbar = Snackbar.make(recyclerView, R.string.load_fail, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.refresh, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadData();
                    }
                });


        mLoadBeforeSnackbar = Snackbar.make(recyclerView, R.string.load_more_fail, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.refresh, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadDataBefore();
                    }
                });*/


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
        o.loadData();
if (!NetUtil.isNetworkConnected()){
    Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
}
    }


    //加载以前的数据
    private void loadDataBefore() {
        if (NetUtil.isNetworkConnected()){
            o.loadMoreData();
        }else {
            Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();

        }
    }



    @Override
    public void loadDataSuccess(OneModel o) {
        oneAdater.changeData(o.getData().getContent_list());
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();

    }

    @Override
    public void loadMoreDataSuccess(OneModel o) {
        oneAdater.addData( o.getData().getContent_list());
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void loadDataFail() {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        Toast.makeText(getContext(),"加载数据失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMoreDataFail() {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }
}

