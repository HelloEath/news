package com.glut.news;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by yy on 2018/1/24.
 */
public class Fragment1 extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout refresh;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home_type,container,false);
        initRefresh(v);
        initRecycleVIew(v);
        return v;
    }

    private void initRefresh(View v) {
        refresh=v.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(true);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();
                refresh.setRefreshing(false);
            }
        });
    }

    private void initRecycleVIew(View v) {

        recyclerView=v.findViewById(R.id.recyclerview);
        //获取线性布局管理器
        linearLayoutManager=new LinearLayoutManager(getActivity());
        //设置为垂直方向
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给recyclerview设置布局
        recyclerView.setLayoutManager(linearLayoutManager);
        //创建适配器
        adapter=new HomeRecyclerAdater(getActivity());
        recyclerView.setAdapter(adapter);
    }

    private class HomeRecyclerAdater extends RecyclerView.Adapter<HomeRecyclerAdater.ViewHolder> {




        private LayoutInflater layoutInflater;
        private String[] mTitles=null;

        public HomeRecyclerAdater(Context context) {
            this.layoutInflater= LayoutInflater.from(context);
            this.mTitles=new String[20];
            for (int i=0;i<20;i++){
                int index=i+1;
                mTitles[i]="item"+index;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=layoutInflater.inflate(R.layout.item_home_layout,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);

            return viewHolder;
        }

        /* 数据绑定*/
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.title.setText("标题："+mTitles[position]);
            holder.time.setText("时间:2018-1-23");
            holder.author.setText("作者：张三");
            holder.likes.setText("6评论|8喜欢|");
            Glide.with(getContext()).load(R.drawable.foot1).into(holder.imageView);

        }

        @Override
        public int getItemCount() {
            return mTitles.length;
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public ImageView imageView;
            public TextView time;
            public TextView commits;
            public TextView author;
            public TextView likes;
            public ViewHolder(View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.title);
                imageView=itemView.findViewById(R.id.image);
                time=itemView.findViewById(R.id.time);
                likes=itemView.findViewById(R.id.likes);
                author=itemView.findViewById(R.id.author);

            }
        }
    }
}
