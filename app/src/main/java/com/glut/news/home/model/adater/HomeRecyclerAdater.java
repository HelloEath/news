package com.glut.news.home.model.adater;

import android.content.Context;
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

/**
 * Created by yy on 2018/1/24.
 */

public class HomeRecyclerAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {



    private Context m;
    private LayoutInflater layoutInflater;
    private String[] mTitles=null;

    @Override
    public void onClick(View v) {
        onitemclick.onItemClick(v,(int)v.getTag(),(String)v.getTag(R.string.content_type));
    }


    public    interface OnItemClickListener{
        void onItemClick(View v, int positoin,String contentType);
    }
    public  interface OnItemLongClickListener{
        void onItenLongClickListener(View v,int position,String contentType);
    }
    private OnItemClickListener onitemclick=null;
    private OnItemLongClickListener onitemLongclick;
    private List<ArticleModel.ArticleList> newsList;

    public void setOnItemClickListener(OnItemClickListener mo){
        this.onitemclick=mo;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener ol){
        this.onitemLongclick=ol;
    }


    public HomeRecyclerAdater(Context context,List<ArticleModel.ArticleList> newsList) {
        this.layoutInflater= LayoutInflater.from(context);

        this.m=context;
        this.mTitles=new String[20];
        this.newsList=newsList;

    }



    public void changeData(List<ArticleModel.ArticleList> mnewsList){
        newsList=mnewsList;
        notifyDataSetChanged();
    }

    public void addData(List<ArticleModel.ArticleList> mnewsList){
        if (newsList==null){
            changeData(mnewsList);
        }else {
          newsList.addAll(mnewsList);
            notifyDataSetChanged();
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        view=layoutInflater.inflate(R.layout.item_home_main,parent,false);
        viewHolder=new ItemViewHolder(view);
        view.setOnClickListener(this);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final  RecyclerView.ViewHolder  holder, int position) {

        if (holder instanceof ItemViewHolder){
            holder.itemView.setTag(newsList.get(position).getArticle_Id());
            holder.itemView.setTag(R.string.content_type,newsList.get(position).getArticle_Type());

            ((ItemViewHolder)holder).title.setText(newsList.get(position).getArticle_Title());

            ((ItemViewHolder)holder).author.setText(newsList.get(position).getArticle_Author_name());
            ((ItemViewHolder)holder).time.setText(newsList.get(position).getArticle_Time());
            ((ItemViewHolder)holder).likes.setText(newsList.get(position).getArticle_Looks()+"已阅  "+newsList.get(position).getArticle_Likes()+"喜欢");
            Glide.with(m).load(newsList.get(position).getArticle_Image()).into( ((ItemViewHolder)holder).imageView);
        }

    }



    /* 数据绑定*/


    @Override
    public int getItemCount() {
        if (newsList==null){
            return 0;

        }else {
            return newsList.size();

        }
    }

    public  class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;
        public TextView time;
        public TextView commits;
        public TextView author;
        public TextView likes;
        public ItemViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            imageView=itemView.findViewById(R.id.image);
            time=itemView.findViewById(R.id.time);
            likes=itemView.findViewById(R.id.likes);
            author=itemView.findViewById(R.id.author);

        }
    }


}
