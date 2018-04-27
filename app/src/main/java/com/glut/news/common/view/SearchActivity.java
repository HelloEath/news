package com.glut.news.common.view;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.glut.news.AppApplication;
import com.glut.news.R;
import com.glut.news.common.model.adater.SearchAdater;
import com.glut.news.common.presenter.impl.SearchActivityPresenterImpl;
import com.glut.news.common.view.searchview.ICallBack;
import com.glut.news.common.view.searchview.SearchView;
import com.glut.news.home.model.entity.ArticleModel;
import com.glut.news.home.view.activity.ArticleDetailActivity;
import com.glut.news.video.view.activity.VideoDetailActivity;
import com.mingle.widget.LoadingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2018/3/19.
 */

public class SearchActivity extends AppCompatActivity implements ISearchActivityView {
    private Toolbar toolbar;
    private SearchView mSearchView;
    private RecyclerView recyclerView;
    private TextView textView;
    private List<ArticleModel.ArticleList> searchData;
    private SearchAdater searchAdater;
    private SearchActivityPresenterImpl s = new SearchActivityPresenterImpl(this);

    private SmartRefreshLayout mRefreshLayout;
    private String SearchValue;
    private LoadingView mLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_search);
        initView();
        initData();
        AppApplication.getInstance().addActivity(this);
    }

    private void initData() {
         SearchValue = getIntent().getStringExtra("v");
        s.search(SearchValue,"fp");
    }

    private void initView() {
        mLoadingView=findViewById(R.id.loadView);
        mRefreshLayout=findViewById(R.id.refreshLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mSearchView = (SearchView) findViewById(R.id.search);
        recyclerView = (RecyclerView) findViewById(R.id.search_result);
        textView = (TextView) findViewById(R.id.btn_search);
        LinearLayoutManager l = new LinearLayoutManager(this);
        l.setOrientation(LinearLayoutManager.VERTICAL);
        searchData = new ArrayList<>();
        searchAdater = new SearchAdater(this, searchData);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(searchAdater);

        searchAdater.setOnItemClickListener(new SearchAdater.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int articleId, String contentType, String searchType) {
                if ("1".equals(searchType)) {//点击的是图文
                    Intent i = new Intent(SearchActivity.this, ArticleDetailActivity.class);
                    i.putExtra("id", articleId+"");
                    i.putExtra("ContentType", contentType);
                    startActivity(i);
                } else {//点击的是视频
                    Intent i = new Intent(SearchActivity.this, VideoDetailActivity.class);
                    i.putExtra("id", articleId+"");
                    i.putExtra("ContentType", contentType);

                    startActivity(i);
                }
            }
        });
        mSearchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                SearchValue = string;
                s.search(string,"fp");//执行搜索
                mLoadingView.setVisibility(View.VISIBLE);
                searchAdater.changeDta(new ArrayList<ArticleModel.ArticleList>());

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s.search(SearchValue,"fp");//执行搜索
            }
        });
        setSupportActionBar(toolbar);
        //动态改变Toolbar返回按钮颜色：改为灰色
        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);

        upArrow.setColorFilter(getResources().getColor(R.color.tab_color3), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        ActionBar a = getSupportActionBar();
        if (a != null) {
            a.setDisplayShowTitleEnabled(false);
            a.setDisplayHomeAsUpEnabled(true);
        }
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                s.search(SearchValue,"null");//执行搜索

            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                s.search(SearchValue,"fp");//执行搜索

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

        }
        return false;
    }

    @Override
    public void onSearchSuccess(ArticleModel h) {
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.finishRefresh();
        searchAdater.changeDta(h.getData());
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void onMoreSearchSuccess(ArticleModel h) {
        mRefreshLayout.finishLoadMore(true);
        searchAdater.addData(h.getData());
    }

    @Override
    public void onSearchFail() {
        mRefreshLayout.finishRefresh();
        Toast.makeText(this, "搜索失败", Toast.LENGTH_SHORT).show();
        mLoadingView.setVisibility(View.GONE);

    }

    @Override
    public void noMoreData() {
        mRefreshLayout.setNoMoreData(true);
        mRefreshLayout.setEnableLoadMore(false);
    }
}
