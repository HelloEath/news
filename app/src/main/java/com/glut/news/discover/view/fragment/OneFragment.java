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
import com.glut.news.discover.model.adater.OneAdater;
import com.glut.news.discover.model.entity.OneDataModel;
import com.glut.news.discover.model.entity.OneModel;
import com.glut.news.discover.presenter.impl.OneFragmentPresenterImpl;
import com.glut.news.discover.view.fragment.activity.OneDetailActivity;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2018/2/4.
 */
public class OneFragment extends android.support.v4.app.Fragment implements PullToRefreshView.OnRefreshListener,IOneFragmentView {

    private RecyclerView recyclerView;
    private PullToRefreshView refreshLayout;
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
        oneAdater = new OneAdater(getActivity(), oneList);
        //设置列表动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(oneAdater);
        //item点击事件
        oneAdater.setOnItemClickListener(new OneAdater.OnItemClickListener() {
            @Override
            public void OnItemclick(View v, String id, String author) {

                if (NetUtil.isNetworkConnected()){
                    Intent i=new Intent(getActivity(),OneDetailActivity.class);
                    i.putExtra("author",author);
                    i.putExtra("id",id);

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
                    o.loadMoreData();//加载以前的数据


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
    o.loadData();
}else {

    Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
}



    }


    //加载以前的数据
    private void loadDataBefore() {
    o.loadMoreData();
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
    public void loadDataSuccess(OneModel o) {
        oneAdater.changeData(o.getData().getContent_list());

    }

    @Override
    public void loadMoreDataSuccess(OneModel o) {
        oneAdater.addData( o.getData().getContent_list());

    }

    @Override
    public void loadDataFail() {

    }

    @Override
    public void loadMoreDataFail() {

    }
}

