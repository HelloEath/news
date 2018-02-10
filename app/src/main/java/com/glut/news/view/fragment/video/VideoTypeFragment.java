package com.glut.news.view.fragment.video;

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
import com.glut.news.adapter.VideoRecyclerAdapter;
import com.glut.news.entity.Video;
import com.glut.news.view.VideoDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2018/1/26.
 */

public  class VideoTypeFragment extends Fragment {
    private RecyclerView recyclerView;
    private String urlStr;
    private VideoRecyclerAdapter videoRecyclerAdapter;
    private List<Video> videoList=new ArrayList<>();
    private SwipeRefreshLayout sfresh;
    private LinearLayoutManager linearLayoutManager;
    private boolean isloading=false;
    public VideoTypeFragment() {
    }
    public VideoTypeFragment(String url) {
        this.urlStr=url;
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
                videoRecyclerAdapter.notifyDataSetChanged();
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
                if (!isloading && totallItemcounts<lastItem+2){
                    isloading=true;
                    loadVideoData();
                    isloading=false;

                    videoRecyclerAdapter.notifyDataSetChanged();
                }

            }
        });

        videoRecyclerAdapter.setOnItemListener(new VideoRecyclerAdapter.OnItemListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent i = new Intent(getActivity(), VideoDetailActivity.class);
                startActivity(i);
            }
        });
    }

    //加载更多数据
    private void loadMoreData() {
    }

    //加载视频列表数据
    private void loadVideoData() {

        for (int i=0;i<10;i++){
            Video  vides=new Video();
            vides.setTitle("标题"+i);
            vides.setAuthor("头条"+i);
            vides.setWatchs("20万");
            vides.setCommits("2355");

            videoList.add(vides);

        }
        videoRecyclerAdapter.addData(videoList);


    }

}