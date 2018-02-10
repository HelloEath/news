package com.glut.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.glut.news.R;
import com.glut.news.entity.Video;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;


/**
 * Created by yy on 2018/1/28.
 */


public class VideoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<Video> videoList;
    private LayoutInflater layoutInflater;
    private Context context;
    private OnItemListener onItemListener;

    @Override
    public void onClick(View v) {
        this.onItemListener.onItemClick(v,(int)v.getTag());
    }

    public interface OnItemListener{
        void onItemClick(View v,int position);
    }

    public void setOnItemListener(OnItemListener o){
        this.onItemListener=o;
    }
    public  VideoRecyclerAdapter(Context c,List<Video> videoList){
        this.layoutInflater=layoutInflater.from(c);
        this.context=c;
        this.videoList=videoList;


    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.item_video_main,parent,false);

        view.setOnClickListener(this);
        RecyclerView.ViewHolder viewHolder=new VideoItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof VideoItemViewHolder){
            VideoItemViewHolder videoHolder=(VideoItemViewHolder)holder;
            videoHolder.itemView.setTag(position);
            videoHolder.video_author.setText(videoList.get(position).getAuthor());
            videoHolder.video_watchs.setText(videoList.get(position).getWatchs());
            videoHolder.video_commits.setText(videoList.get(position).getCommits());
            videoHolder.mvideo.setUp("http://ips.ifeng.com/video19.ifeng.com/video09/2017/07/25/4710314-102-008-0939.mp4",JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,videoList.get(position).getTitle());
            Glide.with(context).load("http://img07.tooopen.com/images/20170316/tooopen_sy_201956178977.jpg").into(videoHolder.mvideo.thumbImageView);
        }

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public void addData(List<Video> mvideoList){

        if (videoList==null){
            changeData(mvideoList);
        }else {
            videoList.addAll(mvideoList);
            notifyDataSetChanged();
        }
    }

    private void changeData(List<Video> mvideoList) {
        videoList=mvideoList;
        notifyDataSetChanged();
    }


    class VideoItemViewHolder extends RecyclerView.ViewHolder {
        public TextView video_author;
        public TextView video_watchs;
        public JZVideoPlayerStandard mvideo;
        public TextView video_commits;

        public VideoItemViewHolder(View view) {
            super(view);
            mvideo=view.findViewById(R.id.custom_videoplayer_standard);
            video_author=view.findViewById(R.id.video_author);
            video_watchs=view.findViewById(R.id.video_watchs);
            video_commits=view.findViewById(R.id.video_commits);
        }

    }

}


