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
import com.glut.news.discover.model.entity.OneCommentsModel;

import java.util.List;

/**
 * Created by yy on 2018/2/9.
 */
public class OneDetailAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater l;
    private List<OneCommentsModel.CommentsDataList> commentsList;
    private Context c;

    private View VIEW_FOOTER;
    private View VIEW_HEADER;

    public OneDetailAdater(Context context, List<OneCommentsModel.CommentsDataList> commentsList) {
        l = LayoutInflater.from(context);
        this.commentsList = commentsList;
        c = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = null;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 0) {
            v = l.inflate(R.layout.item_dicover_onedetail_header, parent, false);
            viewHolder = new OneCommentHeaderViewHolder(v);
        } else if (viewType == 2) {
            v = l.inflate(R.layout.item_dicover_onedetail, parent, false);
            viewHolder = new OneDetailViewHolder(v);

        } else {

            v = l.inflate(R.layout.item_dicover_onedetail_footer, parent, false);
            viewHolder = new OneCommentFooterViewHolder(v);
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return 0;
        } else if (position ==getItemCount() - 1) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (!isHeaderView(position) && !isFooterView(position)) {
            if (haveHeaderView()) position--;
            Glide.with(c).load(commentsList.get(position).getUser().getWeb_url()).apply(
                    RequestOptions.circleCropTransform()).into(((OneDetailViewHolder) holder).mAuthor_logo);
            ((OneDetailViewHolder) holder).mAuthor_name.setText(commentsList.get(position).getUser().getUser_name());


            Glide.with(c).load(R.drawable.btn_dianzan).into(((OneDetailViewHolder) holder).mLike_logo);
            ((OneDetailViewHolder) holder).mLike_num.setText(commentsList.get(position).getPraisenum());
            ((OneDetailViewHolder) holder).mContent.setText(commentsList.get(position ).getContent());
            ((OneDetailViewHolder) holder).mTime.setText(commentsList.get(position).getInput_date());

        }


    }

    @Override
    public int getItemCount() {
        int count= commentsList.size() == 0 ? 0 : commentsList.size() ;
        if (VIEW_FOOTER != null) {
            count++;
        }

        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
    }




    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            notifyItemInserted(0);
        }
    }

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            notifyItemInserted(getItemCount() - 1);

        }
    }

    private boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }


    public void changeData(List<OneCommentsModel.CommentsDataList> data) {
        commentsList = data;
        notifyDataSetChanged();
    }

    private class OneDetailViewHolder extends RecyclerView.ViewHolder {
        private ImageView mAuthor_logo;
        private TextView mAuthor_name;
        private ImageView mLike_logo;
        private TextView mLike_num;
        private TextView mContent;
        private TextView mTime;

        public OneDetailViewHolder(View v) {
            super(v);
            mAuthor_logo = v.findViewById(R.id.logo);
            mAuthor_name = v.findViewById(R.id.name);
            mLike_logo = v.findViewById(R.id.like_logo);
            mLike_num = v.findViewById(R.id.like_num);
            mContent = v.findViewById(R.id.content);
            mTime = v.findViewById(R.id.time);
        }
    }

    private class OneCommentHeaderViewHolder extends RecyclerView.ViewHolder {


        public OneCommentHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class OneCommentFooterViewHolder extends RecyclerView.ViewHolder {


        public OneCommentFooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}

