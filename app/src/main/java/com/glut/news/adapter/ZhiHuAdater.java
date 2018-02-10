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
import com.glut.news.entity.ZhiHuNews;
import com.glut.news.utils.DateUtil;

import java.util.List;

/**
 * Created by yy on 2018/2/1.
 */
public class ZhiHuAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context c;
    private List<ZhiHuNews> zhiHuLists;
    private LayoutInflater  l;
    private OnItemClickListener OnItemClickListener;

    @Override
    public void onClick(View v) {
        this.OnItemClickListener.itemClick(v,v.getTag()+"");
    }

    public void setOnItemClickListener(OnItemClickListener o){
        this.OnItemClickListener=o;
    }
    public interface OnItemClickListener{
        void itemClick(View v,String url);
    }

    public ZhiHuAdater(Context context, List<ZhiHuNews> zhiHuList) {
        this.c=context;
        l=LayoutInflater.from(context);
        this.zhiHuLists=zhiHuList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        }
        String currentDate = zhiHuLists.get(position).getDate();
        int preIndex = position - 1;
        boolean isDifferent = !zhiHuLists.get(preIndex).getDate().equals(currentDate);
        return isDifferent ? 1 : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        RecyclerView.ViewHolder viewHoler = null;
        if (viewType==0){
           v=l.inflate(R.layout.item_dicover_zhihu,parent,false);
            viewHoler=new ZhuHuItemViewHolder(v);
            v.setOnClickListener(this);
        }else{
            v=l.inflate(R.layout.item_dicover_zhuhu_date,parent,false);
            viewHoler=new ZhiHuDateViewholder(v);
        }


        return viewHoler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        holder.itemView.setTag(zhiHuLists.get(position).getId());
        if (holder instanceof ZhuHuItemViewHolder ){
          ZhuHuItemViewHolder  ZhuHuItemViewHolder=(ZhuHuItemViewHolder)holder;
            Glide.with(c).load(zhiHuLists.get(position).getImages().get(0))
                    .into(ZhuHuItemViewHolder.zhihu_img);
            ZhuHuItemViewHolder.zhihi_title.setText(zhiHuLists.get(position).getTitle());

        }else{
            String dateFormat = null;
            dateFormat = DateUtil.formatDate(zhiHuLists.get(position).getDate());
            ((ZhiHuDateViewholder)holder).date.setText(dateFormat);
        }

    }
    public void addData(List<ZhiHuNews> z){
        if (zhiHuLists==null){
            changeData(z);
        }else{
            zhiHuLists.addAll(z);
            notifyDataSetChanged();
        }
    }

    public void changeData(List<ZhiHuNews> z){
        zhiHuLists=z;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
         return zhiHuLists == null ? 0 : zhiHuLists.size();
    }

    private class ZhuHuItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView zhihu_img;
        public TextView zhihi_title;
        public ZhuHuItemViewHolder(View v) {
            super(v);
            zhihu_img=v.findViewById(R.id.zhihu_img);
            zhihi_title=v.findViewById(R.id.zhihu_title);
        }
    }
    class ZhiHuDateViewholder extends RecyclerView.ViewHolder{
public TextView date;
        public ZhiHuDateViewholder(View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.up_date);
        }
    }

}
