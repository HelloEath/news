package com.glut.news.discover.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.glut.news.common.view.customview.VideoPlayer;
import com.glut.news.discover.model.adater.KaiYanAdater;
import com.glut.news.discover.model.entity.KaiYanModel;
import com.glut.news.discover.presenter.impl.KaiYanPresenterImpl;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2018/2/7.
 */

public class KaiYanFragment extends Fragment implements IKaiYanFragmentView {

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private List<KaiYanModel.KaiYanList> kaiYanList = new ArrayList<>();
    private KaiYanAdater kaiYanAdater;
    private boolean isloading=false;
    private String nextPage;
    TextView mTvLoadEmpty;
    TextView mTvLoadError;
    ContentLoadingProgressBar mPbLoading;
    private Snackbar mLoadLatestSnackbar;
    private Snackbar mLoadBeforeSnackbar;
    private KaiYanPresenterImpl kaiYanPresenter=new KaiYanPresenterImpl(this);



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my, container, false);
       /* //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);*/
        initView(v);
        loadDta();
        return v;
    }



    private void initView(View v) {
        recyclerView = v.findViewById(R.id.dicover_recycler);
        refreshLayout = v.findViewById(R.id.refreshLayout);
        mTvLoadEmpty=v.findViewById(R.id.tv_load_empty);
        mTvLoadError=v.findViewById(R.id.tv_load_error);
       // mPbLoading=v.findViewById(R.id.pb_loading);
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        l.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(l);
        kaiYanAdater = new KaiYanAdater(getActivity(), kaiYanList);
        //设置列表动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(kaiYanAdater);

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

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadDta();
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

    public void loadDta() {
        kaiYanPresenter.loadData();
        if (!NetUtil.isNetworkConnected()){
            Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();

        }

    }
    //加载更多
    private void loadDataBefore() {
        if (NetUtil.isNetworkConnected()){
            kaiYanPresenter.loadBeforeData();

        }else {
            Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        VideoPlayer.releaseAllVideos();

    }

    @Override
    public void onPause() {
        super.onPause();
        VideoPlayer.releaseAllVideos();
    }






    @Override
    public void addAdaterData(KaiYanModel guoKrListModel) {
        kaiYanAdater.addData(guoKrListModel.getItemList());
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void changeAdaterData(KaiYanModel guoKrListModel) {
        kaiYanAdater.changeData(guoKrListModel.getItemList());
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }


}

