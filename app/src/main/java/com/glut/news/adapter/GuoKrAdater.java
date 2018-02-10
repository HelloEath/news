package com.glut.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.glut.news.R;
import com.glut.news.entity.GuoKrList;
import com.glut.news.utils.DateUtil;
import com.glut.news.view.CircleImage;

import java.util.List;

/**
 * Created by yy on 2018/2/5.
 */
public class GuoKrAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<GuoKrList.GuokrHandpickNewsResult> guoKrList;
private LayoutInflater l;
    private Context c;
    private OnItemClickListener mOnItemClickListener;
    public GuoKrAdater(Context c,List<GuoKrList.GuokrHandpickNewsResult> guoKrList){
        l=LayoutInflater.from(c);
        this.c=c;
        this.guoKrList=guoKrList;

    }

    public interface OnItemClickListener{
        void itemClick(View v,String id,String image,String image_from);
    }

    @Override
    public void onClick(View v) {
        mOnItemClickListener.itemClick(v,v.getTag(R.string.atricle_url)+"",v.getTag(R.string.image_url)+"",v.getTag(R.string.image_from)+"");
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v=l.inflate(R.layout.item_dicover_guokr,parent,false);
        RecyclerView.ViewHolder viewHolder;
        viewHolder=new GuoKrViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((GuoKrViewHolder)holder).itemView.setOnClickListener(this);
        String time= DateUtil.formatDate_GuoKr(guoKrList.get(position).getDatePublished());
        ((GuoKrViewHolder)holder).itemView.setTag(R.string.atricle_url,guoKrList.get(position).getUrl());
        ((GuoKrViewHolder)holder).itemView.setTag(R.string.image_url,guoKrList.get(position).getSmallImage());
        ((GuoKrViewHolder)holder).itemView.setTag(R.string.image_from,guoKrList.get(position).getAuthor().getNickname());

        ((GuoKrViewHolder)holder).zhihi_title.setText(guoKrList.get(position).getTitle());
        ((GuoKrViewHolder)holder).author_name.setText(guoKrList.get(position).getAuthor().getNickname());
        ((GuoKrViewHolder)holder).time.setText(time);
        ((GuoKrViewHolder)holder).type.setText(guoKrList.get(position).getSubject().getName());
        Glide.with(c).load(guoKrList.get(position).getSmallImage()).into(((GuoKrViewHolder)holder).zhihu_img);
        Glide.with(c).load(guoKrList.get(position).getAuthor().getAvatar().getSmall()).transform(new CircleImage(c)).into(((GuoKrViewHolder)holder).author_logo);
    }

    @Override
    public int getItemCount() {
        return guoKrList.size();
    }

    public void changeData(List<GuoKrList.GuokrHandpickNewsResult> guoKrList) {
        this.guoKrList=guoKrList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener o) {
        mOnItemClickListener=o;
    }

    public void addData(List<GuoKrList.GuokrHandpickNewsResult> guoKrList) {
        if (this.guoKrList==null){
            changeData(guoKrList);
        }else {
            this.guoKrList.addAll(guoKrList);
            notifyDataSetChanged();
        }
    }

    private class GuoKrViewHolder extends RecyclerView.ViewHolder {
        public ImageView zhihu_img;
        public TextView zhihi_title;
        private ImageView author_logo;
        private TextView author_name;
        private TextView time;
        private TextView type;
        public GuoKrViewHolder(View v) {
            super(v);
            zhihu_img=v.findViewById(R.id.zhihu_img);
            zhihi_title=v.findViewById(R.id.zhihu_title);
            author_logo=v.findViewById(R.id.author_logo);
            author_name=v.findViewById(R.id.author_name);
            time=v.findViewById(R.id.time);
            type=v.findViewById(R.id.type);
        }
    }
}
