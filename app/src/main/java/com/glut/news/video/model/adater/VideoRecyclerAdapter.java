package com.glut.news.video.model.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.glut.news.R;
import com.glut.news.video.model.entity.VideoModel;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;


/**
 * Created by yy on 2018/1/28.
 */


public class VideoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<VideoModel.VideoList> videoList;
    private LayoutInflater layoutInflater;
    private Context context;
    private OnItemListener onItemListener;

    @Override
    public void onClick(View v) {
        this.onItemListener.onItemClick(v,(String)v.getTag(),(String) v.getTag(R.string.douban_url),(String) v.getTag(R.string.load_fail));
    }



    public interface OnItemListener{
        void onItemClick(View v,String id,String abstracts,String player);
    }

    public void setOnItemListener(OnItemListener o){
        this.onItemListener=o;
    }
    public  VideoRecyclerAdapter(Context c,List<VideoModel.VideoList> videoList){
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
            videoHolder.itemView.setTag(R.string.douban_url, videoList.get(position).getVideo_Player());
            videoHolder.itemView.setTag(R.string.load_fail, videoList.get(position).getVideo_Abstract());
            videoHolder.itemView.setTag(videoList.get(position).getVideo_Id());
            videoHolder.video_author.setText(videoList.get(position).getVideo_Author_Name());

            videoHolder.video_watchs.setText("播放量"+videoList.get(position).getVideo_Players());
           // videoHolder.video_watchs.setText(videoList.get(position).getVideo_Players());
            videoHolder.video_commits.setText("评论量"+videoList.get(position).getVideo_Comments());
            videoHolder.mvideo.setUp(videoList.get(position).getVideo_Player(),JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,videoList.get(position).getVideo_Title());
            Glide.with(context).load(videoList.get(position).getVideo_Image()).into(videoHolder.mvideo.thumbImageView);
            Glide.with(context).load(videoList.get(position).getVideo_Author_Logo()).apply(
                    RequestOptions.circleCropTransform()).into(videoHolder.video_author_logo);
        }

    }

    @Override
    public int getItemCount() {
       return videoList.size();
    }

    public void addData(List<VideoModel.VideoList> mvideoList){

        if (videoList==null){
            changeData(mvideoList);
        }else {
            videoList.addAll(mvideoList);
            notifyDataSetChanged();
        }
    }

    public void changeData(List<VideoModel.VideoList> mvideoList) {
        videoList=mvideoList;
        notifyDataSetChanged();
    }


    class VideoItemViewHolder extends RecyclerView.ViewHolder {
        public TextView video_author;
        public TextView video_watchs;
        public JZVideoPlayerStandard mvideo;
        public TextView video_commits;

        public ImageView video_author_logo;
        public VideoItemViewHolder(View view) {
            super(view);
            mvideo=view.findViewById(R.id.custom_videoplayer_standard);
            video_author=view.findViewById(R.id.video_author_name);
            video_watchs=view.findViewById(R.id.video_watchs);
            video_commits=view.findViewById(R.id.video_commits);
            video_author_logo=view.findViewById(R.id.video_author_logo);
        }

    }

}


