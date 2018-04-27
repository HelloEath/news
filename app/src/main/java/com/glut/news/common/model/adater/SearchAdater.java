package com.glut.news.common.model.adater;

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
import com.glut.news.home.model.entity.ArticleModel;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by yy on 2018/4/11.
 */

public class SearchAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
   private Context mContext;
   private LayoutInflater mLayoutInflater;
   private List<ArticleModel.ArticleList> mArticleModelList;


    private OnItemClickListener onitemclick=null;

    @Override
    public void onClick(View v) {
        onitemclick.onItemClick(v,(int)v.getTag(),(String)v.getTag(R.string.content_type),(String)v.getTag(R.string.search_type));

    }
    public void setOnItemClickListener(OnItemClickListener mo){
        this.onitemclick=mo;
    }
    public    interface OnItemClickListener{
        void onItemClick(View v, int articleId,String contentType,String searchType);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=mLayoutInflater.inflate(R.layout.item_home_main,viewGroup,false);
        RecyclerView.ViewHolder   viewHolder=new ItemViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(mArticleModelList.get(position).getArticle_Id());
        viewHolder.itemView.setTag(R.string.content_type,mArticleModelList.get(position).getArticle_Type());

        if (mArticleModelList.get(position).getArticle_Content()!=null){//是图文
            Glide.with(mContext).load(mArticleModelList.get(position).getArticle_Image()).into( ((ItemViewHolder)viewHolder).imageView);
            ((ItemViewHolder)viewHolder).imageView.setVisibility(View.VISIBLE);
            viewHolder.itemView.setTag(R.string.search_type,"1");

        }else {//是视频
            ((ItemViewHolder)viewHolder).mJZVideoPlayerStandard.setUp(mArticleModelList.get(position).getArticle_Url(),JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"");
            ((ItemViewHolder)viewHolder).mJZVideoPlayerStandard.setVisibility(View.VISIBLE);
            ((ItemViewHolder)viewHolder).imageView.setVisibility(View.GONE);
            Glide.with(mContext).load(mArticleModelList.get(position).getArticle_Image()).into( ((ItemViewHolder)viewHolder).mJZVideoPlayerStandard.thumbImageView);
            viewHolder.itemView.setTag(R.string.search_type,"0");

        }
        ((ItemViewHolder)viewHolder).title.setText(mArticleModelList.get(position).getArticle_Title());

        ((ItemViewHolder)viewHolder).author.setText(mArticleModelList.get(position).getArticle_Author_name());
        ((ItemViewHolder)viewHolder).time.setText(mArticleModelList.get(position).getArticle_Time());
        ((ItemViewHolder)viewHolder).itemView.setOnClickListener(this);
        //((ItemViewHolder)viewHolder).likes.setText(mArticleModelList.get(position).getArtilce_Comments()+"评论|"+mArticleModelList.get(position).getArticle_Likes()+"喜欢|");
    }

    @Override
    public int getItemCount() {
        return mArticleModelList.size();
    }

    public SearchAdater(Context context, List<ArticleModel.ArticleList> searchData) {
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
        mArticleModelList=searchData;

    }

    public void changeDta(List<ArticleModel.ArticleList> data) {
        mArticleModelList=data;
        notifyDataSetChanged();
    }

    public void addData(List<ArticleModel.ArticleList> data) {
        if (mArticleModelList==null){

            changeDta(data);

        }else {
           mArticleModelList.addAll(data);
            notifyDataSetChanged();
        }
    }
    public  class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;
        public TextView time;
        public TextView commits;
        public TextView author;
        public TextView likes;
        private JZVideoPlayerStandard mJZVideoPlayerStandard;
        public ItemViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            imageView=itemView.findViewById(R.id.image);
            time=itemView.findViewById(R.id.time);
            likes=itemView.findViewById(R.id.likes);
            author=itemView.findViewById(R.id.author);
            mJZVideoPlayerStandard=itemView.findViewById(R.id.custom_videoplayer_standard);

        }
    }
}
