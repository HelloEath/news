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
import com.glut.news.entity.DouBanList;
import com.glut.news.view.CircleImage;

import java.util.List;

import static com.glut.news.R.string.image_from;

/**
 * Created by yy on 2018/2/6.
 */
public class OneAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private LayoutInflater l;
    private Context c;
    private List<DouBanList.DoubanMomentNewsPosts> douBanList;
    private OnItemClickListener o;

    public interface OnItemClickListener{
        void OnItemclick(View v,String url,String image,String image_from);
    }
    public void setOnItemClickListener(OnItemClickListener o){
        this.o=o;
    }

    public OneAdater(Context c, List<DouBanList.DoubanMomentNewsPosts> douBanList){
        l= LayoutInflater.from(c);
        this.c=c;
        this.douBanList=douBanList;

    }

    @Override
    public int getItemViewType(int position) {
        if (douBanList.get(position).getThumbs().size()==0){

            return 1;
        }else{
            if (douBanList.get(position).getThumbs().get(0).getLarge().getHeight()>douBanList.get(position).getThumbs().get(0).getLarge().getWidth()){
                return 2;
            }else {
                return 3;
            }

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=null;
        RecyclerView.ViewHolder viewHolderl=null;
        if (viewType==2){
            v=l.inflate(R.layout.item_dicover_douban2,parent,false);
            viewHolderl=new DouBanViewHolder2(v);
        }else if (viewType==1){
            v=l.inflate(R.layout.item_dicover_douban3,parent,false);
            viewHolderl=new DouBanViewHolder3(v);
        }else {

            v=l.inflate(R.layout.item_dicover_douban,parent,false);
            viewHolderl=new DouBanViewHolder(v);
        }





        return viewHolderl;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DouBanViewHolder){

            ((DouBanViewHolder)holder).itemView.setTag(R.string.douban_url,douBanList.get(position).getUrl());
            ((DouBanViewHolder)holder).itemView.setTag(image_from,douBanList.get(position).getColumn());
            if (douBanList.get(position).getThumbs().size()==0){

            }else{
                ((DouBanViewHolder)holder).itemView.setTag(R.string.image_url,douBanList.get(position).getThumbs().get(0).getLarge().getUrl());
                Glide.with(c).load(douBanList.get(position).getThumbs().get(0).getLarge().getUrl()).into(((DouBanViewHolder)holder).mImageview);


            }
            ((DouBanViewHolder)holder).mTitle.setText(douBanList.get(position).getTitle());
            if (null==douBanList.get(position).getAuthor()){

            }else{
                Glide.with(c).load(douBanList.get(position).getAuthor().getAvatar()).transform(new CircleImage(c)).into(((DouBanViewHolder)holder).mAuthor_logo);
                ((DouBanViewHolder)holder).mAuthor_name.setText(douBanList.get(position).getAuthor().getName());
            }

            ((DouBanViewHolder)holder).mType.setText("#"+douBanList.get(position).getColumn());
            //((DouBanViewHolder)holder).mTime.setText(douBanList.get(position).getDate());

        }else if (holder instanceof DouBanViewHolder2){
            ((DouBanViewHolder2)holder).itemView.setTag(R.string.douban_url,douBanList.get(position).getUrl());
            ((DouBanViewHolder2)holder).itemView.setTag(image_from,douBanList.get(position).getColumn());
            if (douBanList.get(position).getThumbs().size()==0){

            }else{
                ((DouBanViewHolder2)holder).itemView.setTag(R.string.image_url,douBanList.get(position).getThumbs().get(0).getLarge().getUrl());
                Glide.with(c).load(douBanList.get(position).getThumbs().get(0).getLarge().getUrl()).into(((DouBanViewHolder2)holder).mImageview);


            }
            ((DouBanViewHolder2)holder).mTitle.setText(douBanList.get(position).getTitle());
            if (null==douBanList.get(position).getAuthor()){

            }else{
                Glide.with(c).load(douBanList.get(position).getAuthor().getAvatar()).transform(new CircleImage(c)).into(((DouBanViewHolder2)holder).mAuthor_logo);
                ((DouBanViewHolder2)holder).mAuthor_name.setText(douBanList.get(position).getAuthor().getName());
            }

            ((DouBanViewHolder2)holder).mType.setText("#"+douBanList.get(position).getColumn());
            //((DouBanViewHolder2)holder).mTime.setText(douBanList.get(position).getDate());

        }else {
            ((DouBanViewHolder3)holder).itemView.setTag(R.string.douban_url,douBanList.get(position).getUrl());
            ((DouBanViewHolder3)holder).itemView.setTag(image_from,douBanList.get(position).getColumn());
            if (douBanList.get(position).getThumbs().size()==0){

            }else{
               /* ((DouBanViewHolder3)holder).itemView.setTag(R.string.image_url,douBanList.get(position).getThumbs().get(0).getLarge().getUrl());
                Glide.with(c).load(douBanList.get(position).getThumbs().get(0).getLarge().getUrl()).into(((DouBanViewHolder3)holder).mImageview);
*/

            }
            ((DouBanViewHolder3)holder).mTitle.setText(douBanList.get(position).getTitle());
            if (null==douBanList.get(position).getAuthor()){

            }else{
                Glide.with(c).load(douBanList.get(position).getAuthor().getAvatar()).transform(new CircleImage(c)).into(((DouBanViewHolder3)holder).mAuthor_logo);
                ((DouBanViewHolder3)holder).mAuthor_name.setText(douBanList.get(position).getAuthor().getName());
            }

            ((DouBanViewHolder3)holder).mType.setText("#"+douBanList.get(position).getColumn());
            //((DouBanViewHolder3)holder).mTime.setText(douBanList.get(position).getDate());

        }

        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return douBanList.size();
    }

    public void changeData(List<DouBanList.DoubanMomentNewsPosts> posts) {
        douBanList=posts;
        notifyDataSetChanged();
    }

    public void addData(List<DouBanList.DoubanMomentNewsPosts> postses){
        if (douBanList==null){
            changeData(postses);
        }else{
            douBanList.addAll(postses);
            notifyDataSetChanged();
        }
    }
    @Override
    public void onClick(View v) {
        o.OnItemclick(v,v.getTag(R.string.douban_url)+"",v.getTag(R.string.image_url)+"",v.getTag(image_from)+"");

    }

    private class DouBanViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageview;
        private ImageView mAuthor_logo;
        private TextView mAuthor_name;
        private TextView mTime;
        private TextView mTitle;
        private TextView mType;
        public DouBanViewHolder(View v) {
            super(v);
            mImageview=v.findViewById(R.id.image);
            mAuthor_logo=v.findViewById(R.id.author_logo);
            mAuthor_name=v.findViewById(R.id.author_name);
            mTime=v.findViewById(R.id.time);
            mTitle=v.findViewById(R.id.title);
            mType=v.findViewById(R.id.type);
        }
    }
    private class DouBanViewHolder2 extends RecyclerView.ViewHolder{
        private ImageView mImageview;
        private ImageView mAuthor_logo;
        private TextView mAuthor_name;
        private TextView mTime;
        private TextView mTitle;
        private TextView mType;
        public DouBanViewHolder2(View v) {
            super(v);
            mImageview=v.findViewById(R.id.image);
            mAuthor_logo=v.findViewById(R.id.author_logo);
            mAuthor_name=v.findViewById(R.id.author_name);
            mTime=v.findViewById(R.id.time);
            mTitle=v.findViewById(R.id.title);
            mType=v.findViewById(R.id.type);
        }
    }

    private class DouBanViewHolder3 extends RecyclerView.ViewHolder {
        private ImageView mImageview;
        private ImageView mAuthor_logo;
        private TextView mAuthor_name;
        private TextView mTime;
        private TextView mTitle;
        private TextView mType;
        public DouBanViewHolder3(View v) {
            super(v);
            mImageview=v.findViewById(R.id.image);
            mAuthor_logo=v.findViewById(R.id.author_logo);
            mAuthor_name=v.findViewById(R.id.author_name);
            mTime=v.findViewById(R.id.time);
            mTitle=v.findViewById(R.id.title);
            mType=v.findViewById(R.id.type);
        }
    }
}
