package com.glut.news.video.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.glut.news.R;
import com.glut.news.common.utils.NetUtil;
import com.glut.news.common.utils.UserUtil;
import com.glut.news.common.view.customview.VideoPlayer;
import com.glut.news.video.model.adater.VideoRecyclerAdapter;
import com.glut.news.video.model.entity.VideoModel;
import com.glut.news.video.presenter.impl.VideoTypeFragmentPresenterImpl;
import com.glut.news.video.view.activity.VideoDetailActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yy on 2018/1/26.
 */

public  class VideoTypeFragment extends Fragment implements IVideoTypeFragmentView{
    private RecyclerView recyclerView;
    private String videoType;
    private VideoRecyclerAdapter videoRecyclerAdapter;
    private List<VideoModel.VideoList> videoList=new ArrayList<>();
    private SmartRefreshLayout sfresh;
    private LinearLayoutManager linearLayoutManager;
    private VideoTypeFragmentPresenterImpl v=new VideoTypeFragmentPresenterImpl(this);





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_video_type,container,false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        videoType=getArguments().getString("title");
        sfresh.autoRefresh();
        if (!NetUtil.isNetworkConnected()){

            Toast.makeText(getContext(),"网络已走失",Toast.LENGTH_SHORT).show();

        }
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
        sfresh=view.findViewById(R.id.refreshLayout);
        //下拉刷新数据
        sfresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                if (NetUtil.isNetworkConnected()){
                    if (UserUtil.isUserLogin()){
                        v.loadVideoData("login",videoType,"fp");

                    }else {
                        v.loadVideoData("",videoType,"fp");

                    }


                }else {
                    Toast.makeText(getContext(),"网络已走失",Toast.LENGTH_SHORT).show();

                    refreshlayout.finishRefresh();
                }

            }
        });
        //上拉加载更多数据
        sfresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {

                if (NetUtil.isNetworkConnected()){
                    if (UserUtil.isUserLogin()){
                        v.loadVideoData("login",videoType,"null");

                    }else {
                        v.loadVideoData("",videoType,"null");

                    }


                }else {
                    Toast.makeText(getContext(),"网络已走失",Toast.LENGTH_SHORT).show();

                    refreshlayout.finishRefresh();

                }

            }
        });


        //item点击事件
        videoRecyclerAdapter.setOnItemListener(new VideoRecyclerAdapter.OnItemListener() {
            @Override
            public void onItemClick(View v, String position,String palyer,String a,String title,String contentType) {
                if (NetUtil.isNetworkConnected()){
                    Intent i = new Intent(getContext(), VideoDetailActivity.class);
                    i.putExtra("id",position);
                    i.putExtra("ContentType",contentType);

                    startActivity(i);

                }else {
                    Toast.makeText(getContext(),"网络已走失",Toast.LENGTH_SHORT).show();


                }

            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        VideoPlayer.releaseAllVideos();
    }


    @Override
    public void loadVideoDataSuccess(VideoModel v) {
        videoRecyclerAdapter.changeData(v.getData());
        sfresh.finishRefresh(true);
        sfresh.setNoMoreData(false);



    }

    @Override
    public void loadVideoDataFail() {
        sfresh.finishRefresh(false);
        sfresh.finishLoadMore();


    }

    @Override
    public void loadVideoMoreVideoDataSuccesss(VideoModel v) {
        videoRecyclerAdapter.addData(v.getData());
        sfresh.finishRefresh();
        sfresh.finishLoadMore();

    }


    @Override
    public void loadVideoMoreVideoDataFail() {
    sfresh.finishLoadMoreWithNoMoreData();

    }

    @Override
    public void noMoreData() {
        sfresh.finishLoadMoreWithNoMoreData();
    }
}