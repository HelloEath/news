package com.glut.news.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.glut.news.R;

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
        onitemclick.onItemClick(v,(int)v.getTag());
    }


    public    interface OnItemClickListener{
        void onItemClick(View v, int positoin);
    }
    public  interface OnItemLongClickListener{
        void onItenLongClickListener(View v,int position);
    }
    private OnItemClickListener onitemclick=null;
    private OnItemLongClickListener onitemLongclick;
    private List<NewsTest> newsList;

    public void setOnItemClickListener(OnItemClickListener mo){
        this.onitemclick=mo;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener ol){
        this.onitemLongclick=ol;
    }


    public HomeRecyclerAdater(Context context,List<NewsTest> newsList) {
        this.layoutInflater= LayoutInflater.from(context);

        this.m=context;
        this.mTitles=new String[20];
        this.newsList=newsList;

    }

    @Override
    public int getItemViewType(int position) {
        NewsTest newsTest=newsList.get(position);
        if (newsTest==null){
            return  2;
        }else{
            return 1;
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case 2:
                view=layoutInflater.inflate(R.layout.refresh_footer,parent,false);
                viewHolder=new FooterViewHolder(view);

                break;
            case 1:
                view=layoutInflater.inflate(R.layout.item_home_layout,parent,false);
                viewHolder=new ItemViewHolder(view);
                view.setOnClickListener(this);
                break;
        }

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final  RecyclerView.ViewHolder  holder, int position) {
      /*  Date date=new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        try {
            date=dateFormat.parse(newsList.get(position).date);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
       /* Log.d("当前日期",day+"");
        Log.d("发布时间",date.getDate()+"");
        Log.d("当前时间",hours+"");
        Log.d("发布时间",date.getHours()+"");
        if (date.getDate()==day){
            holder.time.setText(( hours-date.getHours())+"小时前");
        }else{
            holder.time.setText(date.getDay()+"");

        }*/
        if (holder instanceof ItemViewHolder){
            holder.itemView.setTag(position);
            ((ItemViewHolder)holder).title.setText(newsList.get(position).getTitle());

            ((ItemViewHolder)holder).author.setText(newsList.get(position).getAuthor());
            ((ItemViewHolder)holder).likes.setText("6评论|8喜欢|");
            Glide.with(m).load(newsList.get(position).getSrc()).into( ((ItemViewHolder)holder).imageView);
        }

    }



    /* 数据绑定*/


    @Override
    public int getItemCount() {
        return newsList.size();
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
    /**
     * 底部加载更多
     */
    class FooterViewHolder extends RecyclerView.ViewHolder{
        private TextView p;
        public FooterViewHolder(View itemView) {
            super(itemView);
            this.p=itemView.findViewById(R.id.refresh);
        }


    }

}
