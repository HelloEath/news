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

import com.glut.news.R;
import com.glut.news.adapter.KaiYanAdater;
import com.glut.news.entity.KaiYanModel;
import com.glut.news.net.manager.RetrofitManager;
import com.glut.news.net.service.RetrofitService;
import com.glut.news.view.customview.VideoPlayer;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/2/7.
 */

public class KaiYanFragment extends Fragment implements PullToRefreshView.OnRefreshListener {

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

        RetrofitManager.builder(RetrofitService.KAIYAB_BASE_URL,"KaiYanService").getLatestNews3()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<KaiYanModel, KaiYanModel>() {
                    @Override
                    public KaiYanModel call(KaiYanModel kaiYanModel) {
                        return kaiYanModel;
                    }
                })
                .subscribe(new Action1<KaiYanModel>() {
                    @Override
                    public void call(KaiYanModel kaiYanModel) {
                        mPbLoading.setVisibility(View.GONE);
                        if (kaiYanModel ==null){
                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        }else{

                            nextPage= kaiYanModel.getNextPageUrl().substring(55, kaiYanModel.getNextPageUrl().length()-7);

                            kaiYanAdater.changeData(kaiYanModel.getItemList());
                            mTvLoadEmpty.setVisibility(View.GONE);
                        }
                        mLoadLatestSnackbar.dismiss();
                        refreshLayout.setRefreshing(false);
                        mTvLoadError.setVisibility(View.GONE);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mLoadLatestSnackbar.show();
                        refreshLayout.setRefreshing(false);
                        mLoadLatestSnackbar.show();
                        mTvLoadError.setVisibility(View.VISIBLE);
                        mTvLoadEmpty.setVisibility(View.GONE);

                    }
                });


    }
    //加载更多
    private void loadDataBefore() {

        RetrofitManager.builder(RetrofitService.KAIYAB_BASE_URL,"KaiYanService").getNextPageNews(nextPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<KaiYanModel, KaiYanModel>() {
                    @Override
                    public KaiYanModel call(KaiYanModel kaiYanModel) {
                        return kaiYanModel;
                    }
                })
                .subscribe(new Action1<KaiYanModel>() {
                    @Override
                    public void call(KaiYanModel kaiYanModel) {
                        mPbLoading.setVisibility(View.GONE);
                        if (kaiYanModel ==null){
                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        }else{
                            nextPage= kaiYanModel.getNextPageUrl().substring(55, kaiYanModel.getNextPageUrl().length()-7);

                            kaiYanAdater.addData(kaiYanModel.getItemList());
                            mTvLoadEmpty.setVisibility(View.GONE);
                        }
                        mLoadBeforeSnackbar.dismiss();
                        mTvLoadError.setVisibility(View.GONE);
                        isloading=false;
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mLoadBeforeSnackbar.show();
                        mPbLoading.setVisibility(View.GONE);
                        mTvLoadError.setVisibility(View.VISIBLE);
                        mTvLoadEmpty.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadDta();
    }



    @Override
    public void onPause() {
        super.onPause();
        VideoPlayer.releaseAllVideos();
    }
}

