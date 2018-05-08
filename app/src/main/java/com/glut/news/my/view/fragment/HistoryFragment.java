package com.glut.news.my.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glut.news.R;
import com.glut.news.home.view.activity.ArticleDetailActivity;
import com.glut.news.my.model.adater.HistoryAdater;
import com.glut.news.my.model.entity.CommonData;
import com.glut.news.my.model.entity.HistoryWithStarModel;
import com.glut.news.my.presenter.impl.HistoryFragmentPresenterImpl;
import com.glut.news.video.view.activity.VideoDetailActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by yy on 2018/2/12.
 */
public class HistoryFragment extends android.support.v4.app.Fragment implements HistoryFragmentView {
    private RecyclerView r;
    private List<CommonData> historyList=new ArrayList<>();

    private HistoryAdater<CommonData> Ada;

    private String title;
    private boolean isloading;
    private SmartRefreshLayout mRefreshLayout;


    private HistoryFragmentPresenterImpl starWithHistoryPresenter=new HistoryFragmentPresenterImpl(this) ;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_setting_history,container,false);
        initView(v);

        initData();
        loadData();
        return v;
    }

    private void loadData() {
        starWithHistoryPresenter.loadHistory("fp");

    }

    private void initData() {
        r.setAdapter(Ada);
    }



    private void initView(View v) {
        mRefreshLayout=v.findViewById(R.id.refreshLayout);
        Ada=new HistoryAdater<CommonData>(getContext(),historyList);

        r=v.findViewById(R.id.recyclerview);
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        r.setLayoutManager(lm);
        //下拉刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Ada.changeDta(new ArrayList<CommonData>());
                starWithHistoryPresenter.loadHistory("fp");
            }
        });
        //加载更多
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                starWithHistoryPresenter.loadHistory("null");
            }
        });
        Ada.OnItemClickListener(new HistoryAdater.onItemclick() {
            @Override
            public void onItemClick(String id, String type,String contentType) {
                if ("v".equals(type)){

                    Intent intent=new Intent(getActivity(),VideoDetailActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("ContentType",contentType);
                    startActivity(intent);

                }else {
                    Intent intent=new Intent(getActivity(),ArticleDetailActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("ContentType",contentType);

                    startActivity(intent);
                }
            }
        });
    }




    @Override
    public void onLoadHistorySuccess(HistoryWithStarModel h) {
        Ada.changeDta(h.getData());
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.finishRefresh(true);
        mRefreshLayout.setNoMoreData(false);

    }

    @Override
    public void onLoadHistoryFail() {

       mRefreshLayout.finishRefresh(false);
    }

    @Override
    public void onNoMoreHistoryData() {
       mRefreshLayout.finishLoadMoreWithNoMoreData();
        mRefreshLayout.finishRefresh();

    }

    @Override
    public void noHistoryData() {
        Snackbar.make(getView(), R.string.nohistoryData, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onLoadMoreHistorySuccess(HistoryWithStarModel h) {
        Ada.addData(h.getData());
        mRefreshLayout.finishLoadMore(true);
    }



}
