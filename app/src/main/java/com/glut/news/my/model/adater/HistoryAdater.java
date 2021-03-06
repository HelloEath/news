package com.glut.news.my.model.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.glut.news.R;
import com.glut.news.my.model.entity.CommonData;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**User_Stars
 * Created by yy on 2018/2/12.
 */
public class HistoryAdater<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private LayoutInflater l;
    private Context c;
    private List<CommonData> historyList;
    private onItemclick onItemclick;
    public HistoryAdater(Context context, List<CommonData> history) {
        l=LayoutInflater.from(context);
        c=context;
        historyList=history;
    }

    @Override
    public void onClick(View v) {
        onItemclick.onItemClick( (String) v.getTag(R.string.h_id),(String)v.getTag(R.string.h_type),(String) v.getTag(R.string.h_type2));
    }

    public void OnItemClickListener(onItemclick o){
        onItemclick=o;
    }

    public interface onItemclick{
        void onItemClick(String id,String type,String contentType);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v=null;
        RecyclerView.ViewHolder viewHolder=null;
        v=l.inflate(R.layout.item_setting_history,parent,false);

        viewHolder=new HistoryViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (historyList.get(position).getArticle_Id()!=0){

            ((HistoryViewHolder)holder).title.setText(historyList.get(position).getArticle_Title());
            Glide.with(c).load(historyList.get(position).getArticle_Image()).into((((HistoryViewHolder) holder).imageView));
            ((HistoryViewHolder)holder).date.setText(historyList.get(position).getArticle_Time());
            ((HistoryViewHolder)holder).author.setText(historyList.get(position).getArticle_Author_name());
            ((HistoryViewHolder)holder).itemView.setTag(R.string.h_id,historyList.get(position).getArticle_Id()+"");
            ((HistoryViewHolder)holder).itemView.setTag(R.string.h_type,"a");
            ((HistoryViewHolder)holder).itemView.setTag(R.string.h_type2,historyList.get(position).getArticle_Type());


        }else {



        ((HistoryViewHolder)holder).title.setText(historyList.get(position).getVideo_Title());
        Glide.with(c).load(historyList.get(position).getVideo_Image()).into((((HistoryViewHolder) holder).imageView));
        ((HistoryViewHolder)holder).date.setText(historyList.get(position).getVideo_PutTime());
        ((HistoryViewHolder)holder).author.setText(historyList.get(position).getVideo_Author_Name());
            ((HistoryViewHolder)holder).mJZVideoPlayer.setUp(historyList.get(position).getVideo_Player(),JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"");
            ((HistoryViewHolder)holder).mJZVideoPlayer.setVisibility(View.VISIBLE);
            ((HistoryViewHolder)holder).imageView.setVisibility(View.GONE);
            Glide.with(c).load(historyList.get(position).getVideo_Image()).into( ((HistoryViewHolder)holder).mJZVideoPlayer.thumbImageView);
            ((HistoryViewHolder)holder).itemView.setTag(R.string.h_id,historyList.get(position).getVideo_Id()+"");
            ((HistoryViewHolder)holder).itemView.setTag(R.string.h_type,"v");
            ((HistoryViewHolder)holder).itemView.setTag(R.string.h_type2,historyList.get(position).getArticle_Type());
        }
        ((HistoryViewHolder)holder).itemView.setOnClickListener(this);
    }

    public void addData(List<CommonData> historyList){
        if (this.historyList==null){
            changeDta(historyList);
        }else{
            this.historyList.addAll(historyList);
            notifyDataSetChanged();
        }

    }

    public void changeDta(List<CommonData> historyList) {
        this.historyList=historyList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView date;
        private TextView author;
        private JZVideoPlayerStandard mJZVideoPlayer;
        public HistoryViewHolder(View v) {
            super(v);
            imageView=v.findViewById(R.id.h_logo);
            title=v.findViewById(R.id.h_title);
            date=v.findViewById(R.id.h_date);
            author=v.findViewById(R.id.h_author);
            mJZVideoPlayer=v.findViewById(R.id.custom_videoplayer_standard);
        }
    }
}
