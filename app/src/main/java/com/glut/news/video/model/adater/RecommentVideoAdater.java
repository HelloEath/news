package com.glut.news.video.model.adater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.glut.news.R;
import com.glut.news.video.model.entity.VideoModel;

import java.util.List;

/**
 * Created by yy on 2018/4/11.
 */

public class RecommentVideoAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private List<VideoModel.VideoList> mVideoLists;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    @Override
    public void onClick(View view) {
        mOnItemClickListener.clickItem(view,(String)view.getTag(R.string.VideoId),(String)view.getTag(R.string.content_type));
    }

    public void changeData(List<VideoModel.VideoList> data) {
        mVideoLists=data;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{

        void clickItem(View v,String id,String contentType);
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener=onItemClickListener;
    }
    public RecommentVideoAdater(Context context, List<VideoModel.VideoList> videoLists) {
        mLayoutInflater=LayoutInflater.from(context);
        mVideoLists=videoLists;
        mContext=context;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View view=mLayoutInflater.inflate(R.layout.item_detail_recomment,viewGroup,false);

        RecyclerView.ViewHolder viewHolder=new VideoItemViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        VideoItemViewHolder videoHolder=(VideoItemViewHolder)viewHolder;
        videoHolder.itemView.setTag(R.string.VideoId, mVideoLists.get(position).getVideo_Id());
        videoHolder.itemView.setTag(R.string.content_type, mVideoLists.get(position).getVideo_Type());
        videoHolder.video_title.setText(mVideoLists.get(position).getVideo_Title());
        videoHolder.video_plays.setText(mVideoLists.get(position).getVideo_Players()+"");
        videoHolder.video_commits.setText(mVideoLists.get(position).getVideo_Likes()+"");
        Glide.with(mContext).load(mVideoLists.get(position).getVideo_Image()).into(videoHolder.icon_cover);
        videoHolder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mVideoLists.size();
    }



    class VideoItemViewHolder extends RecyclerView.ViewHolder {
        private TextView video_plays;
        private TextView video_commits;
        private TextView video_title;

        private ImageView icon_plays;
        private ImageView icon_commits;
        private ImageView icon_cover;



        public VideoItemViewHolder(View view) {
            super(view);
            video_title=view.findViewById(R.id.title_recomment);
            video_plays=view.findViewById(R.id.text_plays);
            video_commits=view.findViewById(R.id.text_comments);
            icon_cover=view.findViewById(R.id.image_recomment);

        }

    }
}
