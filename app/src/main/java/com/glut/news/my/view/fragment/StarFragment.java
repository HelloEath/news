package com.glut.news.my.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.glut.news.R;
import com.glut.news.home.view.activity.ArticleDetailActivity;
import com.glut.news.my.model.adater.HistoryAdater;
import com.glut.news.my.model.entity.CommonData;
import com.glut.news.my.model.entity.HistoryWithStarModel;
import com.glut.news.my.presenter.impl.StarFragmentPresenterImpl;
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
public class StarFragment extends android.support.v4.app.Fragment implements IStarFragmentView{
    private RecyclerView r;
    private List<CommonData> historyList=new ArrayList<>();


    private HistoryAdater<CommonData> Ada;

    private String title;
    private SmartRefreshLayout mRefreshLayout;


    private StarFragmentPresenterImpl starPresenter=new StarFragmentPresenterImpl(this) ;


    public StarFragment() {

    }

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
        starPresenter.loadStarData("fp");

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
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                starPresenter.loadStarData("null");
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Ada.changeDta(new ArrayList<CommonData>());
                starPresenter.loadStarData("fp");
            }
        });

        Ada.OnItemClickListener(new HistoryAdater.onItemclick() {
            @Override
            public void onItemClick(String id, String type,String ContentType) {
                if ("v".equals(type)){

                    Intent intent=new Intent(getActivity(),VideoDetailActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("ContentType",ContentType);
                    startActivity(intent);

                }else {
                    Intent intent=new Intent(getActivity(),ArticleDetailActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("ContentType",ContentType);

                    startActivity(intent);
                }
            }
        });
    }




    @Override
    public void onLoadStarSuccess(HistoryWithStarModel h) {
        Ada.changeDta(h.getData());
        mRefreshLayout.finishRefresh(true);
        mRefreshLayout.setNoMoreData(false);

    }

    @Override
    public void onLoadSMoretarSuccess(HistoryWithStarModel h) {
        mRefreshLayout.finishLoadMore(true);
        mRefreshLayout.finishRefresh(true);

        Ada.addData(h.getData());
    }


    @Override
    public void onLoadStarFail() {
        mRefreshLayout.finishRefresh(false);
        Toast.makeText(getContext(),"加载失败,下拉可再次刷新",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadSMoretarFail() {
        Toast.makeText(getContext(),"加载更多失败,下拉可再次刷新",Toast.LENGTH_SHORT).show();
        mRefreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void onNoMoreData() {
        mRefreshLayout.finishLoadMoreWithNoMoreData();

    }

}
