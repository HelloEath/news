package com.glut.news.discover.view.fragment;

import android.os.Bundle;
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
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2018/2/7.
 */

public class KaiYanFragment extends Fragment implements PullToRefreshView.OnRefreshListener,IKaiYanFragmentView {

    private RecyclerView recyclerView;
    private PullToRefreshView refreshLayout;
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
        refreshLayout = v.findViewById(R.id.dicover_refresh);
        mTvLoadEmpty=v.findViewById(R.id.tv_load_empty);
        mTvLoadError=v.findViewById(R.id.tv_load_error);
        mPbLoading=v.findViewById(R.id.pb_loading);
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        l.setOrientation(OrientationHelper.VERTICAL);
        refreshLayout.setOnRefreshListener(this);

        recyclerView.setLayoutManager(l);
        kaiYanAdater = new KaiYanAdater(getActivity(), kaiYanList);
        //设置列表动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(kaiYanAdater);
      /*  //item点击事件
        kaiYanAdater.setOnItemClickListener(new ZhiHuAdater.OnItemClickListener() {
            @Override
            public void itemClick(View v, String id) {
                Intent i = new Intent(getActivity(), ZhiHuDetailActivity.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });*/

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
                    kaiYanPresenter.loadBeforeData();


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
            kaiYanPresenter.loadData();

        }else {
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
    public void onRefresh() {
        if (NetUtil.isNetworkConnected()){
            loadDta();

        }else {
            Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        VideoPlayer.releaseAllVideos();
    }

    @Override
    public void showLoading() {
        mPbLoading.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        mPbLoading.setVisibility(View.GONE);


    }

    @Override
    public void showEmpty() {
        mTvLoadEmpty.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideEnpty() {
        mTvLoadEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showLoadError() {
        mTvLoadError.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoadError() {
        mTvLoadEmpty.setVisibility(View.GONE);

    }

    @Override
    public void addAdaterData(KaiYanModel guoKrListModel) {
        kaiYanAdater.addData(guoKrListModel.getItemList());
    }

    @Override
    public void changeAdaterData(KaiYanModel guoKrListModel) {
        kaiYanAdater.changeData(guoKrListModel.getItemList());
    }


}

