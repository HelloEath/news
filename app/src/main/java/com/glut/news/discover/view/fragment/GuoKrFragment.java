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

import com.glut.news.R;
import com.glut.news.adapter.GuoKrAdater;
import com.glut.news.entity.GuoKrListModel;
import com.glut.news.net.manager.RetrofitManager;
import com.glut.news.net.service.RetrofitService;
import com.glut.news.view.GuoKrDetailActivity;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;



/**
 * Created by yy on 2018/2/4.
 */
public class GuoKrFragment extends android.support.v4.app.Fragment implements PullToRefreshView.OnRefreshListener {

    private RecyclerView recyclerView;
    private PullToRefreshView refreshLayout;
    private List<GuoKrListModel.GuokrHandpickNewsResult> guoKrList = new ArrayList<>();
    private GuoKrAdater guoKrAdater;
    private boolean isloading=false;
    private String currentDate;
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
        guoKrAdater = new GuoKrAdater(getActivity(), guoKrList);
        //设置列表动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(guoKrAdater);
        //item点击事件
       guoKrAdater.setOnItemClickListener(new GuoKrAdater.OnItemClickListener() {
           @Override
           public void itemClick(View v, String id, String image, String image_from) {
               Intent i=new Intent(getActivity(),GuoKrDetailActivity.class);
               i.putExtra("url",id);
               i.putExtra("image_url",image);
               i.putExtra("image_from",image_from);
               startActivity(i);
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
                    //loadDataBefore();


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
                        //loadDataBefore();
                    }
                });
    }

    public void loadDta() {

        RetrofitManager.builder(RetrofitService.GUOKR_HANDPICK_BASE,"GuoKrService").getLatestNews1()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<GuoKrListModel, GuoKrListModel>() {
                    @Override
                    public GuoKrListModel call(GuoKrListModel guoKrListModel) {
                        return guoKrListModel;
                    }
                })
                .subscribe(new Action1<GuoKrListModel>() {
                    @Override
                    public void call(GuoKrListModel guoKrListModel) {
                        mPbLoading.setVisibility(View.GONE);
                        if (guoKrListModel ==null){
                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        }else{

                            guoKrAdater.changeData(guoKrListModel.getResult());
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
    /*//加载以前的数据
    private void loadDataBefore() {
        RetrofitManager.builder(RetrofitService.BASE_ZHIHU_URL,"ZhuHuService").getBeforeData(currentDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<ZhiHuList, ZhiHuList>() {
                    @Override
                    public ZhiHuList call(ZhiHuList zhiHuList2) {
                        return zhiHuList2;
                    }
                })
                .subscribe(new Action1<ZhiHuList>() {
                    @Override
                    public void call(ZhiHuList zhiHuList) {
                        mPbLoading.setVisibility(View.GONE);
                        if (zhiHuList==null){
                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        }else{
                            currentDate = zhiHuList.getDate();
                            for (ZhiHuNewsModel z:zhiHuList.getStories()){

                                z.setDate(currentDate);
                            }
                            guoKrAdater.addData(zhiHuList.getStories());
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
    }*/

    @Override
    public void onRefresh() {
        loadDta();
    }
}


