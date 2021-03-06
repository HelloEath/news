package com.glut.news.common.model.adater;

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
import com.glut.news.video.model.entity.VideoCommentListModel;

import java.util.List;

/**
 * Created by yy on 2018/3/4.
 */
public class ArticleCommentAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<VideoCommentListModel> commentListList;
    private LayoutInflater layoutInflater;
    private View noramlView;
    private View FootView;
    private View HeadView;
    public ArticleCommentAdater(Context context, List<VideoCommentListModel> clist) {
        this.context=context;
        layoutInflater=  LayoutInflater.from(context);
        commentListList=clist;
    }



    public  void addSingGonComments(VideoCommentListModel v){
            commentListList.add(v);
        notifyItemInserted(commentListList.size());
        notifyDataSetChanged();

    }

    public  void addComments(List<VideoCommentListModel> v){
        if (commentListList.size()==0){

            changeData(v);
        }else {

            commentListList.addAll(v);
        }

        notifyItemInserted(commentListList.size());
        notifyDataSetChanged();

    }
    public void removeComments(){

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paret, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view=layoutInflater.inflate(R.layout.item_video_detailcomment,paret,false);
        viewHolder=new VideoDetailViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


            Glide.with(context).load(commentListList.get(position).getAuthor_logo()).apply(RequestOptions.circleCropTransform()).into(  ((VideoDetailViewHolder) holder).mAntuorLogo);

            ((VideoDetailViewHolder) holder).mAuthorName.setText(commentListList.get(position).getAuthor_name());
            Glide.with(context).load(R.drawable.btn_dianzan).into( ((VideoDetailViewHolder) holder).mDianZanLogo);
            ((VideoDetailViewHolder) holder).mDianZanSum.setText(commentListList.get(position).getLikes()+"");
            ((VideoDetailViewHolder) holder).mContent.setText(commentListList.get(position).getComment_Content());
            ((VideoDetailViewHolder) holder).mTime.setText(commentListList.get(position).getComment_Time());

    }




    @Override
    public int getItemCount() {

        return commentListList.size();
    }

    public void changeData(List<VideoCommentListModel> data) {
        commentListList=data;
        notifyDataSetChanged();
    }

    private class VideoDetailViewHolder extends RecyclerView.ViewHolder {
        private ImageView mAntuorLogo;
        private TextView mAuthorName;
        private ImageView mDianZanLogo;
        private TextView mDianZanSum;
        private TextView mContent;
        private TextView mTime;

        public VideoDetailViewHolder(View view) {
            super(view);
            mAntuorLogo=view.findViewById(R.id.authorLogo);
            mAuthorName=view.findViewById(R.id.authorName);
            mDianZanLogo=view.findViewById(R.id.dianzanLogo);
            mDianZanSum=view.findViewById(R.id.dianzanSum);
            mContent=view.findViewById(R.id.content);
            mTime=view.findViewById(R.id.commentTime);

        }
    }
}
