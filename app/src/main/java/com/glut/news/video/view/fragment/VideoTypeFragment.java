package com.glut.news.video.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glut.news.R;
import com.glut.news.video.model.adater.VideoRecyclerAdapter;
import com.glut.news.video.model.entity.VideoModel;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.video.view.activity.VideoDetailActivity;
import com.glut.news.common.view.customview.VideoPlayer;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/1/26.
 */

public  class VideoTypeFragment extends Fragment {
    private RecyclerView recyclerView;
    private String videoType;
    private VideoRecyclerAdapter videoRecyclerAdapter;
    private List<VideoModel.VideoList> videoList=new ArrayList<>();
    private SwipeRefreshLayout sfresh;
    private LinearLayoutManager linearLayoutManager;
    private boolean isloading=false;
    private int nextPage_type;
    private int nextPage_tuijian;

    public VideoTypeFragment() {
    }
    public VideoTypeFragment(String videoType) {
        this.videoType=videoType;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_video_type,container,false);

        initView(view);

        loadVideoData();
        return view;
    }

    private void initView(View view) {

        recyclerView=view.findViewById(R.id.video_main);

        //获取线性布局管理器
        linearLayoutManager=new LinearLayoutManager(getActivity());
        //设置为垂直方向
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给recyclerview设置布局
        recyclerView.setLayoutManager(linearLayoutManager);
        videoRecyclerAdapter=new VideoRecyclerAdapter(getContext(),videoList);
        recyclerView.setAdapter(videoRecyclerAdapter);
        sfresh=view.findViewById(R.id.video_fresh);
        sfresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sfresh.setRefreshing(true);
                loadVideoData();
                sfresh.setRefreshing(false);
            }
        });

        //上拉加载更多
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager l= (LinearLayoutManager) recyclerView.getLayoutManager();
                //获取总的item数
                int totallItemcounts=l.getItemCount();
                //获取最后一个item数
                int lastItem=l.findLastVisibleItemPosition();
                if (!isloading&&totallItemcounts<lastItem+2){
                    isloading=true;
                    loadMoreData();

                }

            }
        });

        videoRecyclerAdapter.setOnItemListener(new VideoRecyclerAdapter.OnItemListener() {
            @Override
            public void onItemClick(View v, String position,String palyer,String a) {
                Intent i = new Intent(getActivity(), VideoDetailActivity.class);
                i.putExtra("id",position);
                i.putExtra("abstract",a);
                i.putExtra("player",palyer);
                startActivity(i);
            }
        });
    }

    //加载更多数据
    private void loadMoreData() {


            if (videoType.equals("推荐")) {
                RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "VideoService").getVideo(nextPage_tuijian)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                //mPbLoading.setVisibility(View.VISIBLE);
                            }
                        })
                        .map(new Func1<VideoModel, VideoModel>() {
                            @Override
                            public VideoModel call(VideoModel videoModel) {
                                return videoModel;
                            }
                        })
                        .subscribe(new Action1<VideoModel>() {
                            @Override
                            public void call(VideoModel videoModel) {
                                // mPbLoading.setVisibility(View.GONE);
                                if (videoModel == null) {
                                    //mTvLoadEmpty.setVisibility(View.VISIBLE);
                                } else {

                                    //判断是否使用缓存数据
                                    if (nextPage_tuijian== videoModel.getNextpage()){


                                    }else {
                                        nextPage_tuijian= videoModel.getNextpage();
                                        videoRecyclerAdapter.addData(videoModel.getData());
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
                RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "VideoService").getTypeVideo(videoType, nextPage_type)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                //mPbLoading.setVisibility(View.VISIBLE);
                            }
                        })
                        .map(new Func1<VideoModel, VideoModel>() {
                            @Override
                            public VideoModel call(VideoModel videoModel) {
                                return videoModel;
                            }
                        })
                        .subscribe(new Action1<VideoModel>() {
                            @Override
                            public void call(VideoModel videoModel) {
                                // mPbLoading.setVisibility(View.GONE);
                                if (videoModel == null) {
                                    //mTvLoadEmpty.setVisibility(View.VISIBLE);
                                } else {

                                    //判断是否使用缓存数据
                                    if (nextPage_type== videoModel.getNextpage()){


                                    }else {
                                        nextPage_type= videoModel.getNextpage();
                                        videoRecyclerAdapter.addData(videoModel.getData());
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


    }

    //加载视频列表数据
    private void loadVideoData() {

        if (videoType.equals("推荐")){

            RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL,"VideoService").getVideo(1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            //mPbLoading.setVisibility(View.VISIBLE);
                        }
                    })
                    .map(new Func1<VideoModel,VideoModel>() {
                        @Override
                        public VideoModel call(VideoModel videoModel) {
                            return videoModel;
                        }
                    })
                    .subscribe(new Action1<VideoModel>() {
                        @Override
                        public void call(VideoModel videoModel) {
                            // mPbLoading.setVisibility(View.GONE);
                            if (videoModel ==null){
                                //mTvLoadEmpty.setVisibility(View.VISIBLE);
                            }else{

                                nextPage_tuijian= videoModel.getNextpage();
                                videoRecyclerAdapter.changeData(videoModel.getData());
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
            //videoRecyclerAdapter.addData(videoList);

        }else {
            RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL,"VideoService").getTypeVideo(videoType,1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            //mPbLoading.setVisibility(View.VISIBLE);
                        }
                    })
                    .map(new Func1<VideoModel,VideoModel>() {
                        @Override
                        public VideoModel call(VideoModel videoModel) {
                            return videoModel;
                        }
                    })
                    .subscribe(new Action1<VideoModel>() {
                        @Override
                        public void call(VideoModel videoModel) {
                            // mPbLoading.setVisibility(View.GONE);
                            if (videoModel ==null){
                                //mTvLoadEmpty.setVisibility(View.VISIBLE);
                            }else{

                                nextPage_type= videoModel.getNextpage();
                                videoRecyclerAdapter.changeData(videoModel.getData());
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
            //videoRecyclerAdapter.addData(videoList);
        }



    }
    @Override
    public void onPause() {
        super.onPause();
        VideoPlayer.releaseAllVideos();
    }
}