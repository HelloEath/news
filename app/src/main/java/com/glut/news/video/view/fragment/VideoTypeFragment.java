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
       initData();
        return view;
    }

    private void initData() {
        if (NetUtil.isNetworkConnected()){
            v.loadVideoData(videoType,null);

        }else {
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
                    v.loadVideoData(videoType,refreshlayout);


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
                    v.loadMoreData(videoType,refreshlayout);


                }else {
                    Toast.makeText(getContext(),"网络已走失",Toast.LENGTH_SHORT).show();

                    refreshlayout.finishRefresh();

                }

            }
        });


        //item点击事件
        videoRecyclerAdapter.setOnItemListener(new VideoRecyclerAdapter.OnItemListener() {
            @Override
            public void onItemClick(View v, String position,String palyer,String a,String title) {
                if (NetUtil.isNetworkConnected()){
                    Intent i = new Intent(getActivity(), VideoDetailActivity.class);
                    i.putExtra("id",position);
                    i.putExtra("abstract",a);
                    i.putExtra("player",palyer);
                    i.putExtra("title",title);
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
    public void loadVideoDataSuccess(VideoModel v,RefreshLayout r) {
        videoRecyclerAdapter.changeData(v.getData());
        if (r!=null){
            r.finishRefresh(2000);
        }



    }

    @Override
    public void loadVideoDataFail(RefreshLayout r) {
        if (r!=null){
            r.finishRefresh(false);
        }


    }

    @Override
    public void loadVideoMoreVideoDataSuccesss(VideoModel v,RefreshLayout r) {
        videoRecyclerAdapter.addData(v.getData());

        if (r!=null){
          r.finishLoadMore(2000);

      }

    }


    @Override
    public void loadVideoMoreVideoDataFail(RefreshLayout r) {

        if (r!=null){
            r.finishLoadMore(false);
        }

    }
}