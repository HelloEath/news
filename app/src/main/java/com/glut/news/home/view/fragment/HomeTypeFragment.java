package com.glut.news.home.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.glut.news.R;
import com.glut.news.common.utils.NetUtil;
import com.glut.news.common.utils.UserUtil;
import com.glut.news.home.model.adater.HomeRecyclerAdater;
import com.glut.news.home.model.entity.ArticleModel;
import com.glut.news.home.presenter.impl.HomeTypeFragmentPresenterImpl;
import com.glut.news.home.view.activity.ArticleDetailActivity;
import com.mingle.widget.LoadingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yy on 2018/1/24.
 */
public class HomeTypeFragment extends Fragment implements IHomeTypeFragmentView {

    private boolean isloading = false;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private HomeRecyclerAdater adapter;
    private SmartRefreshLayout refresh;
    private List<ArticleModel.ArticleList> newslist = new ArrayList<>();

    private int nextPage;
    private String Article_Type;





    private LoadingView loadingView;

    private HomeTypeFragmentPresenterImpl hyf = new HomeTypeFragmentPresenterImpl(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_type, container, false);
        initView(v);
        initData();
        return v;
    }

    private void initData() {
        Article_Type=getArguments().getString("title");
        loadData("fp", Article_Type);
    }

    private void initView(View v) {
        loadingView = v.findViewById(R.id.loadView);
        refresh = v.findViewById(R.id.refreshLayout);
        recyclerView = v.findViewById(R.id.recyclerview);
        //获取线性布局管理器
        linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直方向
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给recyclerview设置布局
        recyclerView.setLayoutManager(linearLayoutManager);
        //创建适配器
        //loadData();
        adapter = new HomeRecyclerAdater(getActivity(), newslist);
        refresh.autoRefresh();
        recyclerView.setAdapter(adapter);
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (NetUtil.isNetworkConnected()) {
                    loadData("fp", Article_Type);
                } else {
                    Toast.makeText(getContext(), "网络走失了...", Toast.LENGTH_SHORT).show();
                    refresh.finishRefresh();

                }
            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {


            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (NetUtil.isNetworkConnected()) {
                    loadMoreData(null, Article_Type);
                } else {
                    Toast.makeText(getContext(), "网络走失了...", Toast.LENGTH_SHORT).show();
                    refresh.finishLoadMore();
                }

            }


        });


        //recycler中每一项的点击事件
        adapter.setOnItemClickListener(new HomeRecyclerAdater.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int positoin, String ContentType) {
                if (NetUtil.isNetworkConnected()) {
                    Intent i = new Intent(getActivity(), ArticleDetailActivity.class);
                    String id = positoin + "";

                    i.putExtra("id", id);
                    i.putExtra("ContentType", ContentType);
                    startActivity(i);
                } else {
                    Toast.makeText(getContext(), "网络已走失", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    //加载更多数据
    private void loadMoreData(String f, String article_Type) {
        if(UserUtil.isUserLogin()){
            hyf.loadData("login","null", article_Type);//加载数据

        }else {
            hyf.loadData("","null", article_Type);//加载数据

        }


    }

    //加载新闻列表数据
    private void loadData(String fp, String article_Type) {
        if(UserUtil.isUserLogin()){
            hyf.loadData("login",fp, article_Type);//加载数据

        }else {
            hyf.loadData("",fp, article_Type);//加载数据

        }

    }


    @Override
    public void onloadDataSuccess(List<ArticleModel.ArticleList> data) {
        adapter.changeData(data);
        refresh.finishRefresh(true);
        refresh.setNoMoreData(false);
        Toast.makeText(getContext(), "刷新成功！", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoadDataFail() {
        refresh.finishRefresh(false);

    }

    @Override
    public void onLoadMoreDataFail() {
        refresh.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void onloadMoreDataSuccess(List<ArticleModel.ArticleList> data) {
        adapter.addData(data);
        refresh.finishLoadMore(true);
        refresh.finishRefresh();


    }

    @Override
    public void onLoadEmptyData() {
        refresh.setNoMoreData(true);
    }
}
