package com.glut.news.home.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.glut.news.R;
import com.glut.news.common.utils.NetUtil;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.home.model.adater.HomeRecyclerAdater;
import com.glut.news.home.model.entity.ArticleModel;
import com.glut.news.home.view.activity.ArticleDetailActivity;
import com.mingle.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by yy on 2018/1/24.
 */
public class HomeTypeFragment extends Fragment {

    private boolean isloading = false;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private HomeRecyclerAdater adapter;
    private SwipeRefreshLayout refresh;
    private List<ArticleModel.ArticleList> newslist = new ArrayList<>();

    private int nextPage;
    private String Article_Type;

    public HomeTypeFragment(String s) {
        Article_Type=s;
    }
    private LoadingView loadingView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_type, container, false);
        initView(v);
        initData();
        loadData();
        return v;
    }

    private void initData() {
    }

    private void initView(View v) {
        loadingView=v.findViewById(R.id.loadView);
        refresh = v.findViewById(R.id.refresh);
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
//添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

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
                    isloading = true;
                    loadMoreData();

                }
            }
        });
        //recycler中每一项的点击事件
        adapter.setOnItemClickListener(new HomeRecyclerAdater.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int positoin,String ContentType) {
                if (NetUtil.isNetworkConnected()){
                    Intent i = new Intent(getActivity(), ArticleDetailActivity.class);
                    String id = positoin + "";

                    i.putExtra("id", positoin);
                    i.putExtra("ContentType",ContentType);
                    startActivity(i);
                }else {
                    Toast.makeText(getContext(),"网络已走失",Toast.LENGTH_SHORT).show();

                }

            }
        });

        //下拉刷新
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(true);
                if (NetUtil.isNetworkConnected()){
                    loadData();
                    refresh.setRefreshing(false);
                }else {
                    Toast.makeText(getContext(),"网络已走失",Toast.LENGTH_SHORT).show();
                    refresh.setRefreshing(false);
                }





            }
        });
    }

    //加载更多数据
    private void loadMoreData() {

        if (NetUtil.isNetworkConnected()){
            if (Article_Type.equals("推荐")) {
                RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "ArticleService").getTuiJianArticle(Article_Type,nextPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                            }
                        })
                        .map(new Func1<ArticleModel, ArticleModel>() {
                            @Override
                            public ArticleModel call(ArticleModel articleModel) {
                                return articleModel;
                            }
                        })
                        .subscribe(new Action1<ArticleModel>() {
                            @Override
                            public void call(ArticleModel articleModel) {
                                // mPbLoading.setVisibility(View.GONE);
                                if (articleModel == null) {
                                    //mTvLoadEmpty.setVisibility(View.VISIBLE);
                                } else {

                                    //判断是否使用缓存数据
                                    if (nextPage== articleModel.getNextpage()){


                                    }else {
                                        nextPage= articleModel.getNextpage();
                                        adapter.addData(articleModel.getData());
                                    }
                                    //mTvLoadEmpty.setVisibility(View.GONE);
                                }

                                isloading=false;
                                //mLoadLatestSnackbar.dismiss();
                                // refreshLayout.setRefreshing(false);
                                // mTvLoadError.setVisibility(View.GONE);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                // mLoadLatestSnackbar.show();
                                // refreshLayout.setRefreshing(false);
                                // mLoadLatestSnackbar.show();
                                // mTvLoadError.setVisibility(View.VISIBLE);
                                // mTvLoadEmpty.setVisibility(View.GONE);

                            }
                        });
            } else {
                RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "ArticleService").getTypeArticle(Article_Type, nextPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                //mPbLoading.setVisibility(View.VISIBLE);
                            }
                        })
                        .map(new Func1<ArticleModel, ArticleModel>() {
                            @Override
                            public ArticleModel call(ArticleModel articleModel) {
                                return articleModel;
                            }
                        })
                        .subscribe(new Action1<ArticleModel>() {
                            @Override
                            public void call(ArticleModel articleModel) {
                                // mPbLoading.setVisibility(View.GONE);
                                if (articleModel == null) {
                                    //mTvLoadEmpty.setVisibility(View.VISIBLE);
                                } else {

                                    //判断是否使用缓存数据
                                    if (nextPage== articleModel.getNextpage()){


                                    }else {
                                        nextPage= articleModel.getNextpage();
                                        adapter.addData(articleModel.getData());
                                    }
                                    //mTvLoadEmpty.setVisibility(View.GONE);
                                }

                                isloading=false;
                                //mLoadLatestSnackbar.dismiss();
                                // refreshLayout.setRefreshing(false);
                                // mTvLoadError.setVisibility(View.GONE);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                // mLoadLatestSnackbar.show();
                                // refreshLayout.setRefreshing(false);
                                // mLoadLatestSnackbar.show();
                                // mTvLoadError.setVisibility(View.VISIBLE);
                                // mTvLoadEmpty.setVisibility(View.GONE);

                            }
                        });
                //videoRecyclerAdapter.addData(videoList);
            }

        }else {
            Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
        }



    }

    //加载新闻列表数据
    private void loadData() {
        if (NetUtil.isNetworkConnected()){

            if (Article_Type.equals("推荐")) {
                RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "ArticleService").getTuiJianArticle(Article_Type,1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                //mPbLoading.setVisibility(View.VISIBLE);
                            }
                        })
                        .map(new Func1<ArticleModel, ArticleModel>() {
                            @Override
                            public ArticleModel call(ArticleModel articleModel) {
                                return articleModel;
                            }
                        })
                        .subscribe(new Action1<ArticleModel>() {
                            @Override
                            public void call(ArticleModel articleModel) {
                                // mPbLoading.setVisibility(View.GONE);
                                if (articleModel == null) {
                                    //mTvLoadEmpty.setVisibility(View.VISIBLE);
                                } else {

                                    //判断是否使用缓存数据
                                    if (nextPage== articleModel.getNextpage()){


                                    }else {
                                        nextPage= articleModel.getNextpage();
                                        adapter.changeData(articleModel.getData());
                                    }
                                    //mTvLoadEmpty.setVisibility(View.GONE);
                                }

                                isloading=false;
                                //mLoadLatestSnackbar.dismiss();
                                // refreshLayout.setRefreshing(false);
                                // mTvLoadError.setVisibility(View.GONE);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                // mLoadLatestSnackbar.show();
                                // refreshLayout.setRefreshing(false);
                                // mLoadLatestSnackbar.show();
                                // mTvLoadError.setVisibility(View.VISIBLE);
                                // mTvLoadEmpty.setVisibility(View.GONE);

                            }
                        });
            } else {
                RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "ArticleService").getTypeArticle(Article_Type, 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                //mPbLoading.setVisibility(View.VISIBLE);
                            }
                        })
                        .map(new Func1<ArticleModel, ArticleModel>() {
                            @Override
                            public ArticleModel call(ArticleModel articleModel) {
                                return articleModel;
                            }
                        })
                        .subscribe(new Action1<ArticleModel>() {
                            @Override
                            public void call(ArticleModel articleModel) {
                                // mPbLoading.setVisibility(View.GONE);
                                if (articleModel == null) {
                                    //mTvLoadEmpty.setVisibility(View.VISIBLE);
                                } else {
                                    nextPage = articleModel.getNextpage();
                                    adapter.changeData(articleModel.getData());
                                    //mTvLoadEmpty.setVisibility(View.GONE);
                                }
                                //mLoadLatestSnackbar.dismiss();
                                // refreshLayout.setRefreshing(false);
                                // mTvLoadError.setVisibility(View.GONE);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                // mLoadLatestSnackbar.show();
                                // refreshLayout.setRefreshing(false);
                                // mLoadLatestSnackbar.show();
                                // mTvLoadError.setVisibility(View.VISIBLE);
                                // mTvLoadEmpty.setVisibility(View.GONE);

                            }
                        });
            }
        }else {
            Toast.makeText(getContext(),"网络走失了",Toast.LENGTH_SHORT).show();
        }

    }







}
