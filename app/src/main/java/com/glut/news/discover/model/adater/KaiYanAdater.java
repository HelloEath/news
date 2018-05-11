package com.glut.news.discover.model.adater;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.glut.news.R;
import com.glut.news.discover.model.entity.KaiYanModel;
import com.glut.news.common.view.customview.VideoPlayer;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;


/**
 * Created by yy on 2018/2/7.
 */
public class KaiYanAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private LayoutInflater l;
    private Context c;
    private List<KaiYanModel.KaiYanList> kaiyanList;
    public  OnItemClickListener o=null;


    public interface OnItemClickListener{
        void itemClick(View v,String url);
    }
    public void  setItemClickListener(OnItemClickListener o){
        this.o=o;
    }

    public KaiYanAdater(FragmentActivity activity, List<KaiYanModel.KaiYanList> kaiYanList) {
        this.l=LayoutInflater.from(activity);
        this.c=activity;
        this.kaiyanList=kaiYanList;

    }

    public void changeData(List<KaiYanModel.KaiYanList> stories) {
        this.kaiyanList=stories;
        notifyDataSetChanged();
    }

    public void addData(List<KaiYanModel.KaiYanList> stories) {
        if (this.kaiyanList==null){
            changeData(stories);
        }else{
            this.kaiyanList.addAll(stories);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=null;
        RecyclerView.ViewHolder viewHolder=null;
            v=l.inflate(R.layout.item_dicover_kaiyan,parent,false);
            viewHolder=new ItemViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


if (kaiyanList.get(position).getType().equals("video")){
    Glide.with(c).load(kaiyanList.get(position).getData().getCover().getFeed()).into(((ItemViewHolder)holder).viderplayer.thumbImageView);
    ((ItemViewHolder) holder).viderplayer.setUp(kaiyanList.get(position).getData().getPlayUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,kaiyanList.get(position).getData().getTitle());

    if (kaiyanList.get(position).getData().getAuthor()!=null && kaiyanList.get(position).getData().getAuthor().getIcon()!=null){

        Glide.with(c).load(kaiyanList.get(position).getData().getAuthor().getIcon()).apply(
                RequestOptions.circleCropTransform()).into(  ((ItemViewHolder) holder).author_logo);
        ((ItemViewHolder) holder).author_name.setText(kaiyanList.get(position).getData().getAuthor().getName());
    }

    ((ItemViewHolder) holder).type.setText("#"+kaiyanList.get(position).getData().getCategory()+"#");
    ((ItemViewHolder) holder).dec.setText("#"+kaiyanList.get(position).getData().getDescription()+"#");

}



    }

    @Override
    public int getItemCount() {
        return this.kaiyanList.size();
    }

    @Override
    public void onClick(View v) {
        o.itemClick(v,v.getTag()+"");
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private VideoPlayer viderplayer;
        private ImageView author_logo;
        private TextView author_name;
        private TextView type;
        private TextView dec;
        public ItemViewHolder(View itemView) {
            super(itemView);
            viderplayer= itemView.findViewById(R.id.videoplyer);
            author_logo=itemView.findViewById(R.id.author_logo);
            author_name=itemView.findViewById(R.id.author_name);
            type=itemView.findViewById(R.id.type);
            dec=itemView.findViewById(R.id.dec);
        }
    }
}

