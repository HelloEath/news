package com.glut.news.my.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glut.news.R;
import com.glut.news.home.view.activity.ArticleDetailActivity;
import com.glut.news.my.model.adater.HistoryAdater;
import com.glut.news.my.model.entity.CommonData;
import com.glut.news.my.model.entity.HistoryWithStarModel;
import com.glut.news.my.model.entity.Star;
import com.glut.news.my.presenter.impl.StarWithHistoryPresenterImpl;
import com.glut.news.my.view.activity.StarWithHistoryView;
import com.glut.news.video.view.activity.VideoDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2018/2/12.
 */
public class StarFragment extends android.support.v4.app.Fragment implements StarWithHistoryView{
    private RecyclerView r;
    private List<CommonData> historyList=new ArrayList<>();

    private List<Star> starList=new ArrayList<>();
    private HistoryAdater<CommonData> Ada;

    private int NextPage;
    private String title;
    private boolean isloading;


    private StarWithHistoryPresenterImpl starWithHistoryPresenter=new StarWithHistoryPresenterImpl(this) ;

    private boolean IsLastPage;
    public StarFragment(String s) {
        title=s;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_setting_history,container,false);
        initView(v);

        initData();

            starWithHistoryPresenter.loadStarData();

            //starWithHistoryPresenter.loadHistory();


        return v;
    }

    private void initData() {
        r.setAdapter(Ada);
    }



    private void initView(View v) {
        Ada=new HistoryAdater<CommonData>(getContext(),historyList);

        r=v.findViewById(R.id.recyclerview);
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        r.setLayoutManager(lm);
        r.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        //上拉加载更多
        r.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                //获取所有item数目
                int totalItemCount = layoutManager.getItemCount();

                //获取最后一个item位置
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();


                if (!isloading && totalItemCount < (lastVisibleItem + 2)) {
                    isloading=true;
                    if("我的收藏".equals(title)){
                        starWithHistoryPresenter.loadMoreStarData();

                    }else {
                        starWithHistoryPresenter.loadMoreHistory();

                    }



                }

            }
        });

        Ada.OnItemClickListener(new HistoryAdater.onItemclick() {
            @Override
            public void onItemClick(String id, String type,String player) {
                if ("v".equals(type)){

                    Intent intent=new Intent(getActivity(),VideoDetailActivity.class);
                    intent.putExtra("id",intent);
                    intent.putExtra("player",player);
                    startActivity(intent);

                }else {
                    Intent intent=new Intent(getActivity(),ArticleDetailActivity.class);
                    intent.putExtra("id",intent);

                    startActivity(intent);
                }
            }
        });
    }




    @Override
    public void onLoadStarSuccess(HistoryWithStarModel h) {
        Ada.changeDta(h.getData());

    }

    @Override
    public void onLoadSMoretarSuccess(HistoryWithStarModel h) {
        Ada.addData(h.getData());
    }

    @Override
    public void onLoadHistorySuccess(HistoryWithStarModel h) {
        Ada.changeDta(h.getData());
    }

    @Override
    public void onLoadMoreHistorySuccess(HistoryWithStarModel h) {
        Ada.addData(h.getData());
    }

    @Override
    public void onLoadStarFail() {

    }

    @Override
    public void onLoadSMoretarFail() {


    }

}
