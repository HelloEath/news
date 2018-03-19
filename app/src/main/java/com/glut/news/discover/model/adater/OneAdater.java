package com.glut.news.discover.model.adater;

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
import com.glut.news.entity.OneDataModel;

import java.util.List;

/**
 * Created by yy on 2018/2/6.
 */
public class OneAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private LayoutInflater l;
    private Context c;
    private List<OneDataModel.OneList> oneList;
    private OnItemClickListener o;

    public interface OnItemClickListener{
        void OnItemclick(View v,String id,String author);
    }
    public void setOnItemClickListener(OnItemClickListener o){
        this.o=o;
    }

    public OneAdater(Context c, List<OneDataModel.OneList> oneList){
        l= LayoutInflater.from(c);
        this.c=c;
        this.oneList=oneList;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=null;
        RecyclerView.ViewHolder viewHolderl=null;
        /*if (viewType==2){
            v=l.inflate(R.layout.item_dicover_douban2,parent,false);
            viewHolderl=new DouBanViewHolder2(v);
        }else if (viewType==1){
            v=l.inflate(R.layout.item_dicover_douban3,parent,false);
            viewHolderl=new DouBanViewHolder3(v);
        }else {

            v=l.inflate(R.layout.item_dicover_one,parent,false);
            viewHolderl=new DouBanViewHolder(v);
        }*/
        v=l.inflate(R.layout.item_dicover_one,parent,false);
        viewHolderl=new OneViewHolder(v);




        return viewHolderl;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (oneList.get(position).getCategory().equals("0")){
            ((OneViewHolder)holder).mType.setText("- 摄影 -");

        }else if (oneList.get(position).getCategory().equals("1")){
            ((OneViewHolder)holder).mType.setText("- 阅读 -");
        }else if (oneList.get(position).getCategory().equals("2")){

            ((OneViewHolder)holder).mType.setText("- 连载 -");
        }else if(oneList.get(position).getCategory().equals("3")){
            ((OneViewHolder)holder).mType.setText("- 问答 -");

        }else if (oneList.get(position).getCategory().equals("4")) {

            ((OneViewHolder) holder).mType.setText("- 音乐 -");
        }

        holder.itemView.setTag(R.string.content,oneList.get(position).getItem_id());
        holder.itemView.setTag(R.string.author,"文/"+oneList.get(position).getAuthor().getUser_name());
        Glide.with(c).load(oneList.get(position).getImg_url()).into(((OneViewHolder)holder).mImageview);
        ((OneViewHolder)holder).mTitle.setText(oneList.get(position).getTitle());
        ((OneViewHolder)holder).mAuthor_name.setText(oneList.get(position).getAuthor().getUser_name());
        Glide.with(c).load(oneList.get(position).getAuthor().getWeb_url()).apply(
                RequestOptions.circleCropTransform()).into(((OneViewHolder)holder).mAuthor_logo);
        ((OneViewHolder)holder).mDesc.setText(oneList.get(position).getForward().substring(0,oneList.get(position).getForward().length()-1)+"...");
        ((OneViewHolder)holder).mTime.setText(oneList.get(position).getPost_date().substring(5,10));
        Glide.with(c).load(R.drawable.like).into(((OneViewHolder)holder).mLike_logo);
        ((OneViewHolder)holder).mLike_num.setText(oneList.get(position).getLike_count());

        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return oneList.size();
    }

    public void changeData(List<OneDataModel.OneList> posts) {
        oneList=posts;
        notifyDataSetChanged();
    }

    public void addData(List<OneDataModel.OneList> postses){
        if (oneList==null){
            changeData(postses);
        }else{
            oneList.addAll(postses);
            notifyDataSetChanged();
        }
    }
    @Override
    public void onClick(View v) {
        o.OnItemclick(v,v.getTag(R.string.content)+"",v.getTag(R.string.author)+"");

    }

    private class OneViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageview;
        private ImageView mAuthor_logo;
        private TextView mAuthor_name;
        private TextView mTime;
        private TextView mTitle;
        private TextView mType;
        private TextView mDesc;
        private ImageView mLike_logo;
        private TextView mLike_num;
        public OneViewHolder(View v) {
            super(v);
            mImageview=v.findViewById(R.id.image);
            mAuthor_logo=v.findViewById(R.id.author_logo);
            mAuthor_name=v.findViewById(R.id.author_name);
            mTime=v.findViewById(R.id.time);
            mTitle=v.findViewById(R.id.title);
            mType=v.findViewById(R.id.type);
            mDesc=v.findViewById(R.id.dec);
            mLike_logo=v.findViewById(R.id.like_logo);
            mLike_num=v.findViewById(R.id.like_num);
        }
    }
  /*  private class DouBanViewHolder2 extends RecyclerView.ViewHolder{
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
    }*/
}
