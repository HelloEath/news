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
import com.glut.news.adapter.ZhiHuAdater;
import com.glut.news.entity.ZhiHuNewsModel;
import com.glut.news.net.manager.RetrofitManager;
import com.glut.news.net.service.RetrofitService;
import com.glut.news.net.service.ZhiHuList;
import com.glut.news.view.ZhiHuDetailActivity;
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
public class ZhiHuFragment extends android.support.v4.app.Fragment implements PullToRefreshView.OnRefreshListener{

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
        zhiHuAdater = new ZhiHuAdater(getActivity(), zhiHuList);
        //设置列表动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(zhiHuAdater);
        //item点击事件
        zhiHuAdater.setOnItemClickListener(new ZhiHuAdater.OnItemClickListener() {
            @Override
            public void itemClick(View v, String id) {
                Intent i = new Intent(getActivity(), ZhiHuDetailActivity.class);
                i.putExtra("id", id);
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
        /*OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .get()
                .url(RetrofitManager.BASE_ZHIHU_URL+"stories/latest")
                .build();
        //通过Client
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    String responseString = response.body().string();

                    Gson g=new Gson();
                    Log.d("ddd",responseString);
                    ZhiHuList z=  g.fromJson(responseString,ZhiHuList.class);
                    zhiHuList=z.getStories();

                    Message m=new Message();
                    m.what=1;
                    m.obj=zhiHuList;
                    hand.sendMessage(m);


                }
            }
        });*/

        RetrofitManager.builder(RetrofitService.BASE_ZHIHU_URL,"ZhuHuService").getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                    public void call(ZhiHuList zhiHuList2) {
                        mPbLoading.setVisibility(View.GONE);
                        if (zhiHuList2==null){
                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        }else{
                            currentDate=zhiHuList2.getDate();
                            for (ZhiHuNewsModel z:zhiHuList2.getStories()){

                                z.setDate(currentDate);
                            }
                            zhiHuAdater.changeData(zhiHuList2.getStories());
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
    //加载以前的数据
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
                            zhiHuAdater.addData(zhiHuList.getStories());
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
}
